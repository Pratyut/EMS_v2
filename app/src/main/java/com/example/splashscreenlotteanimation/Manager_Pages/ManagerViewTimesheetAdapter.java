package com.example.splashscreenlotteanimation.Manager_Pages;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Pojo.Leave;
import com.example.splashscreenlotteanimation.Pojo.Timesheet;
import com.example.splashscreenlotteanimation.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ManagerViewTimesheetAdapter extends RecyclerView.Adapter<ManagerViewTimesheetAdapter.ManagerViewTimesheetViewHolder>{
    Context context;
    ArrayList<Timesheet> list;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Timesheet_info");

    public ManagerViewTimesheetAdapter(Context context, ArrayList<Timesheet> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ManagerViewTimesheetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.manager_view_timesheet_item,parent,false);
        return new ManagerViewTimesheetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerViewTimesheetViewHolder holder, int position) {
        Timesheet timesheet = list.get(position);
        holder.ts_status.setText(timesheet.status);
        holder.ts_date.setText(timesheet.getDate().replace("_","/"));
        holder.applier_name.setText("Emp : "+timesheet.emp_id);
        holder.timesheet_hrs.setText(timesheet.hours+" hours");
        holder.ts_summary.setText(timesheet.summary);
        Log.d("manager timsheet","Emp ID: "+timesheet.emp_id);
        Log.d("manager timsheet","Emp ID: "+timesheet.timesheet_id);

        if(timesheet.status.equals("pending")){
            holder.removeleaverequest.setVisibility(View.VISIBLE);
            holder.approveleaverequest.setVisibility(View.VISIBLE);
        }
        else if(timesheet.status.equals("approved")){
//            holder.removeleaverequest.setVisibility(View.VISIBLE);
        }
        else{
//            holder.approveleaverequest.setVisibility(View.VISIBLE);
        }
        holder.removeleaverequest.setOnClickListener(v -> {
            database.child(timesheet.timesheet_id).child("status").setValue("declined");
            Toast.makeText(v.getContext(), "Entry declined successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(v.getContext(), view_timesheet.class);
            context.startActivity(i);
        });
        holder.approveleaverequest.setOnClickListener(v -> {
            database.child(timesheet.timesheet_id).child("status").setValue("approved");
            Toast.makeText(v.getContext(), "Entry Approved successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(v.getContext(), view_timesheet.class);
            context.startActivity(i);

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ManagerViewTimesheetViewHolder extends RecyclerView.ViewHolder{

        TextView ts_summary, ts_status,ts_date,applier_name,timesheet_hrs;
        Button removeleaverequest, approveleaverequest;

        public ManagerViewTimesheetViewHolder(@NonNull View itemView) {
            super(itemView);
            ts_summary = itemView.findViewById(R.id.timesheet_summary);
            ts_status = itemView.findViewById(R.id.timesheet_Status);
            ts_date = itemView.findViewById(R.id.ts_date);
            applier_name=itemView.findViewById(R.id.ts_Emp_id);
            timesheet_hrs=itemView.findViewById(R.id.timesheet_hours);
            removeleaverequest = itemView.findViewById(R.id.ts_removeLeaveRequest);
            approveleaverequest = itemView.findViewById(R.id.ts_approveLeaveRequest);
            ts_summary.setSelected(true);


        }
    }



}




