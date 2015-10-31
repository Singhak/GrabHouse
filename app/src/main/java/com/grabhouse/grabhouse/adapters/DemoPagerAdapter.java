package com.grabhouse.grabhouse.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.grabhouse.grabhouse.fragments.DemoFragment1;
import com.grabhouse.grabhouse.fragments.DemoFragment2;
import com.grabhouse.grabhouse.fragments.DemoFragment3;

public class DemoPagerAdapter extends FragmentPagerAdapter {
    public DemoPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }
    @Override
    public Fragment getItem(int i) {
        //Fragment fragment = new DemoFragment1();

        switch (i){
            case 0: return new DemoFragment1();
            case 1: return new DemoFragment2();
            case 2: return new DemoFragment3();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
