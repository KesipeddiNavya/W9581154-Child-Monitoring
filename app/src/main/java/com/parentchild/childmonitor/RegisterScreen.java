package com.parentchild.childmonitor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterScreen extends AppCompatActivity {

    TextView loginTxt;
    EditText email_field, pass_field, name_txt, confirm_pass_field;
    Button reg_btn;
    String email, pass, confirm_pass, name, uid;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        getSupportActionBar().hide();

        loginTxt = findViewById(R.id.login_txt);
        name_txt = findViewById(R.id.reg_name);
        email_field = findViewById(R.id.reg_email);
        pass_field = findViewById(R.id.reg_pass);
        reg_btn = findViewById(R.id.reg_btn);
        confirm_pass_field = findViewById(R.id.reg_confirm_pass);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = name_txt.getText().toString();
                pass = pass_field.getText().toString();
                email = email_field.getText().toString();
                confirm_pass = confirm_pass_field.getText().toString();

                if(isValid(email, pass, confirm_pass)){
                    auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            uid = auth.getUid();
                            UserModel um = new UserModel(name, email, uid);
                            Toast.makeText(RegisterScreen.this, "Done !", Toast.LENGTH_SHORT).show();
                            ref.child(uid).setValue(um).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    SharedPreferences sharedPreferences = getSharedPreferences("ChildMonitor",MODE_PRIVATE);

                                    SharedPreferences.Editor data = sharedPreferences.edit();

                                    data.putString("parentName", name);
                                    data.commit();
                                    Toast.makeText(RegisterScreen.this, "Registered !", Toast.LENGTH_SHORT).show();
                                  startActivity(new Intent(RegisterScreen.this, MainActivity3.class));
                                }
                            });
                        }
                    });

                }
            }
        });

        loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterScreen.this, LoginScreen.class));
            }
        });

    }

    boolean isValid(String email, String pass, String confirm_pass) {
        if (pass.length() < 6) {
            pass_field.setError("Password should be atleast of 6 characters");
            return false;
        }
        if (!(pass.equals(confirm_pass))) {
            confirm_pass_field.setError("The password should be same as the previous password");
            return false;
        }
        if (!email.contains("@")) {
            email_field.setError("Email is invalid");
            return false;
        }
        return true;
    }
}