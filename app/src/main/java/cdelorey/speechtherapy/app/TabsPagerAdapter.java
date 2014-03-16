package cdelorey.speechtherapy.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TabsPagerAdapter  extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int index) {
        switch(index) {
            case 0:
                Log.e(Constants.LOG, "creating MultipleButtonsFragment");
                return new MultipleButtonsFragment();
            case 1:
                Log.e(Constants.LOG, "creating SingleButtonFragment");
                return new SingleButtonFragment();
            case 2:
                Log.e(Constants.LOG, "creating SingleBarFragment");
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
