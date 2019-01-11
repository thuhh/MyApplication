package com.example.admin.myapplication.controller.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public final class ImageViewForListImage extends ImageView {
    private static final int NUMBER = 25;

    public ImageViewForListImage(Context context) {
        super(context);
    }

    public ImageViewForListImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewForListImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageViewForListImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        return super.setFrame(l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, NUMBER, NUMBER, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
