package cdelorey.speechtherapy.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/**
 * Fragment with multiple buttons.
 */
public class MultipleButtonsFragment extends TimerFragment {
    private static final int[] BUTTON_IDS = { R.id.button1, R.id.button2, R.id.button3, R.id.button4,
        R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9 };

    ArrayList<TimerButton> buttons = new ArrayList<TimerButton>();

    public MultipleButtonsFragment() {

    }

    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_multiple_buttons, container, false);
        return rootView;
    }

    public void setupTimerButtons() {
        for(int id : BUTTON_IDS) {
            buttons.add((TimerButton) getActivity().findViewById(id));
        }
    }

    public void registerCommunicator() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setMultipleButtonsFragmentCommunicator(this);
    }

    public void clearTimers() {
        for(TimerButton button : buttons) {
            button.clearTimer();
        }
    }

    public void setTimers(int milliseconds) {
        for(TimerButton button : buttons) {
            button.setTimerLength(milliseconds);
        }
    }
}
