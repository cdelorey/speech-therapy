package cdelorey.speechtherapy.app;

import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;
import android.util.AttributeSet;
import android.graphics.drawable.Drawable;
import android.graphics.Rect;
import android.graphics.Canvas;

/**
 *
 */
public class TimerButtonVertical extends TimerButton {
    private boolean isFinished = false; // has the button been pressed long enough?
    private TimerButtonCommunicator timerButtonCommunicator;
    private int x, y, z, w;

    /* TimerButtonCommunicator
     * Used to call fragment methods from TimerButton */
    public interface TimerButtonCommunicator {
        // TimerButton can notify fragment that it has been pressed long enough
        public void onFinished();
    }

    // Constructors --------------------------------------------------------------------------------
    public TimerButtonVertical(Context context) {
        super(context);
        setupListener();
    }

    public TimerButtonVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupListener();
    }

    public void setTimerButtonCommunicator(TimerButtonCommunicator communicator) {
        timerButtonCommunicator = communicator;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    @Override
    public void onTimerFinish() {
        setProgressBarBackground(getResources().getDrawable(R.drawable.timer_button_done));
    }

    public void resetButton() {
        isFinished = false;
        setProgressBarBackground(getResources().getDrawable(R.drawable.timer_button));
        setProgress(0);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
        this.x = w;
        this.y = h;
        this.z = oldw;
        this.w = oldh;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas canvas) {
        canvas.rotate(-90);
        canvas.translate(-getHeight(), 0);
        super.onDraw(canvas);
    }

    @Override
    public synchronized void setProgress(int progress) {

        if (progress >= 0)
            super.setProgress(progress);

        else
            super.setProgress(0);
        onSizeChanged(x, y, z, w);

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
                        isFinished = true;
                        // multiple buttons
                        if(timerButtonCommunicator != null) {
                            timerButtonCommunicator.onFinished();
                        // single button
                        } else {
                            resetButton();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
