package com.example.adprojectteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeptManagerHomeActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_manager_home);

        Button DelegateEmployeeListBtn = findViewById(R.id.DelegateEmployeeListBtn);
        if (DelegateEmployeeListBtn != null)
            DelegateEmployeeListBtn.setOnClickListener(this);
        Button CancelDelegateEmployeeBtn = findViewById(R.id.CancelDelegateEmployeeBtn);
        if (CancelDelegateEmployeeBtn != null)
            CancelDelegateEmployeeBtn.setOnClickListener(this);
        Button    ApproveRequestBtn = findViewById(R.id.ApproveRequestBtn);
        if (ApproveRequestBtn != null)
            ApproveRequestBtn.setOnClickListener(this);







    }
    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId())
        {
            case R.id.DelegateEmployeeListBtn:

                intent = new Intent(this, DelegateEmployeeListActivity.class);
                break;

            case R.id.CancelDelegateEmployeeBtn:

                intent = new Intent(this, CancelDelegateEmployeeActivity.class);
                break;

            case R.id.ApproveRequestBtn:

                intent = new Intent(this, ApproveRequestActivity.class);
                break;


        }

        if (intent != null)
            if (intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);
    }
}
