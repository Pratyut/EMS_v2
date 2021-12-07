package com.example.splashscreenlotteanimation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class SendMail extends AppCompatActivity {

    final String[] designation = {"Employee", "Manager"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> myAdapter;
    String item;
    TextInputEditText id, name, email;
    Button send_mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Objects.requireNonNull(getSupportActionBar()).setTitle("Enter your Details");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        autoCompleteTxt = findViewById(R.id.autoCompleteTextView);
        myAdapter = new ArrayAdapter<>(SendMail.this, R.layout.dropdown_item_designation, designation);
        autoCompleteTxt.setAdapter(myAdapter);
        autoCompleteTxt.setOnItemClickListener((parent, view, position, id) -> item = parent.getItemAtPosition(position).toString());
        name = findViewById(R.id.name_text);
        id = findViewById(R.id.id_text);
        email = findViewById(R.id.email_text);
        send_mail = findViewById(R.id.send_mail_button);
        send_mail.setOnClickListener(v -> {
            if(!Objects.requireNonNull(email.getText()).toString().isEmpty() && !Objects.requireNonNull(name.getText()).toString().isEmpty() && !Objects.requireNonNull(id.getText()).toString().isEmpty() && !item.isEmpty()) {

                String body = "Greetings of the day.\nI request you to provide me credentials to access the organization application - " + getString(R.string.app_name)
                        + ". My details are as provided below :\nName : " + name.getText().toString()
                        + ".\nID : " + id.getText().toString()
                        + ".\nDesignation : " + item
                        +".\n\nThank you.";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"admin@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "REQUESTING CREDENTIALS");
                intent.putExtra(Intent.EXTRA_TEXT, body);
                intent.setType("message/rfc822");
                try {
                    startActivity(Intent.createChooser(intent,"Send email using..."));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SendMail.this,
                            "No email clients installed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(SendMail.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}   