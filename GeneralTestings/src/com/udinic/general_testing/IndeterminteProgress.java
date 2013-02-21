package com.udinic.general_testing;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 11/07/12
 * Time: 12:27
 * To change this template use File | Settings | File Templates.
 */
public class IndeterminteProgress extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.act_progress_indeterminte);

        findViewById(R.id.udini).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                udini();
            }
        });
        long now =  1342365476l;
        long time = 1336377950l;
        Log.d("udini", DateUtils.getRelativeTimeSpanString(time, now, DateUtils.SECOND_IN_MILLIS).toString());

        udini();
    }

    private void udini() {
        new AsyncTask<Void, Integer, Void>() {
            ProgressBar bar = (ProgressBar) findViewById(R.id.progressLoading);

            @Override
            protected void onPreExecute() {
                bar.setProgress(0);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                bar.setProgress(values[0]);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                for (int i = 0; i <= 100; i += 20) {
                    this.publishProgress(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                return null;
            }
        }.execute();

    }

}
