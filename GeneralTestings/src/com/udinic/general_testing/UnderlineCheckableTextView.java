package com.udinic.general_testing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.Gravity;
import android.widget.CheckedTextView;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 22/04/13
 * Time: 12:21
 */
public class UnderlineCheckableTextView extends CheckedTextView {

    private static int mUnderlineColor;
    private static float mUnderlineThickness;

    static {
        mUnderlineColor = Color.RED;
        mUnderlineThickness = 6f;
    }

    public UnderlineCheckableTextView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // If the component is checked - let's draw an underline
        if (isChecked()) {
            TextPaint wp = getPaint();

            // kStdUnderline_Offset = 1/9, defined in SkTextFormatParams.h
            // We want to put the underline a little more far away from the text, so the new underline offset is 1/4.
            float underlineTop = getBaseline() + wp.baselineShift + (1.0f / 4.0f) * wp.getTextSize();

            int previousColor = wp.getColor();
            Paint.Style previousStyle = wp.getStyle();
            boolean previousAntiAlias = wp.isAntiAlias();

            wp.setStyle(Paint.Style.FILL);
            wp.setAntiAlias(true);

            float xOffset;
            float width = wp.measureText(getText().toString());

            if ((getGravity()  & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.RIGHT)
                xOffset = getWidth() - getPaddingRight() - width;
            else if ((getGravity()  & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL)
                xOffset = getWidth()/2 - width/2;
            else
                xOffset = getPaddingLeft();

            wp.setColor(mUnderlineColor);
            canvas.drawRect(xOffset, underlineTop, xOffset + width, underlineTop + mUnderlineThickness, wp);

            wp.setStyle(previousStyle);
            wp.setColor(previousColor);
            wp.setAntiAlias(previousAntiAlias);
        }
    }
}
