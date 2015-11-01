package com.grabhouse.grabhouse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.grabhouse.grabhouse.R;

public class MainActivity extends ActivityCustom {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setActionBar();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public void onClick(View view){
        int id = view.getId();
        switch(id){
            case R.id.button_search:
                Intent intent = new Intent(this, ActivitySearch.class);
                startActivity(intent);
                break;
            case R.id.button_sell:
                Intent intent2 = new Intent(this, ActivityPost.class);
                startActivity(intent2);
                break;
        }
    }
}
