package cdelorey.speechtherapy.app;


import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.util.Log;

/**
 *
 */
public abstract class TimerButton extends ProgressBar {
    // Data ----------------------------------------------------------------------------------------
    private static final int TIMER_INTERVAL = 10; // milliseconds
    protected int timerLength; // milliseconds
    protected CountDownTimer countDownTimer;
    protected boolean timerIsRunning = false;
    protected boolean isMovingBackwards = false;

    // Constructors --------------------------------------------------------------------------------
    public TimerButton(Context context) {
        super(context);
    }

    public TimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    // Public Methods ------------------------------------------------------------------------------
    public void setTimerLength(int length) {
        timerLength = length;
        countDownTimer = new CountDownTimer(timerLength, TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = 0;
                if(isMovingBackwards) {
                    progress = (int) millisUntilFinished;
                } else {
                    progress = timerLength - (int) millisUntilFinished;
                }
                setProgress(progress);
            }

            @Override
            public void onFinish() {
                // button has been held long enough
                timerIsRunning = false;
                if(isMovingBackwards) {
                    setProgress(0);
                } else {
                    setProgress(timerLength);
                }
                onTimerFinish();
            }
        };
        this.setMax(length);
    }

    public void clearTimer() {
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
        setProgress(0);
    }

    public void startTimer() {
        timerIsRunning = true;
        countDownTimer.start();
    }

    // Abstract Methods ----------------------------------------------------------------------------
    public abstract void onTimerFinish();
}
