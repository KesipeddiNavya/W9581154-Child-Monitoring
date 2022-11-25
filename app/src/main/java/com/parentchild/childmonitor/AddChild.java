package com.parentchild.childmonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddChild extends AppCompatActivity {

    EditText name_field, email_field, pass_field, username_field;
    String name, email, pass, username;
    Button add;
    ChildModel cm;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Childrens");
    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        email_field = findViewById(R.id.add_email);
        pass_field = findViewById(R.id.add_pass);
        name_field = findViewById(R.id.add_name);
        add = findViewById(R.id.add_btn);
        username_field = findViewById(R.id.add_username);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_field.getText().toString();
                pass = pass_field.getText().toString();
                name = name_field.getText().toString();
                username = username_field.getText().toString();

                cm = new ChildModel(name, username,email, pass, auth.getUid());
                ref.push().setValue(cm).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ref2.child(auth.getUid()).child("Childrens").child(username).setValue(cm);
                        Toast.makeText(AddChild.this, "Child Added !", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                        SharedPreferences.Editor data = sharedPreferences.edit();
                        data.putString("username", username);
                        data.commit();
                        startActivity(new Intent(AddChild.this, MainActivity3.class));
                    }
                });


            }
        });

    }
}