package com.xgn.vlv.client.base.adapter.viewpager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * NoDestroyItemFragmentPagerAdapter
 *
 * @author Administrator
 */
public class NoDestroyItemFragmentPagerAdapter extends FragmentPagerAdapter {

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
    public NoDestroyItemFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
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

    /**
     * destroyItem
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //不销毁item
        //super.destroyItem(container, position, object);
    }
}
