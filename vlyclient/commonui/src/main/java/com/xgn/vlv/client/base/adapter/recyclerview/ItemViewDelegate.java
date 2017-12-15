package com.xgn.vlv.client.base.adapter.recyclerview;


import android.view.View;

/**
 * itemView代表
 */
public interface ItemViewDelegate<T>
{

    /**
     * 获取itemView的布局id
     * @return
     */
    int getItemViewLayoutId();

    /**
     * 是否是该itemView的类型
     * @param item
     * @param position
     * @return
     */
    boolean isForViewType(T item, int position);

    /**
     * 更新数据
     * @param holder
     * @param t
     * @param position
     */
    void convert(ViewHolder holder, T t, int position);

}
