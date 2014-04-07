package cdelorey.speechtherapy.app;

import android.app.ActionBar;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

/**
 * Fragment with multiple buttons.
 */
public class MultipleButtonsFragment extends TimerFragment implements
        TimerButtonVertical.TimerButtonCommunicator {
    private static final int[] BUTTON_IDS = { R.id.button1, R.id.button2, R.id.button3, R.id.button4,
        R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9 };
    private static final int PADDING = 15;

    ArrayList<TimerButtonVertical> buttons = new ArrayList<TimerButtonVertical>();

    public MultipleButtonsFragment() {

    }

    public View inflateView(LayoutInflater inflater, ViewGroup container) {
        ViewTreeObserver observer = null;
        final View rootView = inflater.inflate(R.layout.fragment_multiple_buttons, container, false);
        if(rootView != null){
            observer = rootView.getViewTreeObserver();
        }
        if (observer != null) {

            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if(Build.VERSION.SDK_INT < 16) {
                        rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    setupButtons();
                }
            });
        }
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

    @Override
    public void onLeaveFragment() {
        resetButtons();
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

    private void setupButtons() {
        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.button_grid);
        int viewWidth = layout.getMeasuredWidth();
        int viewHeight = layout.getMeasuredHeight();
        int buttonSize;
        int buttonViewPadding;

        // calculate sizes so buttons are arranged in a grid that is centered in the view
        if(viewWidth < viewHeight) {
            // portrait layout
            buttonSize = (viewWidth - (PADDING * 4)) / 3;
            buttonViewPadding = ((viewHeight - (buttonSize * 3 + PADDING * 2))) / 2;
        } else {
            // landscape layout
            buttonSize = (viewHeight - (PADDING * 4)) / 3;
            buttonViewPadding = ((viewWidth - (buttonSize * 3 + PADDING * 2))) / 2;
        }

        setButtonSize(buttonSize);
        setButtonViewPadding(buttonViewPadding, viewWidth < viewHeight);
    }

    private void setButtonSize(int buttonSize) {
        for(TimerButtonVertical button : buttons) {
            button.getLayoutParams().height = buttonSize;
            button.getLayoutParams().width = buttonSize;
            button.setPadding(PADDING, PADDING, PADDING, PADDING);
        }
    }

    private void setButtonViewPadding(int buttonViewPadding, boolean isPortrait) {
        if(isPortrait) {
            LinearLayout top_row = (LinearLayout) getActivity().findViewById(R.id.top_row);
            top_row.setPadding(0, buttonViewPadding, 0 , 0);
        } else {
            LinearLayout top_row = (LinearLayout) getActivity().findViewById(R.id.top_row);
            LinearLayout middle_row = (LinearLayout) getActivity().findViewById(R.id.middle_row);
            LinearLayout bottom_row = (LinearLayout) getActivity().findViewById(R.id.bottom_row);
            top_row.setPadding(buttonViewPadding, 0, 0, 0);
            middle_row.setPadding(buttonViewPadding, 0, 0, 0);
            bottom_row.setPadding(buttonViewPadding, 0, 0, 0);
        }
    }


}
