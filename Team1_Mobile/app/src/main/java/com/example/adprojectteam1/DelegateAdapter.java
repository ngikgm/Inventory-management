package com.example.adprojectteam1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DelegateAdapter extends ArrayAdapter<Staff> {
    private Context context;
    public int chosenId = -1;

    public DelegateAdapter(Context context, int resourceId, ArrayList<Staff> staffs) {
        super(context, resourceId, staffs);
        this.context = context;
    }

    public View getView(int pos, View view, ViewGroup parent) {

        //get information from array
        int staffId = getItem(pos).getStaffId();
        String name = getItem(pos).getName();
        String position = getItem(pos).getPosition();




        
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.listview_delegate_item, null);






        TextView StaffNameItem = (TextView) view.findViewById(R.id.StaffNameItem);
        TextView StaffPositionItem = (TextView) view.findViewById(R.id.StaffPositionItem);


        StaffNameItem.setText(name);
        StaffPositionItem.setText(position);

        return view;


    }
}
