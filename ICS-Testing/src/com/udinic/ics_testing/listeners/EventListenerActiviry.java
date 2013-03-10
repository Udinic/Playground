package com.udinic.ics_testing.listeners;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Opens from the stock calendar add in case we use this while inserting new event:
 *
 *         values.put(CalendarContract.Events.CUSTOM_APP_PACKAGE, getActivity().getPackageName());
 *         values.put(CalendarContract.Events.CUSTOM_APP_URI, Uri.decode("udini://udini")); // Not sure how to work with it
 .
 * User: Udini
 * Date: 10/03/13
 * Time: 17:02
 */
public class EventListenerActiviry extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("udini", "Started EventListenerActiviry with action " + getIntent().getAction());

        finish();
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }
}
