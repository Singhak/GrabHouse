package com.grabhouse.grabhouse.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.grabhouse.grabhouse.activities.MainActivity;
import com.grabhouse.grabhouse.utilities.Data;
import com.grabhouse.grabhouse.utilities.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HttpService extends IntentService {

    private PrefManager prefManager;

    public HttpService() {
        super(HttpService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        prefManager = new PrefManager(getApplicationContext());
        if (intent != null && !prefManager.isLoggedIn()) {
            String otp = intent.getStringExtra("otp");
            verifyOtp(otp);
            Log.e("Riyas", "Intent Service ends...");
        }
    }

    /**
     * Sends otp to to server for verification
     * @param otp is verification password recieved by sms
     */
    private void verifyOtp(final String otp) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Data.URL_VERIFY_OTP, mResponseListener, mErrorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("otp", otp);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private Response.Listener<String> mResponseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject_response = new JSONObject(response);
                Log.d("Riyas", jsonObject_response.toString());
                boolean error = jsonObject_response.getBoolean("error");
                String message = jsonObject_response.getString("message");
                if (!error) {
                    JSONObject profileObj = jsonObject_response.getJSONObject("profile");
                    String mobile = profileObj.getString("mobile");
                    PrefManager pref = new PrefManager(getApplicationContext());
                    pref.createLogin(mobile);
                    pref.setServerKey(profileObj.getString("apikey"));
                    Intent intent = new Intent(HttpService.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Registration Successful.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            Log.e("Riyas", "onResponse ends...");
        }
    };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}