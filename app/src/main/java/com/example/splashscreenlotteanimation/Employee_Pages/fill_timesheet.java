package com.example.splashscreenlotteanimation.Employee_Pages;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.Pojo.Timesheet;
import com.example.splashscreenlotteanimation.R;
import com.example.splashscreenlotteanimation.ViewProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class fill_timesheet extends AppCompatActivity {


    // creating variables for
    // EditText and buttons.

    TextInputEditText date, hours, summary, description;
    TextInputLayout date_;
    String date_str,hours_str,summary_str,description_str;
    private DatePickerDialog picker;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private Button sendDatabtn;



    // creating a variable for
    // our object class
    Timesheet timesheet;

    Employee temp_emp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_timesheet_v1);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Fill Timesheet");
        // initializing our edittext and button


        hours = findViewById(R.id.hours_etext);
        summary = findViewById(R.id.summary_etext);
        description = findViewById(R.id.description_text);
        date_ = findViewById(R.id.effort_date_text);
        date = findViewById(R.id.date_etext);



        date_.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                picker = new DatePickerDialog(fill_timesheet.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });


//////// setting the employee object
            user=FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference databaseReference_emp = FirebaseDatabase.getInstance().getReference("Employee");
            databaseReference_emp.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Employee employee = snapshot1.getValue(Employee.class);
                        if (employee.getEmail().equals(user.getEmail())) {
                            setTemp_emp(employee);
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });





        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        // initializing our object
        // class variable.
        timesheet = new Timesheet();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Timesheet_info");




        sendDatabtn = findViewById(R.id.idBtnSendData);
        // adding on click listener for our button.
        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting text from our edittext fields.
                date_str = Objects.requireNonNull(date.getText()).toString().replace("/","_");
                hours_str= Objects.requireNonNull(hours.getText()).toString();
                description_str= Objects.requireNonNull(description.getText()).toString();
                summary_str= Objects.requireNonNull(summary.getText()).toString();

                if(date_str.isEmpty() || hours_str.isEmpty()|| description_str.isEmpty()|| summary_str.isEmpty())
                {
                    Toast.makeText(fill_timesheet.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                }
                else if( Integer.parseInt(hours_str)>24)
                {
                    Toast.makeText(fill_timesheet.this, "Please fill practical hours", Toast.LENGTH_SHORT).show();
                }
                else{

                    String user_email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    String user_email_curated=user_email.replace("@","at").replace(".","dot");

                    String timesheet_id=getTemp_emp().employee_id+"_"+date_str;
                    timesheet=new Timesheet(getTemp_emp().employee_id,getTemp_emp().supervisor_id,date_str,summary_str,description_str,hours_str,timesheet_id);

                    databaseReference.child(getTemp_emp().employee_id+date_str).setValue(timesheet);
                       /*     (timesheet, new OnCompleteListener<Task>() {
                        @Override
                        public void onComplete(@NonNull Task<Task> task) {
                           if(task.isSuccessful())
                           {
                               Toast.makeText(fill_timesheet.this, "Timesheet filled", Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(fill_timesheet.this,EmployeeDashboard.class));
                                finish();
                           }
                           else{
                               Toast.makeText(fill_timesheet.this, "Failure, seems like you already filled timesheet for the day", Toast.LENGTH_SHORT).show();
                           }
                        }
                    });*/

                }
//                addDatatoFirebase(date, time, taskdesc);

            }
        });
    }

/*
    private void addDatatoFirebase(String date, String[] time, String[] taskdesc) {
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
                for (int i = 0; i < 8; i++)
                {
                    DatabaseReference newPostRef = databaseReference.push();
                    //newPostRef.setValue(timesheet.getTaskDescreption()[i]);

                    String S = timesheet.getDate() + "    " + timesheet.getTime()[i] + "    "  + timesheet.getTaskDescreption()[i];
                    newPostRef.setValue(S);
                }

                // adding string instead
                //databaseReference.setValue(timesheet);


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
*/

    public Employee getTemp_emp() {
        return temp_emp;
    }

    public void setTemp_emp(Employee temp_emp) {
        this.temp_emp = temp_emp;
    }


}
