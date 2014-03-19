package cdelorey.speechtherapy.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 *
 */
public class TimerButtonVertical extends TimerButton {
    // Constructors --------------------------------------------------------------------------------
    public TimerButtonVertical(Context context) {
        super(context);
        setupListener();
    }

    public TimerButtonVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupListener();
    }

    @Override
    public void onTimerFinish() {
        setProgressDrawable(getResources().getDrawable(R.drawable.timer_button_done));
    }

    // Private Methods -----------------------------------------------------------------------------
    private void setupListener() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Start new timer
                    setProgressDrawable(getResources().getDrawable(R.drawable.timer_button));
                    countDownTimer.start();
                    timerIsRunning = true;
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(timerIsRunning) {
                        // too slow
                        clearTimer();
                        setProgressDrawable(getResources().getDrawable(R.drawable.timer_button_too_fast));
                        setProgress(0);
                        return true;
                    } else {
                        // reset button
                        setProgressDrawable(getResources().getDrawable(R.drawable.timer_button));
                        setProgress(0);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
