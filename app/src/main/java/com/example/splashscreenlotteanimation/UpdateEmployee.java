package com.example.splashscreenlotteanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.Pojo.Manager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UpdateEmployee extends AppCompatActivity {

    final String[] designation = {"Employee", "Manager"};
    TextInputEditText name_text, id_text, email_text, password_text;
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> myAdapter;
    String item, email_address, password, name, id ;
    Button update;
    private Employee emp;
    private Manager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Update Employee Details");

        autoCompleteTxt = findViewById(R.id.autoCompleteTextView);
        myAdapter = new ArrayAdapter<>(UpdateEmployee.this, R.layout.dropdown_item_designation, designation);
        autoCompleteTxt.setAdapter(myAdapter);
        autoCompleteTxt.setOnItemClickListener((parent, view, position, id) -> item = parent.getItemAtPosition(position).toString());
        name_text = findViewById(R.id.name_text);
        name  = Objects.requireNonNull(name_text.getText()).toString();

        id_text = findViewById(R.id.id_text);
        id = Objects.requireNonNull(id_text.getText()).toString();

        email_text = findViewById(R.id.email_text);
        email_address = Objects.requireNonNull(email_text.getText()).toString();

        update = findViewById(R.id.add_employee_button);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference(item).child(id);

            }
        });



    }
}