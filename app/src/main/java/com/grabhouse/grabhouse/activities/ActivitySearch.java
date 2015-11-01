package com.grabhouse.grabhouse.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.grabhouse.grabhouse.R;
import com.grabhouse.grabhouse.utilities.Data;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivitySearch extends ActivityCustom {

    boolean isBuying = true;
    boolean is1BHK = true;
    boolean isLocationSet = false;

    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";
    private double latitude;
    private double longitude;

    int factor = 10000;
    int price = factor * 50;

    private RadioButton radioButton_buy;
    private RadioButton radioButton_1bhk;
    private SeekBar seekBar;
    private TextView textView_price;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setActionBar("Find House");

        // initialize views
        radioButton_buy = (RadioButton) findViewById(R.id.radioButton_buy);
        radioButton_buy.setOnCheckedChangeListener(mOnCheckedChangeListener_buy);
        radioButton_1bhk = (RadioButton) findViewById(R.id.radioButton_1bhk);
        radioButton_1bhk.setOnCheckedChangeListener(mOnCheckedChangeListener_1bhk);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        textView_price = (TextView) findViewById(R.id.textView_price);
    }

    public void onClick(View view){
        int id = view.getId();
        switch(id){
            case R.id.button_location:
                Intent intent = new Intent(this, ActivityLocation.class);
                startActivityForResult(intent, ActivityPost.RESULT);
                break;
            case R.id.button_search:
                if(validateForm()){
                    progressDialog = ProgressDialog.show(this, null, "Searching...", true, false);
                    searchHouse();
                }
                break;
        }
    }

    private void searchHouse(){

        String buy_or_rent = isBuying ? "SELL" : "RENT";
        String house_type = is1BHK ? Data.ONE_BHK : Data.TWO_BHK;

        JSONObject data = new JSONObject();
        try {
            data.put(Data.BUY_OR_RENT, buy_or_rent);
            data.put(Data.HOUSE_TYPE, house_type);
            data.put(Data.LATITUDE, latitude);
            data.put(Data.LONGITUDE, longitude);
            data.put(Data.PRICE, price);

        }catch (JSONException e){
            e.printStackTrace();
        }

        Log.d("Riyas", data.toString());

        // publish this to api
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Data.URL_GET_HOUSE, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //putAToast("onResponse: " + response.toString());
                progressDialog.dismiss();
                Log.d("Riyas", response.toString());
                Intent i = new Intent(getBaseContext(), ActivityHouseMap.class);
                i.putExtra(ActivityHouseMap.RESPONSE, response.toString());
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if(error instanceof NoConnectionError){
                    Snackbar.make(findViewById(R.id.layout), "No connection", Snackbar.LENGTH_LONG).setAction("RETRY", null).show();
                }else{
                    error.printStackTrace();
                }
            }
        });
        int timeOut = 20 * 1000;
        int retry = 0;
        int backoff = 2;
        RetryPolicy policy = new DefaultRetryPolicy(timeOut, retry, backoff);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);
    }

    private boolean validateForm(){

        if(!isLocationSet){
            Toast.makeText(ActivitySearch.this, "Select Location", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener_buy = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isBuying = isChecked;
            if(isChecked) {
                factor = 1000;
            }
            else {
                factor = 100;
            }
            setPrice(seekBar.getProgress());
        }
    };

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener_1bhk = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            is1BHK = isChecked;
        }
    };

    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            setPrice(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void setPrice(int progress){
        price = factor * progress;
        textView_price.setText("Rs."+price);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ActivityPost.RESULT && data != null){
            isLocationSet = true;
            // get location point
            latitude = data.getExtras().getDouble(LATITUDE);
            longitude = data.getExtras().getDouble(LONGITUDE);
        }
    }
}
