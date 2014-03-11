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

import java.text.DecimalFormat;

/**
 * Dialog fragment that prompts user to set the timer.
 */
public class SetTimerDialogFragment extends DialogFragment {
    private static final int SEEKBAR_MAX = 200;
    private TextView timerValue;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get dialog layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.timer_dialog, null);

        // setup seekbar
        SeekBar seekBar = (SeekBar) layout.findViewById(R.id.timer_dialog_seekbar);
        timerValue = (TextView) layout.findViewById(R.id.current_value);
        seekBar.setMax(SEEKBAR_MAX);
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
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    private void setSeekbarLabel(int progress) {
        // Seekbars store progress as an int, so this value must be converted
        // to a float to display timer values in terms of milliseconds.
        double result = progress / 100.0;
        timerValue.setText("Timer length: "
                + new DecimalFormat("0.00").format(result)
                + " seconds");
    }

}