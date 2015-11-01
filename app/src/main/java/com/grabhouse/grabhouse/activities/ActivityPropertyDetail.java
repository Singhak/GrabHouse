package com.grabhouse.grabhouse.activities;

import android.os.Bundle;

import com.grabhouse.grabhouse.R;

public class ActivityPropertyDetail extends ActivityCustom {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setActionBar("Detail");
    }
}
