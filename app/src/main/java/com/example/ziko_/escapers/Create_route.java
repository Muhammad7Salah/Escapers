package com.example.ziko_.escapers;

import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class Create_route extends FragmentActivity implements OnMapReadyCallback ,GoogleMap.OnMapLongClickListener ,GoogleMap.OnMarkerDragListener{

    private GoogleMap mMap;
    ArrayList<LatLng> routes = new ArrayList<LatLng>();
    PolylineOptions polylineOptions;
    Polyline polyline;
    float distance;
    float total_distance_route=0;
    String dist;
    TextView total;
    Location locationA = new Location("A");
    Location locationB = new Location("B");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        total=(TextView) findViewById(R.id.total_distance_route);

    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

/*
            if(mMap != null){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 10.0f));
              //  mMap.addMarker(new MarkerOptions().title("Start").position(loc));
            }*/
            ;
        }
    };

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
        // Add a marker in Sydney and move the camera
        googleMap.setMyLocationEnabled(true);
        mMap.setOnMapLongClickListener(this);
        googleMap.setOnMyLocationChangeListener(myLocationChangeListener);
        mMap.setOnMarkerDragListener(this);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title("start").draggable(true));
        routes.add(latLng);
        if(routes.size()>1) {
            locationA.setLatitude(routes.get(routes.size() - 1).latitude);
            locationA.setLongitude(routes.get(routes.size() - 1).longitude);
            locationB.setLatitude(routes.get(routes.size() - 2).latitude);
            locationB.setLongitude(routes.get(routes.size() - 2).longitude);
            distance=locationB.distanceTo(locationA);
            total_distance_route+=distance;
            dist = Float.toString(total_distance_route);
            total.setText(dist);
        }
        polylineOptions =new PolylineOptions().addAll(routes).width(10).color(Color.BLUE);
        polyline = mMap.addPolyline(polylineOptions);


    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        total_distance_route -= distance;
        routes.remove(routes.size() - 1);
        routes.add(marker.getPosition());
        locationA.setLatitude(routes.get(routes.size() - 1).latitude);
        locationA.setLongitude(routes.get(routes.size() - 1).longitude);
        locationB.setLatitude(routes.get(routes.size() - 2).latitude);
        locationB.setLongitude(routes.get(routes.size() - 2).longitude);
        float distance_change=locationB.distanceTo(locationA);
        Log.i("escape",Float.toString(distance_change));
        total_distance_route+=distance_change;
        dist = Float.toString(total_distance_route);
        total.setText(dist);
        polyline.remove();
        polylineOptions =new PolylineOptions().addAll(routes).width(10).color(Color.BLUE);
        polyline = mMap.addPolyline(polylineOptions);
    }
}
