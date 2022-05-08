package com.tech.tastykingdom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tech.tastykingdom.Models.Delivery;
import com.tech.tastykingdom.Models.OrderDetails;
import com.tech.tastykingdom.Models.TableBoking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageTable extends AppCompatActivity {

    Button button;
    ListView listView;
    List<TableBoking> user;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_table);

        listView = (ListView) findViewById(R.id.listviewtable);
        button = (Button) findViewById(R.id.gototable);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageTable.this, BookTable.class);
                startActivity(intent);
            }
        });

        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Tables");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    TableBoking tableBoking = studentDatasnap.getValue(TableBoking.class);
                    user.add(tableBoking);
                }

                MyAdapter adapter = new MyAdapter(ManageTable.this, R.layout.custom_table_details, (ArrayList<TableBoking>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static class ViewHolder {

        ImageView imageView;
        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        TextView COL5;
        Button button1;
        Button button2;
    }

    class MyAdapter extends ArrayAdapter<TableBoking> {
        LayoutInflater inflater;
        Context myContext;
        List<TableBoking> user;


        public MyAdapter(Context context, int resource, ArrayList<TableBoking> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_table_details, null);

                holder.COL1 = (TextView) view.findViewById(R.id.name);
                holder.COL2 = (TextView) view.findViewById(R.id.contact);
                holder.COL3 = (TextView) view.findViewById(R.id.idno);
                holder.COL4 = (TextView) view.findViewById(R.id.person);
                holder.COL5 = (TextView) view.findViewById(R.id.date);
                holder.button1 = (Button) view.findViewById(R.id.edit);
                holder.button2 = (Button) view.findViewById(R.id.delete);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText(user.get(position).getName());
            holder.COL2.setText(user.get(position).getContact());
            holder.COL3.setText(user.get(position).getIdNo());
            holder.COL4.setText(user.get(position).getNoOfPerson());
            holder.COL5.setText(user.get(position).getDate());
            System.out.println(holder);

            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    final String idd = user.get(position).getId();
                                    FirebaseDatabase.getInstance().getReference("Tables").child(idd).removeValue();
                                    //remove function not written
                                    Toast.makeText(myContext, "Item deleted successfully", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(R.layout.custom_update_table, null);
                    dialogBuilder.setView(view1);

                    final EditText editText1 = (EditText) view1.findViewById(R.id.nameu);
                    final EditText editText2 = (EditText) view1.findViewById(R.id.mbileu);
                    final EditText editText3 = (EditText) view1.findViewById(R.id.nicu);
                    final EditText editText4 = (EditText) view1.findViewById(R.id.personu);
                    final EditText editText5 = (EditText) view1.findViewById(R.id.dateu);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.updatebtn);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tables").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = (String) snapshot.child("id").getValue();
                            String name = (String) snapshot.child("name").getValue();
                            String mobile = (String) snapshot.child("contact").getValue();
                            String nic = (String) snapshot.child("idNo").getValue();
                            String person = (String) snapshot.child("noOfPerson").getValue();
                            String date = (String) snapshot.child("date").getValue();

                            editText1.setText(name);
                            editText2.setText(mobile);
                            editText3.setText(nic);
                            editText4.setText(person);
                            editText5.setText(date);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    buttonupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = editText1.getText().toString();
                            String mobile = editText2.getText().toString();
                            String nic = editText3.getText().toString();
                            String person = editText4.getText().toString();
                            String date = editText5.getText().toString();

                            if (name.equals("")) {
                                editText1.setError("Name is required");
                            }else if (nic.isEmpty()) {
                                editText3.setError("NIC is required");
                            }else if (mobile.isEmpty()) {
                                editText2.setError("Contact Number is required");
                            } else if (mobile.length() > 10) {
                                editText2.setError("Contact Number must have 10 numbers");
                            }else if (person.isEmpty()) {
                                editText4.setError("Number of persons is required");
                            }else if (date.isEmpty()) {
                                editText5.setError("Date is required");
                            } else {

                                HashMap map = new HashMap();
                                map.put("name", name);
                                map.put("contact", mobile);
                                map.put("idNo", nic);
                                map.put("noOfPerson", person);
                                map.put("date", date);
                                reference.updateChildren(map);

                                Toast.makeText(ManageTable.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                                alertDialog.dismiss();
                            }
                        }
                    });
                }
            });

            return view;

        }
    }
}