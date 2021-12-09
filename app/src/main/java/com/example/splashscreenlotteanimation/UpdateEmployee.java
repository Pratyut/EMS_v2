package com.example.splashscreenlotteanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.Pojo.Manager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UpdateEmployee extends AppCompatActivity {

    final String[] designation = {"Employee", "Manager"};
    TextInputEditText name_text, id_text, email_text, supervisor_id;
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> myAdapter;
    String itemDesignation, email_address, password, name, id ;
    Button update;
    private Employee emp;
    private Manager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Update Employee Details");

        emp = getIntent().getParcelableExtra("user");

        autoCompleteTxt = findViewById(R.id.autoCompleteTextView);
        myAdapter = new ArrayAdapter<>(UpdateEmployee.this, R.layout.dropdown_item_designation, designation);
        autoCompleteTxt.setAdapter(myAdapter);
        if(emp.supervisor_id.equals(""))
        {
            autoCompleteTxt.setEnabled(false);
        }
        autoCompleteTxt.setOnItemClickListener((parent, view, position, id) -> itemDesignation = parent.getItemAtPosition(position).toString());


        name_text = findViewById(R.id.name_text);
        name_text.setText(emp.getName());
        name_text.setEnabled(false);
//        name  = Objects.requireNonNull(name_text.getText()).toString();

        id_text = findViewById(R.id.id_text);
        id_text.setText(emp.getEmployee_id());
        id_text.setEnabled(false);
//        id = Objects.requireNonNull(id_text.getText()).toString();

        email_text = findViewById(R.id.email_text);
        email_text.setText(emp.getEmail());
        email_text.setEnabled(false);
        //        email_address = Objects.requireNonNull(email_text.getText()).toString();


        supervisor_id=findViewById(R.id.supervisor_id);
        String temp=emp.getSupervisor_id();
        String curated_sup_id=temp.substring(temp.indexOf('(')+1,temp.indexOf(')'));
supervisor_id.setText(curated_sup_id);
        /*
        DatabaseReference temp=FirebaseDatabase.getInstance().getReference("Employee");
        Employee temp2=temp.child(emp.employee_id).getClass(Employee.class);
        String supervisor_id=temp2.supervisor_id;
*/


        update = findViewById(R.id.add_employee_button);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.requireNonNull(supervisor_id.getText()).toString().isEmpty() && itemDesignation.isEmpty())
                {
                    Toast.makeText(UpdateEmployee.this, "Make sure all the entries are filled", Toast.LENGTH_SHORT).show();
                }
/*
                else if(itemDesignation.equals("Manager") ){}
*/
                else if(itemDesignation.equals("Employee") && !supervisor_id.getText().toString().isEmpty()) {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    Employee temp = emp;
                    temp.setSupervisor_id(supervisor_id.getText().toString());
                    DatabaseReference databaseReference = firebaseDatabase.getReference(itemDesignation);
                    databaseReference.child(emp.getEmployee_id()).setValue(temp);

                    Toast.makeText(UpdateEmployee.this, "Record has been modified", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if (itemDesignation.equals("Manager"))
                {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    Employee temp = emp;
                    temp.setSupervisor_id("");
                    DatabaseReference databaseReference = firebaseDatabase.getReference(itemDesignation);
                    databaseReference.child(emp.getEmployee_id()).setValue(temp);

                    databaseReference=firebaseDatabase.getReference("Employee");
                    databaseReference.child(emp.getEmployee_id()).removeValue();

                    finish();
                    Toast.makeText(UpdateEmployee.this, "Record has been modified", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}