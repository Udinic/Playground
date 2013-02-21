package com.udinic.general_testing;

import android.app.*;
import android.content.*;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.*;

import static android.media.RingtoneManager.TYPE_NOTIFICATION;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 28/10/12
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public class RingtonePicker extends Activity {

    Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.act_ringtone);

        ((Button) findViewById(R.id.makeSound)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                NotificationManager notManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

//                NotificationCompat.Builder builder = new NotificationCompat.Builder(RingtonePicker.this);
//                builder.setSmallIcon(R.drawable.ic_launcher);
//                builder.setTicker("udini uri is " + (uri == null?"null":"not null"));
//                if (uri != null)
//                    builder.setSound(uri);
//                else
//                    builder.setDefaults(Notification.DEFAULT_SOUND);
//
//                notManager.cancel(4);
//                notManager.notify(4,builder.build());


                int icon = R.drawable.ic_launcher;
                CharSequence tickerText = "Hello"; // ticker-text
                long when = System.currentTimeMillis();
                Context context = getApplicationContext();
                CharSequence contentTitle = "Hello";
                CharSequence contentText = "Hello";
                Intent notificationIntent = new Intent(RingtonePicker.this, RingtonePicker.class);
                PendingIntent contentIntent = PendingIntent.getActivity(RingtonePicker.this, 0, notificationIntent, 0);
                Notification notification = new Notification(icon, tickerText, when);
                if (uri != null)
                    notification.sound = uri;
                else
                    notification.defaults |= Notification.DEFAULT_SOUND;
                notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

                notManager.notify(6, notification);


            }
        });

        ((Button) findViewById(R.id.ringtonePickerDefault)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ringtonePickerDefault();
            }
        });
        ((Button) findViewById(R.id.ringtonePicker)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ringtonePicker();
            }
        });
        ((Button) findViewById(R.id.ringtoneReview)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                reviewRingtones();
            }
        });
        ((Button) findViewById(R.id.ringtoneReviewRM)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                reviewRingtonesRM();
            }
        });
        ((Button) findViewById(R.id.registerSoundInternal)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addSound(RingtonePicker.this, R.raw.anydo_pop, "Anydo Udini", "anydo_pop.mp3", false);
            }
        });
        ((Button) findViewById(R.id.registerSoundExternal)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addSound(RingtonePicker.this, R.raw.anydo_pop, "Anydo Udini", "anydo_pop.mp3", true);
            }
        });
        ((Button) findViewById(R.id.deleteSoundInternal)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                new File(getPath("anydo_pop.mp3", false)).delete();
                int delInternal = getContentResolver().delete(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, MediaStore.Audio.Media.DISPLAY_NAME + " LIKE 'anydo_%'", null);
                Log.d("udini", "deleted internal[" + delInternal + "]");
            }
        });
        ((Button) findViewById(R.id.deleteSoundExternal)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                new File(getPath("anydo_pop.mp3", true)).delete();
                int delExternal = getContentResolver().delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, MediaStore.Audio.Media.DISPLAY_NAME + " LIKE 'anydo_%'", null);
                Log.d("udini", "deleted external["+delExternal +"]");
            }
        });



    }

    public void ringtonePickerDefault() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE,"My customised Title");
        Uri uri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 1l);
        // Don't show 'Silent'
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri);
        startActivityForResult(intent, 123);
    }

    public void ringtonePicker() {
        final RingtoneManager rm = new RingtoneManager(this);
        rm.setType(RingtoneManager.TYPE_NOTIFICATION);
        rm.setIncludeDrm(true);


//        final Cursor curRings = rm.getCursor();
//        while (curRings.moveToNext()) {
//            int id = curRings.getInt(RingtoneManager.ID_COLUMN_INDEX);
//            String title = curRings.getString(RingtoneManager.TITLE_COLUMN_INDEX);
//            String uri = curRings.getString(RingtoneManager.URI_COLUMN_INDEX);
//
//            Log.d("udini", "id["+id+"] title["+title+"] uri["+uri+"]");
//        }
//        curRings.close();

        int pos = rm.getRingtonePosition(RingtoneManager.getDefaultUri(TYPE_NOTIFICATION));

        AlertDialog.Builder builder = new AlertDialog.Builder(RingtonePicker.this);
        builder.setSingleChoiceItems(rm.getCursor(), pos, rm.getCursor().getColumnName(RingtoneManager.TITLE_COLUMN_INDEX), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, final int i) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Ringtone ringtone = rm.getRingtone(i);
                        if (ringtone != null)
                            ringtone.play();
                    }
                }, 300);
            }
        });
        builder.setTitle("Pick!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("udini", "positive");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("udini", "Negative");
            }
        });

        View udini = getLayoutInflater().inflate(R.layout.alert_dialog_holo, null);
        AlertDialog dlg = builder.create();

//        dlg.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
//        dlg.setContentView(udini);
//        View title = dlg.findViewById(android.R.id.custom);
//        title.setTextColor(Color.parseColor("#00FF00"));
        dlg.show();

    }

    public void reviewRingtones() {
        Uri newFileUri = MediaStore.Audio.Media.getContentUriForPath("");

        String[] INTERNAL_COLUMNS = new String[] {
                MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
                "\"" + MediaStore.Audio.Media.INTERNAL_CONTENT_URI + "\"",
                MediaStore.Audio.Media.TITLE_KEY
        };

//        String bla[] = INTERNAL_COLUMNS;
                String bla[] = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.IS_ALARM,
                MediaStore.Audio.Media.IS_MUSIC,
                MediaStore.Audio.Media.IS_NOTIFICATION,
                MediaStore.Audio.Media.IS_RINGTONE,
                MediaStore.Audio.Media.ARTIST
        };
        Cursor curNotification = this.getContentResolver().query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, bla, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        while (curNotification.moveToNext()) {
            boolean show = (curNotification.getString(curNotification.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)).toLowerCase().startsWith("a"));

            if (!show) continue;

            StringBuilder string = new StringBuilder();

            for (int i = 0; i < bla.length; ++i) {
                string.append(bla[i] + "[" + curNotification.getString(curNotification.getColumnIndex(bla[i])) + "] ");
            }
            string.append("\n");

            Log.d("udini", string.toString());
        }

        curNotification.close();
    }

    public void reviewRingtonesRM() {
        RingtoneManager rm = new RingtoneManager(this);
        rm.setType(TYPE_NOTIFICATION);
        Cursor curRings = rm.getCursor();

        while (curRings.moveToNext()) {
            if(curRings.getString(RingtoneManager.TITLE_COLUMN_INDEX).toLowerCase().startsWith("a"))
                Log.d("udini", "Title [" + curRings.getString(RingtoneManager.TITLE_COLUMN_INDEX) + "] Uri [" + curRings.getString(RingtoneManager.URI_COLUMN_INDEX) + "]");
        }
        curRings.close();
    }

    public String getPath(String filename, boolean external) {
        String pathInternal="/sdcard/media/audio/notifications/";
        String pathExternal= Environment.getExternalStorageDirectory() + "/media/audio/notifications/";

        String path = external ? pathExternal : pathInternal;

        return path + filename;
    }

    public boolean addSound(Context ctx, int ressound, String notificationName, String filename, boolean external){
        byte[] buffer=null;
        InputStream fIn = ctx.getResources().openRawResource(ressound);
        int size=0;

        try {
            size = fIn.available();
            buffer = new byte[size];
            fIn.read(buffer);
            fIn.close();
        } catch (IOException e) {
            return false;
        }

        String pathInternal="/sdcard/media/audio/notifications/";
        String pathExternal= Environment.getExternalStorageDirectory() + "/media/audio/notifications/";

        String path = external ? pathExternal : pathInternal;

        boolean exists = (new File(path)).exists();
        if (!exists){new File(path).mkdirs();}

        FileOutputStream save;
        try {
            save = new FileOutputStream(path+filename);
            save.write(buffer);
            save.flush();
            save.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + filename)));

        File k = new File(path, filename);

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, notificationName);
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.ARTIST, "Any.DO");
        values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
        values.put(MediaStore.Audio.Media.IS_ALARM, true);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        Uri newFileUri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());

        Cursor curNotification = ctx.getContentResolver().query(newFileUri,
                new String[]{MediaStore.Audio.Media._ID},
                MediaStore.Audio.Media.TITLE + "=?",
                new String[]{notificationName}, null);


        Log.d("udini", "new notification is " + notificationName + " filename " + filename);

        int id = -1;

        if (curNotification.getCount() == 0) {
            // Insert it into the database
            Uri newSound = ctx.getContentResolver().insert(newFileUri, values);
//            Cursor curAdded = ctx.getContentResolver().query(newSound, new String[]{MediaStore.Audio.Media._ID}, null, null, null);
//            if (curAdded.getCount() > 0) {
//                curAdded.moveToFirst();
//                id = curAdded.getInt(curAdded.getColumnIndex(MediaStore.Audio.Media._ID));
//            }
//            curAdded.close();

            uri = newSound;
        } else {
            curNotification.moveToFirst();
            id = curNotification.getInt(curNotification.getColumnIndex(MediaStore.Audio.Media._ID));
            int updated = ctx.getContentResolver().update(newFileUri, values, MediaStore.Audio.Media._ID + "= ?", new String[] {String.valueOf(id)});
        }

        curNotification.close();


        return true;
    }
}
