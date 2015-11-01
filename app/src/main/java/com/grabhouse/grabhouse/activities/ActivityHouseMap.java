package com.grabhouse.grabhouse.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.grabhouse.grabhouse.R;
import com.grabhouse.grabhouse.utilities.House;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityHouseMap extends ActivityCustom implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    public static final String RESPONSE = "response";
    private Context mContext = this;
    private ArrayList<House> houseList= new ArrayList<House>();
    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_map);
        this.setActionBar("Results");

        // make the array list
        makeArrayList();

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mGoogleMap = supportMapFragment.getMap();
        mGoogleMap.setOnMarkerClickListener(mMarkerClickListener);
        buildGoogleApiClient();


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void makeArrayList(){
        // get the passed array
        String json_houses = getIntent().getExtras().getString(RESPONSE);

        try{
            JSONObject jsonObject = new JSONObject(json_houses);
            JSONArray jsonArray = jsonObject.getJSONArray("houses");
            Log.d("Riyas", jsonArray.toString());
            for(int i=0; i<jsonArray.length(); i++){
                Log.d("Riyas", "Json array size: " + jsonArray.length());
                JSONObject h = jsonArray.getJSONObject(i);
                House house = new House(h.getInt("id"), h.getString("name"), h.getString("sale_type"),h.getString("house_type"), h.getString("latitude"), h.getString("longitude"), h.getString("price"), h.getString("is_open"), h.getString("image"));
                houseList.add(house);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
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

    public void onClick(View view){
        int id = view.getId();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("Riyas", "onConnected");
        putMarkersOnMap();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(mContext, "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }

    private void putMarkersOnMap(){
        buildGoogleApiClient();

        for (int i = 0; i < houseList.size(); i++) {
            Log.d("Riyas", "house list " + i);
            House house = houseList.get(i);

            if(Integer.valueOf(house.getIs_open()) == 0){
                LatLng ltlng = new LatLng(Double.valueOf(house.getLatitude()), Double.valueOf(house.getLongitude()));
                mGoogleMap.addMarker(new MarkerOptions()
                        .position(ltlng)
                        .snippet("Rs." + house.getPrice())
                        .title(house.getHouse_type().toUpperCase()));
            }else{
                LatLng ltlng = new LatLng(Double.valueOf(house.getLatitude()), Double.valueOf(house.getLongitude()));
                mGoogleMap.addMarker(new MarkerOptions()
                        .position(ltlng)
                        .snippet("Rs." + house.getPrice())
                        .title(house.getHouse_type().toUpperCase())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
        }
    }

    private GoogleMap.OnMarkerClickListener mMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            return false;
        }
    };

}
