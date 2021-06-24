package com.ipast.textclock;

import android.content.Context;

import android.os.Handler;
import android.util.AttributeSet;

import java.util.Calendar;

/**
 * author:chenggang
 * date:2020/11/12
 * description:十二小时制Text控件
 */
public class TwelveTextClock extends androidx.appcompat.widget.AppCompatTextView {
    private Runnable mTickerRunnable = null;
    private Handler mHandler = null;

    public TwelveTextClock(Context context) {
        super(context);
    }

    public TwelveTextClock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TwelveTextClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHandler = new Handler();
        mTickerRunnable = () -> {
            Calendar calendar = Calendar.getInstance();
            setText(format12Time(calendar));
            invalidate();
            int second = calendar.get(Calendar.SECOND);
            int milliSecond = calendar.get(Calendar.MILLISECOND);
            long delayMillis = 60 * 1000;
            if (second != 0 || milliSecond != 0) {
                delayMillis -= (second * 1000 + milliSecond);
            }
            mHandler.postDelayed(mTickerRunnable, delayMillis);
        };
        mTickerRunnable.run();
    }

    private String format12Time(Calendar calendar) {
        String hoursStr = timeStrFormat(String.valueOf(calendar.get(Calendar.HOUR)));
        String minutesStr = timeStrFormat(String.valueOf(calendar.get(Calendar.MINUTE)));
        return String.format("%s:%s", hoursStr, minutesStr);
    }

    private String timeStrFormat(String timeStr) {
        return timeStr.length() == 1 ? "0" + timeStr : timeStr;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHandler != null && mTickerRunnable != null) {
            mHandler.removeCallbacks(mTickerRunnable);
        }
    }
}
