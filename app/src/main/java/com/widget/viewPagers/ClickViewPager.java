package com.widget.viewPagers;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by cwj on 16/1/31.
 * 可点击的ViewPager,提供接口
 */
public class ClickViewPager extends ViewPager {

    public interface OnClickListener {
        void onClick();
    }

    private OnClickListener onClickListener;

    private float firstX = -1;
    private float firstY = -1;

    public ClickViewPager(Context context) {
        super(context);
    }

    public ClickViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstX = ev.getRawX();
                firstY = ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float curX = ev.getRawX();
                float curY = ev.getRawY();
                handle(firstX, firstY, curX, curY);
                firstX = firstY = -1;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void handle(float firstX, float firstY, float curX, float curY) {
        float dis = getDistance(firstX, firstY, curX, curY);
        if (dis == 0 && onClickListener != null) {
            onClickListener.onClick();
        }
    }

    float getDistance(float firstX, float firstY, float curX, float curY) {
        float x = curX - firstX;
        float y = curY - firstY;
        return (float) Math.sqrt(x * x + y * y);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
