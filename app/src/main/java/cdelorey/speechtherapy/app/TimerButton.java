package cdelorey.speechtherapy.app;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 *
 */
public class TimerButton extends Button {
    // Constants -----------------------------------------------------------------------------------
    private static final int TIMER_BUTTON_HEIGHT = 100;
    private static final int TIMER_BUTTON_WIDTH = 100;


    // Constructors --------------------------------------------------------------------------------
    public TimerButton(Context context) {
        super(context);
        setupListener();
    }

    public TimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupListener();
    }


    // Private Methods -----------------------------------------------------------------------------
    private void setupListener() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    TimerButton.this.setBackgroundColor(Color.GREEN);
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    TimerButton.this.setBackgroundColor(Color.RED);
                }
                return false;
            }
        });
    }

}
