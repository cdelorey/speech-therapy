package cdelorey.speechtherapy.app;

import android.content.Context;
import android.util.AttributeSet;

/**
 *
 */
public class TimerBar extends TimerButton {
    // Constructors --------------------------------------------------------------------------------
    public TimerBar(Context context) {
        super(context);
    }

    public TimerBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onTimerFinish() {
        isMovingBackwards = !isMovingBackwards;
        startTimer();
    }

    @Override
    public void clearTimer() {
        super.clearTimer();
        isMovingBackwards = false;
    }

}
