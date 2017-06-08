package my.xbl.com.viewpagerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by April on 2017/6/8.
 */

public class SecondActivity extends Activity {
    private List<View> list;
    private int images[] = {R.drawable.bana1, R.drawable.bana2, R.drawable.bana3};
    //设置指示器
    private List<View> pointList;
    private ImageView banner_iv;
    private ViewPager viewPager;
    private LinearLayout ll;
    private boolean isWorking=true;
    private boolean isAlive=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        ll = (LinearLayout) findViewById(R.id.main_ll);
        initViewPagerData();
        initPointData();
        initEvent();
        SecondPagerAdapter secondPagerAdapter = new SecondPagerAdapter(list);
        viewPager.setAdapter(secondPagerAdapter);
        //定义ViewPager初始的位置
        viewPager.setCurrentItem(1000 * 6);
        new TimerThread().start();



    }
    //将
    private void initPointData() {
        pointList = new ArrayList<>();
        int len = images.length;
        for (int i = 0; i < len; i++) {
            ImageView imageView = (ImageView) LayoutInflater.from(SecondActivity.this).inflate(R.layout.view_banner_point, null);
            imageView.setImageResource(R.drawable.banner_point);
            //设置添加TextView的宽高(像素)
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20);
            lp.leftMargin = 10;
            lp.rightMargin = 10;
            imageView.setLayoutParams(lp);
            //  pointList.add(textView);
            //将这个View添加到线性布局当中
            ll.addView(imageView);
        }
       // setPoint(0);

    }

    private void initViewPagerData() {
        list = new ArrayList<>();
        int len = images.length;
        //如果是两张或者是三张图片循环添加两次
        int foradd = 1;
        if (len == 2 || len == 3) {
            foradd = 2;
        }
        //重复循环
        for (int j = 0; j < foradd; j++) {
            for (int i = 0; i < len; i++) {
                View v = LayoutInflater.from(this).inflate(R.layout.view_banner, null);
                banner_iv = (ImageView) v.findViewById(R.id.view_banner_iv);
                banner_iv.setImageResource(images[i]);
                list.add(v);
            }
        }

    }

    //选中某一个指示器
    private void setPoint(int position) {
        int len = images.length;
        for (int i = 0; i < len; i++) {
            //选择线性布局中第i个小圆圈
            ImageView imageView = (ImageView) ll.getChildAt(i);
            //如果选择的小圆圈和当前显示的View的position是吻合的
            if (i ==position) {
                //将第i个圆圈设成被选中状态
                imageView.setSelected(true);
            } else {
                imageView.setSelected(false);
            }
        }
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                //切换到下一页
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        }
    };
    //viewPager的监听事件
    private void initEvent() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面在滚动，页面的滑动信息
            //positionOffset 滑动的百分比  positionOffsetPixels滑动的像素点
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //修改小圆点显示的位置
                setPoint(position % images.length);

            }
            @Override
            public void onPageScrollStateChanged(int state) {
                //处于空闲状态，线程工作
                if(state==ViewPager.SCROLL_STATE_IDLE){
                    isWorking=true;
                    //手动切换
                }else if(state==ViewPager.SCROLL_STATE_DRAGGING){
                    //线程停止工作
                    isWorking=false;
                }


            }
        });
    }
    //两秒钟切换一次界面
    private class TimerThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (isAlive){
                try {
                    Thread.sleep(1000*2);
                    if (isAlive) {
                        if (isWorking) {
                            handler.sendEmptyMessage(1);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAlive=false;
    }
}
