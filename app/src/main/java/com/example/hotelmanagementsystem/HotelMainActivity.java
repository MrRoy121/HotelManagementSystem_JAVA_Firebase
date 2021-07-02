package com.example.hotelmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HotelMainActivity extends AppCompatActivity {

    TextView hname, room, cost, cin,cout;
    Button confirm;
    FirebaseFirestore fs;
    ImageView himage;
    Map<String, String> data;
    FirebaseStorage fst;
    StorageReference storage;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_main);

        fs = FirebaseFirestore.getInstance();
        data = new HashMap<>();

        himage = findViewById(R.id.himage);
        hname = findViewById(R.id.hname);
        room = findViewById(R.id.room);
        cost = findViewById(R.id.cost);
        cin = findViewById(R.id.cin);
        cout =findViewById(R.id.cout);
        confirm = findViewById(R.id.confirm);
        fst = FirebaseStorage.getInstance();
        storage = fst.getReference();

        SharedPreferences i = getSharedPreferences("MySharedPref", MODE_PRIVATE);


        fs.collection("Hotels").document(i.getString("hid","")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot d) {
                hname.setText(d.getString("Name"));
                room.setText(d.getString("Rooms"));
                cost.setText(d.getString("Price"));
                data.put("Price", d.getString("Price"));
                data.put("HotelId", i.getString("hid",""));
            }
        });

        fs.collection("Bookings").document(i.getString("uid","")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot d) {
                cin.setText(d.getString("CheckIn"));
                cout.setText(d.getString("CheckOut"));
                data.put("CheckIn", d.getString("CheckIn"));
                data.put("CheckOut", d.getString("CheckOut"));
                data.put("userId", i.getString("uid",""));
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(HotelMainActivity.this)
                        .setTitle("Please Confirm!!")
                        .setMessage("Do you really want to Checkout?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                fs.collection("Bookings").document(i.getString("uid","")).delete();
                                fs.collection("BookingHistory").add(data);
                                SharedPreferences.Editor myEdit = i.edit();
                                myEdit.putString("uid", "");
                                myEdit.putString("hid", "");
                                myEdit.apply();
                                Toast.makeText(HotelMainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        try {
            StorageReference ds = storage.child("image/" + i.getString("hid",""));
            File l = File.createTempFile(i.getString("hid",""),"*");
            ds.getFile(l).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitmap = BitmapFactory.decodeFile(l.getAbsolutePath());
                    himage.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed!!  " + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        new androidx.appcompat.app.AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
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