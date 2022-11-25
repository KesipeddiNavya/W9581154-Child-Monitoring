package com.parentchild.childmonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class CallLogs extends AppCompatActivity {
    String username;
    CallLogModel cm;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    List<CallLogModel> cl;
    RecyclerView rec;
    ProgressBar pb;
    c_adapter ca;
    FloatingActionButton del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_logs);
        getSupportActionBar().setTitle("Call Logs");

        username = getIntent().getStringExtra("username");
        rec = findViewById(R.id.cl_rec);
        del = findViewById(R.id.log_delete);
        pb = findViewById(R.id.cl_pro);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this));
        cl = new ArrayList<>();
        ca = new c_adapter(this, cl);
        rec.setAdapter(ca);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCallLogs();
            }
        });

        loadCallLog();
    }

    public void loadCallLog(){
        DatabaseReference ref  = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.child(auth.getUid()).child("Childrens").child(username).child("CallLogs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cl.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    String name = s.child("name").getValue().toString();
                    String date = s.child("date").getValue().toString();
                    String time = s.child("time").getValue().toString();
                    String duration = s.child("duration").getValue().toString();
                    String type = s.child("type").getValue().toString();
                    String number = s.child("number").getValue().toString();
                    cm = new CallLogModel(name, date, time, duration, type, number);
                    cl.add(cm);
                }
                ca.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void deleteCallLogs(){
        DatabaseReference delLog = FirebaseDatabase.getInstance().getReference().child("Users");
        delLog.child(auth.getUid()).child("Childrens").child(username).child("CallLogs").removeValue();
    }
}