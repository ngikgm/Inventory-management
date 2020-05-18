package com.example.adprojectteam1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ApproveRequestAdapter extends ArrayAdapter<Orders> {

    private Context context;
    public int chosenId = -1;

    public ApproveRequestAdapter(Context context, int resourceId, ArrayList<Orders> orders) {
        super(context, resourceId, orders);
        this.context = context;
    }

    public View getView(int pos, View view, ViewGroup parent){
        //get information from array
        int id =getItem(pos).getOrdersId();
        String staffName = getItem(pos).getStaff_obj().getName();
        String orderItem = getItem(pos).getItem_obj().getItem_description();
        String orderQuantity = String.valueOf(getItem(pos).getProposed_quantity());




        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.listview_approve_request_item, null);






        TextView ApproveRequestStaffNameItem = (TextView) view.findViewById(R.id.ApproveRequestStaffNameItem);
        TextView ApproveRequestOrderItem = (TextView) view.findViewById(R.id.ApproveRequestOrderItem);
        TextView ApproveRequestOrderQuantityItem = (TextView) view.findViewById(R.id.ApproveRequestOrderQuantityItem);
        Button ApproveRequestRejectItem = (Button) view.findViewById(R.id.ApproveRequestRejectItem);
        Button ApproveRequestApproveItem = (Button) view.findViewById(R.id.ApproveRequestApproveItem);


        ApproveRequestStaffNameItem.setText(staffName);
        ApproveRequestOrderItem.setText(orderItem);
        ApproveRequestOrderQuantityItem.setText(orderQuantity);

        ApproveRequestRejectItem.setTag(id);
        ApproveRequestApproveItem.setTag(id);





        return view;
    }
}
