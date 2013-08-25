package com.udinic.general_testing;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import static java.lang.Math.min;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 28/02/13
 * Time: 17:47
 */
public class CalenderTypeIndicator extends View {
    Paint pnt = new Paint();
    int diameter = -1;

    public CalenderTypeIndicator(Context context) {
        super(context);
        init();
    }

    public CalenderTypeIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalenderTypeIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Minimum size
        int defaultWidth = 10;
        int defaultHeight = 10;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(defaultWidth, widthSize);
        } else {
            //Be whatever you want
            width = defaultWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(defaultHeight, heightSize);
        } else {
            //Be whatever you want
            height = defaultHeight;
        }

        int size = min(width, height);

        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        diameter = min(w, h);
    }

    public void init() {
        pnt.setStyle(Paint.Style.FILL_AND_STROKE);
        setColor(Color.RED);
    }

    public void setColor(int color) {
        pnt.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, diameter / 2, pnt);
    }
}
