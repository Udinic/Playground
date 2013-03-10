package com.udinic.ics_testing;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 25/02/13
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public class ReminderService extends IntentService {
    private static final String TAG = CalendarActivity.MAIN_TAG + " > ReminderService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public ReminderService() {
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        Log.d(TAG, "Got intent");

        Uri uri = intent.getData();
//        Log.d(TAG, "Querying form Uri ["+uri+"]");
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        while (cd:ursor.moveToNext()) {
//            for (String name : cursor.getColumnNames()) {
//                int idx = cursor.getColumnIndex(name);
//                int type = cursor.getType(idx);
//                switch (type){
//                    case (Cursor.FIELD_TYPE_INTEGER):
//                        Log.d("udini", "cur[" + name + "]=" + cursor.getInt(idx));
//                        break;
//                    default:
//                    case (Cursor.FIELD_TYPE_STRING):
//                        Log.d("udini", "cur[" + name + "]=" + cursor.getString(idx));
//                        break;
//                }
//            }
//        }
    }
}
