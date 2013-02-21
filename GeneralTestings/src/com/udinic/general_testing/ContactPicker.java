package com.udinic.general_testing;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
    import android.provider.ContactsContract.Contacts;
    import android.provider.ContactsContract.CommonDataKinds.Email;
import android.util.Log;
import android.view.View;

import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 11/11/12
 * Time: 12:59
 * To change this template use File | Settings | File Templates.
 */
public class ContactPicker extends Activity {

    private final static String DEBUG_TAG = "udini";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.act_contact_picker);

    }

    private static final int CONTACT_PICKER_RESULT = 1001;
    public void doLaunchContactPicker(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:

                    Bundle extras = data.getExtras();
                    Set<String> keys = extras.keySet();
                    Iterator<String> iterate = keys.iterator();
                    while (iterate.hasNext()) {
                        String key = iterate.next();
                        Log.v(DEBUG_TAG, key + "[" + extras.get(key) + "]");
                    }
                    Uri result = data.getData();
                    Log.v(DEBUG_TAG, "Got a result: "
                            + result.toString());


                    // handle contact results
                    break;
            }
        } else {
            // gracefully handle failure
            Log.w(DEBUG_TAG, "Warning: activity result not ok");
        }
    }

}
