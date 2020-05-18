package com.example.adprojectteam1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HeaderFragment extends Fragment {
    public HeaderFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutRoot = inflater.inflate(R.layout.fragment_header,
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
        SharedPreferences pref = this.getActivity().getSharedPreferences("user_credentials", Context.MODE_PRIVATE);

        TextView name = view.findViewById(R.id.name);
        String nameText = pref.getString("name", "");
        name.setText(nameText);
        final TextView logout = view.findViewById(R.id.Logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Logout();


            }
        });

    }




    public void Logout(){
        SharedPreferences pref = this.getActivity().getSharedPreferences("user_credentials",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        Intent newIntent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(newIntent);
    }

}