package com.example.adprojectteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class CancelDelegateEmployeeActivity extends AppCompatActivity implements AsyncToServer.IServerResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_delegate_employee);



        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("name", pref.getString("name", ""));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(CancelDelegateEmployeeActivity.this, "Cancel_Delegate_Authority",
                "http://10.0.2.2:53900/Android/Cancel_Delegate_Authority", jsonObj);
        new AsyncToServer().execute(cmd);
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {

        if (jsonObj == null)
            return;

        try {
            String context = (String) jsonObj.get("context");
            if (context.compareTo("Cancel_Delegate_Item") == 0){

                Intent intent = new Intent(this, CancelDelegateEmployeeActivity.class);
                startActivity(intent);

            }
            // success message and direct to main screen
            if (context.compareTo("Cancel_Delegate_Authority") == 0) {
                JSONArray parentArray = jsonObj.getJSONArray("staff_representative_obj");
                final ArrayList<Staff_representative> Staff_representatives = new ArrayList<>();


                for (int i = 0; i < parentArray.length(); i++) {

                    JSONObject finalObject = parentArray.getJSONObject(i);


                    int staff_representativeId = Integer.parseInt(finalObject.getString("staff_representativeId"));
                    String start_date = finalObject.getString("start_date");
                    String end_date = finalObject.getString("end_date");
                    String position = finalObject.getString("position");
                    //creating staff obj from Json
                    JSONObject staff_obj = finalObject.getJSONObject("representative_staff_obj");
                    String name = staff_obj.getString("name");
                    int staffId = Integer.parseInt(staff_obj.getString("staffId"));
                    String position1 = staff_obj.getString("position");
                    Staff staff = new Staff(staffId, name, position1);

                    Staff_representative staff_representative = new Staff_representative(staff_representativeId, start_date, end_date, position, staff);

                    Staff_representatives.add(staff_representative);

                }
                ListView CancelDelegateListView = findViewById(R.id.CancelDelegateListView);
                CancelDelegateAdapter adapter = new CancelDelegateAdapter(this, R.layout.listview_cancel_delegate_item, Staff_representatives);
                CancelDelegateListView.setAdapter(adapter);

            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void CancelDelegate(View view){

        Button CancelDelegateItem = (Button) view.findViewById(R.id.CancelDelegateItem);
        int temp_id = (int)CancelDelegateItem.getTag();

        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("temp_id", temp_id);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(CancelDelegateEmployeeActivity.this, "Cancel_Delegate_Item",
                "http://10.0.2.2:53900/Android/Cancel_Delegate_Item", jsonObj);
        new AsyncToServer().execute(cmd);

    }
    }