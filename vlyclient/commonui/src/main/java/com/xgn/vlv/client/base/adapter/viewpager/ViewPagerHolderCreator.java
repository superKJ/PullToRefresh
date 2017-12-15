package com.xgn.vlv.client.base.adapter.viewpager;

/**
 * ViewPagerHolderCreator
 */
public interface ViewPagerHolderCreator<VH extends ViewPagerHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}
