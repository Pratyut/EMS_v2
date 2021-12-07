package com.example.splashscreenlotteanimation.Manager_Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.splashscreenlotteanimation.R;
import com.example.splashscreenlotteanimation.RiskAssessment;
import com.example.splashscreenlotteanimation.ViewDirectory;
import com.example.splashscreenlotteanimation.ViewProfile;

import java.util.Calendar;
import java.util.Objects;

public class ManagerDashboard extends AppCompatActivity {

    TextView greeting, user;
    Button profile, access_timesheet, approve_leave, notices, directory, risk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Dashboard");
        setContentView(R.layout.activity_manager_dashboard);
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

        user = findViewById(R.id.manager);
        user.setText("MANAGER");
        profile = findViewById(R.id.view_profile_button);
        profile.setOnClickListener(v -> startActivity(new Intent(ManagerDashboard.this, ViewProfile.class)));
        access_timesheet = findViewById(R.id.access_timesheet_button);
        access_timesheet.setOnClickListener(v -> {
            //Not implemented
            Intent intent = new Intent(ManagerDashboard.this, view_timesheet.class);
            intent.putExtra("Employee", "no");
            startActivity(intent);
        });
        approve_leave = findViewById(R.id.approve_leave_application_button);
        approve_leave.setOnClickListener(v -> {
            //Not implemented
            Intent intent = new Intent(ManagerDashboard.this, ViewLeave.class);
            intent.putExtra("Employee", "no");
            startActivity(intent);
        });
        notices = findViewById(R.id.notice_board_button);
        notices.setOnClickListener(v -> {
            //Not implemented
            Intent intent = new Intent(ManagerDashboard.this, publish_notice.class);
            intent.putExtra("Employee", "no");
            startActivity(intent);
        });
        directory = findViewById(R.id.view_directory_button);
        directory.setOnClickListener(v -> {
            //Not implemented
            startActivity(new Intent(ManagerDashboard.this, ViewDirectory.class));
        });
        risk = findViewById(R.id.risk_assessment_button);
        risk.setOnClickListener(v -> {
            //Not implemented
            startActivity(new Intent(ManagerDashboard.this, RiskAssessment.class));
        });
    }
}