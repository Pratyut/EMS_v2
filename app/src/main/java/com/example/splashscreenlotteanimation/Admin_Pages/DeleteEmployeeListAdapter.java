package com.example.splashscreenlotteanimation.Admin_Pages;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreenlotteanimation.Pojo.Employee;
import com.example.splashscreenlotteanimation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


public class DeleteEmployeeListAdapter extends RecyclerView.Adapter<DeleteEmployeeListAdapter.ViewHolder>  implements Filterable{

    private static final String TAG = "RecyclerAdapter";
//    ArrayList<Employee> moviesList,moviesListAll;
    Context ctx;
    List<Employee> moviesList,moviesListFilter;



    public DeleteEmployeeListAdapter(Context context,List<Employee> moviesList) {
        this.ctx=context;
        this.moviesList = moviesList;
        this.moviesListFilter=moviesList;

        /*moviesListAll = new ArrayList<>();
        moviesListAll.addAll(moviesList);*/
        Log.d("filtered MvListCons", String.valueOf(moviesList.size()));
        Log.d("filtered MvListCons", String.valueOf(moviesListFilter.size()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.delete_employee_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.emp_id.setText("ID: "+moviesListFilter.get(position).employee_id);
        holder.emp_name.setText(moviesListFilter.get(position).name);
        if(moviesListFilter.get(position).supervisor_id==null)
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
    }

    @Override
    public int getItemCount() {
        return moviesListFilter.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView,imageView_delete;
        TextView emp_id, emp_name,emp_type,supervisor_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            imageView_delete = itemView.findViewById(R.id.imageView_delete);
            emp_id = itemView.findViewById(R.id.emp_id);
            emp_name=itemView.findViewById(R.id.emp_name);
            emp_type=itemView.findViewById(R.id.emp_type);
            supervisor_name=itemView.findViewById(R.id.supervisor_name);

            imageView_delete.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(view.getId()==itemView.getId()) {
//                Toast.makeText(view.getContext(), moviesList.get(getAdapterPosition()).employee_id, Toast.LENGTH_SHORT).show();
            }
            else if( view.getId()==imageView_delete.getId())
            {
                Toast.makeText(view.getContext(), "Shall we delete", Toast.LENGTH_SHORT).show();


                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Delete access of "+moviesListFilter.get(getAdapterPosition()).name);
                // Click on Recover and a email will be sent to your registered email id
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String emp_email_to_b_deleted = moviesListFilter.get(getAdapterPosition()).email.toString().trim().replace("@", "at").replace(".", "dot");
                        String emp_id_to_b_deleted = moviesListFilter.get(getAdapterPosition()).employee_id.toString();

                        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

                        //THis needs to be reviewed... and
                        if(moviesListFilter.get(getAdapterPosition()).supervisor_id.equals("")) {
                            DatabaseReference db_temp=db.child("Employee");
                            db_temp.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Boolean managerHasSubordinates = false;
                                    for(DataSnapshot ds: snapshot.getChildren())
                                    {
                                        Log.d("chrc",ds.child("supervisor_id").getValue(String.class));
                                        Log.d("chrc",emp_id_to_b_deleted);
                                        if(emp_id_to_b_deleted.trim().equalsIgnoreCase(ds.child("supervisor_id").getValue(String.class).trim()))
                                        {
                                            managerHasSubordinates = true;
                                            Log.d("chrc---setted value",String.valueOf(managerHasSubordinates));
                                            break;
                                        }
                                    }

                                    if(!managerHasSubordinates)
                                    {
                                        db.child("Role").child(emp_email_to_b_deleted).removeValue();
                                        db.child("Employee").child(emp_id_to_b_deleted).removeValue();
                                        db.child("Manager").child(emp_id_to_b_deleted).removeValue();

                                        Toast.makeText(view.getContext(), "Employee Record deleted", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (managerHasSubordinates)
                                    {
                                        Toast.makeText(ctx, "Failure. The user supervisor to atleast 1 employee.", Toast.LENGTH_SHORT).show();

                                    }


                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {  }
                            });
                        } else {
                            db.child("Role").child(emp_email_to_b_deleted).removeValue();
                            db.child("Employee").child(emp_id_to_b_deleted).removeValue();
                            db.child("Manager").child(emp_id_to_b_deleted).removeValue();

                            Toast.makeText(view.getContext(), "Employee Record deleted", Toast.LENGTH_SHORT).show();

                        }

//                            Log.d("chrc----getting value",String.valueOf(managerIs));
                            Log.d("Content---", db.child("Role").child(emp_email_to_b_deleted).toString());
                            Log.d("Content---", db.child("Role").child(emp_email_to_b_deleted).getKey().toString());
                            db.child("Role").child(emp_email_to_b_deleted).removeValue();
                            Log.d("Content---", db.child("Employee").child(emp_id_to_b_deleted).toString());
                            Log.d("Content---", db.child("Employee").child(emp_id_to_b_deleted).getKey().toString());
                            db.child("Employee").child(emp_id_to_b_deleted).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(view.getContext(), "Employee Record deleted", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(view.getContext(), "Facing problem while deletion, please try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    }});
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
            else{}
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
            Log.d("filteredList MovieList", String.valueOf(moviesList.size()));
            Log.d("filteredList MvListAll", String.valueOf(moviesListFilter.size()));
            Log.d("filteredList char_seq", String.valueOf(charSequence));
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
//            filterResults.values = filteredList;
            filterResults.values = moviesListFilter;
//            Log.d("filtered list", String.valueOf(filterResults.size()));
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            /*moviesList.clear();
            Log.d("filtered list publish", String.valueOf(filterResults.values.toString()));
            moviesList.addAll((Collection<? extends Employee>) filterResults.values);
            notifyDataSetChanged();*/

            moviesListFilter=(ArrayList<Employee>) filterResults.values;
            notifyDataSetChanged();
        }
    };
}
