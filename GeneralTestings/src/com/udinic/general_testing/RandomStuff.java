package com.udinic.general_testing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.Time;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 19/11/12
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class RandomStuff extends Activity {

    private ClickableSpan mCycleClickSpan;
    private ClickableSpan mTimeClickSpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_slidedrawer);

    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    private View layerDrawable() {

        LayerDrawable udini2 = ((LayerDrawable)getResources().getDrawable(R.drawable.layer));

//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.exec_mail_big);

        Drawable drawable = getResources().getDrawable(R.drawable.exec_call_big);

        BitmapDrawable bmd = new BitmapDrawable(getResources(), drawableToBitmap(drawable));
        bmd.setGravity(Gravity.CENTER);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        bmd.setTargetDensity(metrics);

        LayerDrawable udini = new LayerDrawable(new Drawable[]{getResources().getDrawable(R.drawable.exec_empty), bmd});

//        udini2.setDrawableByLayerId(R.id.udini, getResources().getDrawable(R.drawable.exec_call_big));



        Button ib = new Button(this);
        ib.setCompoundDrawablesWithIntrinsicBounds(null, udini, null, null);
        ImageView ib2 = new ImageView(this);
        ib2.setImageDrawable(getResources().getDrawable(R.drawable.exec_call_big));

        LinearLayout frame = new LinearLayout(this);
        frame.addView(ib, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        frame.addView(ib2, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        return frame;

    }

    private static final int CONTACT_PICKER_RESULT = 1001;

    private TextView textUdini(int idxCycle) {
        String udini = "I want to take a moment to plan my day on %s at %s";
        String udini2 = "I want to take a moment to plan my day %s";

        String[] items = getResources().getStringArray(com.udinic.general_testing.R.array.moment_cycle);
        String firstSpan = items[idxCycle];
        String secondSpan = "09:00";

        String post = idxCycle == 2 ? String.format(udini2, firstSpan) : String.format(udini, firstSpan, secondSpan);

        SpannableStringBuilder builder = new SpannableStringBuilder(post);
        builder.setSpan(mCycleClickSpan, post.indexOf(firstSpan), post.indexOf(firstSpan) + firstSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (idxCycle < 2)
            builder.setSpan(mTimeClickSpan, post.indexOf(secondSpan), post.indexOf(secondSpan) + secondSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView tv = new TextView(this);
        tv.setTextSize(30);

        MovementMethod m = tv.getMovementMethod();
        if ((m == null) || !(m instanceof LinkMovementMethod)) {
            tv.setMovementMethod(LinkMovementMethod.getInstance());
        }


        tv.setText(builder, TextView.BufferType.SPANNABLE);

        return tv;
    }

    private Button createButton() {
        Button button3 = new Button(this);
        button3.setText("Pick Contact");
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                contactPickerIntent.setType(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);

                startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);

            }
        });

        return button3;
    }

    String DEBUG_TAG = "udini";
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CONTACT_PICKER_RESULT) {
////            Cursor cursor = null;
////            String email = "";
//            try {
//                Uri result = data.getData();
//                Log.v(DEBUG_TAG, "Got a contact result: "
//                        + result.toString());
//                // get the contact id from the Uri
//                String id = result.getLastPathSegment();
//                // query for everything email
//                Cursor cursor2 = getContentResolver().query(result, null, null,null, null);
//
////                cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
////                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[] { id },
////                        null);
//                // let's just get the first email
//                if (cursor2.moveToFirst()) {
//                    String phone = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
//                    String name = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                    Integer photoId = cursor2.getInt(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID));
//
//                    ContactData contactData = new ContactData(getBaseContext(), name, Lists.newArrayList(id), newArrayList(phone), null, 1, photoId);
//
//                    Log.v(DEBUG_TAG, "Got email: " + phone);
//                } else {
//                    Log.w(DEBUG_TAG, "No results");
//                }
//
//                if (cursor2 != null) {
//                    cursor2.close();
//                }
//            } catch (Exception e) {
//                Log.e(DEBUG_TAG, "Failed to get email data", e);
//            }
//
////            EditText emailEntry = (EditText) findViewById(R.id.invite_email);
////            emailEntry.setText(email);
////            if (email.length() == 0) {
////                Toast.makeText(this, "No email found for contact.",
////                        Toast.LENGTH_LONG).show();
////            }
//
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);    //To change body of overridden methods use File | Settings | File Templates.
//    }

    private Button createButtonOld() {
        Button button = new Button(this);
        button.setText("Udini");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
                if (df instanceof SimpleDateFormat) {
                    String pattern = ((SimpleDateFormat) df).toPattern();
                    pattern = pattern.replaceAll("[^\\p{Alpha}]*y+[^\\p{Alpha}]*", "");
                    ((SimpleDateFormat) df).applyPattern(pattern);
                }
                String date = df.format(new Date(1353344400000l));

//                SimpleDateFormat sdf = new SimpleDateFormat("MMM d");
//                date = sdf.format(new Date(1353344400000l));

                new AlertDialog.Builder(RandomStuff.this).setTitle("Date: " + date).create().show();
            }
        });

        Button button2 = new Button(this);
        button2.setText("udini2");
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Time hourAgo = new Time();
                hourAgo.setToNow();
                hourAgo.minute = 0;
                hourAgo.second = 0;
                new AlertDialog.Builder(RandomStuff.this).setTitle("Date: " + new Date(hourAgo.toMillis(false))).create().show();

            }
        });

        Button button3 = new Button(this);
        button3.setText("SMS crap");
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//                smsIntent.setType("vnd.android-dir/mms-sms");
//                smsIntent.putExtra("address", "0546392840");
//                smsIntent.putExtra("sms_body","Body of Message");
//                smsIntent.putExtra("compose_mode",false);
//                startActivity(smsIntent);

                Uri smsUri = Uri.parse("sms:" + "0546392840");
                Intent intent = new Intent(Intent.ACTION_SENDTO, smsUri);
                intent.putExtra("sms_body", "udini is the man");
                intent.putExtra("compose_mode", true);
                startActivity(intent);


            }
        });

        return button3;
    }
}
