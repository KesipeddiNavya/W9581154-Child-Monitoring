package com.parentchild.childmonitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class msg_adapter extends RecyclerView.Adapter<msg_adapter.ViewHolder> {

    List<MessageModel> ml;
    Context context;

    public msg_adapter(Context context, List<MessageModel> ml){
        this.context = context;
        this.ml = ml;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.number.setText(ml.get(position).getNumber());
        holder.body.setText(ml.get(position).getMsg());
        holder.time.setText(ml.get(position).getTime() + " on "+ml.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return ml.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView number, body, time;
        public ViewHolder(View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.msg_number);
            time = itemView.findViewById(R.id.msg_time);
            body = itemView.findViewById(R.id.msg_body);
        }
    }
}
