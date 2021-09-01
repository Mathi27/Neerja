package com.proudcoder.firebase_login;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.proudcoder.firebase_login.databinding.ActivityMaps2Binding;

import org.jetbrains.annotations.NotNull;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener  {
    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;

    private float GEOFENCE_RADIUS = 200;
    private String GEOFENCE_ID = "SOME_GEOFENCE_ID";
    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 1001;
    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 1002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geofencingClient = LocationServices.getGeofencingClient(this);
        geofenceHelper = new GeofenceHelper(this);
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

        // Add a marker in Sydney and move the camera
        LatLng Delhi = new LatLng(28.535164, 77.149912);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Delhi,16));
        enableUserLocation();
        mMap.setOnMapLongClickListener(this);
    }
    private void enableUserLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        }else {
//            Ask for permission!
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
//                we need to show user dialog for display why persmission is needed and then ask for the permission
                ActivityCompat.requestPermissions(this , new String[] {Manifest.permission.ACCESS_FINE_LOCATION},FINE_LOCATION_ACCESS_REQUEST_CODE);
            }else{
                ActivityCompat.requestPermissions(this , new String[] {Manifest.permission.ACCESS_FINE_LOCATION},FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
//                 We Have Permission
            }
            else {
//                 We Dont Have Permission.
            }
        }
        if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE  ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
//                 We Have Permission
                Toast.makeText(this,"You can add geofences",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Background location access is neccessary for geofences to trigger",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapLongClick(@NonNull @NotNull LatLng latLng) {

        if(Build.VERSION.SDK_INT >= 29){
//            we need background permission
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)==PackageManager.PERMISSION_GRANTED){
                addGeoFence(latLng);
            }else {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)){
//                    we show a dialog and ask for permission
                    ActivityCompat.requestPermissions(this,new String[] {Manifest.permission
                            .ACCESS_BACKGROUND_LOCATION},BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                }else{
                    ActivityCompat.requestPermissions(this,new String[] {Manifest.permission
                            .ACCESS_BACKGROUND_LOCATION},BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                }

            }
        }else{
            addGeoFence(latLng);
        }
    }
    private void addGeoFence(LatLng latLng){
        mMap.clear();
        addMarker(latLng);
        addCircle(latLng,GEOFENCE_RADIUS);
        addGeofence(latLng,GEOFENCE_RADIUS);
    }
    @SuppressLint("MissingPermission")
    private void addGeofence(LatLng latLng, float radius){
        Geofence geofence = geofenceHelper.getGeofence(GEOFENCE_ID,latLng,radius,Geofence.GEOFENCE_TRANSITION_ENTER |Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofenceHelper.geofencingRequest(geofence);
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent();

        geofencingClient.addGeofences(geofencingRequest,pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: GeoFence added...");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                String errorMessage = geofenceHelper.getErrorString(e);
                Log.d(TAG, "onFailure: " + errorMessage);
            }
        });
    }
    private void addMarker(LatLng latLng){
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        mMap.addMarker(markerOptions);

    }
    private  void addCircle(LatLng latLng , float radius){
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb((int) 255,255,0,0));
        circleOptions.fillColor(Color.argb(64,255,0,168));
        circleOptions.strokeWidth(6);
        mMap.addCircle(circleOptions);


    }


}