package com.example.ziko_.escapers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    private static final long min_distance=10; //1 meter
    private static final long min_time=100; //1 minute
    ArrayList <Location> current=new ArrayList<Location>();
    ArrayList <LatLng> polylines=new ArrayList<LatLng>();
    LocationManager locationManager;
    String provider;
    PolylineOptions polylineOptions;
    Polyline polyline;
    float total_distance=0;
    //TextView textView;
    String dist=null;
    double longi=0;
    double lati=0;
    TextView total;
    /*
        to remove search location and don't drain battery
        locationManager.removeUpdates(this);
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
       //textView=(TextView) findViewById(R.id.distance);
        total=(TextView) findViewById(R.id.total_distance);
       //GET Location object
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Creating an empty criteria object
        Criteria criteria=new Criteria();
        //GET the name of the provider that meet the criteria
        provider=locationManager.getBestProvider(criteria,false);
        if(provider!=null && !provider.equals("")){
            //GET the location from the given provider
           // Location location=locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider,min_time,min_distance , (LocationListener) this);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        //Get the start and the end point of route and draw the polyline between them

    }

    @Override
    public void onLocationChanged(Location location) {
        current.add(location);
        LatLng point = new LatLng(location.getLatitude(),location.getLongitude());
        polylines.add(point);
        polylineOptions =new PolylineOptions().addAll(polylines).width(10).color(Color.BLUE);
        polyline = mMap.addPolyline(polylineOptions);
            if(current.size()>1) {
                float distance = current.get(current.size()-1).distanceTo(current.get(current.size()-2));
                total_distance += distance;
                dist = Float.toString(total_distance);
                //String local=Float.toString(distance);
                //textView.setText(local);
                total.setText(dist);
                //Toast.makeText(getBaseContext(),dist,Toast.LENGTH_SHORT).show();
            }

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void create_route(View view) {
        Intent intent = new Intent(MapsActivity.this, Create_route.class);
        startActivity(intent);

    }
}
