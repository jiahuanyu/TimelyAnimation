package com.jiahuan.timelyanimation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.jiahuan.library.timelyanimation.NumberSwitchView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity
{
    private NumberSwitchView mNumberSwitchView;
    int number = 0;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (number % 3 == 0)
            {
                mNumberSwitchView.setNumberColor(Color.RED);
                mNumberSwitchView.setNumberBGColor(Color.BLACK);
            }
            else
            {
                mNumberSwitchView.setNumberColor(Color.rgb(10, 10, 10));
                mNumberSwitchView.setNumberBGColor(Color.BLUE);
            }
            mNumberSwitchView.animateTo(number);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumberSwitchView = (NumberSwitchView) findViewById(R.id.numberswitchview);
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                number++;
                if (number > 9)
                {
                    number = 0;
                }

                handler.obtainMessage().sendToTarget();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 1000, 1000);
    }

}
