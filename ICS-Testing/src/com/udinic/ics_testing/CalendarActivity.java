package com.udinic.ics_testing;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 20/02/13
 * Time: 00:50
 * To change this template use File | Settings | File Templates.
 */
public class CalendarActivity extends Activity {
    public static final String MAIN_TAG = "CalendarTesting";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.act_cal_list);

        startService(new Intent(this, CalendarService.class));
        Account accounts[] = AccountManager.get(this).getAccounts();
        for (Account acc : accounts) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
            ContentResolver.requestSync(acc, "com.android.calendar", bundle);
        }

        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null)
            Log.d("udini", "Started CalendarActivity with action " + intent.getAction());

    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

}
