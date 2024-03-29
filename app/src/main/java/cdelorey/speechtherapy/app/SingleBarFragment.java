package cdelorey.speechtherapy.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Fragment with a single bar.
 */
public class SingleBarFragment extends TimerFragment {
    private Button button;
    private TimerBar bar;
    private boolean barIsRunning = false;

    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_single_bar, container, false);
        return rootView;
    }

    public void setupTimerButtons() {
        bar = (TimerBar) getActivity().findViewById(R.id.timer_bar);
        button = (Button) getActivity().findViewById(R.id.timer_bar_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bar.timerLength > 0) {
                    if (barIsRunning) {
                        barIsRunning = false;
                        bar.clearTimer();
                        button.setText("Start");
                    } else {
                        barIsRunning = true;
                        bar.startTimer();
                        button.setText("Stop");
                        Log.e(Constants.LOG, "Starting timer");
                    }
                }
            }
        });
    }

    public void registerCommunicator() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setSingleBarFragmentCommunicator(this);
    }

    public void clearTimers() {
        if(barIsRunning) {
            bar.clearTimer();
            barIsRunning = false;
            button.setText("Start");
        }
    }

    public void setTimers(int milliseconds) {
        bar.setTimerLength(milliseconds);
    }

    @Override
    public void onLeaveFragment() {
        clearTimers();
    }


}
