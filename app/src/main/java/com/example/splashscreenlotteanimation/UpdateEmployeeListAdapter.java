package com.example.splashscreenlotteanimation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Admin_Pages.DeleteEmployeeListAdapter;
import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.UserDirectory_Profile.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class UpdateEmployeeListAdapter extends RecyclerView.Adapter<UpdateEmployeeListAdapter.ViewHolder>  implements Filterable{

    private static final String TAG = "RecyclerAdapter";
    //    ArrayList<Employee> moviesList,moviesListAll;
    Context ctx;
    List<Employee> moviesList,moviesListFilter;

    public UpdateEmployeeListAdapter(Context context,List<Employee> moviesList) {
            this.ctx=context;
            this.moviesList = moviesList;
            this.moviesListFilter=moviesList;
     }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.update_employee_item, parent, false);
        UpdateEmployeeListAdapter.ViewHolder viewHolder = new UpdateEmployeeListAdapter.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.emp_id.setText("ID: "+moviesListFilter.get(position).employee_id);
        holder.emp_name.setText(moviesListFilter.get(position).name);
        if(moviesListFilter.get(position).supervisor_id==null ||moviesListFilter.get(position).supervisor_id.equals("") )
        {
            holder.supervisor_name.setText("");
            holder.emp_type.setText("Manager");
        }
        else {
            Log.d("testing..", "Supervisor: "+ moviesListFilter.get(position).supervisor_id);
            Log.d("testing..", "Supervisor: "+ moviesListFilter.get(position).supervisor_id.toString());

            holder.supervisor_name.setText("Supervisor: " + moviesListFilter.get(position).supervisor_id);
            holder.emp_type.setText("Employee");
        }

        holder.itemView.setOnClickListener(v -> {
            Employee user1 = moviesListFilter.get(holder.getAdapterPosition());
//            Toast.makeText(ctx, user1.name, Toast.LENGTH_SHORT).show();
            Intent profileIntent = new Intent(ctx, UpdateEmployee.class);
            profileIntent.putExtra("user", user1);
            ctx.startActivity(profileIntent);
        });

    }

    @Override
    public int getItemCount() {
        return moviesListFilter.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imageView;
    TextView emp_id, emp_name,emp_type,supervisor_name;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        emp_id = itemView.findViewById(R.id.emp_id);
        emp_name=itemView.findViewById(R.id.emp_name);
        emp_type=itemView.findViewById(R.id.emp_type);
        supervisor_name=itemView.findViewById(R.id.supervisor_name);

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==itemView.getId()) {
//                Toast.makeText(view.getContext(), moviesList.get(getAdapterPosition()).employee_id, Toast.LENGTH_SHORT).show();
              /*Toast.makeText(ctx, moviesListFilter.get(getAdapterPosition()).name, Toast.LENGTH_SHORT).show();
               Employee temp= moviesListFilter.get(getAdapterPosition());
               Intent i=new Intent(ctx,UpdateEmployee.class);
               i.putExtra("user",temp);*/
        }

    }
}

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            /*ArrayList<Employee> filteredList = new ArrayList<>();*/
            if (charSequence == null || charSequence.length() == 0) {
//                filteredList.addAll(moviesListAll);
                moviesListFilter=moviesList;
//                Log.d("filteredList IN 1case",String.valueOf(filteredList.size()));
            } else {
                List<Employee> filteredList = new ArrayList<>();
                for (Employee temp_emp: moviesList) {
                    if (temp_emp.employee_id.toString().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || temp_emp.name.toString().toLowerCase().contains(charSequence.toString().toLowerCase()))
                    {
                        filteredList.add(temp_emp);
                    }
                }
                Log.d("filteredList IN 2case",String.valueOf(filteredList.size()));
                moviesListFilter=filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = moviesListFilter;
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            moviesListFilter=(ArrayList<Employee>) filterResults.values;
            notifyDataSetChanged();
        }
    };


}










