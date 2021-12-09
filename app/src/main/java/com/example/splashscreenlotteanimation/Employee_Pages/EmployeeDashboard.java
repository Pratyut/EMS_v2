package com.example.splashscreenlotteanimation.Employee_Pages;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Admin_Pages.AdminDashboard;
import com.example.splashscreenlotteanimation.LoginActivity;
import com.example.splashscreenlotteanimation.Pojo.Manager;
import com.example.splashscreenlotteanimation.R;
import com.example.splashscreenlotteanimation.UserDirectory_Profile.UserList;
import com.example.splashscreenlotteanimation.ViewDirectory;
import com.example.splashscreenlotteanimation.ViewProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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

public class EmployeeDashboard extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView greeting,emp;
    FirebaseUser user;
    Button profile, notice_board, timesheet, leave_application,view_leave_button, directory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dashboard);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
        greeting = findViewById(R.id.greeting_message);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour < 12) {
            greeting.setText("Good Morning,");
        } else if (hour < 16) {
            greeting.setText("Good Afternoon,");
        } else {
            greeting.setText("Good Evening,");
        }

        emp = findViewById(R.id.employee);
//        emp.setText("EMPLOYEE");
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Employee");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Manager manager = snapshot1.getValue(Manager.class);
                    if (manager.getEmail().equals(user.getEmail())) {

                        emp.setText(manager.name.toUpperCase(Locale.ROOT));
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });





        profile = findViewById(R.id.view_profile_button);
        profile.setOnClickListener(v -> startActivity(new Intent(EmployeeDashboard.this, ViewProfile.class)));

        notice_board = findViewById(R.id.notice_board_button);
        notice_board.setOnClickListener(v -> {
            //Not implemented
            Intent intent = new Intent(EmployeeDashboard.this, view_notice.class);
            intent.putExtra("Employee", "yes");
            startActivity(intent);
        });

        timesheet = findViewById(R.id.fill_timesheet_button);
        timesheet.setOnClickListener(v -> {
            //Not implemented
            Intent intent = new Intent(EmployeeDashboard.this, fill_timesheet.class);
            intent.putExtra("Employee", "yes");
            startActivity(intent);
        });

        leave_application = findViewById(R.id.leave_application_button);
        leave_application.setOnClickListener(v -> {

            Intent intent = new Intent(EmployeeDashboard.this, ApplyLeave.class);
            intent.putExtra("Employee", "yes");
            startActivity(intent);
        });

        view_leave_button=findViewById(R.id.view_leave_button);
        view_leave_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeDashboard.this, EmployeeViewLeave.class);
                intent.putExtra("Employee", "yes");
                startActivity(intent);

            }
        });

        directory = findViewById(R.id.view_directory_button);
        directory.setOnClickListener(v -> {

//            startActivity(new Intent(EmployeeDashboard.this, ViewDirectory.class));
            startActivity(new Intent(EmployeeDashboard.this, UserList.class));
        });

        ///// change_password action button starts

        ////change password button action
        Button change_password = findViewById(R.id.password_change);
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
                        Toast.makeText(EmployeeDashboard.this, "Please fill all of the fields", Toast.LENGTH_SHORT).show();

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
                                                        Toast.makeText(EmployeeDashboard.this, "Password updated", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(EmployeeDashboard.this, "Error password not updated", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(EmployeeDashboard.this, "Error password authentication failed", Toast.LENGTH_SHORT).show();
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


        ///// change_password action button ends

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
                Intent intent = new Intent(EmployeeDashboard.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Toast.makeText(EmployeeDashboard.this, "Some issue observed. Please try later", Toast.LENGTH_SHORT).show();
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
