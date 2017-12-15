package com.xgn.vlv.client.base.adapter.abslistview;

import android.content.Context;



import java.util.List;

/**
 * 通用Adapter
 * @param <T> 数据类型
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>
{

    /**
     * 构造器
     * @param context 上下文
     * @param layoutId 布局id
     * @param datas 数据集合
     */
    public CommonAdapter(Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    /**
     * 更新item数据
     * @param viewHolder item的ViewHolder
     * @param item item
     * @param position item的position
     */
    protected abstract void convert(ViewHolder viewHolder, T item, int position);

}
