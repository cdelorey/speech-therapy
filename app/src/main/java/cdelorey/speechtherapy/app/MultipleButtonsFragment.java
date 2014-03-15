package cdelorey.speechtherapy.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/**
 * Fragment with multiple buttons.
 */
public class MultipleButtonsFragment extends TimerFragment {
    ArrayList<TimerButton> buttons = new ArrayList<TimerButton>();

    public MultipleButtonsFragment() {

    }

    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_multiple_buttons, container, false);
        return rootView;
    }

    public void setupTimerButtons() {
        // TODO: add button ids to static final list somehow and add to buttons list in for loop
        buttons.add((TimerButton) getActivity().findViewById(R.id.button1));
        buttons.add((TimerButton) getActivity().findViewById(R.id.button2));
        buttons.add((TimerButton) getActivity().findViewById(R.id.button3));
        buttons.add((TimerButton) getActivity().findViewById(R.id.button4));
        buttons.add((TimerButton) getActivity().findViewById(R.id.button5));
        buttons.add((TimerButton) getActivity().findViewById(R.id.button6));
        buttons.add((TimerButton) getActivity().findViewById(R.id.button7));
        buttons.add((TimerButton) getActivity().findViewById(R.id.button8));
        buttons.add((TimerButton) getActivity().findViewById(R.id.button9));
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
