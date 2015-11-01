package com.grabhouse.grabhouse.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import com.grabhouse.grabhouse.utilities.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityPost extends ActivityCustom {


    public static final int RESULT = 100;
    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";
    private double latitude;
    private double longitude;
    private PrefManager prefManager;

    private EditText editText_name;
    private EditText editText_price;
    private TextView textView_cordinates;
    private RadioButton radioButton_sell;
    private RadioButton radioButton_rent;
    private RadioButton radioButton_1bhk;
    private RadioButton radioButton_2bhk;
    private ToggleButton toggleButton;
    private ProgressDialog progressDialog;

    private boolean isLocationSet = false;
    private boolean isSellOrRentSet = false;
    private boolean isTypeSet = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        setActionBar("Post it");
        prefManager = new PrefManager(this);
        textView_cordinates = (TextView) findViewById(R.id.textView_cordinates);
        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_price = (EditText) findViewById(R.id.editText_price);
        radioButton_sell = (RadioButton) findViewById(R.id.radioButton_sell);
        radioButton_sell.setChecked(true);
        radioButton_rent = (RadioButton) findViewById(R.id.radioButton_rent);
        radioButton_1bhk = (RadioButton) findViewById(R.id.radioButton_1bhk);
        radioButton_1bhk.setChecked(true);
        radioButton_2bhk = (RadioButton) findViewById(R.id.radioButton_2bhk);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
    }

    public void onClick(View view){
        int id = view.getId();
        switch(id){
            case R.id.button_location:
                Intent intent = new Intent(this, ActivityLocation.class);
                startActivityForResult(intent, RESULT);
                break;

            case R.id.button_post:
                if(validateTheForm()){
                    // show progress dialog
                    progressDialog = ProgressDialog.show(this, null, "Posting...", true, false);
                    publishPack();
                }
        }
    }

    private void publishPack(){
        String name = editText_name.getText().toString();
        String sale_type = radioButton_sell.isChecked() ? Data.SELL : Data.RENT;
        String house_type = radioButton_1bhk.isChecked() ? Data.ONE_BHK :Data.TWO_BHK;

        String price = editText_price.getText().toString();
        int is_open = toggleButton.isChecked() ? 1 : 0;
        String key = prefManager.getKeyServerKey();

        JSONObject data = new JSONObject();
        try {
            data.put(Data.NAME, name);
            data.put(Data.SALE_TYPE, sale_type);
            data.put(Data.HOUSE_TYPE, house_type);
            data.put(Data.LATITUDE, latitude);
            data.put(Data.LONGITUDE, longitude);
            data.put(Data.PRICE, price);
            data.put(Data.IS_OPEN, is_open);

        }catch (JSONException e){
            e.printStackTrace();
        }

        Log.d("Riyas", data.toString());

        // publish this to api
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        final String URL = String.format(Data.URL_POST_HOUSE, key);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //putAToast("onResponse: " + response.toString());
                progressDialog.dismiss();
                Log.d("Riyas", response.toString());
                Toast.makeText(getBaseContext(), "Posted Successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
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

    public boolean validateTheForm(){
        if(editText_name.getText().toString().equals("")){
            Toast.makeText(this, "Fill name", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isLocationSet){
            Toast.makeText(this, "Set Location", Toast.LENGTH_SHORT).show();
            return false;
        }else if(editText_price.getText().toString().equals("")){
            Toast.makeText(this, "Set price", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULT && data != null){
            isLocationSet = true;
            // get location point
            latitude = data.getExtras().getDouble(LATITUDE);
            longitude = data.getExtras().getDouble(LONGITUDE);
            textView_cordinates.setText("( " + latitude + ", " + longitude + " )");
        }
    }
}
