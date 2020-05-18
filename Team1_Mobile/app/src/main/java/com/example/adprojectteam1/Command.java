package com.example.adprojectteam1;

import android.content.Intent;
import android.content.SharedPreferences;

import org.json.JSONObject;

public class Command {
    protected AsyncToServer.IServerResponse callback;
    protected String context;
    protected String endPt;
    protected JSONObject data;

    Command(AsyncToServer.IServerResponse callback,String context,String endPt,JSONObject data ){
        this.callback = callback;
        this.context = context;
        this.endPt = endPt;
        this.data = data;
}


}

