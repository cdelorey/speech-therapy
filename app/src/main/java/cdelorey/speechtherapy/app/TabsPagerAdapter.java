package cdelorey.speechtherapy.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import cdelorey.speechtherapy.app.Constants;

public class TabsPagerAdapter  extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int index) {
        switch(index) {
            case 0:
                return new MultipleButtonsFragment();
            case 1:
                return new SingleButtonFragment();
            case 2:
                return new SingleBarFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // number of tabs
        return 3;
    }
}
