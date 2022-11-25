package com.parentchild.childmonitor.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parentchild.childmonitor.ChildModel;
import com.parentchild.childmonitor.LoginScreen;
import com.parentchild.childmonitor.R;
import com.parentchild.childmonitor.child_adapter;
import com.parentchild.childmonitor.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    List<ChildModel> cl;
    ChildModel cm;
    RecyclerView rec;
    child_adapter ca;
    TextView parent_name;
    String name;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FloatingActionButton logout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rec = root.findViewById(R.id.child_rec);
        logout = root.findViewById(R.id.logout);
        parent_name = root.findViewById(R.id.profile_name);
        cl = new ArrayList<>();
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        ca = new child_adapter(getContext(), cl);
        rec.setAdapter(ca);

        SharedPreferences pref = getContext().getSharedPreferences("ChildMonitor", Context.MODE_PRIVATE);
        name = pref.getString("parentName", "");

        if (name != "")
            parent_name.setText("Hey "+name);
        else
            getName();

        loadChildrens();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getContext(), LoginScreen.class));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getName(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("name").getValue().toString();
                parent_name.setText("Hey "+name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadChildrens(){
        FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid()).child("Childrens").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cl.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    String name = s.child("name").getValue().toString();
                    String email = s.child("email").getValue().toString();
                    String username = s.child("username").getValue().toString();
                    String pass = s.child("pass").getValue().toString();

                    cm = new ChildModel(name, username, email, pass, auth.getUid());
                    cl.add(cm);
                }
                ca.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}