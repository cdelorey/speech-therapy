package cdelorey.speechtherapy.app;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Base class for all fragments containing timerButtons
 */
public abstract class TimerFragment extends Fragment implements MainActivity.FragmentCommunicator {

    public TimerFragment() {

    }

    // Fragment methods ----------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflateView(inflater, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupTimerButtons();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        registerCommunicator();
    }

    @Override
    public void onPause() {
        super.onPause();
        clearTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(Constants.LOG, "Fragment: onResume");
        getTimeFromActivity();
    }

    // FragmentCommunicator methods ----------------------------------------------------------------
    public void onChangeTimer(int milliseconds) {
        setTimers(milliseconds);
    }
    public void onDragGesture() { clearTimers(); }
    public void onLeaveFragment() {}

    // Private methods -----------------------------------------------------------------------------
    private void getTimeFromActivity() {
        MainActivity activity = (MainActivity) getActivity();
        int milliseconds = activity.getTimer();
        Log.e(Constants.LOG, Integer.toString(milliseconds));
        setTimers(milliseconds);
    }

    // Abstract methods ----------------------------------------------------------------------------
    protected abstract View inflateView(LayoutInflater inflater, ViewGroup container);
    protected abstract void setupTimerButtons();
    protected abstract void registerCommunicator();
    protected abstract void clearTimers();
    protected abstract void setTimers(int milliseconds);
}
