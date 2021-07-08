package com.example.hotelmanagementsystem.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.hotelmanagementsystem.R;
import com.example.hotelmanagementsystem.admin.EditHotelActivity;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class AdminhotelAdapter extends ArrayAdapter<hotel> {

    public AdminhotelAdapter (@NonNull Context context, ArrayList<hotel> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_history_item, parent, false);
        }

        hotel currentNumberPosition = getItem(position);

        TextView hname = currentItemView.findViewById(R.id.hname);
        hname.setText(currentNumberPosition.getName());

        TextView t1 = currentItemView.findViewById(R.id.t1);
        t1.setText("Price :  ");
        TextView t2 = currentItemView.findViewById(R.id.t2);
        t2.setText("Rooms :  ");
        TextView t3 = currentItemView.findViewById(R.id.t3);
        t3.setText("Address :  ");
        TextView cin = currentItemView.findViewById(R.id.cin);
        cin.setText(currentNumberPosition.getPrice());
        TextView cout = currentItemView.findViewById(R.id.cout);
        cout.setText(currentNumberPosition.getRoom());
        TextView price = currentItemView.findViewById(R.id.price);
        price.setText(currentNumberPosition.getAddress());

        CardView cd = currentItemView.findViewById(R.id.cd);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditHotelActivity.class);
                i.putExtra("hid", currentNumberPosition.getUid());
                getContext().startActivity(i);
            }
        });
        return currentItemView;
    }
}