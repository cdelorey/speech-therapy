package cdelorey.speechtherapy.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.app.Activity;
import java.text.DecimalFormat;

/**
 * Dialog fragment that prompts user to set the timer.
 */
public class SetTimerDialogFragment extends DialogFragment {
    // Constants -----------------------------------------------------------------------------------
    private static final int SEEKBAR_MAX = 200;

    // State ---------------------------------------------------------------------------------------
    private TextView timerLabel;
    private int timerValue;
    private TimerDialogListener listener;

    // Interfaces ----------------------------------------------------------------------------------
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks. */
    public interface TimerDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    // Getters -------------------------------------------------------------------------------------
    public int getTimerValue() {
        return timerValue;
    }

    // Initialization Methods ----------------------------------------------------------------------
    public SetTimerDialogFragment(int timer) {
        timerValue = timer;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (TimerDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TimerDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get dialog layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.timer_dialog, null);

        // setup seekbar
        SeekBar seekBar = (SeekBar) layout.findViewById(R.id.timer_dialog_seekbar);
        timerLabel = (TextView) layout.findViewById(R.id.current_value);
        setSeekbarLabel(timerValue);
        seekBar.setMax(SEEKBAR_MAX);
        seekBar.setProgress(timerValue);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setSeekbarLabel(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                timerValue = seekBar.getProgress();
            }
        });

        // create dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_set_timer)
                .setView(layout)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // set timer preference
                        listener.onDialogPositiveClick(SetTimerDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SetTimerDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }


    // Private Methods -----------------------------------------------------------------------------
    private void setSeekbarLabel(int progress) {
        timerLabel.setText("Timer length: "
                + new DecimalFormat("0.00").format(progress)
                + " seconds");
    }

}
