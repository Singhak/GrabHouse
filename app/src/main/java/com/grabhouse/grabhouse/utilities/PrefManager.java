package com.grabhouse.grabhouse.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "my_pref";

    // All Shared Preferences Keys
    private static final String KEY_DEMO_SHOWN = "demo_shown";
    private static final String KEY_LOCATION_SET = "location";
    private static final String KEY_IS_WAITING_FOR_SMS = "IsWaitingForSms";
    private static final String KEY_MOBILE_NUMBER = "mobile_number";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_SERVER_KEY = "key";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isDemoShown() {
        return pref.getBoolean(KEY_DEMO_SHOWN, false);
    }

    public void setDemoShown(boolean isDemoShown) {
        editor.putBoolean(KEY_DEMO_SHOWN, isDemoShown);
        editor.commit();
    }

    public boolean isLocationSet() {
        return pref.getBoolean(KEY_LOCATION_SET, false);
    }

    public void setKeyLocationSet(boolean isLocationSet) {
        editor.putBoolean(KEY_LOCATION_SET, isLocationSet);
        editor.commit();
    }

    public void setIsWaitingForSms(boolean isWaiting) {
        editor.putBoolean(KEY_IS_WAITING_FOR_SMS, isWaiting);
        editor.commit();
    }

    public boolean isWaitingForSms() {
        return pref.getBoolean(KEY_IS_WAITING_FOR_SMS, false);
    }

    public void setServerKey(String key) {
        editor.putString(KEY_SERVER_KEY, key);
        editor.commit();
    }

    public String getKeyServerKey() {
        return pref.getString(KEY_SERVER_KEY, null);
    }

    public void setMobileNumber(String mobileNumber) {
        editor.putString(KEY_MOBILE_NUMBER, mobileNumber);
        editor.commit();
    }

    public String getMobileNumber() {
        return pref.getString(KEY_MOBILE_NUMBER, null);
    }


    public void createLogin(String mobile) {
        editor.putString(KEY_MOBILE, mobile);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }
}