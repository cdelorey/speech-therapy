package cdelorey.speechtherapy.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

/**
 * Fragment with a single button.
 */
public class SingleButtonFragment extends TimerFragment {
    private TimerButton singleButton;

    public SingleButtonFragment() {

    }

    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_single_button, container, false);
        Log.e(Constants.LOG, "View created");
        return rootView;
    }

    public void setupTimerButtons() {
        Log.e(Constants.LOG, "Fragment: OnActivityCreated");
        singleButton = (TimerButton) getActivity().findViewById(R.id.single_button);
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
}
