package com.udinic.ics_testing;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;

public class MyActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.getApplicationContext().getContentResolver().registerContentObserver(CalendarContract.Events.CONTENT_URI, true, contentObserver);
    }

    private class MyContentObserver extends ContentObserver {

        public MyContentObserver() {
            super(null);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d("udini", "udini is the man");
        }
    }

    MyContentObserver contentObserver = new MyContentObserver();

//    public void observe() {
//        this.getContentResolver().registerContentObserver(CalendarContract.Events.CONTENT_URI,true, new ContentObserver(this) {
//            @Override
//            public void onChange(boolean selfChange) {
//                Log.d("udini", "udini is the man!");
//            }
//        });
//    }

    public void insertEvent() {
        ContentResolver cr = this.getContentResolver();
//        cr.insert()
        
    }

    public void getCalendars() {
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String[] projection = new String[] {
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.NAME,
                CalendarContract.Calendars.CALENDAR_COLOR
        };

        CursorLoader curLoader = new CursorLoader(this, uri, projection, null, null, null);
//        curLoader.
    }

    public void getEvents() {
        ContentResolver cr = this.getContentResolver();
        Cursor cur = cr.query(CalendarContract.Events.CONTENT_URI, null, null, null, null);

        while (cur.moveToNext()) {
            String[] names = cur.getColumnNames();

        }
    }
}
