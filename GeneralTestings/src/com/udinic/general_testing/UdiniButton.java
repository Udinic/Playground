package com.udinic.general_testing;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 31/10/12
 * Time: 00:39
 * To change this template use File | Settings | File Templates.
 */
public class UdiniButton extends Button {
    public UdiniButton(Context context) {
        super(context);
    }

    public UdiniButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UdiniButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setBackgroundDrawable(Drawable d) {
        // Replace the original background drawable (e.g. image) with a LayerDrawable that
        // contains the original drawable.
        SAutoBgButtonBackgroundDrawable layer = new SAutoBgButtonBackgroundDrawable(d);
        super.setBackgroundDrawable(layer);
    }

    /**
     * The stateful LayerDrawable used by this button.
     */
    protected class SAutoBgButtonBackgroundDrawable extends LayerDrawable {

        // The color filter to apply when the button is pressed
        protected ColorFilter _pressedFilter = new LightingColorFilter(Color.LTGRAY, 1);
        protected ColorFilter udiniFilter = new LightingColorFilter(Color.LTGRAY, 1);
        // Alpha value when the button is disabled
        protected int _disabledAlpha = 100;

        public SAutoBgButtonBackgroundDrawable(Drawable d) {
            super(new Drawable[] { d });
        }

        @Override
        protected boolean onStateChange(int[] states) {
            boolean enabled = false;
            boolean pressed = false;

            for (int state : states) {
                if (state == android.R.attr.state_enabled)
                    enabled = true;
                else if (state == android.R.attr.state_pressed)
                    pressed = true;
            }

            mutate();
            if (enabled && pressed) {
                setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
            } else if (!enabled) {
                setColorFilter(null);
                setAlpha(_disabledAlpha);
            } else {
                setColorFilter(null);
            }

            invalidateSelf();

            return super.onStateChange(states);
        }

        @Override
        public boolean isStateful() {
            return true;
        }
    }
}
