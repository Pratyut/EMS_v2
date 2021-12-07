package com.example.splashscreenlotteanimation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class SplashActivityFirstScreen extends AppCompatActivity {
    FirebaseAuth mAuth;

    public String getDashboard_number() {
        return dashboard_number;
    }

    public void setDashboard_number(String dashboard_number) {
        this.dashboard_number = dashboard_number;
    }

    String dashboard_number = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Objects.requireNonNull(getSupportActionBar()).hide();
            setContentView(R.layout.activity_splash_screen_first_screen);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MyAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    @SuppressWarnings("deprecation")
    private final class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                int SPLASH_DISPLAY_LENGTH = 2000;
                Thread.sleep(SPLASH_DISPLAY_LENGTH);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void params) {


//            if (mAuth.getCurrentUser() == null) {
                startActivity(new Intent(SplashActivityFirstScreen.this, LoginActivity.class));
                finish();
          /*  } else {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String email_curated = currentUser.getEmail().replace("@", "at").replace(".", "dot");
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Role");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postsnapShot : snapshot.getChildren()) {
                            Log.d("Content----", postsnapShot.getKey().toString() + "=====" + postsnapShot.getValue().toString());
                            if (email_curated.trim().equalsIgnoreCase(postsnapShot.getKey().toString().trim())) {
                                String role = postsnapShot.getValue().toString();
                                if (role.trim().equalsIgnoreCase("E")) {
                                    setDashboard_number("1");
                                    break;
                                }
//                                            dashboard_number="1";
                                if (role.trim().equalsIgnoreCase("M")) {
                                    setDashboard_number("2"); break;
                                }
                                if (role.trim().equalsIgnoreCase("A")) {
                                    setDashboard_number("0"); break;
                                }
                            }

                            if (!getDashboard_number().equalsIgnoreCase("-1")) {
                                Intent intent = new Intent(SplashActivityFirstScreen.this, UserAuthenticated.class);
                                intent.putExtra("Dashboard", dashboard_number);
                                intent.putExtra("Account", currentUser.getEmail());
                                startActivity(intent);
                                finish();
                            }
                            if (getDashboard_number().equalsIgnoreCase("-1")) {
                                Toast.makeText(SplashActivityFirstScreen.this, "Particular user not found, Please try again", Toast.LENGTH_SHORT).show();
                            } else {
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
*/
            }
        }
    }
