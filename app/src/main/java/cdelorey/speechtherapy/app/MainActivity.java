package cdelorey.speechtherapy.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences.Editor;


public class MainActivity extends ActionBarActivity implements TabListener,
        SetTimerDialogFragment.TimerDialogListener {
    // UI ------------------------------------------------------------------------------------------
    private ViewPager viewPager;
    private TabsPagerAdapter adapter;
    private ActionBar actionBar;
    private String[] tabs = { "Buttons", "Button", "Bar" };

    // State ---------------------------------------------------------------------------------------
    private double timer;

    // Lifecycle Methods ---------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        adapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Add tabs
        for(String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // select correct tab
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        // Get last timer value or set timer to 0 if first use
        loadTimerFromPreferences();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveTimerToPreferences();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTimerFromPreferences();
    }

    // Options Methods -----------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_timer:
                showTimerDialog();
                return true;
            case R.id.action_help:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Tab Listener Methods ------------------------------------------------------------------------
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // show selected fragment
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {

    }


    // TimerDialogListener Methods -----------------------------------------------------------------
    /* onDialogPositiveClick
     * Set timer to value specified by user in SetTimerDialog */
    public void onDialogPositiveClick(DialogFragment dialog) {
        SetTimerDialogFragment fragment = (SetTimerDialogFragment) dialog;
        timer = fragment.getTimerValue();
    }


    // Private Methods -----------------------------------------------------------------------------
    private void showTimerDialog() {
        SetTimerDialogFragment dialog = new SetTimerDialogFragment();
        dialog.show(getSupportFragmentManager(), "SetTimerDialogFragment");
    }

    private void loadTimerFromPreferences() {
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        timer = Double.longBitsToDouble(prefs.getLong("timer", 0));
    }

    private void saveTimerToPreferences() {
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putLong("timer", Double.doubleToRawLongBits(timer));
    }
}
