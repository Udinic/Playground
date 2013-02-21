package com.udinic.general_testing;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.udinic.general_testing.contact_accessor.ContactAccessor;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 05/12/12
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public class ContactsViewer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        ListView list = new ListView(this);
        setContentView(list);

        ContactAccessor contactAccessor = ContactAccessor.getInstance();
        contactAccessor.initContacts(this);

        ContactsAdaptre ds = new ContactsAdaptre(this);
        ds.addAll(contactAccessor.getAllContacts());
        list.setAdapter(ds);

    }

    class ContactsAdaptre extends ArrayAdapter<com.udinic.general_testing.contact_accessor.ContactData> {

        public ContactsAdaptre(Context context) {
            super(context, R.layout.simple_list_item_1, 0);
        }
    }

}
