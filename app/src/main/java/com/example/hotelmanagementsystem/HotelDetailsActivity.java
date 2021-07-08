package com.example.hotelmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HotelDetailsActivity extends AppCompatActivity {

    TextView hname, room, cost, details;
    Button book;
    FirebaseFirestore fs;
    ImageView himage;
    FirebaseStorage fst;
    StorageReference storage;
    Bitmap bitmap;

    private int mYear, mMonth, mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);

        himage = findViewById(R.id.himage);
        fs = FirebaseFirestore.getInstance();

        hname = findViewById(R.id.hname);
        room = findViewById(R.id.room);
        cost = findViewById(R.id.cost);
        details = findViewById(R.id.det);
        book = findViewById(R.id.book);
        fst = FirebaseStorage.getInstance();
        storage = fst.getReference();
        Intent i = getIntent();


        Log.e("Asd", i.getStringExtra("hid"));
        fs.collection("Hotels").document(i.getStringExtra("hid")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot d) {
                hname.setText(d.getString("Name"));
                room.setText(d.getString("Rooms"));
                cost.setText(d.getString("Price"));
                details.setText(d.getString("Details"));
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final Dialog dialog = new Dialog(HotelDetailsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.book_dialog);

                TextView c1 = dialog.findViewById(R.id.cidate);
                TextView c2 = dialog.findViewById(R.id.codate);
                TextView d = dialog.findViewById(R.id.date);

                ImageView visa = dialog.findViewById(R.id.visa);
                ImageView master = dialog.findViewById(R.id.mstr);

                EditText num = dialog.findViewById(R.id.num);
                EditText cvv = dialog.findViewById(R.id.cvv);
                visa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        master.setBackgroundColor(getResources().getColor(R.color.white));
                        visa.setBackgroundColor(getResources().getColor(R.color.select));
                    }
                });
                master.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        master.setBackgroundColor(getResources().getColor(R.color.select));
                        visa.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                });
                cvv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            if (cvv.getText().toString().trim().length() < 3)
                                cvv.setError("Failed");
                            else
                                cvv.setError(null);
                        }
                    }
                });
                num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            if (num.getText().toString().trim().length() < 16)
                                num.setError("Failed");
                            else
                                num.setError(null);
                        }
                    }
                });
                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(HotelDetailsActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        c1.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.setTitle("Date Of Check In");
                        datePickerDialog.show();
                    }
                });
                c2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(HotelDetailsActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        c2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.setTitle("Date Of Check Out");
                        datePickerDialog.show();
                    }
                });
                d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(HotelDetailsActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        d.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.setTitle("Date Of Check In");
                        datePickerDialog.show();
                    }
                });

                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button book = (Button) dialog.findViewById(R.id.book);
                book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(c1.getText().length()==0||c2.getText().length()==0){
                            Toast.makeText(HotelDetailsActivity.this,"Please Provide the both Check In Check Out Date!!",Toast.LENGTH_LONG).show();
                        }else if(num.getText().length()<16||cvv.getText().length()<3||d.getText().length()==0){
                            Toast.makeText(HotelDetailsActivity.this,"Please Provide the Payment Details!!",Toast.LENGTH_LONG).show();
                        } else{
                            fs.collection("User").document(i.getStringExtra("uid")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot d) {
                                    Map<String, String> data = new HashMap<>();
                                    data.put("UserPhone", d.getString("Phone"));
                                    data.put("UserName", d.getString("Name"));
                                    data.put("CheckIn", c1.getText().toString());
                                    data.put("CheckOut", c2.getText().toString());
                                    data.put("HotelID", i.getStringExtra("hid"));

                                    fs.collection("Bookings").document(i.getStringExtra("uid")).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(HotelDetailsActivity.this, "Hotel Booked..", Toast.LENGTH_SHORT).show();
                                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                            myEdit.putString("uid", i.getStringExtra("uid"));
                                            myEdit.putString("hid", i.getStringExtra("hid"));
                                            myEdit.apply();
                                            startActivity(new Intent(HotelDetailsActivity.this, HotelMainActivity.class));
                                            finish();
                                        }
                                    });
                                }
                            });

                        }
                    }
                });
                dialog.show();


            }
        });

        try {
            StorageReference ds = storage.child("image/" + i.getStringExtra("hid"));
            File l = File.createTempFile(i.getStringExtra("hid"),"*");
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


}