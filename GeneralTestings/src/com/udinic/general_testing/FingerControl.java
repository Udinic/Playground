package com.udinic.general_testing;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 25/04/13
 * Time: 16:05
 */
public class FingerControl extends Activity implements View.OnTouchListener {

    TextView _view;
    ViewGroup _root;

    FrameLayout mLayout;
    private float _xDelta;
    private float _yDelta;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _root = new FrameLayout(this);
        _root.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setContentView(_root);


        mLayout = new FrameLayout(this);
        mLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 102));
        mLayout.setBackgroundColor(Color.GREEN);

        mLayout.setOnTouchListener(this);

        _root.addView(mLayout);

//        _view = new TextView(this);
//        _view.setText("udini is the man");
//
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
//        _view.setLayoutParams(layoutParams);
//        _view.setOnTouchListener(this);
//        _root.addView(_view);
    }

    float yLoc = 0;
    int height = 0;

    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                yLoc = Y;
                height = view.getLayoutParams().height;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:

                Log.d("udini", "["+view.getLayoutParams().height+"] -> ["+(view.getLayoutParams().height + (Y - yLoc))+" ("+Y+"/"+yLoc+")]");
                view.getLayoutParams().height = height + (int)(Y - yLoc);
                view.requestLayout();
                break;
        }
//        _root.invalidate();
        return true;
    }


    public boolean onTouch2(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                _xDelta = X - view.getX();
                _yDelta = Y - view.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:

                view.setX(X - _xDelta);
                view.setY(Y - _yDelta);
                view.invalidate();
                break;
        }
        _root.invalidate();
        return true;
    }}
