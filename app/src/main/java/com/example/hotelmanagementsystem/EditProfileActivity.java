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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    EditText email, pass, name, phone;
    Button update;
    Map<String,String> data;
    FirebaseFirestore fs;
    FirebaseAuth fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.password);
        update = findViewById(R.id.update);
        data = new HashMap<>();
        fs = FirebaseFirestore.getInstance();
        fa = FirebaseAuth.getInstance();


        fs.collection("User").document(fa.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot d) {
                name.setText(d.getString("Name"));
                phone.setText(d.getString("Phone"));
                email.setText(d.getString("Email"));
                pass.setText(d.getString("Password"));
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().length()==0 || pass.getText().length()==0 || name.getText().length()==0 || phone.getText().length()==0){
                    Toast.makeText(EditProfileActivity.this, "All Fields Are Required!!", Toast.LENGTH_SHORT).show();
                }else{
                    data.put("Name", name.getText().toString());
                    data.put("Email", email.getText().toString());
                    data.put("Phone", phone.getText().toString());
                    data.put("Password", pass.getText().toString());

                    Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                    fs.collection("User").document(fa.getCurrentUser().getUid()).set(data);
                    Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}