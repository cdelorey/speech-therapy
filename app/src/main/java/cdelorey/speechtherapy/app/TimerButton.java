package cdelorey.speechtherapy.app;


import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;
import android.widget.ProgressBar;

/**
 *
 */
public class TimerButton extends ProgressBar {
    // Data ----------------------------------------------------------------------------------------
    private static final int GREEN = 0xff00b358;
    private static final int BLUE = 0xff0969a2;
    private static final int RED = 0xffff3d00;
    private static final int TIMER_INTERVAL = 10; // milliseconds
    private boolean timerIsRunning = false;
    private int timerLength; // milliseconds
    private CountDownTimer countDownTimer;

    // Constructors --------------------------------------------------------------------------------
    public TimerButton(Context context) {
        super(context);
        setup();
    }

    public TimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }


    // Public Methods ------------------------------------------------------------------------------
    public void setTimerLength(int length) {
        timerLength = length;
        countDownTimer = new CountDownTimer(timerLength, TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = timerLength - (int) millisUntilFinished;
                setProgress(progress);
            }

            @Override
            public void onFinish() {
                // button has been held long enough
                timerIsRunning = false;
                TimerButton.this.setBackgroundColor(BLUE);
                //TimerButton.this.setText("DONE");
                setProgress(timerLength);
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


    // Private Methods -----------------------------------------------------------------------------
    private void setup() {
        //this.setText("");
        setBackgroundColor(GREEN);
        setupListener();
    }

    private void setupListener() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Start new timer
                    countDownTimer.start();
                    timerIsRunning = true;
                    setBackgroundColor(GREEN);
                    //TimerButton.this.setText("");
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(timerIsRunning) {
                        // too slow
                        clearTimer();
                        setBackgroundColor(RED);
                        //TimerButton.this.setText("TOO FAST");
                        setProgress(0);
                        return true;
                    } else {
                        // reset button
                        setBackgroundColor(GREEN);
                        //TimerButton.this.setText("");
                        setProgress(0);
                        return true;
                    }
                }
                return false;
            }
        });
    }

}
