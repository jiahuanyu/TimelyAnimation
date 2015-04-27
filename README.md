# TimelyAnimation
Number Vector Animation. Apply in timer or other application.

## Sample Show
![ALT TEXT](./sample.gif)

## System Demand
Android v2.2+

## Environment
`TimelyAnimation` is developed by Android Studio

## Apply Step
- Gradle
```
compile(group: 'com.jiahuan.timelyanimation', name: 'timelyanimation', version: '0.5.0', ext: 'aar')
```

- Maven
```xml
<dependency>
        <groupId>com.jiahuan.timelyanimation</groupId>
        <artifactId>timelyanimation</artifactId>
        <version>0.5.0</version>
        <type>aar</type>
</dependency>
```

- Add `NumberSwitchView` in layout
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:ta="http://schemas.android.com/apk/res-auto"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="#FFFFFF">

    <!-- Custom Properties -->
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

- Use `NumberSwitchView`
```java
mNumberSwitchView = (NumberSwitchView) findViewById(R.id.numberswitchview);
// animation function number = [0,9]
mNumberSwitchView.animateTo(number);
```

- Simple Sample
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

## Exist Problem
- Not good number points array
