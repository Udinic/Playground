package com.udinic.general_testing;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.*;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 07/01/13
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class AnimationPlayground extends Activity {


    private ArrayList<View> views = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ShapeDrawable ball = (ShapeDrawable)getResources().getDrawable(R.drawable.ball);

        setContentView(R.layout.act_animation_playground);

        FlowLayout flowLayout = (FlowLayout)findViewById(R.id.flowLayout);
        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        flowLayout.setLayoutParams(layoutParams);

        layoutParams.breakLine = false;
//        layoutParams.weight = 1f;

        addBall(flowLayout);
        addBall(flowLayout);
        addBall(flowLayout);
        addBall(flowLayout);
        addBall(flowLayout);
        addBall(flowLayout);
        addBall(flowLayout);
        addBall(flowLayout);
        addBall(flowLayout);
        addText(flowLayout);

        Button btnStart = new Button(this);
        btnStart.setText("start");
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;

        frameLayout.addView(btnStart, params);
        frameLayout.addView(flowLayout);
//        setContentView(frameLayout);
    }

    private void addBall(ViewGroup parent) {
        ImageView view = new ImageView(this);
        view.setImageResource(R.drawable.ball);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50,50);
        params.gravity = Gravity.CENTER;
        params.rightMargin = 20;
        parent.addView(view, params);
        views.add(view);
    }

    private void addText(ViewGroup parent) {
        TextView view = new TextView(this);
        view.setText("udini");
        view.setTextSize(30);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.rightMargin = 20;
        parent.addView(view, params);
        views.add(view);
    }

    private void startAnimation() {



        AnimatorSet set = new AnimatorSet();
        set.playTogether(
            ObjectAnimator.ofFloat(views.get(0), "translationX", 0f, 30f),
            ObjectAnimator.ofFloat(views.get(1), "translationX", 0f, 30f)
        );
        set.setDuration(400);
        set.start();
    }

    private void startAnimation2() {

        AnimatorSet enlarge = new AnimatorSet();
        enlarge.playTogether(
            ObjectAnimator.ofFloat(views.get(0), "scaleX", 1, 1.3f),
            ObjectAnimator.ofFloat(views.get(0), "scaleY", 1, 1.3f)
        );
        enlarge.setInterpolator(new BounceInterpolator());

        AnimatorSet delarge = new AnimatorSet();
        delarge.playTogether(
            ObjectAnimator.ofFloat(views.get(0), "scaleX", 1.3f,1),
            ObjectAnimator.ofFloat(views.get(0), "scaleY", 1.3f,1)
        );
        delarge.setInterpolator(new BounceInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(
                enlarge, delarge
        );
        set.setDuration(400);
        set.start();
    }

    private void startAnimation3() {

        Animator ani1 = ObjectAnimator.ofFloat(views.get(1), "scaleX", 1f, 2f);
        ani1.setDuration(300);
//        ani1.setStartDelay(300);
        Animator ani2 = ObjectAnimator.ofFloat(views.get(1), "scaleY", 1f, 0f);
        ani2.setDuration(300);

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(ani2, ani1
        );
        set.start();
    }
}
