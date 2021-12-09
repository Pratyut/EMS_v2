package com.example.splashscreenlotteanimation.Employee_Pages;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Pojo.Leave;
import com.example.splashscreenlotteanimation.Pojo.Notice;
import com.example.splashscreenlotteanimation.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ViewNoticeListAdapter extends RecyclerView.Adapter<ViewNoticeListAdapter.ViewNoticeListViewHolder> {
        Context context;
        ArrayList<Notice> list;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Leave");

        public ViewNoticeListAdapter (Context context, ArrayList<Notice> list) {
        this.context = context;
        this.list = list;
        }

    @NonNull
    @Override
    public ViewNoticeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.view_notice_item,parent,false);
        return new ViewNoticeListViewHolder (v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewNoticeListViewHolder holder, int position) {
        Notice notice = list.get(position);
        holder.subject.setText(notice.getSubject());
        holder.body.setText(notice.getBody());


        }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewNoticeListViewHolder extends RecyclerView.ViewHolder{
        TextView subject,body;
        public ViewNoticeListViewHolder(@NonNull View itemView) {
            super(itemView);
            subject= itemView.findViewById(R.id.view_notice_subject);
            body=itemView.findViewById(R.id.view_notice_body);

        }
    }


}




/*    @Override
    public void onBindViewHolder(@NonNull com.example.splashscreenlotteanimation.EmployeeViewLeaveAdapter.EmployeeViewLeaveViewHolder holder, int position) {
        Leave leave = list.get(position);
        holder.viewleavesubject.setText(leave.getSubject());
        holder.leaveStart.setText(leave.getFrom().substring(0,leave.getFrom().lastIndexOf('/')));
        holder.leaveEnd.setText(leave.getTo().substring(0,leave.getFrom().lastIndexOf('/')));
        holder.viewleavesubject.setText(leave.getSubject());
        holder.removeleaverequest.setOnClickListener(v -> {
            Log.d("Leaves",leave.leave_number);
            database.child(leave.leave_number).removeValue();
            Toast.makeText(v.getContext(), "Leave Removed successfully", Toast.LENGTH_SHORT).show();
            *//*Intent i = new Intent(v.getContext(), ViewLeave.class);
            context.startActivity(i);*//*
        });

    }*/





