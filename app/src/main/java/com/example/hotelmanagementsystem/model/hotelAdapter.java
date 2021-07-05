package com.example.hotelmanagementsystem.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelmanagementsystem.HotelDetailsActivity;
import com.example.hotelmanagementsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class hotelAdapter extends RecyclerView.Adapter<hotelAdapter.ViewHolder>{
    private ArrayList<hotel> listdata;
    Context context;
    FirebaseFirestore fs;
    FirebaseAuth fa;

    public hotelAdapter(ArrayList<hotel>  listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.hotel_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final hotel myListData = listdata.get(position);
        holder.t1.setText(listdata.get(position).getName());
        holder.t2.setText(listdata.get(position).getAddress());
        holder.t3.setText(listdata.get(position).getRoom());
        holder.t4.setText(listdata.get(position).getPrice());
        fs = FirebaseFirestore.getInstance();
        fa = FirebaseAuth.getInstance();
        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, HotelDetailsActivity.class);
                i.putExtra("uid", fa.getCurrentUser().getUid());
                i.putExtra("hid", myListData.uid);
                context.startActivity(i);
             }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView t1,t2,t3,t4;
        public Button book;
        public ViewHolder(View itemView) {
            super(itemView);
            this.t1 = (TextView) itemView.findViewById(R.id.name);
            this.t2 = (TextView) itemView.findViewById(R.id.address);
            this.t3 = (TextView) itemView.findViewById(R.id.room);
            this.t4 = (TextView) itemView.findViewById(R.id.cost);
            this.book =  itemView.findViewById(R.id.book);
        }
    }

}
