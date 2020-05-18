package com.example.adprojectteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements AsyncToServer.IServerResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button submitLogin = findViewById(R.id.submitLogin);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);

        //auto login if the user has not logged out
        SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
        if (pref.contains("email") && pref.contains("password")) {


        logIn(pref.getString("email", ""),
                    pref.getString("password", ""));

        }

        //login after user input credentials
        submitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                logIn(emailText, passwordText);
            }
    });
    }

    @Override
    public void onServerResponse(JSONObject jsonObj) {
        if (jsonObj == null)
            return;

        try {
            String context = (String) jsonObj.get("context");
            // save user data to shared preference for successful login
            if (context.compareTo("login") == 0)
            {
                String status = (String)jsonObj.get("status");
                if (status.compareTo("ok") == 0){
                    String name = (String) jsonObj.get("name");
                    String email = (String) jsonObj.get("email");
                    String password = (String) jsonObj.get("password");
                    String position = (String) jsonObj.get("position");

                    SharedPreferences pref = getSharedPreferences("user_credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("name",name);
                    editor.putString("email",email);
                    editor.putString("password",password);
                    editor.putString("position",position);
                    editor.commit();
                    Intent newIntent;
                    //direct to different page for different user
                    switch (position)
                    {

                        case "Temporary_Head" :
                        case "Head":
                            newIntent = new Intent(this, DeptManagerHomeActivity.class);
                            startActivity(newIntent);
                            break;

                        case "Clerk":
                             newIntent = new Intent(this, ClerkDisbursementListActivity.class);
                            startActivity(newIntent);
                            break;

                        case "Representative":
                            newIntent = new Intent(this, RepresentativeDisbursementActivity.class);
                            startActivity(newIntent);
                            break;
                        case "Supervisor":
                        case "Manager":
                        case "Employee":
                            Toast.makeText(getApplicationContext(),
                                    "Mobile features is not available for the user group",Toast.LENGTH_SHORT).show();
                            break;

                    }

                }
                // message for failed login
                else{
                    Toast.makeText(getApplicationContext(),
                            "Credentials are not valid",Toast.LENGTH_SHORT).show();
                }


            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }




    }


    //login method
    private void logIn(String email, String password) {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("email", email);
            jsonObj.put("password", password);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Command cmd = new Command(MainActivity.this, "login",
                "http://10.0.2.2:53900/Android/Login", jsonObj);
        new AsyncToServer().execute(cmd);

    }

}
