package com.xgn.vlv.client.base.adapter.abslistview;



/**
 * 用于实现多个itemViewType的接口
 */
public interface ItemViewDelegate<T>
{

    /**
     * 获取item的布局id
     * @return
     */
    public abstract int getItemViewLayoutId();

    /**
     * viewType是否匹配
     * @param item item
     * @param position position
     * @return
     */
    public abstract boolean isForViewType(T item, int position);

    /**
     * 更新数据
     * @param holder item的ViewHolder
     * @param t item的数据
     * @param position item的position
     */
    public abstract void convert(ViewHolder holder, T t, int position);


}
