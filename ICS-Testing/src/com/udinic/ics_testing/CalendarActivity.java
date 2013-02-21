package com.udinic.ics_testing;

import android.app.Activity;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 20/02/13
 * Time: 00:50
 * To change this template use File | Settings | File Templates.
 */
public class CalendarActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.act_cal_list);


        Uri uri = CalendarContract.Events.CONTENT_URI;

        getContentResolver().registerContentObserver(uri, true, new ContentObserver(new Handler()) {
            @Override
            public boolean deliverSelfNotifications() {
                Log.d("udini", "deliverSelfNotifications");
                return super.deliverSelfNotifications();    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public void onChange(boolean selfChange) {
                Log.d("udini", "onChange(" + selfChange + ")");
                super.onChange(selfChange);    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                Log.d("udini", "onChange(" + selfChange + ","+uri+")");
                super.onChange(selfChange, uri);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });
    }
}
