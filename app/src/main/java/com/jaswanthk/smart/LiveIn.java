package com.jaswanthk.smart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LiveIn extends AppCompatActivity {

    Button ondrive,scan;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_in);


        ondrive=(Button)findViewById(R.id.ondrive);
        scan=(Button)findViewById(R.id.scan);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog= ProgressDialog.show(LiveIn.this,"Scanning...","Pot Hole Zone.....",true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(5000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        threaddismiss();
                    }});

                thread.start();
                Toast.makeText(LiveIn.this, "Pot Hole Location Found", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public  void threaddismiss()
    {
        dialog.dismiss();
        Intent intent=new Intent(LiveIn.this,AccidentLocationList.class);
        startActivity(intent);

    }

}
