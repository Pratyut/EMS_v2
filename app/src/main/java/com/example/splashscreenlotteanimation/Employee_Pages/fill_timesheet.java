package com.example.splashscreenlotteanimation.Employee_Pages;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.splashscreenlotteanimation.Pojo.Timesheet;
import com.example.splashscreenlotteanimation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fill_timesheet extends AppCompatActivity {


    // creating variables for
    // EditText and buttons.
    private EditText DateEdt, TimeEdt1, TaskDescreptionEdt1;
    private EditText TimeEdt2, TaskDescreptionEdt2;
    private EditText TimeEdt3, TaskDescreptionEdt3;
    private EditText TimeEdt4, TaskDescreptionEdt4;
    private EditText TimeEdt5, TaskDescreptionEdt5;
    private EditText TimeEdt6, TaskDescreptionEdt6;
    private EditText TimeEdt7, TaskDescreptionEdt7;
    private EditText TimeEdt8, TaskDescreptionEdt8;
    private Button sendDatabtn;

    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    Timesheet timesheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_timesheet);

        // initializing our edittext and button
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




        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Timesheet_info");

        // initializing our object
        // class variable.
        timesheet = new Timesheet();

        sendDatabtn = findViewById(R.id.idBtnSendData);

        // adding on click listener for our button.
        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting text from our edittext fields.
                String date = DateEdt.getText().toString();

                ArrayList<String> time;
                ArrayList<String> taskdesc;

                time = new ArrayList();
                taskdesc = new ArrayList();

                time.add(TimeEdt1.getText().toString());
                taskdesc.add(TaskDescreptionEdt1.getText().toString());

                time.add(TimeEdt2.getText().toString());
                taskdesc.add(TaskDescreptionEdt2.getText().toString());

                time.add(TimeEdt3.getText().toString());
                taskdesc.add(TaskDescreptionEdt3.getText().toString());

                time.add(TimeEdt4.getText().toString());
                taskdesc.add(TaskDescreptionEdt4.getText().toString());

                time.add(TimeEdt5.getText().toString());
                taskdesc.add(TaskDescreptionEdt5.getText().toString());

                time.add(TimeEdt6.getText().toString());
                taskdesc.add(TaskDescreptionEdt6.getText().toString());

                time.add(TimeEdt7.getText().toString());
                taskdesc.add(TaskDescreptionEdt7.getText().toString());

                time.add(TimeEdt8.getText().toString());
                taskdesc.add(TaskDescreptionEdt8.getText().toString());

                addDatatoFirebase(date, time, taskdesc);

            }
        });
    }

    private void addDatatoFirebase(String date, ArrayList<String> time, ArrayList<String> taskdesc) {
        // below 3 lines of code is used to set
        // data in our object class.
        timesheet.setDate(date);
        timesheet.setTime(time);
        timesheet.setTaskDescreption(taskdesc);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addListenerForSingleValueEvent (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.


                // not adding object, adding string instead
//                for (int i = 0; i < 8; i++)
//                {
//                    DatabaseReference newPostRef = databaseReference.push();
//                    //newPostRef.setValue(timesheet.getTaskDescreption()[i]);
//
////                    String S = timesheet.getDate() + "    " + timesheet.getTime()[i] + "    "  + timesheet.getTaskDescreption()[i];
////                    newPostRef.setValue(S);
//                }

//                 adding string instead
                databaseReference.setValue(timesheet);


                // after adding this data we are showing toast message.
                Toast.makeText(fill_timesheet.this, "Timesheet added!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(fill_timesheet.this, "Fail to add data" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}