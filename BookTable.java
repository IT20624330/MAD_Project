package com.tech.tastykingdom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech.tastykingdom.Models.CardDetails;
import com.tech.tastykingdom.Models.TableBoking;

public class BookTable extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    Button buttonAdd;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_table);

        ref = FirebaseDatabase.getInstance().getReference("Tables");

        editText1 = (EditText) findViewById(R.id.name);
        editText2 = (EditText) findViewById(R.id.mbile);
        editText3 = (EditText) findViewById(R.id.nic);
        editText4 = (EditText) findViewById(R.id.person);
        editText5 = (EditText) findViewById(R.id.date);
        buttonAdd = (Button) findViewById(R.id.addbook);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText1.getText().toString();
                String mobile = editText2.getText().toString();
                String nic = editText3.getText().toString();
                String person = editText4.getText().toString();
                String date = editText5.getText().toString();

                if (name.equals("")) {
                    editText1.setError("Name is required");
                } else if (nic.isEmpty()) {
                    editText3.setError("NIC is required");
                } else if (mobile.isEmpty()) {
                    editText2.setError("Contact Number is required");
                } else if (mobile.length() > 10) {
                    editText2.setError("Contact Number must have 10 numbers");
                } else if (person.isEmpty()) {
                    editText4.setError("Number of persons is required");
                } else if (date.isEmpty()) {
                    editText5.setError("Date is required");
                } else {

                    String key = ref.push().getKey();

                    TableBoking tableBoking = new TableBoking(key, name, mobile, nic, person, date);
                    ref.child(key).setValue(tableBoking);

                    Toast.makeText(BookTable.this, "Successfully added", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(BookTable.this, ManageTable.class);
                    startActivity(intent);
                }
            }
        });
    }
}