package com.parentchild.childmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

public class Map extends AppCompatActivity {

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().setTitle("Map");
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        username = sh.getString("username", "");

        Fragment frag = new MapFragment(username);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, frag).commit();

    }
}