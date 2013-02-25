package com.udinic.ics_testing;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 20/02/13
 * Time: 00:19
 * To change this template use File | Settings | File Templates.
 */
public class CalendarEventAttendeesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_UDINI = 1;
    SimpleCursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        String from[] = new String[]{CalendarContract.Attendees.ATTENDEE_STATUS, CalendarContract.Attendees.ATTENDEE_EMAIL};
        int to[] = {R.id.status, R.id.email};

        getLoaderManager().initLoader(LOADER_UDINI, getArguments(), this);

        adapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item_event_attendee, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);

        setHasOptionsMenu(true);
    }

    public static CalendarEventAttendeesFragment newInstance(int eventId) {
        CalendarEventAttendeesFragment fragment = new CalendarEventAttendeesFragment();
        Bundle args = new Bundle();
        args.putInt("eventId", eventId);
        fragment.setArguments(args);
        return fragment;
    }

    public int getShownEvent() {
        return getArguments().getInt("eventId", -1);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_attendees, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.new_event):
                addAttendee();
                break;
            case (R.id.coming):
                updateRsvp(CalendarContract.Attendees.ATTENDEE_STATUS_ACCEPTED);
                break;
            case (R.id.maybe):
                updateRsvp(CalendarContract.Attendees.ATTENDEE_STATUS_TENTATIVE);
                break;
            case (R.id.not_coming):
                updateRsvp(CalendarContract.Attendees.ATTENDEE_STATUS_DECLINED);
                break;
        }
        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case (LOADER_UDINI):
                Uri uri = CalendarContract.Attendees.CONTENT_URI;
                String[] projection = new String[] {
                        CalendarContract.Attendees._ID,
                        CalendarContract.Attendees.EVENT_ID,
                        CalendarContract.Attendees.ATTENDEE_EMAIL,
                        CalendarContract.Attendees.ATTENDEE_STATUS
                };
                int eventId = bundle == null ? -1 : bundle.getInt("eventId");

                return new CursorLoader(getActivity(), uri, projection, CalendarContract.Attendees.EVENT_ID + " = ?", new String[] {String.valueOf(eventId)}, null);
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

    private void addAttendee() {
        long eventID = getShownEvent();

        ContentResolver cr = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
//        values.put(CalendarContract.Attendees.ATTENDEE_NAME, "");
        values.put(CalendarContract.Attendees.ATTENDEE_EMAIL, "udinic.testing@gmail.com");
        values.put(CalendarContract.Attendees.ATTENDEE_RELATIONSHIP, CalendarContract.Attendees.RELATIONSHIP_ATTENDEE);
        values.put(CalendarContract.Attendees.ATTENDEE_TYPE, CalendarContract.Attendees.TYPE_REQUIRED);
        values.put(CalendarContract.Attendees.ATTENDEE_STATUS, CalendarContract.Attendees.ATTENDEE_STATUS_INVITED);
        values.put(CalendarContract.Attendees.EVENT_ID, eventID);
        Uri uri = cr.insert(CalendarContract.Attendees.CONTENT_URI, values);
    }

    private void updateRsvp(int status) {
        ContentResolver cr = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Attendees.ATTENDEE_STATUS, status);

        int rowsUpdated = cr.update(CalendarContract.Attendees.CONTENT_URI, values,
                CalendarContract.Attendees.EVENT_ID + "=? AND " + CalendarContract.Attendees.ATTENDEE_EMAIL + "=?",
                new String[]{String.valueOf(getShownEvent()), "udinic.2testing@gmail.com"});

        if (rowsUpdated != 0)
            Toast.makeText(getActivity(), "We're good..", Toast.LENGTH_SHORT).show();
    }

}
