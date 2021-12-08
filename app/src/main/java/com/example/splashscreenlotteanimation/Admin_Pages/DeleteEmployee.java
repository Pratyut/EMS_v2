package com.example.splashscreenlotteanimation.Admin_Pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DeleteEmployee extends AppCompatActivity {

    RecyclerView recyclerView;
    DeleteEmployeeListAdapter recyclerAdapter;

    ArrayList<Employee>  list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Delete Employee");
        setContentView(R.layout.activity_delete_employee);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference(/*"Employee"*/);
        recyclerAdapter = new DeleteEmployeeListAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(DeleteEmployee.this));
        recyclerView.setAdapter(recyclerAdapter);


        //        Log.d("Content---",String.valueOf(moviesList.size()));


        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Employee emp_temp = new Employee();
                for (DataSnapshot dataSnapshot : snapshot.child("Employee").getChildren()) {
                    emp_temp = dataSnapshot.getValue(Employee.class);
                    String super_id_temp = emp_temp.supervisor_id;
                    String supervisor_name = snapshot.child("Manager").child(super_id_temp).child("name").getValue(String.class);
                    emp_temp.supervisor_id = supervisor_name + " (" + super_id_temp + ")";
                    list.add(emp_temp);
//                    Log.d("Content---",emp_temp.toString());
                    Log.d("Content---",String.valueOf(list.size()));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        dbReference = FirebaseDatabase.getInstance().getReference("Manager");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int position = list.size();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Employee user = dataSnapshot.getValue(Employee.class);
                    list.add(user);
                }
                recyclerAdapter.notifyItemInserted(position);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {  }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search_item, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}













