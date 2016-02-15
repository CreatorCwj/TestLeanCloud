package com.widget.viewPagers.imageViewPager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.testleancloud.R;

/**
 * Created by cwj on 16/2/9.
 * 自定义圆点view
 * 1.可指定选中颜色和未选中颜色
 * 2.可以指定padding
 * 3.可以指定边框
 */
public class PointView extends View {

    private int selectColor;
    private int unSelectColor;

    private int strokeWidth;
    private int strokeColor;

    private Paint paint;
    private Paint strokePaint;

    public PointView(Context context) {
        this(context, null);
    }

    public PointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImageViewPager);
            selectColor = typedArray.getColor(R.styleable.ImageViewPager_selectColor, getResources().getColor(R.color.colorPrimary));
            unSelectColor = typedArray.getColor(R.styleable.ImageViewPager_unSelectColor, Color.GRAY);
            strokeWidth = typedArray.getDimensionPixelSize(R.styleable.ImageViewPager_pointStrokeWidth, 0);
            strokeColor = typedArray.getColor(R.styleable.ImageViewPager_pointStrokeColor, Color.TRANSPARENT);
            typedArray.recycle();
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        //设置画笔
        if (paint == null)
            paint = new Paint();
        paint.setAntiAlias(true);
        if (isSelected()) {
            paint.setColor(selectColor);
        } else {
            paint.setColor(unSelectColor);
        }

        //绘制圆形
        int centerX = getMeasuredWidth() / 2;
        int centerY = getMeasuredHeight() / 2;
        int radius = Math.min(Math.min(centerX - getPaddingLeft(), centerX - getPaddingRight()), Math.min(centerY - getPaddingTop(), centerY - getPaddingBottom()));
        canvas.drawCircle(centerX, centerY, radius, paint);

        //绘制边框
        if (strokePaint == null)
            strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setAntiAlias(true);
        strokePaint.setColor(strokeColor);
        strokePaint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(centerX, centerY, radius - strokeWidth / 2, strokePaint);
    }

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
        invalidate();
    }

    public void setUnSelectColor(int unSelectColor) {
        this.unSelectColor = unSelectColor;
        invalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        invalidate();
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        invalidate();
    }
}
