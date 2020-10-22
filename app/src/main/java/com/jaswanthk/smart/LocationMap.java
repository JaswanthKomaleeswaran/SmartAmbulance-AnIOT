package com.jaswanthk.smart;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LocationMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    FirebaseDatabase db;
    DatabaseReference dr,dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        db= FirebaseDatabase.getInstance();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final Bundle b1=getIntent().getExtras();
        final String cc=b1.getString("curloc");

        dr=db.getReference().child("Locations");
        dc=dr.child(cc);
        Toast.makeText(this,dr.toString()+ "", Toast.LENGTH_SHORT).show();

        Toast.makeText(this, dc+"", Toast.LENGTH_SHORT).show();


        dc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String latitude = dataSnapshot.child("latitude").getValue(String.class);
                String longitude = dataSnapshot.child("longitude").getValue(String.class);
                Toast.makeText(LocationMap.this, ""+latitude+longitude, Toast.LENGTH_SHORT).show();
//                LatLng latlng = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
//                mMap.addMarker(new MarkerOptions().position(latlng).title("Marker in "+cc));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,17));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//
//        LatLng latlng = new LatLng(12.9870879,79.9728308);
//        mMap.addMarker(new MarkerOptions().position(latlng).title("Marker in svce"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,17));

        // Add a marker in Sydney and move the camera

    }
}
