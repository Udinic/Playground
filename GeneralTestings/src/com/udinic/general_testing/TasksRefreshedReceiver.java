package com.udinic.general_testing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import org.apache.http.conn.routing.BasicRouteDirector;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 25/06/13
 * Time: 16:47
 */
public class TasksRefreshedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("udini", "tasks refreshed!");
    }
}
