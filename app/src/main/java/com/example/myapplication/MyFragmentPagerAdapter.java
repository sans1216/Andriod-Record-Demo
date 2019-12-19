package com.example.myapplication;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.ui.fragment1;
import com.example.myapplication.ui.fragment2;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"支出","收入"};
    public MyFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }
    public Fragment getItem(int position){
        if (position==1)
            return new fragment1();
        return new fragment2();
    }
    public int getCount(){
        return mTitles.length;
    }

    public CharSequence getPageTitle(int position){
        return mTitles[position];
    }
}
