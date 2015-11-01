package com.grabhouse.grabhouse.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.grabhouse.grabhouse.R;
import com.grabhouse.grabhouse.adapters.DemoPagerAdapter;
import com.grabhouse.grabhouse.utilities.PrefManager;
import com.grabhouse.grabhouse.utilities.Utilities;


public class ActivityDemo extends ActionBarActivity {

    ActionBarActivity activity = this;
    DemoPagerAdapter mDemoPagerAdapter;
    ViewPager mViewPager;
    PrefManager prefManager;
    private int currentBackgroundColor;
    private Button button_next;
    private Button button_skip;
    private ImageView imageView;

    private int mSelectedPosition = 0;
    private float mSelectionOffset = 0f;
    private int[] mBackgroundColors;
    private int mColor;
    private int mNextColor;

    private int pageCount = 3;
    private final String BUTTON_TEXT_NEXT = "NEXT";
    private final String BUTTON_TEXT_DONE = "DONE";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        prefManager = new PrefManager(this);
        prefManager.setDemoShown(true);

        mBackgroundColors = new int[]{getResources().getColor(R.color.color_1), getResources().getColor(R.color.color_2), getResources().getColor(R.color.color_3)};
        // Finding Views
        button_next = (Button) findViewById(R.id.button_demo_nextanddone);
        button_skip = (Button) findViewById(R.id.button_demo_skip);
        button_next.setText(BUTTON_TEXT_NEXT);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_next.getText() == BUTTON_TEXT_NEXT){
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                }else if (button_next.getText().equals(BUTTON_TEXT_DONE)){
                    goToNextActivity();
                }
            }
        });

        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity();
            }
        });
        // make full screen
        Utilities.makeItFullScreen(activity);
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mDemoPagerAdapter = new DemoPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("Riyas", "onPageScrolled: " + positionOffset);

                if(position==0 && positionOffset > 0f) {
                    //setAlphaForLogo(positionOffset);
                }

                mSelectedPosition = position;
                mColor = mBackgroundColors[mSelectedPosition];

                if (positionOffset > 0f && position < (mViewPager.getChildCount() - 1)) {
                    mNextColor = mBackgroundColors[mSelectedPosition + 1];
                }
                if (mColor != mNextColor) {
                    mColor = mBackgroundColors[position];
                    mViewPager.setBackgroundColor(blendColors(mNextColor, mColor, positionOffset));
                }
                mSelectionOffset = positionOffset;
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("Riyas", "onPageSelected: " + position);

                // mark view pager indicator
                selectViewPagerIndicator(position);

                // changing the Next_Button_text depending on the position of the page
                if(mViewPager.getCurrentItem() == (pageCount - 1)){
                    button_next.setText(BUTTON_TEXT_DONE);
                }else{
                    button_next.setText(BUTTON_TEXT_NEXT);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("Riyas", "onPageScrollStateChanged: " + state);
            }
        });

    }

    private void selectViewPagerIndicator(int position){

        ImageView mOval_1 = (ImageView) activity.findViewById(R.id.iv_oval_1);
        ImageView mOval_2 = (ImageView) activity.findViewById(R.id.iv_oval_2);
        ImageView mOval_3 = (ImageView) activity.findViewById(R.id.iv_oval_3);

        switch (position){
            case 0: mOval_1.setImageResource(R.drawable.oval_viewpager_indicator_dark);
                    mOval_2.setImageResource(R.drawable.oval_viewpager_indicator_light);
                    mOval_3.setImageResource(R.drawable.oval_viewpager_indicator_light);
                    break;
            case 1: mOval_2.setImageResource(R.drawable.oval_viewpager_indicator_dark);
                    mOval_1.setImageResource(R.drawable.oval_viewpager_indicator_light);
                    mOval_3.setImageResource(R.drawable.oval_viewpager_indicator_light);
                    break;
            case 2: mOval_3.setImageResource(R.drawable.oval_viewpager_indicator_dark);
                    mOval_1.setImageResource(R.drawable.oval_viewpager_indicator_light);
                    mOval_2.setImageResource(R.drawable.oval_viewpager_indicator_light);
                    break;
        }
    }

    private void goToNextActivity(){
        if(!prefManager.isLoggedIn()){
            Intent intent = new Intent(activity, ActivityOTP.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }

    private static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }
}
