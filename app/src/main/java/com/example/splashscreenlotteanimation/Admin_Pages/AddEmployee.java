package com.example.splashscreenlotteanimation.Admin_Pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.Pojo.Manager;
import com.example.splashscreenlotteanimation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

@SuppressWarnings({"unchecked", "rawtypes"})
public class AddEmployee extends AppCompatActivity {

    final String[] designation = {"Employee", "Manager"};
    EditText user_mail_ip,password_ip;

    EditText name_txt, id_txt, email_txt, password_txt,cnf_password_txt,supervisor_id_txt,contact_num_txt;
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> myAdapter;
    String item, email_address, password, name, id,cnf_password,supervisor_id,contact_num;
    Button add;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    int register_success=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Employee");
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        autoCompleteTxt = findViewById(R.id.autoCompleteTextView);
        myAdapter = new ArrayAdapter<>(AddEmployee.this, R.layout.dropdown_item_designation, designation);
        autoCompleteTxt.setAdapter(myAdapter);
        autoCompleteTxt.setOnItemClickListener((parent, view, position, id) -> item = parent.getItemAtPosition(position).toString());

        add = findViewById(R.id.add_employee_button);
        add.setOnClickListener(v -> {
            id_txt = findViewById(R.id.reg_emp_id);
            id = id_txt.getText().toString();

            name_txt = findViewById(R.id.reg_username);
            name = name_txt.getText().toString();

            email_txt = findViewById(R.id.reg_emp_email);
            email_address = email_txt.getText().toString();

            password_txt = findViewById(R.id.reg_password);
            password = password_txt.getText().toString();

            cnf_password_txt = findViewById(R.id.reg_password_again);
            cnf_password = password_txt.getText().toString();

            supervisor_id_txt=findViewById(R.id.reg_SupervisorId);
            supervisor_id=supervisor_id_txt.getText().toString();

            contact_num_txt=findViewById(R.id.reg_phone_num);
            contact_num=contact_num_txt.getText().toString();

            //Add user to authentication database
            if (TextUtils.isEmpty(email_address)) {
                Toast.makeText(AddEmployee.this, "Provide Email first!", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(AddEmployee.this, "Set your password", Toast.LENGTH_SHORT).show();
            }
            else if (email_txt.getText().toString().isEmpty()||
                    id_txt.getText().toString().isEmpty()||
                    name_txt.getText().toString().isEmpty()||
                    password_txt.getText().toString().isEmpty()||
                    cnf_password_txt.getText().toString().isEmpty()||
                    cnf_password_txt.getText().toString().isEmpty()  )
            {
                Toast.makeText(AddEmployee.this, "Make sure no field is empty ", Toast.LENGTH_SHORT).show();
            }
            else if(!cnf_password.trim().equalsIgnoreCase(password.trim()))
            {
                Toast.makeText(AddEmployee.this, "Please check, password instances dont match", Toast.LENGTH_SHORT).show();
            }
            else if (item==null)
            {
                Toast.makeText(AddEmployee.this, "Please select either of designations", Toast.LENGTH_SHORT).show();
            }
            else if (item.equals("Employee") && supervisor_id.isEmpty())
            {
                    Toast.makeText(AddEmployee.this, "An employee must have a supervisor", Toast.LENGTH_SHORT).show();
            }
            else {
                mAuth.createUserWithEmailAndPassword(email_address, password).addOnCompleteListener(AddEmployee.this, (OnCompleteListener) task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(AddEmployee.this.getApplicationContext(), "SignUp unsuccessful: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        setRegister_success(1);
                        //Add employee node to database
                        if (item.equals("Employee")) {

                            databaseReference = firebaseDatabase.getReference("Employee");
                            Employee object = new Employee(email_address,name, contact_num,id , supervisor_id);
                            databaseReference.child(id).setValue(object);
                            Toast.makeText(getApplicationContext(), "Employee Added.", Toast.LENGTH_LONG).show();
                            /*databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
 *//*                                   databaseReference.child(id).setValue(object);
                                    Toast.makeText(getApplicationContext(), "Employee Added.", Toast.LENGTH_LONG).show();
 *//*                               }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getApplicationContext(), "Employee not added.", Toast.LENGTH_LONG).show();
                                }
                            });*/


                        } else {

                            databaseReference = firebaseDatabase.getReference("Manager");
                            Manager object = new Manager(email_address,name, contact_num, id);
                            databaseReference.child(id).setValue(object);
                            Toast.makeText(getApplicationContext(), "Manager Added.", Toast.LENGTH_LONG).show();

                            /*databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
   *//*                                 databaseReference.child(id).setValue(object);
                                    Toast.makeText(getApplicationContext(), "Manager Added.", Toast.LENGTH_LONG).show();
   *//*                             }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getApplicationContext(), "Manager not added.", Toast.LENGTH_LONG).show();
                                }
                            });
*/
                        }


// Add the details to Role node as well
                        String curated_email=email_address.replace("@","at").replace(".","dot");

                        DatabaseReference db=FirebaseDatabase.getInstance().getReference("Role");
                        db.child(curated_email).setValue(String.valueOf(item.charAt(0)));

                        if(getRegister_success()==1)
                        {
                            finish();
                        }
//                        Toast.makeText(AddEmployee.this, "Employee Added..", Toast.LENGTH_SHORT).show();

              /*          db.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //starting a main activity.
//                                startActivity(new Intent(AddEmployee.this, Admin_dashboard.class));

//                                finish();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
*/


                    }
                });
            }
        });
    }


    public int getRegister_success() {
        return register_success;
    }

    public void setRegister_success(int register_success) {
        this.register_success = register_success;
    }

}