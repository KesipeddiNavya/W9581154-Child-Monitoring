package com.parentchild.childmonitor.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parentchild.childmonitor.AddChild;
import com.parentchild.childmonitor.CallLogs;
import com.parentchild.childmonitor.Contact;
import com.parentchild.childmonitor.Map;
import com.parentchild.childmonitor.MapFragment;
import com.parentchild.childmonitor.Message;
import com.parentchild.childmonitor.R;
import com.parentchild.childmonitor.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    CardView map, message, contact, call_log;
    FloatingActionButton add;
    List<String> usernames = new ArrayList<>();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String selectedUsername;
    ProgressBar pb;
    TextView username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        map = root.findViewById(R.id.map);
        message = root.findViewById(R.id.message);
        contact = root.findViewById(R.id.contact);
        call_log = root.findViewById(R.id.call_log);
        add = root.findViewById(R.id.add);
        username = root.findViewById(R.id.username);
        pb = root.findViewById(R.id.pb18);

        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        selectedUsername = sh.getString("username", "");
        username.setText(selectedUsername);

        FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid()).child("Childrens").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usernames.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    String name = s.child("username").getValue().toString();
                    usernames.add(name);
                }

                pb.setVisibility(View.GONE);

                Spinner spino = root.findViewById(R.id.spinner);
                spino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedUsername = usernames.get(position);
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);
                        SharedPreferences.Editor data = sharedPreferences.edit();
                        data.putString("username", selectedUsername);
                        data.commit();
                        username.setText(selectedUsername);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                ArrayAdapter ad
                        = new ArrayAdapter(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        usernames);

                // set simple layout resource file
                // for each item of spinner
                ad.setDropDownViewResource(
                        android.R.layout
                                .simple_spinner_dropdown_item);

                spino.setAdapter(ad);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pb.setVisibility(View.GONE);

            }
        });


        call_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CallLogs.class).putExtra("username", selectedUsername));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddChild.class));
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Contact.class).putExtra("username", selectedUsername));
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Message.class).putExtra("username", selectedUsername));
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Map.class).putExtra("username", selectedUsername));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}