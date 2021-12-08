package com.example.splashscreenlotteanimation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    public String getDashboard_number() {
        return dashboard_number;
    }

    public void setDashboard_number(String dashboard_number) {
        this.dashboard_number = dashboard_number;
    }

    String dashboard_number="-1";
    private FirebaseAuth mAuth;
    TextInputLayout til_email_address;
    TextInputEditText email_address, password;
    Button forgot_password, sign_in;
    FloatingActionButton send_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);

        til_email_address = findViewById(R.id.til_email_address);
        til_email_address.setEndIconOnClickListener(v -> Objects.requireNonNull(email_address.getText()).clear());

        email_address = findViewById(R.id.tiet_email_address);
        password = findViewById(R.id.tiet_password);

        forgot_password = findViewById(R.id.forgot_password);

        sign_in = findViewById(R.id.login);
        sign_in.setOnClickListener(v -> {

            String email = Objects.requireNonNull(email_address.getText()).toString().trim();
            String pswd = Objects.requireNonNull(password.getText()).toString().trim();
/*
            String dashboard_number;
            if (email.charAt(0) == 'e') {
                dashboard_number = "1";
            } else if(email.charAt(0) == 'a') {
                dashboard_number = "0";
            } else {
                dashboard_number = "2";
            }
*/
            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(pswd)) {
                Toast.makeText(getApplicationContext(), "Please enter your credentials!!", Toast.LENGTH_LONG).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, pswd).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        //// Figure out the Role of Email id.
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Role");
                        databaseReference.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String email_key=email.replace("@","at").replace(".","dot");
                                for(DataSnapshot postsnapShot: snapshot.getChildren()) {
                                    Log.d("Content----", postsnapShot.getKey().toString() + "=====" + postsnapShot.getValue().toString());
                                    if (email_key.trim().equalsIgnoreCase(postsnapShot.getKey().toString().trim())) {
                                        String role = postsnapShot.getValue().toString();
                                        if (role.trim().equalsIgnoreCase("E")) {
                                            setDashboard_number("1");
                                        }
//                                            dashboard_number="1";
                                        if (role.trim().equalsIgnoreCase("M")) {
                                            setDashboard_number("2");
                                        }
                                        if (role.trim().equalsIgnoreCase("A")) {
                                            setDashboard_number("0");
                                        }
                                        break;
                                    }
                                }
                                    if(!getDashboard_number().equalsIgnoreCase("-1")) {
                                        Intent intent = new Intent(LoginActivity.this, UserAuthenticated.class);
                                        intent.putExtra("Dashboard", dashboard_number);
                                        intent.putExtra("Account", email);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else if(getDashboard_number().equalsIgnoreCase("-1")) {
                                        Toast.makeText(LoginActivity.this, "Particular user not found, Please try again", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                    }

//                        Log.d("Content----",postsnapShot.toString()+"--------"+postsnapShot.getValue().toString());
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "Please check your credentials!!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        forgot_password.setOnClickListener(v -> {
            
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("Recover Password");
                    LinearLayout linearLayout = new LinearLayout(this);
                    final EditText email_ = new EditText(this);
                    // write the email using which you registered
                    email_.setText("Email");
                    email_.setMinEms(16);
                    email_.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    linearLayout.addView(email_);
                    linearLayout.setPadding(10,10,10,10);
                    builder.setView(linearLayout);

                    // Click on Recover and a email will be sent to your registered email id
                    builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String email = email_.getText().toString().trim();
                            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(LoginActivity.this,"Mail sent",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this,"Error Occurred",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                });

        send_mail = findViewById(R.id.send_mail);
        send_mail.setOnClickListener(v -> {
            //Implemented
            startActivity(new Intent(LoginActivity.this, SendMail.class));
            finish();
        });
    }
}