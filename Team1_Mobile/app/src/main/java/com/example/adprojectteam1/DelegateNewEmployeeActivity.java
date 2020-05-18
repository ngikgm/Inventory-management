package com.example.adprojectteam1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public class DelegateNewEmployeeActivity extends AppCompatActivity implements AsyncToServer.IServerResponse{
    private TextView startDateSelect;
    private DatePickerDialog.OnDateSetListener startDateSelectListener;

    private TextView endDateSelect;
    private DatePickerDialog.OnDateSetListener endDateSelectListener;

    private Button submitDelegateBtn;
    private TextView employeeName;
    String duty_start_date ;
    String duty_end_date;
    String staff_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegate_new_employee);
        //change employee name to the selected employee name

        employeeName = findViewById(R.id.employeeName);

        Intent callerIntent = getIntent();
        if(callerIntent !=null){
            Staff staff = (Staff) callerIntent.getSerializableExtra("staff");
            employeeName.setText(staff.getName());
        }





        //let user input delegate start and end date
        startDateSelect = findViewById(R.id.startDateSelect);
        endDateSelect = findViewById(R.id.endDateSelect);

        //input for start date
        startDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DelegateNewEmployeeActivity.this, android.R.style.Theme_DeviceDefault_Dialog_Alert,startDateSelectListener , year,month,day);

                dialog.show();
            }
        });

        startDateSelectListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;

                //to match format in .NET
                String monthStr = month < 10 ? "0"+month : String.valueOf(month);
                String dayStr = dayOfMonth < 10 ? "0"+dayOfMonth : String.valueOf(dayOfMonth);

                String startDate =  dayOfMonth+"-" +month +"-" +year ;
                duty_start_date = year+"-" +monthStr +"-" +dayStr ;
                startDateSelect.setText(startDate);
            }
        };

        //input for end Date
        endDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DelegateNewEmployeeActivity.this, android.R.style.Theme_DeviceDefault_Dialog_Alert,endDateSelectListener , year,month,day);

                dialog.show();
            }
        });

        endDateSelectListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                //to match format in .NET
                String monthStr = month < 10 ? "0"+month : String.valueOf(month);
                String dayStr = dayOfMonth < 10 ? "0"+dayOfMonth : String.valueOf(dayOfMonth);
                duty_end_date = year+"-" +monthStr +"-" +dayStr ;

                String endDate =  dayOfMonth+"-" +month +"-" +year ;

                endDateSelect.setText(endDate);

            }
        };



        submitDelegateBtn =findViewById(R.id.submitDelegate);

        submitDelegateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                    JSONObject jsonObj = new JSONObject();

                    try {
                        jsonObj.put("duty_start_date", duty_start_date);
                        jsonObj.put("duty_end_date", duty_end_date);
                        jsonObj.put("staff_name", employeeName.getText());

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    Command cmd = new Command(DelegateNewEmployeeActivity.this, "DelegateNewEmployee",
                            "http://10.0.2.2:53900/Android/Delegate_Authority_Staff", jsonObj);
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
            // success message and direct to main screen
            if (context.compareTo("DelegateNewEmployee") == 0)
            {
                String status = (String)jsonObj.get("status");
                if (status.compareTo("ok") == 0){

                    Intent newIntent;
                    //
                    Toast.makeText(getApplicationContext(),
                            "Delegate successful",Toast.LENGTH_LONG).show();
                    newIntent = new Intent(this, DelegateEmployeeListActivity.class);
                    startActivity(newIntent);

                }
                // message for failed login
                else{
                    Toast.makeText(getApplicationContext(),
                            "Error has occurred",Toast.LENGTH_LONG).show();
                }


            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }




    }
}

