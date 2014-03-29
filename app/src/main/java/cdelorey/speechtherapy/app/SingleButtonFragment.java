package cdelorey.speechtherapy.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.ProgressBar;

/**
 * Fragment with a single button.
 */
public class SingleButtonFragment extends TimerFragment {
    private TimerButtonVertical singleButton;

    public SingleButtonFragment() {

    }

    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_single_button, container, false);
        return rootView;
    }

    public void setupTimerButtons() {
        singleButton = (TimerButtonVertical) getActivity().findViewById(R.id.single_button);
    }

    public void registerCommunicator() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setSingleButtonFragmentCommunicator(this);
    }

    public void clearTimers() {
        singleButton.clearTimer();
    }

    public void setTimers(int milliseconds) {
        singleButton.setTimerLength(milliseconds);
    }

    @Override
    public void onLeaveFragment() {
        singleButton.resetButton();
    }
}
