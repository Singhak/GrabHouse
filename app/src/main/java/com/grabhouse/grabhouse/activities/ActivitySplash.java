package com.grabhouse.grabhouse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.grabhouse.grabhouse.R;
import com.grabhouse.grabhouse.utilities.PrefManager;
import com.grabhouse.grabhouse.utilities.Utilities;

public class ActivitySplash extends ActivityCustom {
    private AppCompatActivity mActivity = this;
    private ImageView imageView;
    private int SPLASH_SCREEN_DURATION = 2500;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefManager = new PrefManager(this);
        // Make UI Full Screen
        Utilities.makeItFullScreen(mActivity);

        Thread thread = new Thread(){
            public void run(){
                try{
                    Thread.sleep(SPLASH_SCREEN_DURATION);

                    // Check it is first run or not.
                    if(!prefManager.isDemoShown()) {
                        Intent intent = new Intent(getBaseContext(), ActivityDemo.class);
                        startActivity(intent);
                    }else if(!prefManager.isLoggedIn()){
                        Intent intent = new Intent(getBaseContext(), ActivityOTP.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    finish();

                }catch (Exception e){

                }
            }
        };
        thread.start();
    }

    private void setAnimationForLogo() {
        AlphaAnimation animation1 = new AlphaAnimation(0f, 1f);
        animation1.setDuration(1500);
        animation1.setStartOffset(1000);
        animation1.setFillAfter(true);
        imageView.startAnimation(animation1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
