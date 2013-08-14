package com.udinic.ChatHeadsExample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startService(new Intent(getBaseContext(), ChatHeadService.class));
            }
        });

        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent stop = new Intent(getBaseContext(), ChatHeadService.class);
                stop.putExtra("STOP", true);
                startService(stop);
            }
        });


    }
}
