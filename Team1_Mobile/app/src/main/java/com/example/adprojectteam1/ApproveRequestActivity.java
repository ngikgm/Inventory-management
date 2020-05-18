package com.example.adprojectteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApproveRequestActivity extends AppCompatActivity implements AsyncToServer.IServerResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_request);

        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("name", pref.getString("name", ""));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(ApproveRequestActivity.this, "Approve_Request",
                "http://10.0.2.2:53900/Android/View_Staff_Request", jsonObj);
        new AsyncToServer().execute(cmd);
    }


    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null)
            return;

        try {
            String context = (String) jsonObj.get("context");
            if (context.compareTo("ApproveRequestItem") == 0){

                Intent intent = new Intent(this, ApproveRequestActivity.class);
                startActivity(intent);

            }
            if (context.compareTo("RejectRequestItem") == 0){

                Intent intent = new Intent(this, ApproveRequestActivity.class);
                startActivity(intent);

            }



            // success message and direct to main screen
            if (context.compareTo("Approve_Request") == 0) {
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




                    Orders order = new Orders(ordersId, proposed_quantity, staff, item);


                    orders.add(order);

                }
                ListView ApproveRequestListView = findViewById(R.id.ApproveRequestListView);
                ApproveRequestAdapter adapter = new ApproveRequestAdapter(this, R.layout.listview_approve_request_item, orders);
                ApproveRequestListView.setAdapter(adapter);



            }


        }
        catch (Exception e) {
            e.printStackTrace();

         }
    }
    public void ApproveRequest(View view){

        Button ApproveRequestApproveItem = (Button) view.findViewById(R.id.ApproveRequestApproveItem);

        int temp_id = (int)ApproveRequestApproveItem.getTag();

        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("temp_id", temp_id);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(ApproveRequestActivity.this, "ApproveRequestItem",
                "http://10.0.2.2:53900/Android/Approve_Request_Item", jsonObj);
        new AsyncToServer().execute(cmd);

    }
    public void RejectRequest(View view){

        Button ApproveRequestRejectItem = (Button) view.findViewById(R.id.ApproveRequestRejectItem);

        int temp_id = (int)ApproveRequestRejectItem.getTag();

        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("temp_id", temp_id);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(ApproveRequestActivity.this, "RejectRequestItem",
                "http://10.0.2.2:53900/Android/Reject_Request_Item", jsonObj);
        new AsyncToServer().execute(cmd);

    }
}
