package com.jaswanthk.smart;

import android.*;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AsyncResponse {
    EditText user,pass;
    TextView userpwd;
    Button logbtn;
    ProgressDialog dialog;
    String urldata;
    String usertext,passtext;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        provider = locationManager.getBestProvider(new Criteria(), false);
        String s[]={ android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,s,1);
        }

        user=(EditText) findViewById(R.id.user);
        pass=(EditText) findViewById(R.id.pass);
        userpwd = (TextView) findViewById(R.id.userpwd);

        user.setText("");
        pass.setText("");
        logbtn=(Button)findViewById(R.id.logbtn);
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usertext=user.getText().toString();
                passtext=pass.getText().toString();

                if (usertext.indexOf(" ")!=-1  || usertext.trim().length() == 0) {
                    LinearLayout l1 = (LinearLayout) findViewById(R.id.llay1);
                    l1.setBackgroundColor(Color.parseColor("#ff0000"));
                    user.setBackgroundColor(Color.parseColor("#ff0000"));
                    user.requestFocus();
                } else if (passtext.trim().length() == 0) {
                    LinearLayout l1 = (LinearLayout) findViewById(R.id.llay2);
                    l1.setBackgroundColor(Color.parseColor("#ff0000"));
                    pass.setBackgroundColor(Color.parseColor("#ff0000"));
                    pass.requestFocus();
                }
                else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            loadDataFromServer();
                        }
                    });
                    thread.start();
                    dialog = ProgressDialog.show(MainActivity.this, "Validating", "Please Wait...", true);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                }
            }
        });
    }
    public void loadDataFromServer()
    {
        urldata="http://602024.com/jaswanthkomaleeswaran/login.php?name="+ user.getText().toString()+"," +pass.getText().toString();

//        Toast.makeText(this, urldata, Toast.LENGTH_SHORT).show();
        GetOutputFromServer getOutputFromServer=new GetOutputFromServer(MainActivity.this);
        getOutputFromServer.execute(urldata);
    }
    @Override
    public void processFinish(String output) {

        dialog.dismiss();

        if(output.equals("0"))
        {
            userpwd.setText("Username is Wrong");
            userpwd.setVisibility(View.VISIBLE);
        }
        else if (output.equals("1"))
        {
            userpwd.setText("Password is Wrong");
            userpwd.setVisibility(View.VISIBLE);
        }
        else
        {
            Intent intent=new Intent(MainActivity.this,LiveIn.class);
            startActivity(intent);
        }

    }
}

