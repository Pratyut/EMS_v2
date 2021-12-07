package com.example.splashscreenlotteanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.splashscreenlotteanimation.Admin_Pages.AdminDashboard;
import com.example.splashscreenlotteanimation.Employee_Pages.EmployeeDashboard;
import com.example.splashscreenlotteanimation.Manager_Pages.ManagerDashboard;

import java.util.Objects;

public class UserAuthenticated extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Objects.requireNonNull(getSupportActionBar()).hide();
            setContentView(R.layout.activity_user_authenticated);
        }catch(Exception e){
            e.printStackTrace();
        }
        new MyAsyncTask().execute();
    }
    @SuppressLint("StaticFieldLeak")
    @SuppressWarnings("deprecation")
    private final class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute(){
        }
        @Override
        protected Void doInBackground(Void... voids){
            try {
                int SPLASH_DISPLAY_LENGTH = 2000;
                Thread.sleep(SPLASH_DISPLAY_LENGTH);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void params)
        {
            Intent intent = getIntent();
            String arrangement = intent.getStringExtra("Dashboard");
            if (arrangement.equals("0")) {
                startActivity(new Intent(UserAuthenticated.this, AdminDashboard.class));
                finish();
            }
            else if (arrangement.equals("1")) {
                startActivity(new Intent(UserAuthenticated.this, EmployeeDashboard.class));
                finish();
            }
            else {
                startActivity(new Intent(UserAuthenticated.this, ManagerDashboard.class));
                finish();
            }
        }
    }
}