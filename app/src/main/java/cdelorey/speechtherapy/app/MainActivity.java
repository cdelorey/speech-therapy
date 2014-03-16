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
import android.util.Log;

/**
 * Main
 */
public class MainActivity extends ActionBarActivity implements TabListener,
        SetTimerDialogFragment.TimerDialogListener {
    // UI ------------------------------------------------------------------------------------------
    private ViewPager viewPager;
    private TabsPagerAdapter adapter;
    private ActionBar actionBar;
    private String[] tabs = { "Buttons", "Button", "Bar" };

    // State ---------------------------------------------------------------------------------------
    private int timer; // milliseconds
    private int currentTab = 0;
    private FragmentCommunicator singleButtonFragmentCommunicator;
    private FragmentCommunicator multipleButtonsFragmentCommunicator;

    /* TimerCommunicator
     * Used to call fragment methods from MainActivity */
    public interface FragmentCommunicator {
        // MainActivity can notify fragments that user has changed the timer length
        public void onChangeTimer(int milliseconds);
        // MainActivity can notify fragments that the user has dragged the screen
        public void onDragGesture();
    }

    // Getters and Setters -------------------------------------------------------------------------
    public int getTimer() {
        return timer;
    }

    // Communicators -------------------------------------------------------------------------------
    public void setSingleButtonFragmentCommunicator(FragmentCommunicator communicator) {
        singleButtonFragmentCommunicator = communicator;
    }

    public void setMultipleButtonsFragmentCommunicator(FragmentCommunicator communicator) {
        multipleButtonsFragmentCommunicator = communicator;
    }

    // Lifecycle Methods ---------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);
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
                //Log.e(Constants.LOG, Integer.toString(position));
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}

            @Override
            public void onPageScrollStateChanged(int state) {
                // Timers must be cleared once a drag begins because the drag gesture prevents the
                // ACTION_UP MotionEvent from firing, which is necessary for the TimerButtons to
                // function properly.
                if(state == ViewPager.SCROLL_STATE_DRAGGING) {
                    switch(viewPager.getCurrentItem()) {
                        case 0:
                            multipleButtonsFragmentCommunicator.onDragGesture();
                            break;
                        case 1:
                            singleButtonFragmentCommunicator.onDragGesture();
                            break;
                        case 2:
                            break;
                    }
                }

            }
        });
        Log.e(Constants.LOG, "Activity created");
    }

    @Override
    public void onPause() {
        super.onPause();
        saveDataToPreferences();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(Constants.LOG, "Activity: OnResume");
        loadDataFromPreferences();
        viewPager.setCurrentItem(currentTab);
    }

    // Options Methods -----------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // add items to action bar
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    public void onTabReselected(Tab tab, FragmentTransaction ft) {}

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // show selected fragment
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {}


    // TimerDialogListener Methods -----------------------------------------------------------------
    /* onDialogPositiveClick
     * Set timer to value specified by user in SetTimerDialog */
    public void onDialogPositiveClick(DialogFragment dialog) {
        SetTimerDialogFragment fragment = (SetTimerDialogFragment) dialog;
        timer = fragment.getTimerValue();
        singleButtonFragmentCommunicator.onChangeTimer(timer);
        multipleButtonsFragmentCommunicator.onChangeTimer(timer);
    }


    // Private Methods -----------------------------------------------------------------------------
    private void showTimerDialog() {
        SetTimerDialogFragment dialog = new SetTimerDialogFragment(timer);
        dialog.show(getSupportFragmentManager(), "SetTimerDialogFragment");
    }

    private void loadDataFromPreferences() {
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        timer = prefs.getInt("timer", 0);
        currentTab = prefs.getInt("tab", 0);
    }

    private void saveDataToPreferences() {
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putInt("timer", timer);
        editor.putInt("tab", viewPager.getCurrentItem());
        editor.commit();
    }
}
