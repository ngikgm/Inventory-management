package com.example.adprojectteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClerkDisbursementListActivity extends AppCompatActivity implements AsyncToServer.IServerResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_disbursement_list);

        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("name", pref.getString("name", ""));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(ClerkDisbursementListActivity.this, "View_Disbursement_List_Dept",
                "http://10.0.2.2:53900/Android/View_Disbursement_List_Dept", jsonObj);
        new AsyncToServer().execute(cmd);
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {

        final Spinner DeptListSpinner = findViewById(R.id.DeptListSpinner);
        if (jsonObj == null)
            return;
        try {
            String context = (String) jsonObj.get("context");



            // success message and direct to main screen
            if (context.compareTo("View_Disbursement_List_Dept") == 0) {
                ArrayList<String> DeptList= new ArrayList<>();
                JSONArray parentArray = jsonObj.getJSONArray("dept_list");
                for (int i=0; i <parentArray.length(); i++) {
                    String dept = (String)parentArray.get(i);
                    DeptList.add(dept);


                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        ClerkDisbursementListActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        DeptList.toArray( new String[0] ));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                DeptListSpinner.setAdapter(adapter);
                DeptListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        String DeptName = DeptListSpinner.getSelectedItem().toString();
                        showDisbursementList(DeptName);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }

                });

            }
            if (context.compareTo("View_Disbursement_List") == 0) {
                JSONArray parentArray = jsonObj.getJSONArray("order_list");
                 ArrayList<Orders> orders = new ArrayList<>();


                for (int i = 0; i < parentArray.length(); i++) {

                    JSONObject finalObject = parentArray.getJSONObject(i);

                    int ordersId = Integer.parseInt(finalObject.getString("ordersId"));
                    int actual_delivered_quantity_by_clerk = Integer.parseInt(finalObject.getString("actual_delivered_quantity_by_clerk"));
                    int actual_received_quantity_by_representative= Integer.parseInt(finalObject.getString("actual_received_quantity_by_representative"));
                    String delivered_order_date = finalObject.getString("delivered_order_date");

                    JSONObject item_obj = finalObject.getJSONObject("item_obj");
                    int itemId = Integer.parseInt(item_obj.getString("itemId"));
                    String item_description= item_obj.getString("item_description");
                    Item item = new Item(itemId,item_description);



                    Orders order = new Orders( ordersId, actual_delivered_quantity_by_clerk, actual_received_quantity_by_representative, delivered_order_date,item);


                    orders.add(order);


                }
                ListView DisbursementListListView = findViewById(R.id.DisbursementListListView);
                ClerkDisbursementListAdapter adapter = new ClerkDisbursementListAdapter(this, R.layout.listview_clerk_disbursement_list_item, orders);
                DisbursementListListView.setAdapter(adapter);



            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void showDisbursementList(String DeptName){
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("DeptName", DeptName);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(ClerkDisbursementListActivity.this, "View_Disbursement_List",
                "http://10.0.2.2:53900/Android/View_Disbursement_List", jsonObj);
        new AsyncToServer().execute(cmd);

    }
}
