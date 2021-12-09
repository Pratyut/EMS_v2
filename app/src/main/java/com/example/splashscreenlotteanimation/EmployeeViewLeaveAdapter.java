package com.example.splashscreenlotteanimation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Employee_Pages.EmployeeDashboard;
import com.example.splashscreenlotteanimation.Employee_Pages.EmployeeViewLeave;
import com.example.splashscreenlotteanimation.Pojo.Leave;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeeViewLeaveAdapter extends RecyclerView.Adapter<EmployeeViewLeaveAdapter.EmployeeViewLeaveViewHolder> {

    Context context;
    ArrayList<Leave> list;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Leave");

    public EmployeeViewLeaveAdapter(Context context, ArrayList<Leave> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public EmployeeViewLeaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.employee_view_leave,parent,false);
        return new EmployeeViewLeaveViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull EmployeeViewLeaveViewHolder holder, int position) {
        Leave leave = list.get(position);
        holder.viewleavesubject.setText(leave.getSubject());
        holder.leaveStart.setText(leave.getFrom().substring(0,leave.getFrom().lastIndexOf('/')));
        holder.leaveEnd.setText(leave.getTo().substring(0,leave.getFrom().lastIndexOf('/')));
        holder.viewleavesubject.setText(leave.getSubject());
        holder.leaveStatus.setText("Status: "+leave.getStatus());
        holder.removeleaverequest.setOnClickListener(v -> {
            Log.d("Leaves",leave.leave_number);
            Log.d("Leaves if condition",String.valueOf(database.child(leave.leave_number).child("status").equals("pending")));
            Log.d("Leaves keys",String.valueOf(database.child(leave.leave_number).getKey()));

            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                 String status= snapshot.child(leave.leave_number).child("status").getValue(String.class);
                 if(String.valueOf("pending").equalsIgnoreCase(Objects.requireNonNull(status).trim()))
                 {
                     database.child(leave.leave_number).removeValue();
                     Toast.makeText(v.getContext(), "Leave Removed successfully. Please refresh the page", Toast.LENGTH_SHORT).show();
                     v.getContext().startActivity(new Intent(v.getContext(), EmployeeDashboard.class));
                 }
                 else
                 {
                     Toast.makeText(v.getContext(), "Processed leaves cannot be removed.", Toast.LENGTH_SHORT).show();
                 }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
/*
            if(database.child(leave.leave_number).child("status").equals("pending")) {
                database.child(leave.leave_number).removeValue();
                Toast.makeText(v.getContext(), "Leave Removed successfully. Please refresh the page", Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(new Intent(v.getContext(), EmployeeDashboard.class));
            }
            else{
                Toast.makeText(v.getContext(), "Processed leaves cannot be removed.", Toast.LENGTH_SHORT).show();
            }
*/

            /*Intent i = new Intent(v.getContext(), ViewLeave.class);
            context.startActivity(i);*/
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class EmployeeViewLeaveViewHolder extends RecyclerView.ViewHolder{

        TextView viewleavesubject, leaveStart, leaveEnd,leaveStatus;
        Button removeleaverequest;

        public EmployeeViewLeaveViewHolder(@NonNull View itemView) {
            super(itemView);
            viewleavesubject = itemView.findViewById(R.id.viewLeaveSubject);
            leaveStart=itemView.findViewById(R.id.viewLeavestartDate);
            leaveEnd=itemView.findViewById(R.id.LeaveEndDate);
            removeleaverequest = itemView.findViewById(R.id.removeLeaveRequest);
            leaveStatus=itemView.findViewById(R.id.viewLeaveStatus);
        }
    }
}

