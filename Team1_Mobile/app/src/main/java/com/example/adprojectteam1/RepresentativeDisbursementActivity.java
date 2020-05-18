package com.example.adprojectteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class RepresentativeDisbursementActivity extends AppCompatActivity implements AsyncToServer.IServerResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative_disbursement);




        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        JSONObject jsonObjfirst = new JSONObject();

        try {
            jsonObjfirst.put("name", pref.getString("name", ""));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(RepresentativeDisbursementActivity.this, "Representative_Disbursement_List",
                "http://10.0.2.2:53900/Android/View_Collect_Order", jsonObjfirst);
        new AsyncToServer().execute(cmd);

        Button  AcknowledgeBtn = findViewById(R.id.AcknowledgeBtn);
        AcknowledgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Orders> ordersList =getAllValues();
                JSONObject jsonObj = new JSONObject();

                JSONArray jsonArray = new JSONArray();

                try {

                    for (Orders order : ordersList)
                    {
                        JSONObject jsonObj1 = new JSONObject();
                        jsonObj1.put("ordersId", order.getOrdersId());
                        jsonObj1.put("actual_received_quantity_by_representative", order.getActual_received_quantity_by_representative());
                        jsonObj1.put("delivered_order_date", order.getDelivered_order_date());

                        jsonArray.put(jsonObj1);
                    }
                    jsonObj.put("ordersList",jsonArray);

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                Command cmd = new Command(RepresentativeDisbursementActivity.this, "Actual_Quantity_Received_by_Representative",
                        "http://10.0.2.2:53900/Android/Actual_Quantity_Received_by_Representative", jsonObj);
                new AsyncToServer().execute(cmd);
            }
        });
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null)
            return;

        try {
            String context = (String) jsonObj.get("context");
            if (context.compareTo("Actual_Quantity_Received_by_Representative") == 0){
                Intent intent = new Intent(this, RepresentativeDisbursementActivity.class);
                startActivity(intent);
            }



            if (context.compareTo("Representative_Disbursement_List") == 0) {
                JSONArray parentArray = jsonObj.getJSONArray("order_lis");
                final ArrayList<Orders> orders = new ArrayList<>();


                for (int i = 0; i < parentArray.length(); i++) {

                    JSONObject finalObject = parentArray.getJSONObject(i);

                    int ordersId = Integer.parseInt(finalObject.getString("ordersId"));
                    int proposed_quantity = Integer.parseInt(finalObject.getString("proposed_quantity"));


                    JSONObject staff_obj = finalObject.getJSONObject("staff_obj");
                    String name = staff_obj.getString("name");
                    int staffId = Integer.parseInt(staff_obj.getString("staffId"));
                    String position1 = staff_obj.getString("position");
                    Staff staff = new Staff(staffId, name, position1);

                    JSONObject item_obj = finalObject.getJSONObject("item_obj");
                    int itemId = Integer.parseInt(item_obj.getString("itemId"));
                    String item_description= item_obj.getString("item_description");
                    Item item = new Item(itemId,item_description);

                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH)+1;
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    String date = day+"/"+month+"/"+year;

                    Orders order = new Orders(ordersId, proposed_quantity, staff, item,date);


                    orders.add(order);

                }
                ListView RepresentativeDisbursementListView = findViewById(R.id.RepresentativeDisbursementList);
                RepresentativeDisbursementListAdapter adapter = new RepresentativeDisbursementListAdapter(this, R.layout.listview_representative_disbursement_item, orders);
                RepresentativeDisbursementListView.setAdapter(adapter);



            }


        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }



    public ArrayList<Orders> getAllValues() {
        View parentView = null;
        ArrayList<Orders> orders = new ArrayList<>();

        ListView RepresentativeDisbursementListView = findViewById(R.id.RepresentativeDisbursementList);

        for (int i = 0; i < RepresentativeDisbursementListView.getCount(); i++) {
            parentView = RepresentativeDisbursementListView.getAdapter().getView(i, null, null);


            int ordersId = Integer.parseInt(((TextView) parentView.findViewById(R.id.RepresentativeOrderIDItem)).getText().toString());
            String delivered_order_date =((TextView) parentView.findViewById(R.id.RepresentativeDeliveredDateItem)).getText().toString();
            int proposed_quantity = Integer.parseInt(((TextView) parentView.findViewById(R.id.RepresentativeDeliveredQuantityItem)).getText().toString());
            int actual_delivered_quantity;
            String test = "";
            if((((TextView) parentView.findViewById(R.id.RepresentativeActualQuantityItem)).getText().toString()).compareTo(test)== 0){
                actual_delivered_quantity=proposed_quantity;
            }
            else{
                actual_delivered_quantity=Integer.parseInt(((TextView) parentView.findViewById(R.id.RepresentativeActualQuantityItem)).getText().toString());
            }


            Orders order = new Orders(ordersId,actual_delivered_quantity,delivered_order_date);
            orders.add(order);
        }
        return orders;


    }
}
