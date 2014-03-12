package cdelorey.speechtherapy.app;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.os.Handler;

/**
 *
 */
public class TimerButton extends Button {
    // Data ----------------------------------------------------------------------------------------
    private boolean timerIsRunning = false;
    private int timerLength = 1000; // milliseconds
    private Handler handler = new Handler();

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            // button has been held long enough
            timerIsRunning = false;
            TimerButton.this.setBackgroundColor(Color.BLUE);
            TimerButton.this.setText("DONE");
        }
    };


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
    }

    public void clearTimer() {
        handler.removeCallbacks(timer);
    }


    // Private Methods -----------------------------------------------------------------------------
    private void setup() {
        setupListener();
        this.setText("");
        this.setBackgroundColor(Color.GREEN);
    }

    private void setupListener() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Start new timer
                    handler.postDelayed(timer, timerLength);
                    timerIsRunning = true;
                    TimerButton.this.setBackgroundColor(Color.GREEN);
                    TimerButton.this.setText("");
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(timerIsRunning) {
                        // too slow
                        clearTimer();
                        TimerButton.this.setBackgroundColor(Color.RED);
                        TimerButton.this.setText("TOO FAST");
                    } else {
                        // reset button
                        TimerButton.this.setBackgroundColor(Color.GREEN);
                        TimerButton.this.setText("");
                    }
                }
                return false;
            }
        });
    }

}
