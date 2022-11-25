package com.parentchild.childmonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class c_adapter extends RecyclerView.Adapter<c_adapter.ViewHolder> {

    List<CallLogModel> cl;
    Context context;

    public c_adapter(Context context, List<CallLogModel> cl){
        this.context = context;
        this.cl = cl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_log_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(cl.get(position).getName());
        holder.number.setText(cl.get(position).getNumber());
        holder.time.setText(cl.get(position).getTime() + " on "+cl.get(position).getDate());
        holder.type.setText(cl.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return cl.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView name, number, time, type;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.clname);
            type = itemView.findViewById(R.id.cltype);
            number = itemView.findViewById(R.id.clnumber);
            time = itemView.findViewById(R.id.cltime);
        }
    }
}
