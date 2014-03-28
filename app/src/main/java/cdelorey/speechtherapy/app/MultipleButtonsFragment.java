package cdelorey.speechtherapy.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import java.util.ArrayList;

/**
 * Fragment with multiple buttons.
 */
public class MultipleButtonsFragment extends TimerFragment implements
        TimerButtonVertical.TimerButtonCommunicator {
    private static final int[] BUTTON_IDS = { R.id.button1, R.id.button2, R.id.button3, R.id.button4,
        R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9 };

    ArrayList<TimerButtonVertical> buttons = new ArrayList<TimerButtonVertical>();

    public MultipleButtonsFragment() {

    }

    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_multiple_buttons, container, false);
        return rootView;
    }

    public void setupTimerButtons() {
        TimerButtonVertical button;
        for(int id : BUTTON_IDS) {
            button = (TimerButtonVertical) getActivity().findViewById(id);
            button.setTimerButtonCommunicator(this);
            buttons.add(button);
        }
    }

    public void registerCommunicator() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setMultipleButtonsFragmentCommunicator(this);
    }

    public void clearTimers() {
        for(TimerButtonVertical button : buttons) {
            button.clearTimer();
        }
    }

    public void setTimers(int milliseconds) {
        for(TimerButtonVertical button : buttons) {
            button.setTimerLength(milliseconds);
        }
    }

    // called when one of the buttons in the fragment is pressed long enough
    public void onFinished() {
        // reset all buttons
        if(allButtonsAreFinished()) {
            resetButtons();
        }
    }

    // returns true if all buttons have been pressed successfully
    private boolean allButtonsAreFinished() {
        for(TimerButtonVertical button : buttons) {
            if(!button.getIsFinished()) {
                return false;
            }
        }
        return true;
    }

    private void resetButtons() {
        for(TimerButtonVertical button : buttons) {
            button.resetButton();
        }
    }
}
