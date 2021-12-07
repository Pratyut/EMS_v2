package com.example.splashscreenlotteanimation.Manager_Pages;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import com.example.splashscreenlotteanimation.Pojo.Timesheet;
import com.example.splashscreenlotteanimation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class view_timesheet extends AppCompatActivity {


    // creating a variable for
    // our Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseReference;

    // variable for Text view.

    private TextView DateEdt, TimeEdt1, TaskDescreptionEdt1;
    private TextView TimeEdt2, TaskDescreptionEdt2;
    private TextView TimeEdt3, TaskDescreptionEdt3;
    private TextView TimeEdt4, TaskDescreptionEdt4;
    private TextView TimeEdt5, TaskDescreptionEdt5;
    private TextView TimeEdt6, TaskDescreptionEdt6;
    private TextView TimeEdt7, TaskDescreptionEdt7;
    private TextView TimeEdt8, TaskDescreptionEdt8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_timesheet);

        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase.getReference("Timesheet_info");

        // initializing our object class variable.

        DateEdt = findViewById(R.id.idEdtDate);
        TimeEdt1 = findViewById(R.id.idEdtTime5);
        TaskDescreptionEdt1 = findViewById(R.id.idEdtTaskDescription5);

        TimeEdt2 = findViewById(R.id.idEdtTime4);
        TaskDescreptionEdt2 = findViewById(R.id.idEdtTaskDescription4);

        TimeEdt3 = findViewById(R.id.idEdtTime7);
        TaskDescreptionEdt3 = findViewById(R.id.idEdtTaskDescription7);

        TimeEdt4 = findViewById(R.id.idEdtTime6);
        TaskDescreptionEdt4 = findViewById(R.id.idEdtTaskDescription6);

        TimeEdt5 = findViewById(R.id.idEdtTime24);
        TaskDescreptionEdt5 = findViewById(R.id.idEdtTaskDescription24);

        TimeEdt6 = findViewById(R.id.idEdtTime26);
        TaskDescreptionEdt6 = findViewById(R.id.idEdtTaskDescription26);

        TimeEdt7 = findViewById(R.id.idEdtTime25);
        TaskDescreptionEdt7 = findViewById(R.id.idEdtTaskDescription25);

        TimeEdt8 = findViewById(R.id.idEdtTime27);
        TaskDescreptionEdt8 = findViewById(R.id.idEdtTaskDescription27);


        // calling method
        // for getting data.
        //getdata();
    }

    private void getdata() {

        // calling add value event listener method
        // for getting the values from database.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.

                Timesheet value = snapshot.getValue(Timesheet.class);

                // after getting the value we are setting
                // our value to our text view in below line.


                DateEdt.setText(value.getDate()) ;
                TimeEdt1.setText(value.getTime()[0]);
                TaskDescreptionEdt1.setText(value.getTaskDescreption()[0]);
                TimeEdt2.setText(value.getTime()[1]);
                TaskDescreptionEdt2.setText(value.getTaskDescreption()[1]);
                TimeEdt3.setText(value.getTime()[2]);
                TaskDescreptionEdt3.setText(value.getTaskDescreption()[2]);
                TimeEdt4.setText(value.getTime()[3]);
                TaskDescreptionEdt4.setText(value.getTaskDescreption()[3]);
                TimeEdt5.setText(value.getTime()[4]);
                TaskDescreptionEdt5.setText(value.getTaskDescreption()[4]);
                TimeEdt6.setText(value.getTime()[5]);
                TaskDescreptionEdt6.setText(value.getTaskDescreption()[5]);
                TimeEdt7.setText(value.getTime()[6]);
                TaskDescreptionEdt7.setText(value.getTaskDescreption()[6]);
                TimeEdt8.setText(value.getTime()[7]);
                TaskDescreptionEdt8.setText(value.getTaskDescreption()[7]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(view_timesheet.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}