package com.example.splashscreenlotteanimation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Admin_Pages.AdminDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class ChangePassword extends AppCompatActivity {

    TextInputEditText old_password, new_password, new_password_;
    Button change_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Change Password");
        setContentView(R.layout.activity_change_password);

        old_password = findViewById(R.id.password_text);
        new_password = findViewById(R.id.new_password_text);
        new_password_ = findViewById(R.id.reconfirm_password_text);
        change_password = findViewById(R.id.password_change);
        change_password.setOnClickListener(v -> {
            String old = Objects.requireNonNull(old_password.getText()).toString();
            String new_ = Objects.requireNonNull(new_password.getText()).toString();
            String confirm_new = Objects.requireNonNull(new_password_.getText()).toString();

            if (!old.isEmpty() && !new_.isEmpty() && !confirm_new.isEmpty()) {
                if (new_.equals(confirm_new)) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    AuthCredential credential = EmailAuthProvider
                            .getCredential(Objects.requireNonNull(Objects.requireNonNull(user).getEmail()), old);

// Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(new_).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ChangePassword.this, "Password updated", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(ChangePassword.this, "Error password not updated", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(ChangePassword.this, "Error password authentication failed", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });



                 /*   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updatePassword(new_).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "User password updated.", Toast.LENGTH_LONG).show();
                            finish();
                        } else{
                            Toast.makeText(getApplicationContext(), "Password length should be greater than 6.", Toast.LENGTH_LONG).show();
                        }
                    });*/
                }else {
                    Toast.makeText(getApplicationContext(), "Password don't match.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Enter all fields.", Toast.LENGTH_LONG).show();
            }
        });
    }
}