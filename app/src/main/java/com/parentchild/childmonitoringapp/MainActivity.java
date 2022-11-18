package com.parentchild.childmonitoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Animation fade_in, pop_up;
    TextView heading;

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
                startActivity(new Intent(MainActivity.this, LoginScreen.class));
            }
        }, 2500);

    }
}