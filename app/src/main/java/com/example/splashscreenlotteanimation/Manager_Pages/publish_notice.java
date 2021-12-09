package com.example.splashscreenlotteanimation.Manager_Pages;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Pojo.Notice;
import com.example.splashscreenlotteanimation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class publish_notice extends AppCompatActivity {

    // creating variables for
    // EditText and buttons.
    private EditText ReceiverEdt, SubjectEdt, BodyEdt;
    private Button sendDatabtn;

    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    Notice notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Publish Notice");
        setContentView(R.layout.activity_publish_notice);

        // initializing our edittext and button
//        ReceiverEdt = findViewById(R.id.idEdtReceiver);
        SubjectEdt = findViewById(R.id.idEdtSubject);
        BodyEdt = findViewById(R.id.idEdtBody);

        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Notice");

        // initializing our object
        // class variable.
        notice = new Notice();

        sendDatabtn = findViewById(R.id.idBtnSendData);

        // adding on click listener for our button.
        sendDatabtn.setOnClickListener(v -> {

            // getting text from our edittext fields.
//            String receiver = ReceiverEdt.getText().toString();
            String subject = SubjectEdt.getText().toString();
            String body = BodyEdt.getText().toString();

            // below line is for checking weather the
            // edittext fields are empty or not.
            if (/*TextUtils.isEmpty(receiver) &&*/ TextUtils.isEmpty(subject) && TextUtils.isEmpty(body)) {
                // if the text fields are empty
                // then show the below message.
                Toast.makeText(publish_notice.this, "Please add some data.", Toast.LENGTH_SHORT).show();
            } else {
                // else call the method to add
                // data to our database.
//                addDatatoFirebase(/*receiver, */subject, body);
                Notice temp=new Notice(subject,body);
                Random random=new Random();
                String user_email= FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
                String curated_email=user_email.replace("@","at").replace(".","dot");
                databaseReference.child(curated_email+"_"+Integer.toString(random.nextInt(1100))).setValue(temp);
                finish();
            }
        });
    }


    private void addDatatoFirebase(String receiver, String subject, String body) {
        // below 3 lines of code is used to set
        // data in our object class.
        notice.setReceiver(receiver);
        notice.setSubject(subject);
        notice.setBody(body);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(notice);

                // after adding this data we are showing toast message.
                Toast.makeText(publish_notice.this, "Notice Published!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(publish_notice.this, "Failed to publish notice" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}