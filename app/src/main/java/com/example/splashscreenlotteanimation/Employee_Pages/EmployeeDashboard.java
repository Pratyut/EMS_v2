package com.example.splashscreenlotteanimation.Employee_Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.splashscreenlotteanimation.R;
import com.example.splashscreenlotteanimation.ViewDirectory;
import com.example.splashscreenlotteanimation.ViewProfile;

import java.util.Calendar;
import java.util.Objects;

public class EmployeeDashboard extends AppCompatActivity {

    TextView greeting, user;
    Button profile, notice_board, timesheet, leave_application, directory;

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
        }
        else if (hour < 16) {
            greeting.setText("Good Afternoon,");
        }
        else {
            greeting.setText("Good Evening,");
        }

        user = findViewById(R.id.employee);
        user.setText("EMPLOYEE");

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
            //Not implemented
            Intent intent = new Intent(EmployeeDashboard.this, ApplyLeave.class);
            intent.putExtra("Employee", "yes");
            startActivity(intent);
        });

        directory = findViewById(R.id.view_directory_button);
        directory.setOnClickListener(v -> {
            //Not implemented
            startActivity(new Intent(EmployeeDashboard.this, ViewDirectory.class));
        });


    }
}