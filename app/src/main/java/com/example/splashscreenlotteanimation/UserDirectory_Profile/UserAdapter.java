package com.example.splashscreenlotteanimation.UserDirectory_Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context context;
    ArrayList<Employee> list;

    public UserAdapter(Context context, ArrayList<Employee> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item,parent,false);
        return new UserViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Employee user = list.get(position);
        holder.name.setText(user.name);
        holder.userId.setText(user.employee_id);
        if(user.supervisor_id == null)
            holder.post.setText("Manager");
        else
            holder.post.setText("Employee");

        holder.itemView.setOnClickListener(v -> {
            Employee user1 = list.get(holder.getAdapterPosition());
            Intent profileIntent = new Intent(v.getContext(),UserProfile.class);
            profileIntent.putExtra("user", user1);
            v.getContext().startActivity(profileIntent);

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        TextView name, userId, post;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            userId = itemView.findViewById(R.id.userId);
            post = itemView.findViewById(R.id.post);

        }
    }
}
