package cdelorey.speechtherapy.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.app.Activity;

/**
 * Fragment with a single button.
 */
public class SingleButtonFragment extends Fragment implements MainActivity.TimerCommunicator {
    private TimerButton singleButton;

    public SingleButtonFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_single_button, container, false);

        Log.e(Constants.LOG, "View created");
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(Constants.LOG, "Fragment: OnActivityCreated");
        singleButton = (TimerButton) getActivity().findViewById(R.id.single_button);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setSingleButtonFragmentCommunicator(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        singleButton.clearTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(Constants.LOG, "Fragment: onResume");
        getTimerFromActivity();
    }

    public void onChangeTimer(int milliseconds) {
        singleButton.setTimerLength(milliseconds);
    }

    private void getTimerFromActivity() {
        MainActivity activity = (MainActivity) getActivity();
        int milliseconds = activity.getTimer();
        Log.e(Constants.LOG, Integer.toString(milliseconds));
        singleButton.setTimerLength(milliseconds);
    }
}
