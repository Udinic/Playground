package com.udinic.general_testing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 03/01/13
 * Time: 15:48
 * To change this template use File | Settings | File Templates.
 */
public class CanvasPlayground extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        setContentView(R.layout.act_canvas_shit);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((UdiniView) findViewById(R.id.udiniView)).startAnimation(3000);
            }
        });

    }



}
