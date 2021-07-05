package com.example.hotelmanagementsystem.model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hotelmanagementsystem.R;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class CustomAdapter extends ArrayAdapter<hotel> {

    public CustomAdapter(@NonNull Context context, ArrayList<hotel> arrayList) {
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

        TextView cin = currentItemView.findViewById(R.id.cin);
        cin.setText(currentNumberPosition.getRoom());
        TextView cout = currentItemView.findViewById(R.id.cout);
        cout.setText(currentNumberPosition.getPrice());
        TextView price = currentItemView.findViewById(R.id.price);
        price.setText(currentNumberPosition.getAddress());

        return currentItemView;
    }
}