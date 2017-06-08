package my.xbl.com.viewpagerdemo;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by April on 2017/6/7.
 */

public class SecondPagerAdapter extends PagerAdapter {
    private List<View> vList;

    public SecondPagerAdapter(List<View> vList) {
        this.vList = vList;
    }

    //想要做无限轮播，加载的数据个数就要是最大
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    //判断你的每一个数据源对象是否为View对象  Object  数据源
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    //初始化Item上的View,将Object返回对象改成View
    //container相当于ViewPager容器
    @Override
    public View instantiateItem(ViewGroup container, int position) {
        //取出要添加的View
        View view=vList.get(position%(vList.size()));
        //将View添加到ViewPager
        container.addView(view);
        return view;
    }
    //删除super
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //找到要删除的对象，取值在0-5
        View view=vList.get(position%(vList.size()));
        container.removeView(view);
    }


}
