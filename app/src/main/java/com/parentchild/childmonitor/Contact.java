package com.parentchild.childmonitor;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Contact extends AppCompatActivity {
    String username;
    ContactModel cm;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    List<ContactModel> cl;
    RecyclerView rec;
    ProgressBar pb;
    contact_adapter ca;
    FloatingActionButton del;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setTitle("Contacts");

        username = getIntent().getStringExtra("username");
        rec = findViewById(R.id.con_rec);
        del = findViewById(R.id.con_delete);
        pb = findViewById(R.id.con_pro);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this));
        cl = new ArrayList<>();
        ca = new contact_adapter(this, cl);
        rec.setAdapter(ca);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContacts();
            }
        });
        loadContacts();
    }

    public void loadContacts(){
        DatabaseReference ref  = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.child(auth.getUid()).child("Childrens").child(username).child("Contacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cl.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    String name = s.child("name").getValue().toString();
                    String number = s.child("mobileNumber").getValue().toString();
                    cm = new ContactModel("id", name, number);
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
    void deleteContacts(){
        DatabaseReference delLog = FirebaseDatabase.getInstance().getReference().child("Users");
        delLog.child(auth.getUid()).child("Childrens").child(username).child("Contacts").removeValue();
    }
}