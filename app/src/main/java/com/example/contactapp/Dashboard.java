package com.example.contactapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.contactapp.Common.Utils;
import com.example.contactapp.permissions.PermissionHandler;
import com.example.contactapp.permissions.Permissions;

import java.util.ArrayList;

public class Dashboard extends Activity implements  View.OnClickListener {

    Button     btn_Dashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardview);
        // Init GUI Widget
        btn_Dashboard = (Button) findViewById(R.id.btn_Dashboard);
        btn_Dashboard.setOnClickListener(this);

        readwritepermission();
    }

    private  void readwritepermission()
    {
        Permissions.check(getApplicationContext(), Manifest.permission.READ_SMS, null, new PermissionHandler() {
            @Override
            public void onGranted() {

            }
            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                readwritepermission();
                String appname=context.getResources().getString(R.string.app_name);
                String message=context.getResources().getString(R.string.permission);
                String FullMessage=appname+" "+message;
                Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v == btn_Dashboard) {



            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);

        }
    }
}
