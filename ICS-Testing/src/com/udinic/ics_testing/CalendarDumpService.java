package com.udinic.ics_testing;

import android.app.IntentService;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.format.Time;
import android.util.Log;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 26/02/13
 * Time: 18:39
 * To change this template use File | Settings | File Templates.
 */
public class CalendarDumpService extends IntentService {
    private static final String TAG = "CalendarDumpService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public CalendarDumpService() {
        super(TAG);
    }

    private String indent(int n) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < n*2; i++) {
            sb.append("__");
        }
        return sb.toString();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String calFilter = intent.getStringExtra("ID");

        printCalendarDump(calFilter);
    }

    private void printCalendarDump2(String calFilter) {


    }

    private void printCalendarDump(String calFilter) {
        Uri uriCalendars = CalendarContract.Calendars.CONTENT_URI;
        String[] projectionCalendars = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.NAME
        };

        Cursor curTable = getContentResolver().query(uriCalendars, projectionCalendars, calFilter == null?null:CalendarContract.Calendars._ID+"=?", calFilter==null?null:new String[] {calFilter}, null);

        while (curTable.moveToNext()) {
            int calId = curTable.getInt(curTable.getColumnIndex(CalendarContract.Calendars._ID));
            for (String col : projectionCalendars) {
                Log.d(TAG, indent(1) + col + "[" + curTable.getString(curTable.getColumnIndex(col)) + "]");
            }

            // For each Calendar - Print EVENTS
            String[] projectionEvents = new String[]{
                    CalendarContract.Events._ID,
                    CalendarContract.Events.TITLE,
                    CalendarContract.Events.EVENT_LOCATION,
                    CalendarContract.Events.DTSTART,
            };
            Cursor curEvents = getContentResolver().query(CalendarContract.Events.CONTENT_URI, projectionEvents, CalendarContract.Events.CALENDAR_ID + "=?", new String[]{String.valueOf(calId)}, null);
            while (curEvents.moveToNext()) {
                int eventId = curEvents.getInt(curEvents.getColumnIndex(CalendarContract.Events._ID));
                for (String col : projectionEvents) {
                    if (col.equals(CalendarContract.Events.DTSTART))
                        Log.d(TAG, indent(2) + col + "[" + new Date(curEvents.getLong(curEvents.getColumnIndex(col))).toLocaleString() + "]");
                    else
                        Log.d(TAG, indent(2) + col + "[" + curEvents.getString(curEvents.getColumnIndex(col)) + "]");
                }

                Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
                ContentUris.appendId(builder, new Date().getTime());
                Time end  = new Time();
                end.setToNow();
                end.set(5,10,2014);
                ContentUris.appendId(builder, end.toMillis(false));

                printEventsSubTable(eventId, "Instances", builder.build(), new String[] {
                        CalendarContract.Instances._ID,
                        CalendarContract.Instances.BEGIN,
                        CalendarContract.Instances.END});

                printEventsSubTable(eventId, "Reminders", CalendarContract.Reminders.CONTENT_URI, new String[]{
                        CalendarContract.Reminders._ID,
                        CalendarContract.Reminders.MINUTES,
                        CalendarContract.Reminders.METHOD});

                printEventsSubTable(eventId, "Attendees", CalendarContract.Attendees.CONTENT_URI, new String[]{
                        CalendarContract.Attendees._ID,
                        CalendarContract.Attendees.ATTENDEE_EMAIL,
                        CalendarContract.Attendees.ATTENDEE_STATUS});
            }
            curEvents.close();
        }

        curTable.close();
    }

    private void printEventsSubTable(int eventId, String title, Uri uri, String[] projection) {
        Cursor curSubTable = getContentResolver().query(uri, projection, CalendarContract.Instances.EVENT_ID +" =?", new String[] {String.valueOf(eventId)}, null);
        while (curSubTable.moveToNext()) {
            StringBuilder sb = new StringBuilder();
            for (String col : projection) {
                sb.append(col + "[" + curSubTable.getString(curSubTable.getColumnIndex(col)) + "]\t");
            }
            Log.d(TAG, indent(3) + title + " = " +sb.toString());
        }
        curSubTable.close();
    }
}
