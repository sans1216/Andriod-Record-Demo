package com.example.myapplication;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class MyViewPagerAdapter1 extends PagerAdapter {
    private List<View> mListViews;
    public MyViewPagerAdapter1(List<View> mListViews){
        this.mListViews = mListViews;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView(mListViews.get(position));
    }

    public Object instantiateItem(ViewGroup container , int position){
        container.addView(mListViews.get(position));
        return mListViews.get(position);
    }
    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return arg0==arg1;
    }
}
