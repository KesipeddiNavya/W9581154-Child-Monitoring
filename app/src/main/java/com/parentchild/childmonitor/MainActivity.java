package com.parentchild.childmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Animation fade_in, pop_up;
    TextView heading;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        heading = findViewById(R.id.heading);

        pop_up = AnimationUtils.loadAnimation(this, R.anim.pop_up);
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        heading.setAnimation(fade_in);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(auth.getCurrentUser() != null){
                    startActivity(new Intent(MainActivity.this, MainActivity3.class));
                }
                else{
                    startActivity(new Intent(MainActivity.this, LoginScreen.class));
                }
            }
        }, 2500);

    }
}