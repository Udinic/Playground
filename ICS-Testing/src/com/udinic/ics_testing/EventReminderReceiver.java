package com.udinic.ics_testing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 25/02/13
 * Time: 03:39
 * To change this template use File | Settings | File Templates.
 */
public class EventReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("udini", "got EVENT!");
    }
}
