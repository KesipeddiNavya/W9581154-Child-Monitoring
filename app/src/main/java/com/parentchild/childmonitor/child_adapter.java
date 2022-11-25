package com.parentchild.childmonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class child_adapter extends RecyclerView.Adapter<child_adapter.ViewHolder> {

    List<ChildModel> cl;
    Context context;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public child_adapter(Context context, List<ChildModel> cl){
        this.context = context;
        this.cl = cl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(cl.get(position).getName());
        holder.email.setText("Email : "+cl.get(position).getEmail());
        holder.pass.setText("Password : "+cl.get(position).getPass());
        holder.username.setText("Username : "+cl.get(position).getUsername());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                ref.child(auth.getUid()).child("Childrens").child(cl.get(position).getUsername()).removeValue();
                Toast.makeText(context, "Child deleted !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cl.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView name, email, pass, username;// init the item view's
        ImageButton del;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.child_name);
            email = itemView.findViewById(R.id.child_email);
            pass = itemView.findViewById(R.id.child_pass);
            username = itemView.findViewById(R.id.child_username);
            del = itemView.findViewById(R.id.imageButton);
        }
    }
}
