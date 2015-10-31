package com.grabhouse.grabhouse.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends ImageView {

    private static final int FADE_IN_TIME_MS = 500;

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width

    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);

        //TransitionDrawable td = new TransitionDrawable(new Drawable[]{new ColorDrawable(android.R.color.transparent), new BitmapDrawable(getContext().getResources(), bm)});
        //TransitionDrawable td = new TransitionDrawable(new Drawable[]{getDrawable(), new BitmapDrawable(getContext().getResources(), bm)});
        //setImageDrawable(td);
        //td.startTransition(FADE_IN_TIME_MS);
    }
}