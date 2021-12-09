package com.example.splashscreenlotteanimation.Employee_Pages;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.Pojo.Leave;
import com.example.splashscreenlotteanimation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ApplyLeave extends AppCompatActivity {

    Random random=new Random();


    Employee user;
//    User user;
    Button applyLeaveButton;
    ArrayList<Employee> users = new ArrayList<>();;
//    ArrayList<User> users = new ArrayList<>();;
    DatabaseReference database;
   FirebaseAuth mAuth;


    String current_UserEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);

        mAuth=FirebaseAuth.getInstance();
//        if(mAuth.getCurrentUser().getEmail()=null) {
            setCurrent_UserEmail(mAuth.getCurrentUser().getEmail().toString());
            Toast.makeText(ApplyLeave.this, "current_mail_id---"+getCurrent_UserEmail()+"++++"+mAuth.getCurrentUser().getEmail().toString(), Toast.LENGTH_SHORT).show();
//        }


//        user = getIntent().getParcelableExtra("user");
        EditText reasonText= findViewById(R.id.reason);
        EditText subjectText= findViewById(R.id.subject);
        EditText fromDate= (EditText) findViewById(R.id.fromDate);
        EditText toDate= (EditText) findViewById(R.id.toDate);

        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener fromdate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
            private void updateLabel() {
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                fromDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        fromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ApplyLeave.this, fromdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        DatePickerDialog.OnDateSetListener todate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
            private void updateLabel() {
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                toDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        toDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ApplyLeave.this, todate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        applyLeaveButton = findViewById(R.id.applyLeaveButton);
        applyLeaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromdate, todate, reason, subject;
                fromdate = fromDate.getText().toString();
                todate = toDate.getText().toString();
                reason = reasonText.getText().toString();
                subject = subjectText.getText().toString();

                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Employee");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren())
                        {
                            Log.d("Content",dataSnapshot.getValue().toString());
                            Employee temp=dataSnapshot.getValue(Employee.class);
                            Log.d("Content_checking",getCurrent_UserEmail().trim()+"-----"+temp.email.trim());
                            if(getCurrent_UserEmail().trim().equalsIgnoreCase(temp.email.trim()))
                            {
                                Log.d("Content__beforeSET_OBJ",temp.email+"--"+temp.employee_id);

                                setUser(temp);
                                break;
                            }
                        }

                        Log.d("Content__beforeOBJECT",getUser().email+"--"+getUser().employee_id);
                        String leave_number=user.employee_id+"_"+random.nextInt(1400);

                        Leave leave = new Leave(user.employee_id,user.supervisor_id,fromdate, todate, reason, subject,leave_number);
                        database = FirebaseDatabase.getInstance().getReference("Leave");

//                database.child(user.employee_id+).setValue(leave);
                        database.child(leave_number).setValue(leave);
                        fromDate.setText("");
                        toDate.setText("");
                        reasonText.setText("");
                        subjectText.setText("");
                        Toast.makeText(v.getContext(), "Leave Applied Successfully!", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }

    public String getCurrent_UserEmail() {
        return current_UserEmail;
    }

    public void setCurrent_UserEmail(String current_UserEmail) {
        this.current_UserEmail = current_UserEmail;
    }
    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }


}