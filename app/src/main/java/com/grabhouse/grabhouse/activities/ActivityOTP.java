/**
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grabhouse.grabhouse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.grabhouse.grabhouse.R;
import com.grabhouse.grabhouse.utilities.Data;
import com.grabhouse.grabhouse.utilities.PrefManager;
import com.grabhouse.grabhouse.utilities.Utilities;

import org.apache.http.protocol.HttpService;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityOTP extends ActivityCustom implements View.OnClickListener {

  private static String TAG = "Riyas";
  private AppCompatActivity mActivity = this;
  private ViewPager viewPager;
  private ViewPagerAdapter adapter;
  private Button btnRequestSms, btnVerifyOtp;
  private EditText inputMobile, inputOtp;
  private ProgressBar progressBar;
  private PrefManager pref;
  private ImageButton btnEditMobile;
  private TextView txtEditMobile;
  private LinearLayout layoutEditMobile;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_otp);
    Utilities.makeItFullScreen(this);

    viewPager = (ViewPager) findViewById(R.id.viewPagerVertical);
    inputMobile = (EditText) findViewById(R.id.inputMobile);
    inputOtp = (EditText) findViewById(R.id.inputOtp);
    btnRequestSms = (Button) findViewById(R.id.btn_request_sms);
    btnVerifyOtp = (Button) findViewById(R.id.btn_verify_otp);
    progressBar = (ProgressBar) findViewById(R.id.progressBar);
    btnEditMobile = (ImageButton) findViewById(R.id.btn_edit_mobile);
    txtEditMobile = (TextView) findViewById(R.id.txt_edit_mobile);
    layoutEditMobile = (LinearLayout) findViewById(R.id.layout_edit_mobile);

    // view click listeners
    btnEditMobile.setOnClickListener(this);
    btnRequestSms.setOnClickListener(this);
    btnVerifyOtp.setOnClickListener(this);

    // hiding the edit mobile number
    layoutEditMobile.setVisibility(View.GONE);

    pref = new PrefManager(getBaseContext());

    // Checking for user session
    // if user is already logged in, take him to main activity
    if (pref.isLoggedIn()) {
      Intent intent = new Intent(ActivityOTP.this, MainActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(intent);
      finish();
    }

    adapter = new ViewPagerAdapter();
    viewPager.setAdapter(adapter);
    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override
      public void onPageSelected(int position) {
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });


    /**
     * Checking if the device is waiting for sms
     * showing the user OTP screen
     */
    if (pref.isWaitingForSms()) {
      viewPager.setCurrentItem(1);
      layoutEditMobile.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_request_sms:
        validateForm();
        break;

      case R.id.btn_verify_otp:
        verifyOtp();
        break;

      case R.id.btn_edit_mobile:
        viewPager.setCurrentItem(0);
        layoutEditMobile.setVisibility(View.GONE);
        pref.setIsWaitingForSms(false);
        break;
    }
  }

  /**
   * Validating user details form
   */
  private void validateForm() {
    String mobile = inputMobile.getText().toString().trim();

    // validating mobile number
    if (isValidPhoneNumber(mobile)) {
      progressBar.setVisibility(View.VISIBLE);
      pref.setMobileNumber(mobile);
      requestForSMS(mobile);
    } else {
      Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
    }
  }

  private void requestForSMS(final String mobile) {
    RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Data.URL_REQUEST_SMS, mResponseListener,mResponseErrorListener){
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        return params;
      }
    };
    stringRequest.setRetryPolicy(new DefaultRetryPolicy(10*1000, 2, 1));
    requestQueue.add(stringRequest);
  }

  private Response.Listener<String> mResponseListener = new Response.Listener<String>() {
    @Override
    public void onResponse(String response) {
        JSONObject json = null;
        boolean error = false;
          try {
              json = new JSONObject(response);
              error = json.getBoolean("error");
          } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
          }
        if(json != null && !error){
                viewPager.setCurrentItem(1);
                pref.setIsWaitingForSms(true);
                txtEditMobile.setText(pref.getMobileNumber());
                layoutEditMobile.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }
  };

  private Response.ErrorListener mResponseErrorListener = new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
      Log.e(TAG, "Error: " + error.getMessage() + error.toString());
      if(error instanceof NoConnectionError){
        Snackbar.make(mActivity.findViewById(R.id.viewContainer), "No Connection", Snackbar.LENGTH_LONG).show();
      }
      progressBar.setVisibility(View.GONE);
    }
  };

  private void verifyOtp() {
    String otp = inputOtp.getText().toString().trim();

    if (!otp.isEmpty()) {
      Intent grapprIntent = new Intent(getApplicationContext(), HttpService.class);
      grapprIntent.putExtra("otp", otp);
      startService(grapprIntent);
    } else {
      Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * Regex to validate the mobile number
   * mobile number should be of 10 digits length
   *
   * @param mobile
   * @return
   */
  private static boolean isValidPhoneNumber(String mobile) {
    String regEx = "^[0-9]{10}$";
    return mobile.matches(regEx);
  }


  class ViewPagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
      return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return view == ((View) object);
    }

    public Object instantiateItem(View collection, int position) {

      int resId = 0;
      switch (position) {
        case 0:
          resId = R.id.layout_sms;
          break;
        case 1:
          resId = R.id.layout_otp;
          break;
      }
      return findViewById(resId);
    }
  }

}
