package com.example.splashscreenlotteanimation.Manager_Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.splashscreenlotteanimation.Pojo.Leave;
import com.example.splashscreenlotteanimation.R;

public class ManagerSingleViewLeave extends AppCompatActivity {
    TextView employeid, from, to, reason, subject, status, leavenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_single_view_leave);
        String employee_id = getIntent().getStringExtra("employee_id");
        String leave_number = getIntent().getStringExtra("leave_number");
        String fromdate = getIntent().getStringExtra("from");
        String todate = getIntent().getStringExtra("to");
        String subjectd = getIntent().getStringExtra("subject");
        String reasond = getIntent().getStringExtra("reason");
        String statusd = getIntent().getStringExtra("status");
        employeid = findViewById(R.id.msvlEmployeeId);
        from = findViewById(R.id.msvlfrom);
        to = findViewById(R.id.msvlto);
        reason = findViewById(R.id.msvlreason);
        subject = findViewById(R.id.msvlsubject);
        status = findViewById(R.id.msvlstatus);
        leavenumber = findViewById(R.id.msvlLeaveNumber);
        employeid.setText(employee_id);
        from.setText(fromdate);
        leavenumber.setText(leave_number);
        to.setText(todate);
        reason.setText(reasond);
        subject.setText(subjectd);
        status.setText(statusd);
    }
}