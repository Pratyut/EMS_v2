package com.example.splashscreenlotteanimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.Pojo.Manager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class Certificate extends AppCompatActivity {

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    String found = "";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    Button uploadPdf, selectPdf;
    TextView certificate_name;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Upload Vaccination Certificate");
        setContentView(R.layout.activity_certificate);

        Intent i = getIntent();
        name = i.getStringExtra("Name");

        certificate_name = findViewById(R.id.PdfName);
        selectPdf = findViewById(R.id.selectPdf);
        uploadPdf = findViewById(R.id.uploadPdf);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Role");
        storageReference = FirebaseStorage.getInstance().getReference("Certificate");
        uploadPdf.setEnabled(false);

        selectPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();
            }
        });
    }

    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Certificate Pdf"), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uploadPdf.setEnabled(true);
            certificate_name.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));
            uploadPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPdfFileFirebase(data.getData());
                }
            });
        }
    }

    private void uploadPdfFileFirebase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is uploading.");
        progressDialog.show();
        StorageReference reference = storageReference.child("certificate" + name + ".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri uri = uriTask.getResult();
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String email_key = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace("@", "at").replace(".", "dot");
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    if (email_key.trim().equalsIgnoreCase(postSnapshot.getKey().trim())) {
                                        String role = Objects.requireNonNull(postSnapshot.getValue()).toString();
                                        if (role.trim().equalsIgnoreCase("E")) {
                                            setFound("E");
                                        } else if (role.trim().equalsIgnoreCase("M")) {
                                            setFound("M");
                                        }
                                    }
                                }

                                if (getFound().equalsIgnoreCase("E")) {
                                    databaseReference = firebaseDatabase.getReference("Employee");
                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                                Employee employee = snapshot1.getValue(Employee.class);
                                                if (employee.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                                    Employee newEmployee = new Employee(employee.email, employee.name, employee.contact_no, employee.employee_id, employee.supervisor_id, employee.address, employee.gender, uri.toString(),employee.dob);
                                                    databaseReference.child(employee.getEmployee_id()).setValue(newEmployee)
                                                            .addOnSuccessListener(unused -> {
                                                                Toast.makeText(getApplicationContext(), "PDF Updated.", Toast.LENGTH_LONG).show();
                                                            });
                                                    break;
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                                } else if (getFound().equalsIgnoreCase("M")) {
                                    databaseReference = firebaseDatabase.getReference("Manager");
                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                                Manager manager = snapshot1.getValue(Manager.class);
                                                if (manager.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                                    Manager newManager = new Manager(manager.email, manager.name, manager.employee_id, manager.contact_no, manager.address, manager.gender, uri.toString(), manager.dob, "");
                                                    databaseReference.child(manager.getEmployee_id()).setValue(newManager)
                                                            .addOnSuccessListener(unused -> {
                                                                Toast.makeText(getApplicationContext(), "PDF Updated.", Toast.LENGTH_LONG).show();
                                                            });
                                                    break;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                                } else {
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        Toast.makeText(getApplicationContext(), "Certificate Uploaded", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        finish();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage("Certificate uploaded - " + (int) progress + "%");
            }
        });
    }
}