package com.example.splashscreenlotteanimation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.Pojo.Manager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class ViewProfile extends AppCompatActivity {

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    TextView id, name, gender, email;
    TextInputEditText dob, number, address, phone, name_etxt;
    TextInputLayout dob_;
    Button changePassword, certificate, save_changes;
    private DatePickerDialog picker;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    ImageView profile_image_female, profile_image_male;
    String found = "";
    String pdfURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_view_profile);

        id = findViewById(R.id.id_text);
        name = findViewById(R.id.name_text);
        name_etxt = findViewById(R.id.name_etext);
        gender = findViewById(R.id.gender_text);
        email = findViewById(R.id.email_text);
        dob_ = findViewById(R.id.dob_text);
        dob = findViewById(R.id.dob_etext);
        phone = findViewById(R.id.phone_etext);
        profile_image_female = findViewById(R.id.profile_image_female);
        profile_image_male = findViewById(R.id.profile_image_male);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Role");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email_key = user.getEmail().replace("@", "at").replace(".", "dot");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (email_key.trim().equalsIgnoreCase(postSnapshot.getKey().trim())) {
                        String role = Objects.requireNonNull(postSnapshot.getValue()).toString();
                        if (role.trim().equalsIgnoreCase("E")) {
                            setFound("E");
                        } else if (role.trim().equalsIgnoreCase("M")) {
                            setFound("M");
                        }
                    }
                }

                if (getFound().equalsIgnoreCase("E")) {
                    databaseReference = firebaseDatabase.getReference("Employee");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                Employee employee = snapshot1.getValue(Employee.class);
                                if (employee.getEmail().equals(user.getEmail())) {
                                    name.setText(employee.name.toUpperCase(Locale.ROOT));
                                    name_etxt.setText(employee.name);
                                    id.setText(employee.employee_id);
                                    gender.setText(employee.gender);
                                    email.setText(employee.email);
                                    dob.setText(employee.dob);
                                    address.setText(employee.address);
                                    phone.setText(employee.contact_no);
                                    pdfURL = employee.pdf_url;
                                    if (employee.gender.equals("Male")) {
                                        profile_image_male.setVisibility(View.VISIBLE);
                                        profile_image_female.setVisibility(View.INVISIBLE);
                                    } else {
                                        profile_image_male.setVisibility(View.INVISIBLE);
                                        profile_image_female.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                else if(getFound().equalsIgnoreCase("M")) {
                    databaseReference = firebaseDatabase.getReference("Manager");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                Manager manager = snapshot1.getValue(Manager.class);
                                if (manager.getEmail().equals(user.getEmail())) {
                                    name.setText(manager.name.toUpperCase(Locale.ROOT));
                                    name_etxt.setText(manager.name);
                                    id.setText(manager.employee_id);
                                    gender.setText(manager.gender);
                                    email.setText(manager.email);
                                    dob.setText(manager.dob);
                                    address.setText(manager.address);
                                    phone.setText(manager.contact_no);
                                    pdfURL = manager.pdf_url;
                                    if (gender.equals("Male")) {
                                        profile_image_male.setVisibility(View.VISIBLE);
                                        profile_image_female.setVisibility(View.INVISIBLE);
                                    } else {
                                        profile_image_male.setVisibility(View.INVISIBLE);
                                        profile_image_female.setVisibility(View.VISIBLE);
                                    }
                                    break;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                else {
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        dob_.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                picker = new DatePickerDialog(ViewProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        number = findViewById(R.id.phone_etext);
        address = findViewById(R.id.address_text);

        changePassword = findViewById(R.id.password_change);
        changePassword.setOnClickListener(v -> {
            startActivity(new Intent(ViewProfile.this, ChangePassword.class));
        });

        certificate = findViewById(R.id.certificate);
        certificate.setOnClickListener(v -> {
            Intent intent = new Intent(ViewProfile.this, Certificate.class);
            intent.putExtra("Name", name_etxt.getText().toString());

            startActivity(intent);
        });

        save_changes = findViewById(R.id.update);
        save_changes.setOnClickListener(v -> {
            if (found.equalsIgnoreCase("E")) {
                databaseReference = firebaseDatabase.getReference("Employee");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Employee employee = snapshot1.getValue(Employee.class);
                            if (employee.getEmail().equals(user.getEmail())) {
                                String NAME = name_etxt.getText().toString();
                                String EMAIL = employee.getEmail();
                                String ID = employee.getEmployee_id();
                                String CONTACT_NO = phone.getText().toString();
                                String SID = employee.getSupervisor_id();
                                String ADDRESS = address.getText().toString();
                                String GENDER = employee.getGender();
                                String PDF_URL = employee.getPdf_url();
                                String DOB = dob.getText().toString();
                                Employee newEmployee = new Employee(EMAIL, NAME, CONTACT_NO, ID, SID, ADDRESS, GENDER, PDF_URL, DOB);
                                databaseReference.child(employee.getEmployee_id()).setValue(newEmployee)
                                        .addOnSuccessListener(unused -> {
                                            Toast.makeText(getApplicationContext(), "Details Updated.", Toast.LENGTH_LONG).show();
                                            finish();
                                        });
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            } else if(found.equalsIgnoreCase("M")) {
                databaseReference = firebaseDatabase.getReference("Manager");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Manager manager = snapshot1.getValue(Manager.class);
                            if (manager.getEmail().equals(user.getEmail())) {
                                String NAME = name_etxt.getText().toString();
                                String EMAIL = manager.getEmail();
                                String ID = manager.getEmployee_id();
                                String CONTACT_NO = phone.getText().toString();
                                String ADDRESS = address.getText().toString();
                                String GENDER = manager.getGender();
                                String PDF_URL = manager.getPdf_url();
                                String DOB = dob.getText().toString();
                                Manager newManager = new Manager(EMAIL, NAME, ID, CONTACT_NO, ADDRESS, GENDER, PDF_URL, DOB, "");
                                databaseReference.child(manager.getEmployee_id()).setValue(newManager)
                                        .addOnSuccessListener(unused -> {
                                            Toast.makeText(getApplicationContext(), "Details Updated.", Toast.LENGTH_LONG).show();
                                            finish();
                                        });
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            } else {
            }
        });
    }
}