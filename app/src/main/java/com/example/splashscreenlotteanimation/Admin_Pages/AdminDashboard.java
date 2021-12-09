package com.example.splashscreenlotteanimation.Admin_Pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.LoginActivity;
import com.example.splashscreenlotteanimation.R;
import com.example.splashscreenlotteanimation.UpdateEmployee;
import com.example.splashscreenlotteanimation.UserDirectory_Profile.UserList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Objects;

public class AdminDashboard extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    Button create, remove, edit, view;
    TextView greeting, user;
    Button change_password;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
        greeting = findViewById(R.id.greeting_message);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour < 12) {
            greeting.setText("Good Morning,");
        }
        else if (hour < 16) {
            greeting.setText("Good Afternoon,");
        }
        else {
            greeting.setText("Good Evening,");
        }

        user = findViewById(R.id.admin);
        user.setText("ADMINISTRATOR");

        create = findViewById(R.id.add_employee_button);
        create.setOnClickListener(v -> startActivity(new Intent(AdminDashboard.this, AddEmployee.class)));

        remove = findViewById(R.id.delete_employee_button);
        remove.setOnClickListener(v -> startActivity(new Intent(AdminDashboard.this, DeleteEmployee.class)));

        edit = findViewById(R.id.update_employee_button);
        edit.setOnClickListener(v -> startActivity(new Intent(AdminDashboard.this, UpdateEmployee.class)));

        view = findViewById(R.id.view_directory_button);
        view.setOnClickListener(v ->
        {startActivity(new Intent(AdminDashboard.this, UserList.class));
        });

        change_password = findViewById(R.id.password_change);
        change_password.setOnClickListener(v -> {
            //Not implemented
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Reset Password");
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setMinimumHeight(100);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
             EditText old_password = new EditText(this);
             EditText new_password = new EditText(this);
            // write the email using which you registered
            old_password.setHint("Old Password");
            new_password.setHint("New Password");

            linearLayout.addView(old_password);
            linearLayout.addView(new_password);
            linearLayout.setPadding(10,5,5,10);
            builder.setView(linearLayout);

            // Click on Recover and a email will be sent to your registered email id
            builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String old_password_str = old_password.getText().toString().trim();
                    String new_password_str = new_password.getText().toString().trim();

                    if(old_password.getText().toString().isEmpty() || new_password.getText().toString().isEmpty())
                    {
                        Toast.makeText(AdminDashboard.this, "Please fill all of the fields", Toast.LENGTH_SHORT).show();

                    }
                    else {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(Objects.requireNonNull(user.getEmail()), old_password_str);

// Prompt the user to re-provide their sign-in credentials
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.updatePassword(new_password_str).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(AdminDashboard.this, "Password updated", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(AdminDashboard.this, "Error password not updated", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(AdminDashboard.this, "Error password authentication failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                });
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            //////

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signOutMenu) {
            firebaseAuth = FirebaseAuth.getInstance();
            if (firebaseAuth.getCurrentUser() != null) {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                finish();
                Intent intent = new Intent(AdminDashboard.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Toast.makeText(AdminDashboard.this, "Some issue observed. Please try later", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("Really Exit?").setMessage("You'll be logged out. Are you sure you want to exit?").setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getApplicationContext(),"You have been logged out.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).create().show();
    }

}