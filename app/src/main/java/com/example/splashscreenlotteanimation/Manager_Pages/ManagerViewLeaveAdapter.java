package com.example.splashscreenlotteanimation.Manager_Pages;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Pojo.Leave;
import com.example.splashscreenlotteanimation.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ManagerViewLeaveAdapter extends RecyclerView.Adapter<ManagerViewLeaveAdapter.ManagerViewLeaveViewHolder> {

    Context context;
    ArrayList<Leave> list;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Leave");

    public ManagerViewLeaveAdapter(Context context, ArrayList<Leave> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ManagerViewLeaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.manager_view_leave,parent,false);
        return new ManagerViewLeaveViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ManagerViewLeaveViewHolder holder, int position) {
        Leave leave = list.get(position);
        holder.viewleavesubject.setText(leave.getSubject());
        holder.leavestatus.setText(leave.getStatus());
        if(leave.getStatus().equals("pending")){
            holder.removeleaverequest.setVisibility(View.VISIBLE);
            holder.approveleaverequest.setVisibility(View.VISIBLE);
        }
        else if(leave.getStatus().equals("approved")){
            holder.removeleaverequest.setVisibility(View.VISIBLE);
        }
        else{
            holder.approveleaverequest.setVisibility(View.VISIBLE);
        }
        holder.removeleaverequest.setOnClickListener(v -> {
            database.child(leave.leave_number).child("status").setValue("declined");
            Toast.makeText(v.getContext(), "Leave Removed successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(v.getContext(), ManagerViewLeave.class);
            context.startActivity(i);
        });
        holder.approveleaverequest.setOnClickListener(v -> {
            database.child(leave.leave_number).child("status").setValue("approved");
            Toast.makeText(v.getContext(), "Leave Approved successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(v.getContext(), ManagerViewLeave.class);
            context.startActivity(i);

        });
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(),ManagerSingleViewLeave.class);
            i.putExtra("employee_id", leave.userid);
            i.putExtra("leave_number", leave.leave_number);
            i.putExtra("from", leave.from);
            i.putExtra("to", leave.to);
            i.putExtra("reason", leave.reason);
            i.putExtra("subject", leave.subject);
            i.putExtra("status", leave.status);
            v.getContext().startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ManagerViewLeaveViewHolder extends RecyclerView.ViewHolder{

        TextView viewleavesubject, leavestatus;
        Button removeleaverequest, approveleaverequest;

        public ManagerViewLeaveViewHolder(@NonNull View itemView) {
            super(itemView);
            viewleavesubject = itemView.findViewById(R.id.viewLeaveSubject);
            leavestatus = itemView.findViewById(R.id.leaveStatus);
            removeleaverequest = itemView.findViewById(R.id.removeLeaveRequest);
            approveleaverequest = itemView.findViewById(R.id.approveLeaveRequest);

        }
    }
}

