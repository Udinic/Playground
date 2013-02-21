package com.udinic.general_testing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 03/01/13
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class UdiniView extends View {

    Paint mPaint = null;

    public UdiniView(Context context) {
        super(context);
        init();
    }

    public UdiniView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UdiniView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);    //To change body of overridden methods use File | Settings | File Templates.

        canvas.drawColor(Color.parseColor("#00FF00"));
        mPaint.setColor(Color.BLUE);

        float xPos = 120;
        int finalXPos = 320;
        float scaleX = 1.0f;
        float finalScaleX = 3.0f;

        float textSize = 30;
        float finalTextSize = 50;


        if (mAnimStartTime > 0) {
            long timePos = SystemClock.uptimeMillis() - mAnimStartTime;
            float animPercentage = (timePos / (float)duration);

            xPos += animPercentage * (finalXPos - xPos);
            scaleX += animPercentage * (finalScaleX - scaleX);
            textSize += animPercentage * (finalTextSize - textSize);
            canvas.scale(scaleX, scaleX);
        }

        Log.d("udini", "drawCircle at x[" + xPos + "] mAnimStartTime ["+mAnimStartTime+"]");

        canvas.drawCircle(xPos,120,50,mPaint);

        mPaint.setColor(Color.WHITE);

        mPaint.setTextSize(50);
//        mPaint.setTextScaleX(scaleX);
//        mPaint.setTextSkewX(-0.25f);
        canvas.drawText("udini", 50, 300, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);    //To change body of overridden methods use File | Settings | File Templates.
    }

    long mAnimStartTime = 0;
    int duration;

    Handler mHandler = new Handler();
    Runnable mTick = new Runnable() {
        public void run() {
            invalidate();

            if (SystemClock.uptimeMillis() - mAnimStartTime < duration - 20)
                mHandler.postDelayed(this, 20); // 20ms == 60fps
        }
    };

    public void startAnimation(int duration) {
        mAnimStartTime = SystemClock.uptimeMillis();
        this.duration = duration;
        mHandler.removeCallbacks(mTick);
        mHandler.post(mTick);
    }

    public void stopAnimation() {
        mAnimStartTime = 0;
        mHandler.removeCallbacks(mTick);
    }


}
