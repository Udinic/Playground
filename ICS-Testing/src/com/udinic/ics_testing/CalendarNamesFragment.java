package com.udinic.ics_testing;

import android.app.*;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.*;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 20/02/13
 * Time: 00:19
 * To change this template use File | Settings | File Templates.
 */
public class CalendarNamesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_UDINI = 1;
    SimpleCursorAdapter adapter;
    boolean isDualView = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        String from[] = new String[]{CalendarContract.Calendars.NAME};
        int to[] = {R.id.title};

        getLoaderManager().initLoader(LOADER_UDINI, null, this);

        adapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item_calendars, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);

        isDualView = getActivity().findViewById(R.id.fragment_container) != null;

        if (isDualView) {
            getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        calendarSelected(position, (int)id);
    }

    int mCurrSelected = -1;

    private void calendarSelected(int position, int id) {
        mCurrSelected = position;

        if (isDualView) {
            getListView().setItemChecked(position,true);
            CalendarEventsFragment eventsFragment = (CalendarEventsFragment) getFragmentManager().findFragmentById(R.id.fragment_container);

            if (eventsFragment == null || eventsFragment.getShownCalendarId() != id) {
                eventsFragment = CalendarEventsFragment.newInstance(id);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, eventsFragment);

                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }
        } else {
            Intent intent = new Intent(getActivity(), CalendarEventsActivity.class);
            intent.putExtra("calendarId", id);
            startActivity(intent);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case (LOADER_UDINI):
                Uri uri = CalendarContract.Calendars.CONTENT_URI;
                String[] projection = new String[] {
                        CalendarContract.Calendars._ID,
                        CalendarContract.Calendars.ACCOUNT_NAME,
                        CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                        CalendarContract.Calendars.NAME,
                        CalendarContract.Calendars.CALENDAR_COLOR
                };

                return new CursorLoader(getActivity(), uri, projection, null, null, null);
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
