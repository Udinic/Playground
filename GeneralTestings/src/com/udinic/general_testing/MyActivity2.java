package com.udinic.general_testing;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MyActivity2 extends GeneralActivity
{
    ProgressBar progress;

    @Override
    public int getId() {
        return 2;
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        Log.d("udini", "[2]onCreate");
        progress = (ProgressBar)findViewById(R.id.progress);

        ((Button)findViewById(R.id.btn)).setText("back");
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            finish();
            }
        });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
//        Log.d("udini", "[2]onResume");
//
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();    //To change body of overridden methods use File | Settings | File Templates.
//        Log.d("udini", "[2]onStart");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();    //To change body of overridden methods use File | Settings | File Templates.
//        Log.d("udini", "[2]onRestart");
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
//        Log.d("udini", "[2]onPause");
//
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
//        Log.d("udini", "[2]onStop");
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
//        Log.d("udini", "[2]onDestroy");
//
//    }
//
//    @Override
//    protected void onUserLeaveHint() {
//        super.onUserLeaveHint();    //To change body of overridden methods use File | Settings | File Templates.
//        Log.d("udini", "[2]onUserLeaveHint");
//    }

//    @Override
//    public void finish() {
//
//        MyActivity.noSync = true;
//        Log.d("udini", "[2]finish");
//        super.finish();    //To change body of overridden methods use File | Settings | File Templates.
//    }

    //    @Override
//    public void onUserInteraction() {
//        super.onUserInteraction();    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
//        super.onWindowAttributesChanged(params);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
//        ActionMode am = super.onWindowStartingActionMode(callback);    //To change body of overridden methods use File | Settings | File Templates.
//
//
//    }
//
//    @Override
//    public void onActionModeStarted(ActionMode mode) {
//        super.onActionModeStarted(mode);    //To change body of overridden methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public void onActionModeFinished(ActionMode mode) {
//        super.onActionModeFinished(mode);    //To change body of overridden methods use File | Settings | File Templates.
//    }

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
