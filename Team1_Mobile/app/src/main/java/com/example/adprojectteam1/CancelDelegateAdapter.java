package com.example.adprojectteam1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

public class CancelDelegateAdapter extends ArrayAdapter<Staff_representative> {

    private Context context;
    public int chosenId = -1;

    public CancelDelegateAdapter(Context context, int resourceId, ArrayList<Staff_representative> Staff_representatives) {
        super(context, resourceId, Staff_representatives);
        this.context = context;
    }

    public View getView(int pos, View view, ViewGroup parent){
        //get information from array
        final int id =getItem(pos).getStaff_representativeId();
        String name = getItem(pos).getRepresentative_staff_obj().getName();
        String startDate = getItem(pos).getStart_date();
        String startDate1 = startDate.substring(8,10)+startDate.substring(4,8)+startDate.substring(0,4);
        String endDate = getItem(pos).getEnd_date();
        String endDate1 = endDate.substring(8,10)+endDate.substring(4,8)+endDate.substring(0,4);

        String position = getItem(pos).getPosition();




        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.listview_cancel_delegate_item, null);






        final TextView CancelDelegateStaffNameItem = (TextView) view.findViewById(R.id.CancelDelegateStaffNameItem);
        TextView DutyStartDateItem = (TextView) view.findViewById(R.id.DutyStartDateItem);
        TextView DutyEndDateItem = (TextView) view.findViewById(R.id.DutyEndDateItem);
        Button CancelDelegateItem = (Button) view.findViewById(R.id.CancelDelegateItem);



        CancelDelegateStaffNameItem.setText(name);
        DutyStartDateItem.setText(startDate1);
        DutyEndDateItem.setText(endDate1);
        CancelDelegateItem.setTag(id);




        return view;
    }

}
