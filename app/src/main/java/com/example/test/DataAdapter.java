package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;

public class DataAdapter extends ArrayAdapter<User> {

    private Context context;
    private List<User> arrayList;

    public DataAdapter(@NonNull Context context, List<User> list) {
        super(context, -1, list);
        this.context = context;
        this.arrayList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.contact_item, parent, false);

        TextView txtName = (TextView) rowView.findViewById(R.id.txt_name);
        TextView txtPhone = (TextView) rowView.findViewById(R.id.txt_number);
        ImageView imgUser = (ImageView) rowView.findViewById(R.id.image_view);


        User user = arrayList.get(position);

        txtName.setText(user.getFirst_name() + " " + user.getLast_name());
        txtPhone.setText(user.getPhone_number());

        imgUser.setImageDrawable(context.getDrawable(user.getPhoto_user()));

        notifyDataSetChanged();

        return rowView;
    }
}
