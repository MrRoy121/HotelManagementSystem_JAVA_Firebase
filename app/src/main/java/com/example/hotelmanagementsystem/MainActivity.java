package com.example.hotelmanagementsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.hotelmanagementsystem.model.hotel;
import com.example.hotelmanagementsystem.model.hotelAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore fs;
    FirebaseAuth fa;
    ArrayList<hotel> myListData;
    hotelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fs = FirebaseFirestore.getInstance();
        fa = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fa.getCurrentUser();
        if(currentUser==null){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        SharedPreferences i = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        if(!i.getString("hid", "").equals("")){
            startActivity(new Intent(getApplicationContext(),HotelMainActivity.class));
            finish();
        }

        findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
        RecyclerView recyclerView = findViewById(R.id.all);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myListData = new ArrayList<>();
        fs.collection("Hotels").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                if(!q.isEmpty()){
                    for(DocumentSnapshot d: q){
                        myListData.add(new hotel(d.getString("Name"),d.getString("Address"),d.getString("Rooms"),d.getString("Price"),d.getId()));
                        }
                    }
                    adapter = new hotelAdapter(myListData, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                }).setNegativeButton("No", null).show();
    }
}