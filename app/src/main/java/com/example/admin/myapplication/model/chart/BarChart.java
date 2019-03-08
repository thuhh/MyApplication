package com.example.admin.myapplication.model.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BarChart extends View {

    private static final String TAG = "BarChart";
    private Paint mChartPaint;
    private Rect mBound;

    private float textStart;
    private int mHeight;
    private int mWidth;

    private List<Float> verticalList = new ArrayList<>();
    private List<String> horizontalList = new ArrayList<>();

    private float verticalWidth = 100f;
    private float chartWidth;
    private float outSpace = verticalWidth;
    private float startChart = verticalWidth;
    private float interval;

    private float bottomHeight = 100f;
    private float barWidth;
    private float preBarWidth;

    private String maxValue = "2";
    private String middleValue = "1";

    private int paddingTop = 20;

    private int chartHeight = 10;
    private Paint noDataPaint;
    private Paint textXpaint;
    private Paint linePaint;

    private String noDataColor = "#66FF6933";
    private String textColor = "#FFBEBEBE";
    private String lineColor = "#E4E5E6";
    private String chartColor = "#FF6933";
    private int mDuriation = 3000;
    private boolean isShort = false;
    private float allInteval;
    private float allBarwidth;
    private float barPercent = 0.1f;
    private float intevalPercent = 0.04f;

    private float allChartWidth;
    private Paint textYpaint;


    private ChartAnimator mAnimator;


    public BarChart(Context context) {
        this(context, null);
    }

    public BarChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mWidthModle = MeasureSpec.getMode(widthMeasureSpec);
        int mWidthSize = MeasureSpec.getSize(widthMeasureSpec);

        int mHeightModle = MeasureSpec.getMode(heightMeasureSpec);
        int mHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (mWidthModle == MeasureSpec.EXACTLY) {
            mWidth = mWidthSize;
        } else {

            mWidth = (getPaddingLeft() + getPaddingRight());
            if (mWidthModle == MeasureSpec.AT_MOST) {
                mWidth = Math.min(mWidth, mWidthSize);
            }
        }
        if (mHeightModle == MeasureSpec.EXACTLY) {
            mHeight = mHeightSize;
        } else {
            mHeight = (getPaddingTop() + getPaddingBottom());
            if (mHeightModle == MeasureSpec.AT_MOST) {
                mHeight = Math.min(mHeight, mHeightSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight() - paddingTop;

        chartWidth = mWidth - outSpace;
        if (isShort) {
            barWidth = (int) (chartWidth * barPercent);
            interval = (int) (chartWidth * intevalPercent);


            allBarwidth = horizontalList.size() * barWidth;
            allInteval = (horizontalList.size() - 1) * interval;
            allChartWidth = allBarwidth + allInteval;

            startChart = outSpace + (chartWidth / 2f - allChartWidth / 2f);
        } else {

            try {
                preBarWidth = chartWidth / verticalList.size();
            } catch (Exception e) {
            }

            interval = preBarWidth / 10f * 3f;
            barWidth = preBarWidth - interval;

            startChart = verticalWidth;
        }

        //////////////////////////////////////////

        textStart = startChart + (barWidth / 2f);

    }

    private void init() {

        mAnimator = new ChartAnimator(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                postInvalidate();
            }
        });

        mBound = new Rect();


        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);
        mChartPaint.setColor(Color.parseColor(chartColor));


        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.parseColor(lineColor));

        textXpaint = new Paint();
        textXpaint.setAntiAlias(true);
        textXpaint.setTextSize(27f);
        textXpaint.setTextAlign(Paint.Align.CENTER);
        textXpaint.setColor(Color.parseColor(textColor));

        textYpaint = new Paint();
        textYpaint.setAntiAlias(true);
        textYpaint.setTextSize(28f);
        textYpaint.setTextAlign(Paint.Align.LEFT);
        textYpaint.setColor(Color.parseColor(textColor));

        noDataPaint = new Paint();
        noDataPaint.setAntiAlias(true);
        noDataPaint.setColor(Color.parseColor(noDataColor));
        noDataPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float lineInterval = (mHeight - bottomHeight) / 4f;
        float textHeight = mHeight + paddingTop - bottomHeight;

        drawLine(canvas, lineInterval, textHeight);

        drawYtext(canvas, lineInterval, textHeight);

        float textTempStart = textStart;

        drawXtext(canvas, textTempStart);


        float chartTempStart = startChart;

        float size = (mHeight - bottomHeight) / 100f; //比例

        drawBar(canvas, chartTempStart, size);

    }

    private void drawBar(Canvas canvas, float chartTempStart, float size) {
        if (isShort) {

            for (int i = 0; i < verticalList.size(); i++) {

                float barHeight = verticalList.get(i) / Float.valueOf(maxValue) * 100f * size;

                float realBarHeight = barHeight * mAnimator.getPhaseY();

                if (verticalList.get(0) == 0) {
                    canvas.drawRect(chartTempStart,
                            mHeight - bottomHeight + paddingTop - chartHeight
                            , chartTempStart + barWidth,
                            mHeight + paddingTop - bottomHeight
                            , noDataPaint);
                } else {
                    canvas.drawRect(chartTempStart,
                            (mHeight - bottomHeight + paddingTop) - realBarHeight
                            , chartTempStart + barWidth,
                            mHeight + paddingTop - bottomHeight
                            , mChartPaint);
                }

                chartTempStart += (barWidth + interval);

            }

        } else {
            for (int i = 0; i < verticalList.size(); i++) {

                float barHeight = verticalList.get(i) / Float.valueOf(maxValue) * 100f * size;

                float realBarHeight = barHeight * mAnimator.getPhaseY();

                RectF rectF = new RectF();
                rectF.left = chartTempStart;
                rectF.right = chartTempStart + barWidth;
                rectF.bottom = mHeight + paddingTop - bottomHeight;

                if (verticalList.get(i) == 0) {
                    rectF.top = mHeight - bottomHeight + paddingTop - chartHeight;
                    canvas.drawRect(rectF, noDataPaint);
                } else {
                    rectF.top = (mHeight - bottomHeight + paddingTop) - realBarHeight;
                    canvas.drawRect(rectF, mChartPaint);
                }
                chartTempStart += preBarWidth;

            }
        }
    }


    private void drawXtext(Canvas canvas, float textTempStart) {
        if (isShort) {

            for (int i = 0; i < verticalList.size(); i++) {
                textXpaint.getTextBounds(horizontalList.get(i), 0, horizontalList.get(i).length(), mBound);

                Paint.FontMetricsInt fontMetrics = textXpaint.getFontMetricsInt();
                int baseline = (int) (mHeight + paddingTop - bottomHeight + ((bottomHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top));
                canvas.drawText(horizontalList.get(i), textTempStart, baseline, textXpaint);
                textTempStart += (barWidth + interval);
            }

        } else {
            if (horizontalList.size() < 13) {
                for (int i = 0; i < horizontalList.size(); i++) {

                    textXpaint.getTextBounds(horizontalList.get(i), 0, horizontalList.get(i).length(), mBound);

                    Paint.FontMetricsInt fontMetrics = textXpaint.getFontMetricsInt();

                    int baseline = (int) (mHeight + paddingTop - bottomHeight + ((bottomHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top));
                    canvas.drawText(horizontalList.get(i), textTempStart, baseline, textXpaint);

                    textTempStart += preBarWidth;
                }
            } else {
                for (int i = 0; i < horizontalList.size(); i++) {

                    if (i % 4 == 0) {
                        textXpaint.getTextBounds(horizontalList.get(i), 0, horizontalList.get(i).length(), mBound);

                        Paint.FontMetricsInt fontMetrics = textXpaint.getFontMetricsInt();
                        int baseline = (int) (mHeight + paddingTop - bottomHeight + ((bottomHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top));
                        canvas.drawText(horizontalList.get(i), textTempStart, baseline, textXpaint);
                    }
                    textTempStart += preBarWidth;
                }
            }
        }
    }


    private void drawYtext(Canvas canvas, float lineInterval, float textHeight) {
        canvas.drawText("0", 0f, textHeight, textYpaint);

        canvas.drawText(middleValue, 0f, textHeight - lineInterval * 2f + 10f, textYpaint);
        canvas.drawText(maxValue, 0f, textHeight - lineInterval * 4f + 10f, textYpaint);
    }


    private void drawLine(Canvas canvas, float lineInterval, float textHeight) {
        canvas.drawLine(outSpace - 10f, textHeight, mWidth, textHeight, linePaint);
        canvas.drawLine(outSpace - 10f, textHeight - lineInterval, mWidth, textHeight - lineInterval, linePaint);
        canvas.drawLine(outSpace - 10f, textHeight - lineInterval * 2f, mWidth, textHeight - lineInterval * 2f, linePaint);
        canvas.drawLine(outSpace - 10f, textHeight - lineInterval * 3f, mWidth, textHeight - lineInterval * 3f, linePaint);
        canvas.drawLine(outSpace - 10f, textHeight - lineInterval * 4f, mWidth, textHeight - lineInterval * 4f, linePaint);
    }


    private void measureWidthShort(List<Float> verticalList) {
        isShort = true;

        barWidth = (int) (chartWidth * barPercent);
        interval = (int) (chartWidth * intevalPercent);


        allBarwidth = verticalList.size() * barWidth;
        allInteval = (verticalList.size() - 1) * interval;

        allChartWidth = allBarwidth + allInteval;

        startChart = outSpace + (chartWidth / 2f - allChartWidth / 2f);

        textStart = startChart + barWidth / 2f;
    }

    private void measureWidth(List<Float> verticalList) {
        isShort = false;
        try {
            preBarWidth = chartWidth / verticalList.size();
        } catch (Exception e) {
        }
        interval = preBarWidth / 10f * 3f;
        barWidth = preBarWidth - interval;
        startChart = verticalWidth;
        textStart = startChart + (barWidth / 2f);
    }


    public void setVerticalList(List<Float> verticalList) {


        if (verticalList != null) {
            this.verticalList = verticalList;

        } else {
            maxValue = "2";
            middleValue = "1";
            invalidate();
            return;
        }

        if (verticalList.size() <= 6) {

            measureWidthShort(verticalList);

            if (Collections.max(verticalList) > 2) {
                int tempMax = Math.round(Collections.max(verticalList));
                while (tempMax % 10 != 0) {
                    tempMax++;
                }
                int middle = tempMax / 2;
                maxValue = String.valueOf(tempMax);
                middleValue = String.valueOf(middle);
            } else {
                maxValue = "2";
                middleValue = "1";
            }
        } else {

            measureWidth(verticalList);

            if (Collections.max(verticalList) > 2) {
                int tempMax = Math.round(Collections.max(verticalList));
                while (tempMax % 10 != 0) {
                    tempMax++;
                }
                int middle = tempMax / 2;
                maxValue = String.valueOf(tempMax);
                middleValue = String.valueOf(middle);
            } else {
                maxValue = "2";
                middleValue = "1";
            }

        }

        mAnimator.animateY(mDuriation);
    }

    public void setHorizontalList(List<String> horizontalList) {
        if (horizontalList != null)
            this.horizontalList = horizontalList;
    }

}