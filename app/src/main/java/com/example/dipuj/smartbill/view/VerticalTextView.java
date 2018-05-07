package com.example.dipuj.smartbill.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;

import com.example.dipuj.smartbill.R;

public class VerticalTextView extends android.support.v7.widget.AppCompatTextView {
    private final boolean topDown;
    private String TAG = "VerticalTextView";

    /**
     * Initialize context and AttributeSet.
     *
     * @param context context
     * @param attrs   AttributeSet
     */
    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
        final int gravity = getGravity();
        if (Gravity.isVertical(gravity)
                && (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM) {
            setGravity((gravity & Gravity.HORIZONTAL_GRAVITY_MASK)
                    | Gravity.TOP);
            topDown = false;
        } else {
            topDown = true;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();

        canvas.save();

        if (topDown) {
            canvas.translate(getWidth(), 0);
            int ROTATION_ANGLE = 90;
            canvas.rotate(ROTATION_ANGLE);
        } else {
            canvas.translate(0, getHeight());
            int REVERSE_ROTATION_ANGLE = -90;
            canvas.rotate(REVERSE_ROTATION_ANGLE);
        }

        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());

        getLayout().draw(canvas);
        canvas.restore();
    }

    /**
     * Set custom font for vertical text view.
     *
     * @param ctx   Context
     * @param attrs AttributeSet
     */
    private void setCustomFont(Context ctx, AttributeSet attrs) {

    }

    /**
     * Set custom font.
     *
     * @param ctx   Context
     * @param asset asset value
     */
    public boolean setCustomFont(Context ctx, String asset) {

        Typeface tf;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);

        } catch (Exception e) {
            return false;
        }

        setTypeface(tf);
        return true;
    }
}
