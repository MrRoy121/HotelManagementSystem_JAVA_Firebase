package com.example.hotelmanagementsystem.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.hotelmanagementsystem.BookingHistoryActivity;
import com.example.hotelmanagementsystem.MainActivity;
import com.example.hotelmanagementsystem.R;
import com.example.hotelmanagementsystem.model.AdminhotelAdapter;
import com.example.hotelmanagementsystem.model.CustomAdapter;
import com.example.hotelmanagementsystem.model.hotel;
import com.example.hotelmanagementsystem.model.hotelAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HotelListActivity extends AppCompatActivity {


    FirebaseFirestore fs;
    FirebaseAuth fa;
    ArrayList<hotel> myListData;
    AdminhotelAdapter n;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        fs = FirebaseFirestore.getInstance();
        fa = FirebaseAuth.getInstance();
        ListView numbersListView = findViewById(R.id.lll);

        myListData = new ArrayList<>();
        fs.collection("Hotels").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot q) {
                if(!q.isEmpty()){
                    for(DocumentSnapshot d: q){
                        myListData.add(new hotel(d.getString("Name"),d.getString("Address"),d.getString("Price"),d.getString("Rooms"),d.getId()));
                        n = new AdminhotelAdapter(HotelListActivity.this, myListData);
                        numbersListView.setAdapter(n);}
                }
            }
        });

    }
}