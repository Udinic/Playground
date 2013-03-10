package com.udinic.ics_testing;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 07/03/13
 * Time: 14:33
 */
public class CalendarInstancesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_INSTANCES = 1;
    private SimpleCursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String from[] = new String[]{CalendarContract.Instances.TITLE, CalendarContract.Instances.BEGIN};
        int to[] = {R.id.title, R.id.date};

        getLoaderManager().initLoader(LOADER_INSTANCES, getArguments(), this);

        adapter = new InstanceAdapter(getActivity(), R.layout.list_item_instance, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_events, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.new_event):
                addEvent();
                break;
            case (R.id.help):
                Toast.makeText(getActivity(), "HELP", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public static CalendarInstancesFragment newInstance(int calenderId) {
        CalendarInstancesFragment fragment = new CalendarInstancesFragment();
        Bundle args = new Bundle();
        args.putInt("calendarId", calenderId);
        fragment.setArguments(args);
        return fragment;
    }

    public int getShownCalendarId() {
        return getArguments().getInt("calendarId", -1);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case (LOADER_INSTANCES):
                Uri.Builder uriBuilder = CalendarContract.Instances.CONTENT_URI.buildUpon();

                Calendar calendar = Calendar.getInstance();
                long startDay = calendar.getTimeInMillis();
                calendar.add(Calendar.DAY_OF_MONTH, 14);
                long endDay = calendar.getTimeInMillis();

                // TODO what to do about a range from last year to this year?
                ContentUris.appendId(uriBuilder, startDay);
                ContentUris.appendId(uriBuilder, endDay);

                String[] projection = new String[] {
                        CalendarContract.Instances._ID,
                        CalendarContract.Instances.TITLE,
                        CalendarContract.Instances.DTSTART,
                        CalendarContract.Instances.BEGIN,
                        CalendarContract.Instances.STATUS,
                };
                int calendarId = bundle == null ? -1 : bundle.getInt("calendarId");

                return new CursorLoader(getActivity(), uriBuilder.build(), projection, CalendarContract.Instances.CALENDAR_ID + " = ?", new String[] {String.valueOf(calendarId)}, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        adapter.swapCursor(null);
    }

    private class InstanceAdapter extends SimpleCursorAdapter {

        public InstanceAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            long time = cursor.getLong(cursor.getColumnIndex(CalendarContract.Instances.BEGIN));
            ((TextView)view.findViewById(R.id.date)).setText(new Date(time).toLocaleString());
        }
    }

    private void addEvent() {

        long calID = getShownCalendarId();
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.add(Calendar.SECOND, 10);
//        beginTime.set(2013, 2, 26, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.add(Calendar.HOUR, 1);
//        endTime.set(2013, 2, 26, 8, 45);

        startMillis = beginTime.getTimeInMillis();
        endMillis = endTime.getTimeInMillis();

        ContentResolver cr = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "Udini Event");
        values.put(CalendarContract.Events.DESCRIPTION, "Desc for the vent");
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");

        // These 2 lines allowing the stock calendar app to show a link to our app inside the event's info
        // Our "edit event" activity needs to handle the action as described:
        // http://developer.android.com/reference/android/provider/CalendarContract.html#ACTION_HANDLE_CUSTOM_EVENT
        values.put(CalendarContract.Events.CUSTOM_APP_PACKAGE, getActivity().getPackageName());
        values.put(CalendarContract.Events.CUSTOM_APP_URI, Uri.decode("udini://udini")); // Not sure how to work with it

        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        long eventID = Long.parseLong(uri.getLastPathSegment());

        Log.d("Udini", "New event created = " + eventID);
    }
}
