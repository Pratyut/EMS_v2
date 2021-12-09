package com.example.splashscreenlotteanimation.Employee_Pages;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.EmployeeViewLeaveAdapter;
import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.Pojo.Leave;
import com.example.splashscreenlotteanimation.Pojo.Notice;
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


public class view_notice extends AppCompatActivity {



    // creating a variable for
    // our Firebase Database.
    FirebaseDatabase firebaseDatabase;
    RecyclerView recyclerView;
    // creating a variable for our
    // Database Reference for Firebase.
     ViewNoticeListAdapter viewNoticeListAdapter;
    DatabaseReference database;
    ArrayList<Notice> list;

    Employee user;
    //    User user;
    FirebaseAuth mAuth;

    // variable for Text view.
    private TextView idEdtSubject, idEdtBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("View Notice");
        setContentView(R.layout.activity_view_notice);

        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView=findViewById(R.id.viewNoticeListRecycle);
        // below line is used to get
        // reference for our database.

        database = FirebaseDatabase.getInstance().getReference("Notice");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        viewNoticeListAdapter = new ViewNoticeListAdapter(this, list);
        recyclerView.setAdapter(viewNoticeListAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Notice notice = snapshot.getValue(Notice.class);
                    assert notice != null;
                    Log.d("Content", notice.getSubject().toString());
                    list.add(notice);
                }
                Log.d("Content", String.valueOf(list.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // initializing our object class variable.
    /*    idEdtSubject = findViewById(R.id.idEdtSubject);
        idEdtBody = findViewById(R.id.idEdtBody);
*/
        // calling method
        // for getting data.
//        getdata();
    }

    private void getdata() {

        // calling add value event listener method
        // for getting the values from database.
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Notice");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.


                Notice value = snapshot.getValue(Notice.class);

                // after getting the value we are setting
                // our value to our text view in below line.
//                idEdtSubject.setText(value.getSubject());
//                idEdtBody.setText(value.getBody());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(view_notice.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}



