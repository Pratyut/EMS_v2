package com.example.splashscreenlotteanimation.Manager_Pages;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.example.splashscreenlotteanimation.Pojo.Leave;
import com.example.splashscreenlotteanimation.Pojo.Manager;
import com.example.splashscreenlotteanimation.Pojo.Timesheet;
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

public class view_timesheet extends AppCompatActivity {


    // creating a variable for
    // our Firebase Database.
    RecyclerView recyclerView;
    ManagerViewTimesheetAdapter managerViewTimesheetAdapter;
    DatabaseReference database;
    ArrayList<Timesheet> list;
    Manager user;
    //    User user;
    FirebaseAuth mAuth;


    String current_UserEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_timesheet);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Timesheet Actions");


        mAuth=FirebaseAuth.getInstance();
//        if(mAuth.getCurrentUser().getEmail()=null) {
        setCurrent_UserEmail(mAuth.getCurrentUser().getEmail().toString());

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Manager");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Manager temp_manager=dataSnapshot.getValue(Manager.class);
                    if(getCurrent_UserEmail().trim().equalsIgnoreCase(temp_manager.email))
                    {
                        setUser(temp_manager);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        recyclerView = findViewById(R.id.viewTimesheet);
        database = FirebaseDatabase.getInstance().getReference("Timesheet_info");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        managerViewTimesheetAdapter = new ManagerViewTimesheetAdapter(this, list);
        recyclerView.setAdapter(managerViewTimesheetAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Timesheet ts = dataSnapshot.getValue(Timesheet.class);
                    assert ts != null;
                    Log.d("Content",ts.toString());
                    if (ts.approver_id.equals(user.employee_id) && ts.status.equals("pending")) {
                        Log.d("Content-----insideIF", ts.toString());
                        list.add(ts);
                    }
                }
                managerViewTimesheetAdapter.notifyDataSetChanged();
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

    public Manager getUser() {
        return user;
    }

    public void setUser(Manager user) {
        this.user = user;
    }



}