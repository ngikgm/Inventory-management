package com.example.adprojectteam1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class RepresentativeDisbursementListAdapter extends ArrayAdapter<Orders> {

    private Context context;
    public int chosenId = -1;

    public RepresentativeDisbursementListAdapter(Context context, int resourceId, ArrayList<Orders> orders) {
        super(context, resourceId, orders);
        this.context = context;
    }

    public View getView(int pos, View view, ViewGroup parent){
        //get information from array
        String id =String.valueOf(getItem(pos).getOrdersId());
        String deliveredOrderDate= getItem(pos).getDelivered_order_date();
        String orderItem = getItem(pos).getItem_obj().getItem_description();
        String orderQuantity = String.valueOf(getItem(pos).getProposed_quantity());




        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.listview_representative_disbursement_item, null);






        TextView RepresentativeOrderIDItem = (TextView) view.findViewById(R.id.RepresentativeOrderIDItem);
        TextView RepresentativeDeliveredDateItem = (TextView) view.findViewById(R.id.RepresentativeDeliveredDateItem);
        TextView RepresentativeItemNameItem = (TextView) view.findViewById(R.id.RepresentativeItemNameItem);
        TextView RepresentativeDeliveredQuantityItem = (TextView) view.findViewById(R.id.RepresentativeDeliveredQuantityItem);
        EditText RepresentativeActualQuantityItem = (EditText) view.findViewById(R.id.RepresentativeActualQuantityItem);


        RepresentativeOrderIDItem.setText(id);
        RepresentativeDeliveredDateItem.setText(deliveredOrderDate);
        RepresentativeItemNameItem.setText(orderItem);
        RepresentativeDeliveredQuantityItem.setText(orderQuantity);
        RepresentativeActualQuantityItem.setHint(orderQuantity);






        return view;
    }
}
