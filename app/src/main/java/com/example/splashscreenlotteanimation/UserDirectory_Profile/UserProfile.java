package com.example.splashscreenlotteanimation.UserDirectory_Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.R;

public class UserProfile extends AppCompatActivity {

    Employee user;
    TextView relativeName, relativePhoneNumber, relativeUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        user = getIntent().getParcelableExtra("user");
        relativeName = findViewById(R.id.name_profile);
        relativePhoneNumber = findViewById(R.id.phone_profile);
        relativeUserId = findViewById(R.id.userId_profile);
        relativeName.setText(user.name);
        relativePhoneNumber.setText(user.contact_no);
        relativeUserId.setText(user.employee_id);
    }
}