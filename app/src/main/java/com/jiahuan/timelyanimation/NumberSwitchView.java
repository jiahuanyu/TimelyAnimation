package com.jiahuan.timelyanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NumberSwitchView extends View
{
    private static final String TAG = "NumberSwitchView";
    //
    private static final float WIDTH_PRE = 110;   // px
    private static final float HEIGHT_PRE = 170;  // px
    //
    private int animationTime = 500; // ms
    private int numberColor = 0XFF1A2634;
    private int numberBGColor = 0xFFABCDEF;
    private float strokeWidth = 5; // px
    //
    private int height;
    private int width;
    private float widthRatio = 1;
    private float heightRatio = 1;
    private Paint mPaint;
    private NumberSwitchAnimation switchAnimation;

    private List<float[]> numbers = new ArrayList<float[]>();
    private float[] currentNumberPoints;

    public NumberSwitchView(Context context)
    {
        this(context, null);

    }

    public NumberSwitchView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }


    public NumberSwitchView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initialize();
    }


    private void initialize()
    {
        Log.i(TAG, "init");
        setData();
        //
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(numberColor);
        mPaint.setStrokeWidth(strokeWidth);
        //
        Interpolator interc = AnimationUtils.loadInterpolator(getContext(), android.R.interpolator.accelerate_decelerate);
        switchAnimation = new NumberSwitchAnimation();
        switchAnimation.setDuration(animationTime);
        switchAnimation.setInterpolator(interc);
        switchAnimation.setFillAfter(true);
        //
    }

    private void setData()
    {
        numbers.add(NumberData.NUMBER_0);
        numbers.add(NumberData.NUMBER_1);
        numbers.add(NumberData.NUMBER_2);
        numbers.add(NumberData.NUMBER_3);
        numbers.add(NumberData.NUMBER_4);
        numbers.add(NumberData.NUMBER_5);
        numbers.add(NumberData.NUMBER_6);
        numbers.add(NumberData.NUMBER_7);
        numbers.add(NumberData.NUMBER_8);
        numbers.add(NumberData.NUMBER_9);
        //
        currentNumberPoints = Arrays.copyOf(NumberData.NUMBER_0, NumberData.NUMBER_0.length);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.save();
        canvas.scale(this.widthRatio, this.heightRatio);
        canvas.drawColor(numberBGColor);
        canvas.drawPath(makeNumberPath(currentNumberPoints), mPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        this.height = MeasureSpec.getSize(heightMeasureSpec);
        this.width = MeasureSpec.getSize(widthMeasureSpec);
        this.widthRatio = this.width / WIDTH_PRE;
        this.heightRatio = this.height / HEIGHT_PRE;
        setMeasuredDimension(this.width, this.height);
        Log.i(TAG, "width = " + width + ", height = " + height);
    }

    // 0 - 9
    public void animateTo(int number)
    {
        synchronized (this)
        {
            if (number >= 0 && number <= 9)
            {
                Log.i(TAG, number + "");
                Log.i(TAG, "start animation");
                Log.i(TAG, numbers.get(number).length + "");
                switchAnimation.setData(currentNumberPoints, numbers.get(number));
                startAnimation(switchAnimation);
            }
        }
    }


    class NumberSwitchAnimation extends Animation
    {
        private float[] to;
        private float[] from;

        public void setData(float[] from, float[] to)
        {
            this.from = Arrays.copyOf(from, from.length);
            this.to = to;
        }


        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t)
        {
            if (interpolatedTime != 1)
            {
                for (int i = 0, N = from.length; i < N; i++)
                {
                    currentNumberPoints[i] = from[i] + (to[i] - from[i]) * interpolatedTime;
                }
                invalidate();
            }
        }
    }


    private Path makeNumberPath(float[] points)
    {
        Path p = new Path();
        p.moveTo(points[0], points[1]);
        for (int i = 2, N = points.length; i < N; i += 2)
        {
            p.lineTo(points[i], points[i + 1]);
        }
        return p;
    }

}
