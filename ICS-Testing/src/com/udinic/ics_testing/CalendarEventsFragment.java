package com.udinic.ics_testing;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 20/02/13
 * Time: 00:19
 * To change this template use File | Settings | File Templates.
 */
public class CalendarEventsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_UDINI = 1;
    SimpleCursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

        String from[] = new String[]{CalendarContract.Events.TITLE};
        int to[] = {R.id.title};

        getLoaderManager().initLoader(LOADER_UDINI, getArguments(), this);

        adapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item_calendars, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);

        setHasOptionsMenu(true);
    }

    public static CalendarEventsFragment newInstance(int calenderId) {
        CalendarEventsFragment fragment = new CalendarEventsFragment();
        Bundle args = new Bundle();
        args.putInt("calendarId", calenderId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_events, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.new_event):
                Toast.makeText(getActivity(), "NEW", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.help):
                Toast.makeText(getActivity(), "HELP", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void eventSelected(int position, int id) {
        boolean isDualView = getActivity().findViewById(R.id.fragment_container) != null;

        if (isDualView) {
            getListView().setItemChecked(position,true);
//            Fragment eventsFragment = getFragmentManager().findFragmentById(R.id.fragment_container);
            Fragment eventsFragment = CalendarEventAttendeesFragment.newInstance(id);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, eventsFragment);

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            Intent intent = new Intent(getActivity(), CalendarEventsActivity.class);
            intent.putExtra("eventId", id);
            startActivity(intent);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        eventSelected(position, (int)id);
    }

    public int getShownCalendarId() {
        return getArguments().getInt("calendarId", -1);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case (LOADER_UDINI):
                Uri uri = CalendarContract.Events.CONTENT_URI;
                String[] projection = new String[] {
                        CalendarContract.Events._ID,
                        CalendarContract.Events.TITLE
                };
                int calendarId = bundle == null ? -1 : bundle.getInt("calendarId");

                return new CursorLoader(getActivity(), uri, projection, CalendarContract.Events.CALENDAR_ID + " = ?", new String[] {String.valueOf(calendarId)}, null);
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
}
