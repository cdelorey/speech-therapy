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
    // Constants -----------------------------------------------------------------------------------
    private static final int TIMER_BUTTON_HEIGHT = 100;
    private static final int TIMER_BUTTON_WIDTH = 100;

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
        }
    };


    // Constructors --------------------------------------------------------------------------------
    public TimerButton(Context context) {
        super(context);
        setupListener();
    }

    public TimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupListener();
    }

    // Setters -------------------------------------------------------------------------------------
    public void setTimerLength(int length) {
        timerLength = length;
    }

    public void clearTimer() {
        handler.removeCallbacks(timer);
    }

    // Private Methods -----------------------------------------------------------------------------
    private void setupListener() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Start new timer
                    handler.postDelayed(timer, timerLength);
                    timerIsRunning = true;
                    TimerButton.this.setBackgroundColor(Color.GREEN);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    // too slow
                    if(timerIsRunning) {
                        clearTimer();
                        TimerButton.this.setBackgroundColor(Color.RED);
                    }
                }
                return false;
            }
        });
    }

}
