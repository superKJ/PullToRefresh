package com.xgn.vlv.client.base.adapter.viewpager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * BaseFragmentPagerAdapter
 *
 * @author Administrator
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    /**
     * fragment集合
     */
    private List<Fragment> list;

    /**
     * 构造器
     *
     * @param fm   fragment管理器
     * @param list fragment集合
     */
    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    /**
     * 获取item
     */
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    /**
     * 获取item数量
     */
    @Override
    public int getCount() {
        return list.size();
    }

}
