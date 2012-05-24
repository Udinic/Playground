package com.udinic.general_testing;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MyActivity extends GeneralActivity
{
    public static boolean goingBack = false;
    ProgressBar progress;

    @Override
    public int getId() {
        return 1;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d("udini", "[E]SYNC - [1]onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        noSync = true;

        progress = (ProgressBar)findViewById(R.id.progress);

        ((Button)findViewById(R.id.btn)).setText("Next");

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MyActivity.this, com.udinic.general_testing.MyActivity2.class));
            }
        });
    }


    @Override
    protected void onDestroy() {

        Log.d("udini", "[L]SYNC - [1]onDestroy");
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void onStart() {

        if (noSync || goingBack) {
            noSync = false;
            goingBack = false;

            Log.d("udini", "[1]onStart");
        } else {
            Log.d("udini", "[E]SYNC - [1]onStart");
        }
        super.onStart();    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void runProgress() {
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);    //To change body of overridden methods use File | Settings | File Templates.
                Log.d("udini", "progress [" + values[0] + "]");
                progress.setProgress(values[0]);
            }

            @Override
            protected Void doInBackground(Void... params) {

                publishProgress(10);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                publishProgress(30);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                publishProgress(50);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                publishProgress(70);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                publishProgress(90);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                publishProgress(100);

                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }.execute();

    }

}
