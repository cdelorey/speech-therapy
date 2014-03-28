package cdelorey.speechtherapy.app;

import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;
import android.util.AttributeSet;
import android.graphics.drawable.Drawable;
import android.graphics.Rect;

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
        setProgressBarBackground(getResources().getDrawable(R.drawable.timer_button_done));
    }

    // Private Methods -----------------------------------------------------------------------------
    private void setProgressBarBackground(Drawable drawable) {
        // this is necessary due to a bug in gingerbread that makes progressbar drawable disappear
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
            Rect bounds = getProgressDrawable().getBounds();
            setProgressDrawable(drawable);
            getProgressDrawable().setBounds(bounds);
        } else {
            setProgressDrawable(drawable);
        }
    }

    private void setupListener() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Start new timer
                    setProgressBarBackground(getResources().getDrawable(R.drawable.timer_button));
                    startTimer();
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(timerIsRunning) {
                        // too slow
                        clearTimer();
                        setProgressBarBackground(getResources().getDrawable(R.drawable.timer_button_too_fast));
                        setProgress(0);
                        return true;
                    } else {
                        // reset button
                        setProgressBarBackground(getResources().getDrawable(R.drawable.timer_button));
                        setProgress(0);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
