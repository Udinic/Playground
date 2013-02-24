package com.udinic.ics_testing;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.util.Log;

import static android.content.ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE;
import static android.content.ContentResolver.SYNC_OBSERVER_TYPE_PENDING;
import static android.content.ContentResolver.SYNC_OBSERVER_TYPE_SETTINGS;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 21/02/13
 * Time: 20:13
 * To change this template use File | Settings | File Templates.
 */
public class CalendarService extends Service {
    ContentObserver mEventsObserver = new ContentObserver(new Handler()) {
        @Override
        public boolean deliverSelfNotifications() {
            Log.d("CalendarService", "deliverSelfNotifications");
            return super.deliverSelfNotifications();    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onChange(boolean selfChange) {
            Log.d("CalendarService", "onChange(" + selfChange + ")");
            super.onChange(selfChange);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.d("CalendarService", "onChange(" + selfChange + "," + uri + ")");
            super.onChange(selfChange, uri);    //To change body of overridden methods use File | Settings | File Templates.
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("CalendarService", "onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onCreate() {

        Log.d("CalendarService", "onCreate");
        getContentResolver().registerContentObserver(CalendarContract.CONTENT_URI, true, mEventsObserver);

        ContentResolver.addStatusChangeListener(SYNC_OBSERVER_TYPE_ACTIVE | SYNC_OBSERVER_TYPE_PENDING | SYNC_OBSERVER_TYPE_SETTINGS, new SyncStatusObserver() {
            @Override
            public void onStatusChanged(int which) {
                String whichStr;
                switch (which) {
                    case (SYNC_OBSERVER_TYPE_ACTIVE):
                        whichStr = "SYNC_OBSERVER_TYPE_ACTIVE";
                        break;
                    case (SYNC_OBSERVER_TYPE_PENDING):
                        whichStr = "SYNC_OBSERVER_TYPE_PENDING";
                        break;
                    case (SYNC_OBSERVER_TYPE_SETTINGS):
                        whichStr = "SYNC_OBSERVER_TYPE_SETTINGS";
                        break;
                    default:
                        whichStr = "DUNNO";
                        break;
                }
//                Log.d("CalendarService", "onSyncStatusChange[" + which + "|" + whichStr + "]");
            }
        });
    }

    @Override
    public void onDestroy() {
        getContentResolver().unregisterContentObserver(mEventsObserver);
    }
}
