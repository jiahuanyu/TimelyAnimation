package com.jiahuan.timelyanimation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
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
    private static final int WIDTH_PRE = 110;   // px
    private static final int HEIGHT_PRE = 170;  // px
    //
    private int numberWrapSpace = 1; //dp
    private int numberAnimationDuration = 500; // ms
    private int numberColor = 0XFF1A2634;
    private int numberBGColor = 0xFFABCDEF;
    private float numberStrokeWidth = 4; // px
    //
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
        numberWrapSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, numberWrapSpace, context.getResources().getDisplayMetrics());
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberSwitchView);
        numberAnimationDuration = typedArray.getInt(R.styleable.NumberSwitchView_ta_number_animation_duration, numberAnimationDuration);
        numberBGColor = typedArray.getColor(R.styleable.NumberSwitchView_ta_number_bg_color, numberBGColor);
        numberColor = typedArray.getColor(R.styleable.NumberSwitchView_ta_number_color, numberColor);
        numberStrokeWidth = typedArray.getDimension(R.styleable.NumberSwitchView_ta_number_stroke_width, numberStrokeWidth);
        Log.i(TAG, "numberAnimationDuration = " + numberAnimationDuration);
        Log.i(TAG, "numberBGColor = " + numberBGColor + "");
        Log.i(TAG, "numberColor = " + numberColor + "");
        Log.i(TAG, "numberStrokeWidth = " + numberStrokeWidth);
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
        mPaint.setStrokeWidth(numberStrokeWidth);
        mPaint.setPathEffect(new CornerPathEffect(200));
        //
        Interpolator interc = AnimationUtils.loadInterpolator(getContext(), android.R.interpolator.accelerate_decelerate);
        switchAnimation = new NumberSwitchAnimation();
        switchAnimation.setDuration(numberAnimationDuration);
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
        canvas.translate(numberStrokeWidth/2 , numberStrokeWidth/2);
        canvas.scale(this.widthRatio, this.heightRatio);
        canvas.drawColor(numberBGColor);
        canvas.drawPath(makeNumberPath(currentNumberPoints), mPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        this.widthRatio = (width + this.numberStrokeWidth) / WIDTH_PRE;
        this.heightRatio = (height + this.numberStrokeWidth) / HEIGHT_PRE;
        setMeasuredDimension((int) (width + this.numberStrokeWidth), (int) (height + this.numberStrokeWidth));
        Log.i(TAG, "width = " + width + ", height = " + height);
    }


    // 0 - 9
    public void animateTo(int number)
    {
        synchronized (this)
        {
            if (number >= 0 && number <= 9)
            {
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

    // out api
    public int getNumberAnimationDuration()
    {
        return numberAnimationDuration;
    }

    public void setNumberAnimationDuration(int numberAnimationDuration)
    {
        this.numberAnimationDuration = numberAnimationDuration;
        switchAnimation.setDuration(numberAnimationDuration);
    }

    public int getNumberColor()
    {
        return numberColor;
    }

    public void setNumberColor(int numberColor)
    {
        this.numberColor = numberColor;
        invalidate();
    }

    public void setNumberBGColor(int numberBGColor)
    {
        this.numberBGColor = numberBGColor;
    }

    public int getNumberBGColor()
    {
        return numberBGColor;
    }
}
