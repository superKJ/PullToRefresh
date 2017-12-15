package com.xgn.vlv.client.base.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

/**
 * recyclerview的 单itemType 通用adapter
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>
{
    /**
     * 上下文
     */
    protected Context mContext;

    /**
     * 布局id
     */
    protected int mLayoutId;


    /**
     * 布局填充器
     */
    protected LayoutInflater mInflater;

    /**
     * 构造器
     * @param context 上下文
     * @param layoutId 布局id
     * @param datas 数据集合
     */
    public CommonAdapter(final Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        //添加唯一的itemView代表
        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
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
     * 更新数据
     * @param holder
     * @param t
     * @param position
     */
    protected abstract void convert(ViewHolder holder, T t, int position);


}
