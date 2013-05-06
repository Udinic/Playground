package com.udinic.ChatHeadsExample;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * User: udinic
 * Date: 5/6/13
 * Time: 12:49 AM
 */
public class ChatHeadService extends Service {

    private WindowManager windowManager;
    private FrameLayout chatHead;
    private ViewGroup tasks;

    @Override public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        Log.d("udini", "onCreate");

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new FrameLayout(this);
        chatHead.setBackgroundColor(Color.RED);

//        ImageView img = new ImageView(this);
//        img.setImageResource(R.drawable.ic_launcher);
//        chatHead.addView(img);

//        chatHead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (tasks != null) {
//                    tasks.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.RIGHT;
        params.x = 0;
        params.y = 300;

        windowManager.addView(chatHead, params);

        chatHead.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(chatHead, params);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("udini", "onStartCommand");

        if (intent != null && intent.hasExtra("STOP"))
            stopSelf();
        else {
            tasks = (ViewGroup)LayoutInflater.from(getBaseContext()).inflate(R.layout.popup_call_tasks,null,false);

            ListView list = (ListView) tasks.findViewById(R.id.list);
            list.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, new String[] {"udini","bnlabl", "dsdss"}));
//            tasks.setVisibility(View.GONE);
            chatHead.addView(tasks);
        }


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("udini", "onDestroy");

        super.onDestroy();
        if (chatHead != null) windowManager.removeView(chatHead);
    }
}
