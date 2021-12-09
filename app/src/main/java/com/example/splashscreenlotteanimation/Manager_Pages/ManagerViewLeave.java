package com.example.splashscreenlotteanimation.Manager_Pages;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
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

public class ManagerViewLeave extends AppCompatActivity {

    RecyclerView recyclerView;
    ManagerViewLeaveAdapter managerViewLeaveAdapter;
    DatabaseReference database;
    ArrayList<Leave> list;
    Manager user;
    //    User user;
    FirebaseAuth mAuth;


    String current_UserEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_leave);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Approve Leaves");
        user = getIntent().getParcelableExtra("user");


        mAuth=FirebaseAuth.getInstance();
//        if(mAuth.getCurrentUser().getEmail()=null) {
        setCurrent_UserEmail(mAuth.getCurrentUser().getEmail().toString());
        Toast.makeText(ManagerViewLeave.this, "current_mail_id---"+getCurrent_UserEmail()+"++++"+mAuth.getCurrentUser().getEmail().toString(), Toast.LENGTH_SHORT).show();
//        }

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

        recyclerView = findViewById(R.id.viewLeaveList);
        database = FirebaseDatabase.getInstance().getReference("Leave");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        managerViewLeaveAdapter = new ManagerViewLeaveAdapter(this, list);
        recyclerView.setAdapter(managerViewLeaveAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Leave leave = dataSnapshot.getValue(Leave.class);
                    assert leave != null;
                    Log.d("Content",leave.toString());
                    if (leave.approver_id.equals(user.employee_id)) {
                        Log.d("Content-----insideIF", leave.toString());
                        list.add(leave);
                    }
                    }
                managerViewLeaveAdapter.notifyDataSetChanged();
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
