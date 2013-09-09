package com.udinic.general_testing;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 08/09/13
 * Time: 14:42
 */
public class BitmapStuff extends Activity {

    private MyImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_bitmap_stuff);

        image = (MyImageView) findViewById(R.id.imageView);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                image.setShadow(progress/100f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
