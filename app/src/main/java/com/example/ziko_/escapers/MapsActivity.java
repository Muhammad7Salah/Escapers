package com.example.ziko_.escapers;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    private static final long min_distance=1; //1 meter
    private static final long min_time=1*60*1000; //1 minute
    ArrayList <Location> current=new ArrayList<Location>();
    LocationManager locationManager;
    String provider;
    float total_distance=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
       //GET Location object
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Creating an empty criteria object
        Criteria criteria=new Criteria();
        //GET the name of the provider that meet the criteria
        provider=locationManager.getBestProvider(criteria,false);
        if(provider!=null && !provider.equals("")){
            //GET the location from the given provider
            Location location=locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, min_time, min_distance, (LocationListener) this);
            current.add(location);
            if (location!=null){

                onLocationChanged(location);
            }
            else{
                Toast.makeText(getBaseContext(),"Wait to retrive Location",Toast.LENGTH_SHORT).show();
            }

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
        Location old_location=current.get(current.size()-2);
         float distance=old_location.distanceTo(location);
        total_distance+=distance;
        //String dist=Float.toString(total_distance);
        //Toast.makeText(getBaseContext(),dist,Toast.LENGTH_SHORT).show();
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
}
