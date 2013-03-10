package com.udinic.ics_testing;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 20/02/13
 * Time: 02:39
 * To change this template use File | Settings | File Templates.
 */
public class CalendarEventsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.

//        setContentView(R.layout.act_cal_events);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            CalendarInstancesFragment details = new CalendarInstancesFragment();
            details.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }
}

