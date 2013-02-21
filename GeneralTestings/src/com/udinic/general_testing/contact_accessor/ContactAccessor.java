package com.udinic.general_testing.contact_accessor;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;

import java.util.*;

public class ContactAccessor {

    private static ContactAccessor sInstance = null;
    private SortedMap<String, ContactData> mContacts;
    private int totalContactsCount = 0;

    public ContactAccessor() {
        mContacts = new TreeMap<String, ContactData>();
    }

    public static ContactAccessor getInstance() {
        if (sInstance == null) {
            sInstance = new ContactAccessor();
        }
        return sInstance;
    }

    public void initContacts(Context ctx) {
        ContentResolver cr = ctx.getContentResolver();

        String[] contact_fields = {ContactsContract.Contacts.DISPLAY_NAME,
                                    ContactsContract.Contacts._ID,
                                    ContactsContract.Contacts.HAS_PHONE_NUMBER,
                                    ContactsContract.Contacts.PHOTO_ID};
        Uri contact_uri = ContactsContract.Contacts.CONTENT_URI;

        Cursor contact_cursor = null;

        try {
            contact_cursor = cr.query(contact_uri, contact_fields, ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1", null, null);
        } catch (Exception ignored) {}

        // Temporary solution, until problem found
        if (contact_cursor == null) {
//            ErrorReporter.getInstance().handleException(new NullPointerException("The contacts_cursor is null! WHY oh WHYYYYYY????"));
            return;
        }

        if (contact_cursor.moveToFirst()) // check the cursor is not empty
        {
            //iterate over results
            do {

                String name = contact_cursor.getString(contact_cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (name != null) {
                    String id = contact_cursor.getString(contact_cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String nameKey = name.toLowerCase();

                    // if the name already exists
                    if (mContacts.containsKey(nameKey)) {
                        ContactData current_contact_data = mContacts.get(nameKey);
                        current_contact_data.addContactId(id);      // attach this id
                    } else {
                        int photoId = contact_cursor.getInt(contact_cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
                        ContactData contactData = new ContactData(ctx, name, Lists.newArrayList(id),photoId);       // create a new contact data
                        mContacts.put(nameKey, contactData);
                    }
                }

            } while (contact_cursor.moveToNext());
        }

        contact_cursor.close();

        // update the relevance
        Uri call_log_uri = CallLog.Calls.CONTENT_URI;
        String[] call_log_fields = {CallLog.Calls.CACHED_NAME};
        String order = CallLog.Calls.DATE + " DESC";
        Cursor call_log_cursor = null;
        try {
            call_log_cursor = cr.query(call_log_uri, call_log_fields, null, null, order);
        } catch (Exception ignored) {}

        // If we got a call log
        if (call_log_cursor != null) {
            if (call_log_cursor.moveToFirst()) // check the cursor is not empty
            {
                //iterate over results
                do {
                    String name = call_log_cursor.getString(call_log_cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    if (null != name) {
                        String nameKey = name.toLowerCase();
                        ContactData cd = mContacts.get(nameKey);
                        if (null != cd) {
                            totalContactsCount++;
                            cd.incRelevanceScore();
                        }
                    }

                } while (call_log_cursor.moveToNext());
            }
            call_log_cursor.close();
        }

    }

    public ContactData loadContact(Context ctx, String contactName) {
        ContentResolver cr = ctx.getContentResolver();

        String[] contact_fields = {ContactsContract.Contacts.DISPLAY_NAME,
                                    ContactsContract.Contacts._ID,
                                    ContactsContract.Contacts.HAS_PHONE_NUMBER,
                                    ContactsContract.Contacts.PHOTO_ID};
        Uri contact_uri = ContactsContract.Contacts.CONTENT_URI;

        Cursor contact_cursor = cr.query(contact_uri, contact_fields,
                                        ContactsContract.Contacts.DISPLAY_NAME + " = ?", new String[]{contactName}, null);

        if (contact_cursor == null)
            return null;

        if (contact_cursor.moveToFirst()) // check the cursor is not empty
        {
            //iterate over results
            do {

                String name = contact_cursor.getString(contact_cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (name != null) {
                    String id = contact_cursor.getString(contact_cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    int photoId = contact_cursor.getInt(contact_cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));

                    contact_cursor.close();
                    ContactData contact = new ContactData(ctx, name, Lists.newArrayList(id),photoId);       // create a new contact data
                    return contact;
                }

            } while (contact_cursor.moveToNext());
        }

        contact_cursor.close();
        return null;
    }

    public ContactData getContactById(Context ctx, String contactId) {
        ContentResolver cr = ctx.getContentResolver();

        String[] contact_fields = {ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
                ContactsContract.Contacts.PHOTO_ID};
        Uri contact_uri = ContactsContract.Contacts.CONTENT_URI;

        Cursor contact_cursor = cr.query(contact_uri, contact_fields,
                ContactsContract.Contacts._ID + " = ?", new String[]{contactId}, null);

        if (contact_cursor == null)
            return null;
        
        if (contact_cursor.moveToFirst()) // check the cursor is not empty
        {
            //iterate over results
            do {

                String name = contact_cursor.getString(contact_cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (name != null) {
                    String id = contact_cursor.getString(contact_cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    int photoId = contact_cursor.getInt(contact_cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));

                    contact_cursor.close();
                    ContactData contact = new ContactData(ctx, name, Lists.newArrayList(id),photoId);       // create a new contact data
                    return contact;
                }

            } while (contact_cursor.moveToNext());
        }

        contact_cursor.close();
        return null;
    }

    public ContactData getContactByNumber(Context ctx, String phoneNumber) {

        ContentResolver localContentResolver = ctx.getContentResolver();
        Cursor contactLookupCursor =
                localContentResolver.query(
                        Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber)),
                        new String[] {/*ContactsContract.PhoneLookup.DISPLAY_NAME, */ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.HAS_PHONE_NUMBER},
                        null,
                        null,
                        null);

        String contId = null;
        
        if (contactLookupCursor == null)
            return null;
        
        try {
            while(contactLookupCursor.moveToNext()){
//                String contactName = contactLookupCursor.getString(contactLookupCursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                String contactId = contactLookupCursor.getString(contactLookupCursor.getColumnIndex(ContactsContract.PhoneLookup._ID));
                int hasPhone = contactLookupCursor.getInt(contactLookupCursor.getColumnIndex(ContactsContract.PhoneLookup.HAS_PHONE_NUMBER));

                if (hasPhone!=0) {
                    contId = contactId;
                    break;
                }
            }
        } finally {
            contactLookupCursor.close();
        }

        if (contId!= null)
            return getContactById(ctx, contId);
        else
            return null;
    }

    /**
     * Gets the contact's phone numbers. Return the default one as the first
     * @param ctx
     * @param contact_id
     * @return
     */
    public List<String> getContactNumbers(Context ctx, String contact_id) {
        ContentResolver cr = ctx.getContentResolver();
        List<String> numbers = new ArrayList<String>();

        String[] contact_fields = {ContactsContract.Contacts._ID, ContactsContract.Contacts.HAS_PHONE_NUMBER};
        Uri contact_uri = ContactsContract.Contacts.CONTENT_URI;
        String where = ContactsContract.Contacts._ID + "= ?";
        String params[] = {contact_id};
        Cursor contact_cursor = cr.query(contact_uri, contact_fields, where, params, null);

        if (contact_cursor == null)
            return new ArrayList<String>();
        
        if (contact_cursor.moveToFirst()) // check the cursor is not empty
        {
            // phone numbers
            if (Integer.parseInt(contact_cursor.getString(contact_cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.IS_SUPER_PRIMARY},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{contact_id}, ContactsContract.CommonDataKinds.Phone.IS_SUPER_PRIMARY + " DESC");

                if (pCur.moveToFirst()) {
                    do {
                        numbers.add(pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    } while (pCur.moveToNext());
                }
                pCur.close();
            }
        }
        return numbers;
    }

    public List<String> getContactEmails(Context ctx, String contact_id) {
        ContentResolver cr = ctx.getContentResolver();

        List<String> emails = new ArrayList<String>();
        Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{contact_id}, null);
        
        if (emailCur == null)
            return new ArrayList<String>();

        if (emailCur.moveToFirst()) {
            do {
                emails.add(emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
            } while (emailCur.moveToNext());
        }
        emailCur.close();

        return emails;
    }

    public Collection<ContactData> getContactsByPrefix(String prefix, boolean isProper) {
        if (prefix.length() > 0) {
            char nextLetter = (char) (prefix.charAt(prefix.length() - 1) + 1);
            String end = prefix.substring(0, prefix.length() - 1) + nextLetter;
            Collection<ContactData> values = mContacts.subMap(prefix, end).values();

            // if proper is true - remove the exact match
            if (isProper) {
                ArrayList<ContactData> retVal = new ArrayList<ContactData>(values);
                retVal.remove(getContactByName(prefix));
                return retVal;
            }

            return values;
        }
        return null;
    }

    public Collection<ContactData> getAllContacts() {
        return mContacts.values();
    }

    public ContactData getContactByName(String contact_name) {
        return mContacts.get(contact_name);
    }

    public Collection<ContactData> getCallFavoriteContacts() {
        Collection<ContactData> favorites = new TreeSet<ContactData>(relevantScore());

        for (ContactData contactData : mContacts.values()) {
            if (contactData.getRelevanceScore() > 0) {
                favorites.add(contactData);
            }
        }
        return favorites;
    }

    private Comparator<ContactData> relevantScore() {
        return new Comparator<ContactData>() {
            @Override
            public int compare(ContactData contactData, ContactData contactData1) {
                if (contactData.getRelevanceScore() == contactData1.getRelevanceScore()) {
                    return contactData.hashCode() == contactData1.hashCode()?0:1;
                }
                if (contactData.getRelevanceScore() > contactData1.getRelevanceScore()) {
                    return 1;
                }
                return -1;
            }
        };
    }

    public int getTotalContactsCount() {
        return totalContactsCount;
    }

}
