package com.example.splashscreenlotteanimation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Manager_Pages.ViewLeave;
import com.example.splashscreenlotteanimation.Pojo.Leave;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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
        holder.removeleaverequest.setOnClickListener(v -> {
            database.child(leave.userid).removeValue();
            Toast.makeText(v.getContext(), "Leave Removed successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(v.getContext(), ViewLeave.class);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class EmployeeViewLeaveViewHolder extends RecyclerView.ViewHolder{

        TextView viewleavesubject;
        Button removeleaverequest;

        public EmployeeViewLeaveViewHolder(@NonNull View itemView) {
            super(itemView);
            viewleavesubject = itemView.findViewById(R.id.viewLeaveSubject);
            removeleaverequest = itemView.findViewById(R.id.removeLeaveRequest);

        }
    }
}

