package com.example.splashscreenlotteanimation.Employee_Pages;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.EmployeeViewLeaveAdapter;
import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.Pojo.Leave;
import com.example.splashscreenlotteanimation.Pojo.Manager;
import com.example.splashscreenlotteanimation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeeViewLeave extends AppCompatActivity {

    RecyclerView recyclerView;
    EmployeeViewLeaveAdapter employeeViewLeaveAdapter;
    DatabaseReference database;
    ArrayList<Leave> list;
    Employee user;
    //    User user;
    FirebaseAuth mAuth;


    String current_UserEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("View Leaves");
        setContentView(R.layout.activity_view_leave);
        user = getIntent().getParcelableExtra("user");


        mAuth=FirebaseAuth.getInstance();
//        if(mAuth.getCurrentUser().getEmail()=null) {
        setCurrent_UserEmail(mAuth.getCurrentUser().getEmail().toString());
//        Toast.makeText(EmployeeViewLeave.this, "current_mail_id---"+getCurrent_UserEmail()+"++++"+mAuth.getCurrentUser().getEmail().toString(), Toast.LENGTH_SHORT).show();
//        }

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Employee");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Employee temp_emp=dataSnapshot.getValue(Employee.class);
                 if(getCurrent_UserEmail().trim().equalsIgnoreCase(temp_emp.email))
                 {
                     setUser(temp_emp);
                     break;
                 }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        recyclerView = findViewById(R.id.viewLeaveList);
        database = FirebaseDatabase.getInstance().getReference("Leave");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        employeeViewLeaveAdapter = new EmployeeViewLeaveAdapter(this, list);
        recyclerView.setAdapter(employeeViewLeaveAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Leave leave = dataSnapshot.getValue(Leave.class);
                    assert leave != null;
                    Log.d("Content",leave.toString());
                    if (leave.userid.equals(user.employee_id)) {
                        Log.d("Content-----insideIF", leave.toString());
                        list.add(leave);
                    }  //----------- //look for if condition body in manager_view leave segmet as well
                    }
                employeeViewLeaveAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
