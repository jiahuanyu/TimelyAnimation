# TimelyAnimation
Timely数字变化动画效果——可以做计时器相关

## 开发环境
`TimelyAnimation`是基于AndroidStudio开发的

## 嵌入步骤
- 在布局文件中添加`NumberSwitchView`控件
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:ta="http://schemas.android.com/apk/res-auto"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="#FFFFFF">

    <!-- 自定义属性 -->
    <com.jiahuan.timelyanimation.NumberSwitchView
        android:id="@+id/numberswitchview"
        android:layout_width="130dp"
        android:layout_height="200dp"
        ta:ta_number_animation_duration="600"
        ta:ta_number_bg_color="#000000"
        ta:ta_number_stroke_width="3dp"
        ta:ta_number_color="#FFFFFF">
    </com.jiahuan.timelyanimation.NumberSwitchView>
</RelativeLayout>
```

- 在Activity中获取`NumberSwitchView`
```java
// 获取控件
mNumberSwitchView = (NumberSwitchView) findViewById(R.id.numberswitchview);
// 调用数字变化动画 number = [0,9]
mNumberSwitchView.animateTo(number);
```

- 简单案例
```java
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
```

## 存在的问题
- 目前数字的点集数据不是很好
