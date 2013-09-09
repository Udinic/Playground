package com.udinic.general_testing;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 08/09/13
 * Time: 14:43
 */
public class MyImageView extends ImageView {
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setShadow(float value) {
        int colorValue = (int)(255 - 200 * value);
        Log.d("MyImageView", "setShadow value[" + value + "] colorValue[" + colorValue + "])");
                setColorFilter(Color.rgb(colorValue, colorValue, colorValue),
                        android.graphics.PorterDuff.Mode.MULTIPLY);
    }

}
