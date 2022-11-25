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

import java.util.ArrayList;
import java.util.List;

public class Message extends AppCompatActivity {

    String username;
    MessageModel mm;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    List<MessageModel> ml;
    RecyclerView rec;
    ProgressBar pb;
    msg_adapter ma;
    String date, time, type, number, msg;
    FloatingActionButton del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getSupportActionBar().setTitle("Messages");

        getSupportActionBar().setIcon(R.drawable.ic_baseline_email_24);
        username = getIntent().getStringExtra("username");
        rec = findViewById(R.id.msg_rec);
        del = findViewById(R.id.msg_delete);
        pb = findViewById(R.id.msg_pro);
        pb.setVisibility(View.VISIBLE);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this));
        ml = new ArrayList<>();
        ma = new msg_adapter(Message.this, ml);

        rec.setAdapter(ma);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMessages();
            }
        });

        loadMessages();
    }

    public void loadMessages() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.child(auth.getUid()).child("Childrens").child(username).child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ml.clear();
                for (DataSnapshot s : snapshot.getChildren()) {
                    String date = s.child("date").getValue().toString();
                    String time = s.child("time").getValue().toString();
                    String number = s.child("number").getValue().toString();
                    String msg = s.child("msg").getValue().toString();
                    String type = s.child("type").getValue().toString();
                    mm = new MessageModel(msg, number, type, date, time);
                    ml.add(mm);
                }
                ma.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void deleteMessages(){
        DatabaseReference delLog = FirebaseDatabase.getInstance().getReference().child("Users");
        delLog.child(auth.getUid()).child("Childrens").child(username).child("Messages").removeValue();
    }
}