package com.example.adprojectteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DelegateEmployeeListActivity extends AppCompatActivity implements AsyncToServer.IServerResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegate_employee_list);

        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("name", pref.getString("name", ""));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(DelegateEmployeeListActivity.this, "Delegate_Authority",
                "http://10.0.2.2:53900/Android/Delegate_Authority", jsonObj);
        new AsyncToServer().execute(cmd);

    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null)
            return;

        try {
            JSONArray parentArray = jsonObj.getJSONArray("staff_lis");
            final ArrayList<Staff> staffs = new ArrayList<>();


            for(int i = 0; i<parentArray.length(); i++){
                JSONObject finalObject = parentArray.getJSONObject(i);


                String name = finalObject.getString("name");
                int staffId = Integer.parseInt(finalObject.getString("staffId"));
                String position = finalObject.getString("position");
                Staff staff = new Staff(staffId, name, position);
                staffs.add(staff) ;
            }

            ListView DelegateListView = findViewById(R.id.DelegateListView);
            DelegateAdapter adapter = new DelegateAdapter(this, R.layout.listview_delegate_item, staffs);
            DelegateListView.setAdapter(adapter);

            DelegateListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Staff staff = staffs.get(position);
                    Intent intent = new Intent(DelegateEmployeeListActivity.this, DelegateNewEmployeeActivity.class);
                    intent.putExtra("staff", staff);

                    startActivity(intent);

                }
            });



        }catch (Exception e) {
            e.printStackTrace();
        }

        }

    }


