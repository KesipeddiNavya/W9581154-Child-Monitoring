package com.parentchild.childmonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class contact_adapter extends RecyclerView.Adapter<contact_adapter.ViewHolder> {

    List<ContactModel> cl;
    Context context;

    public contact_adapter(Context context, List<ContactModel> cl){
        this.context = context;
        this.cl = cl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(cl.get(position).getName());
        holder.number.setText(cl.get(position).getMobileNumber());
    }

    @Override
    public int getItemCount() {
        return cl.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView name, number;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.msg_number);
            number = itemView.findViewById(R.id.msg_body);
        }
    }
}
