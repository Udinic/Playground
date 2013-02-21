package com.udinic.general_testing.contact_accessor;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ContactData {

    private String mContactName = null;
    private List<String> mContactIDs = null;
    private List<String> mContactNumbers;
    private List<String> mContactEmails;
    private int mContactRelevanceScore;
    private Boolean mIsAnydoUser = null;
    private String mAnydoPUID = null;
    private Context mCtx;
    private int mPhotoId = -1;

    public ContactData() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    // copy ctr
    public ContactData(ContactData c) {
        this(c.mCtx, c.mContactName, c.mContactIDs, c.mContactNumbers, c.mContactEmails, c.mContactRelevanceScore, c.getPhotoId());
    }

    public ContactData(Context ctx, String name, List<String> ids, List<String> numbers, List<String> emails, int relevance, int photoId) {
        super();
        mCtx = ctx;
        mContactName = name;
        mContactIDs = ids;
        mContactNumbers = numbers;
        mContactEmails = emails;
        mPhotoId = photoId;
        mContactRelevanceScore = relevance;
    }

    public ContactData(Context ctx, String name) {
        this(ctx, name, null, null, null, 0, -1);
    }
    public ContactData(Context ctx, String name, List<String> ids, int photoId) {
        this(ctx, name, ids, null, null, 0, photoId);
    }

    public void setPhotoId(int photoId) {
        mPhotoId = photoId;
    }

    public int getPhotoId() {
        return mPhotoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (mContactEmails != null ? !mContactEmails.equals(that.mContactEmails) : that.mContactEmails != null)
            return false;
        if (mContactName != null ? !mContactName.equals(that.mContactName) : that.mContactName != null) return false;
        if (mContactNumbers != null ? !mContactNumbers.equals(that.mContactNumbers) : that.mContactNumbers != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = mContactName != null ? mContactName.hashCode() : 0;
        result = 31 * result + (mContactNumbers != null ? mContactNumbers.hashCode() : 0);
        result = 31 * result + (mContactEmails != null ? mContactEmails.hashCode() : 0);
        return result;
    }

    public String getName() {
        return mContactName;
    }

    public List<String> getNumbers() {
        // un-initialized, it's time
        if (mContactNumbers == null) {
            mContactNumbers = new ArrayList<String>();
            for (String id : mContactIDs) {
                mContactNumbers.addAll(ContactAccessor.getInstance().getContactNumbers(mCtx, id));
            }
        }
        return mContactNumbers;
    }

    public List<String> getContactIDs() {
        return mContactIDs;
    }

    public List<String> getEmails() {
        // un-initialized, it's time
        if (mContactEmails == null) {
            mContactEmails = new ArrayList<String>();
            for (String id : mContactIDs) {
                mContactEmails.addAll(ContactAccessor.getInstance().getContactEmails(mCtx, id));
            }
        }
        return mContactEmails;
    }

    public int getRelevanceScore() {
        return mContactRelevanceScore;
    }


    public void incRelevanceScore() {
        this.mContactRelevanceScore++;
    }

    public void addContactId(String id) {
        if (this.mContactIDs != null)
            this.mContactIDs.add(id);
    }

    public Boolean isAnydoUser() {
        return mIsAnydoUser;
    }

    public void setAnydoUser(boolean isAnydoUser) {
        this.mIsAnydoUser = isAnydoUser;
    }

    public String getAnydoPUID() {
        return mAnydoPUID;
    }

    public void setAnydoPUID(String mAnydoUserId) {
        this.mAnydoPUID = mAnydoUserId;
    }

    @Override
    public String toString() {
        return getName();
    }
}
