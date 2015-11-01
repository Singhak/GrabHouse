package com.grabhouse.grabhouse.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.grabhouse.grabhouse.R;
import com.grabhouse.grabhouse.utilities.Data;
import com.grabhouse.grabhouse.utilities.PrefManager;
import com.grabhouse.grabhouse.utilities.Utilities;

public class ActivityLocation extends ActivityCustom implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private String TAG = "Riyas";

    private AppCompatActivity mActivity = this;
    Context context = this;
    GoogleMap mGoogleMap;
    MarkerOptions markerOptions;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LatLng mLastLocationPoint;
    Context mContext = this;
    LocationManager locationManager;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setActionBar("Select Location");
        Utilities.makeItFullScreen(this);
        prefManager = new PrefManager(this);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mGoogleMap = supportMapFragment.getMap();
        mGoogleMap.setOnCameraChangeListener(mCameraChangeListener);
        buildGoogleApiClient();

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(mContext, "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view){
        int id = view.getId();
        switch(id){
            case R.id.button_save:
                getMarkerLocation();
                break;
        }
    }


    private void getMarkerLocation() {
        // This will give the Latitude and Longitude of center point of map fragment
        LatLng centerPoint = mGoogleMap.getCameraPosition().target;

        // return the result
        Intent intent = this.getIntent();
        intent.putExtra(ActivityPost.LATITUDE, centerPoint.latitude);
        intent.putExtra(ActivityPost.LONGITUDE, centerPoint.longitude);
        setResult(ActivityPost.RESULT, intent);
        finish();
    }

    private GoogleMap.OnCameraChangeListener mCameraChangeListener = new GoogleMap.OnCameraChangeListener() {
        @Override
        public void onCameraChange(CameraPosition cameraPosition) {
            // here we have to display the latitude and longitude

        }
    };

}

