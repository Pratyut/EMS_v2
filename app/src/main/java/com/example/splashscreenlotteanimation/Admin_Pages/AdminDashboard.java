package com.example.splashscreenlotteanimation.Admin_Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.splashscreenlotteanimation.DeleteEmployee;
import com.example.splashscreenlotteanimation.R;
import com.example.splashscreenlotteanimation.UpdateEmployee;
import com.example.splashscreenlotteanimation.ViewDirectory;

import java.util.Calendar;
import java.util.Objects;

public class AdminDashboard extends AppCompatActivity {

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
        view.setOnClickListener(v -> startActivity(new Intent(AdminDashboard.this, ViewDirectory.class)));

        change_password = findViewById(R.id.password_change);
        change_password.setOnClickListener(v -> {
            //Not implemented
        });
    }
}