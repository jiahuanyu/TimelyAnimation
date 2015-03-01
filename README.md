# TimelyAnimation
Timely数字变化动画效果——可以做计时器相关

## 效果展示
![ALT TEXT](https://raw.githubusercontent.com/jiahuanyu/TimelyAnimation/master/sample.gif)


## 嵌入步骤
- 在布局文件中添加`NumberSwitchView`控件
```xml
  <com.jiahuan.timelyanimation.NumberSwitchView
      android:id="@+id/numberswitchview"
      android:layout_width="100dp"
      android:layout_height="200dp">
  </com.jiahuan.timelyanimation.NumberSwitchView>
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

### 下个版本
- 做成类库
- 更多自定义属性
