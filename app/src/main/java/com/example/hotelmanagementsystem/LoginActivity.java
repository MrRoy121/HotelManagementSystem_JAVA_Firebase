package com.example.hotelmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmanagementsystem.admin.MainActivity2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email, pass;
    Button login, alogin;
    TextView register;
    FirebaseAuth fa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.login);
        alogin = findViewById(R.id.alogin);
        register = findViewById(R.id.register);
        fa = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fa.getCurrentUser();
        if(currentUser!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().length()==0 || pass.getText().length()==0){
                    Toast.makeText(LoginActivity.this, "Email & Passwords Are Required!!", Toast.LENGTH_SHORT).show();
                }else{
                    fa.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(
                                                @NonNull Task<AuthResult> task)
                                        {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                }
            }
        });

        alogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().length()==0 || pass.getText().length()==0){
                    Toast.makeText(LoginActivity.this, "Email & Passwords Are Required!!", Toast.LENGTH_SHORT).show();
                }else{
                    if(email.getText().toString().equals("admin@gmail.com")&&pass.getText().toString().equals("admin")){
                        Toast.makeText(LoginActivity.this, "Logged In as Admin", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Email And Password Didn't Matched!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}