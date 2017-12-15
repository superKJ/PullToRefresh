package com.xgn.vly.client.commonui.scrollpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/12/9.
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final int TYPE_RES = 0;
    private static final int TYPE_STRING = 1;
    private int[] titles;
    private String[] titleStrs;
    protected List<Fragment> fragments;
    private int type;
    private Context mContext;

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, Context context) {
        super(fm);
        fragments = fragmentList;
        mContext = context;
    }

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, int[] titles, Context context) {
        super(fm);
        fragments = fragmentList;
        this.titles = titles;
        type = TYPE_RES;
        mContext = context;
    }

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles, Context context) {
        super(fm);
        fragments = fragmentList;
        this.titleStrs = titles;
        type = TYPE_STRING;
        mContext = context;
    }


    @Override
    public Fragment getItem(int arg0) {
        if (fragments == null || fragments.size() < arg0) {
            return null;
        } else {
            return fragments.get(arg0);
        }
    }

    @Override
    public int getCount() {
        if (fragments != null) {
            return fragments.size();

        } else {
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (type == TYPE_RES) {
            if (fragments != null && titles != null
                    && titles.length > position) {
                int resId = titles[position];
                return (resId != 0 && null != mContext) ? mContext.getText(resId) : "";
            }
        } else if (type == TYPE_STRING) {
            if (fragments != null && titleStrs != null
                    && titleStrs.length > position) {
                return titleStrs[position];
            }
        }
        return "";
    }

    public void onPageSelected(int position) {
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}



