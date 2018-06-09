package com.tiilii.rtc.ui.practise.mainpage;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wangxuefeng on 2018/6/9.
 */

public class MyPagerAdapter extends PagerAdapter {
    List<View> views;

    public MyPagerAdapter(List<View> list) {

        views = list;
    }

    @Override
    public int getCount() {
        return views.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(views.get(position));

        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

}
