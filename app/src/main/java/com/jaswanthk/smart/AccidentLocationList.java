package com.jaswanthk.smart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccidentLocationList extends AppCompatActivity {

    LocationManager locationManager;
    FirebaseDatabase db;
    DatabaseReference dr,dc;
    ArrayList arrayList;
    ArrayAdapter arrayAdapter;
    ProgressDialog dialog;
    public String currentlocation=null;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_location_list);


        final ListView Accidentlist=(ListView)findViewById(R.id.acclist);
        dialog=new ProgressDialog(AccidentLocationList.this);
        dialog.setTitle("Loading...");
        dialog.setMessage("Retrieving you data...Please wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        db= FirebaseDatabase.getInstance();
        dr=db.getReference();
        dc=dr.child("Locations");
        intent=new Intent(this,MapActivity.class);
        final Bundle bundle=new Bundle();
        arrayList=new ArrayList();
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        Accidentlist.setAdapter(arrayAdapter);

//        Toast.makeText(this, +"", Toast.LENGTH_SHORT).show();
        dc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dialog.dismiss();
                for (DataSnapshot dss : dataSnapshot.getChildren()) {
                    arrayList.add(dss.getKey());
                }
                arrayAdapter.notifyDataSetChanged();


                Accidentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        currentlocation=Accidentlist.getItemAtPosition(i).toString();
                        bundle.putString("curloc",currentlocation);
                        Toast.makeText(AccidentLocationList.this, currentlocation+" is Selected", Toast.LENGTH_SHORT).show();
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
