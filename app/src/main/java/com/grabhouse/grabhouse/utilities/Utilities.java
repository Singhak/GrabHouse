package com.grabhouse.grabhouse.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class Utilities {

    public static void makeItFullScreen(AppCompatActivity activity){


        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.hide();
        if (Build.VERSION.SDK_INT < 16) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static boolean isItFirstOpen(Context context){
        SharedPreferences sp = context.getSharedPreferences(Data.APP_PREF, Context.MODE_PRIVATE);
        boolean firstVisit = sp.getBoolean("isItFirstVisit", true);
        Log.d("Riyas", String.valueOf(firstVisit));
        if(firstVisit == true){
            sp.edit().putBoolean("isItFirstVisit", false).commit();
            return true;
        }else {
            return false;
        }
    }


    public static boolean isGooglePlayServicesAvailable(AppCompatActivity activity) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, activity, 0).show();
            return false;
        }
    }
}
