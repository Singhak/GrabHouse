package com.grabhouse.grabhouse.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.grabhouse.grabhouse.R;

public class ActivityHouseMap extends ActivityCustom {

    public static final String RESPONSE = "response";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_map);
        this.setActionBar();

        // get the passed array
        String json_houses = getIntent().getExtras().getString(RESPONSE);
        Toast.makeText(this, json_houses, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view){
        int id = view.getId();
    }
}
