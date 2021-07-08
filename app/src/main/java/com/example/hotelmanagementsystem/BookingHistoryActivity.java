package com.example.hotelmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hotelmanagementsystem.model.CustomAdapter;
import com.example.hotelmanagementsystem.model.hotel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BookingHistoryActivity extends AppCompatActivity {

    FirebaseFirestore fs;
    FirebaseAuth fa;
    ArrayList<hotel> list;
    CustomAdapter n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        fs = FirebaseFirestore.getInstance();
        fa = FirebaseAuth.getInstance();
        list = new ArrayList<>();
        ListView numbersListView = findViewById(R.id.lll);

        fs.collection("BookingHistory").whereEqualTo("userId",fa.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                if(!q.isEmpty()){
                    for(DocumentSnapshot d: q){
                        fs.collection("Hotels").document(d.getString("HotelId")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                list.add(new hotel(documentSnapshot.getString("Name"),d.getString("Price"),d.getString("CheckIn"),d.getString("CheckOut"),null));
                                n = new CustomAdapter(BookingHistoryActivity.this, list);
                                numbersListView.setAdapter(n);
                            }
                        });
                    }
                }
            }
        });
    }
}