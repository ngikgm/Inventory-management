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

public class ClerkDisbursementListAdapter extends ArrayAdapter<Orders> {

    private Context context;
    public int chosenId = -1;

    public ClerkDisbursementListAdapter(Context context, int resourceId, ArrayList<Orders> orders) {
        super(context, resourceId, orders);
        this.context = context;
    }

    public View getView(int pos, View view, ViewGroup parent){
        //get information from array

        String orderItem = getItem(pos).getItem_obj().getItem_description();
        String actual_delivered_quantity_by_clerk= String.valueOf(getItem(pos).getActual_delivered_quantity_by_clerk());
        String actual_received_quantity_by_representative = String.valueOf(getItem(pos).getActual_received_quantity_by_representative());
        String delivered_order_date = String.valueOf(getItem(pos).getDelivered_order_date());





        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.listview_clerk_disbursement_list_item, null);






        TextView DisbursementListItemNameItem = (TextView) view.findViewById(R.id.DisbursementListItemNameItem);
        TextView DisbursementListDeliveredQuantityItem = (TextView) view.findViewById(R.id.DisbursementListDeliveredQuantityItem);
        TextView DisbursementListAcknowledgedQuantityItem = (TextView) view.findViewById(R.id.DisbursementListAcknowledgedQuantityItem);
        TextView DisbursementListDeliveredDateItem = (TextView) view.findViewById(R.id.DisbursementListDeliveredDateItem);



        DisbursementListItemNameItem.setText(orderItem);
        DisbursementListDeliveredQuantityItem.setText(actual_delivered_quantity_by_clerk);
        DisbursementListAcknowledgedQuantityItem.setText(actual_received_quantity_by_representative);
        DisbursementListDeliveredDateItem.setText(delivered_order_date);







        return view;
    }
}
