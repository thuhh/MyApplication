package com.example.admin.myapplication.model.chart;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;


public class ChartAnimator {


    protected float mPhaseY = 1f; //0f-1f
    protected float mPhaseX = 1f; //0f-1f

    private ValueAnimator.AnimatorUpdateListener mListener;

    public ChartAnimator(ValueAnimator.AnimatorUpdateListener listener) {
        mListener = listener;
    }

    public void animateY(int durationMillis) {

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "phaseY", 0f, 1f);
        animatorY.setDuration(durationMillis);
        animatorY.addUpdateListener(mListener);
        animatorY.start();
    }


    public void animateX(int durationMillis) {

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "phaseX", 0f, 1f);
        animatorY.setDuration(durationMillis);
        animatorY.addUpdateListener(mListener);
        animatorY.start();
    }


    public float getPhaseY() {
        return mPhaseY;
    }


    public void setPhaseY(float phase) {
        mPhaseY = phase;
    }


    public void setPhaseX(float phase) {
        mPhaseX = phase;
    }

    public float getPhaseX() {
        return mPhaseX;
    }

}
