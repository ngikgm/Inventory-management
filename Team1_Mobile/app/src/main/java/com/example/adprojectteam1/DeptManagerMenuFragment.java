package com.example.adprojectteam1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class DeptManagerMenuFragment extends Fragment {
    public DeptManagerMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutRoot = inflater.inflate(R.layout.fragment_department_manager_menu,
                container, false);

        return layoutRoot;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        TextView MenuDelegateEmployeeListBtn = view.findViewById(R.id.MenuDelegateEmployeeListBtn);

        TextView MenuCancelDelegateEmployeeBtn = view.findViewById(R.id.MenuCancelDelegateEmployeeBtn);

        TextView MenuApproveRequestBtn = view.findViewById(R.id.MenuApproveRequestBtn);


        MenuDelegateEmployeeListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent newIntent = new Intent(getActivity(), DelegateEmployeeListActivity.class);
                startActivity(newIntent);


            }


        });
        MenuCancelDelegateEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent newIntent = new Intent(getActivity(), CancelDelegateEmployeeActivity.class);
                startActivity(newIntent);


            }


        });
        MenuApproveRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent newIntent = new Intent(getActivity(), ApproveRequestActivity.class);
                startActivity(newIntent);


            }


        });

    }
}
