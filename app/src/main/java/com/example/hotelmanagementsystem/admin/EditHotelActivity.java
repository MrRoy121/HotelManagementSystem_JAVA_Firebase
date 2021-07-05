package com.example.hotelmanagementsystem.admin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hotelmanagementsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditHotelActivity extends AppCompatActivity {

    EditText name, address, room, per, det;
    Button add,browse;
    FirebaseFirestore fs;
    Map<String, String> data;
    Uri filepath;
    ImageView img;
    FirebaseStorage fst;
    StorageReference storage;
    Boolean show = false ;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hotel);
        img = findViewById(R.id.img);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        room = findViewById(R.id.room);
        add = findViewById(R.id.add);
        browse = findViewById(R.id.browse);
        per = findViewById(R.id.per);
        det = findViewById(R.id.det);
        data = new HashMap<>();
        fst = FirebaseStorage.getInstance();
        storage = fst.getReference();
        fs = FirebaseFirestore.getInstance();



        Intent i = getIntent();
        fs.collection("Hotels").document(i.getStringExtra("hid")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot d) {
                if(d.exists()){
                    name.setText(d.getString("Name"));
                    address.setText(d.getString("Address"));
                    room.setText(d.getString("Rooms"));
                    per.setText(d.getString("Price"));
                    det.setText(d.getString("Details"));
                }
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().length()==0 || address.getText().length()==0 || room.getText().length()==0 || per.getText().length()==0 || det.getText().length()==0){
                    Toast.makeText(EditHotelActivity.this, "All Fields Are Required!!", Toast.LENGTH_SHORT).show();
                }else if(!show){
                    Toast.makeText(EditHotelActivity.this, "Browse and Select an Image file!!", Toast.LENGTH_SHORT).show();
                }else{
                    data.put("Name", name.getText().toString());
                    data.put("Address", address.getText().toString());
                    data.put("Rooms", room.getText().toString());
                    data.put("Price", per.getText().toString());
                    data.put("Details", det.getText().toString());
                    fs.collection("Hotels").document(i.getStringExtra("hid")).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            uploadtofirebase(i.getStringExtra("hid"));
                            Toast.makeText(EditHotelActivity.this, "Hotel Edited", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosepic();
            }
        });

        try {
            StorageReference ds = storage.child("image/" + i.getStringExtra("hid"));
            File l = File.createTempFile(i.getStringExtra("hid"),"*");
            ds.getFile(l).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitmap = BitmapFactory.decodeFile(l.getAbsolutePath());
                    img.setImageBitmap(bitmap);
                    show = true;

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
    private void uploadtofirebase(String userID) {

        StorageReference d = storage.child("image/" + userID);
        d.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Image Uploaded",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed!!  " + e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void chosepic(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(i);
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        filepath = data.getData();
                        img.setImageURI(filepath);
                        show = true;
                    }
                }
            });
}