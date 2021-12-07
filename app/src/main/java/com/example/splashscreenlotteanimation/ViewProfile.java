package com.example.splashscreenlotteanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class ViewProfile extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    TextInputEditText id, name, email, dob, number;
    Button changePassword, certificate, save_changes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Profile");
        setContentView(R.layout.activity_view_profile);

        id = findViewById(R.id.id_etext);
        name = findViewById(R.id.name_etext);
        email = findViewById(R.id.mail_etext);
        dob = findViewById(R.id.birth_etext);
        number = findViewById(R.id.phone_etext);

        changePassword = findViewById(R.id.password_change);
        changePassword.setOnClickListener(v -> {

        });

        certificate = findViewById(R.id.certificate);
        certificate.setOnClickListener(v -> {

        });

        save_changes = findViewById(R.id.update);
        save_changes.setOnClickListener(v -> {

        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}