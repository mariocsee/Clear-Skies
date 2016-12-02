package mariocsee.android.worldwideweather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mariocsee.android.worldwideweather.fragments.FragmentDetails;
import mariocsee.android.worldwideweather.fragments.FragmentMain;

/**
 * Created by mariocsee on 12/1/16.
 */

public class WeatherPagerAdapter extends FragmentPagerAdapter {
    public WeatherPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FragmentMain();
                break;
            case 1:
                fragment = new FragmentDetails();
                break;
            default:
                fragment = new FragmentMain();
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Main";
            case 1:
                return "Details";
        }

        return "Main";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
