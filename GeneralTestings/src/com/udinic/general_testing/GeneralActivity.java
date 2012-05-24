package com.udinic.general_testing;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import static com.udinic.general_testing.MyActivity.goingBack;

public abstract class GeneralActivity extends Activity
{
    public boolean noSync = false;
    ProgressBar progress;

    public abstract int getId();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        progress = (ProgressBar)findViewById(R.id.progress);
        ((Button)findViewById(R.id.btn)).setText("Next");

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(GeneralActivity.this, MyActivity2.class));
            }
        });
    }

    @Override
    protected void onStart() {
        Log.d("udini", "[" + getId() + "]onStart");
        super.onStart();    //To change body of overridden methods use File | Settings | File Templates.
    }
//
    @Override
    public void startActivity(Intent intent) {
        noSync = true;
        super.startActivity(intent);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        noSync = true;
        super.startActivityForResult(intent, requestCode);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void onStop() {
        Log.d("udini", "[" + getId() + "]onStop");
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.

    }

    @Override
    public void finish() {
        goingBack = true;
        super.finish();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void onUserLeaveHint() {
        if (noSync) {
            noSync = false;
            goingBack = false;
            Log.d("udini", "[" + getId() + "]onUserLeaveHint");
        } else {
            Log.d("udini", "[L]SYNC -[" + getId() + "]onUserLeaveHint");
        }
        super.onUserLeaveHint();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
