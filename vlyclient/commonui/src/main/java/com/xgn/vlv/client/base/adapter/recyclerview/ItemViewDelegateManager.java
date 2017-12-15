package com.xgn.vlv.client.base.adapter.recyclerview;

import android.support.v4.util.SparseArrayCompat;


/**
 * itemView代表管理器
 */
public class ItemViewDelegateManager<T>
{

    /**
     * 保存itemView代表的集合
     */
    SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat();


    /**
     * 获取itemView代表数量
     * @return
     */
    public int getItemViewDelegateCount()
    {
        return delegates.size();
    }


    /**
     * 添加itemView代表
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate)
    {
        int viewType = delegates.size();
        if (delegate != null)
        {
            delegates.put(viewType, delegate);
            viewType++;
        }
        return this;
    }

    /**
     * 添加itemView代表
     * @param viewType
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate)
    {
        if (delegates.get(viewType) != null)
        {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    /**
     * 移除itemView代表
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate)
    {
        if (delegate == null)
        {
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0)
        {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * 移除itemView代表
     * @param itemType
     * @return
     */
    public ItemViewDelegateManager<T> removeDelegate(int itemType)
    {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0)
        {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * 获取itemView类型
     * @param item
     * @param position
     * @return
     */
    public int getItemViewType(T item, int position)
    {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--)
        {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType( item, position))
            {
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    /**
     * 更新数据
     * @param holder
     * @param item
     * @param position
     */
    public void convert(ViewHolder holder, T item, int position)
    {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++)
        {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);

            if (delegate.isForViewType( item, position))
            {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }

    /**
     * 获取itemView代表
     * @param viewType
     * @return
     */
    public ItemViewDelegate getItemViewDelegate(int viewType)
    {
        return delegates.get(viewType);
    }

    /**
     * 获取itemView布局id
     * @param viewType
     * @return
     */
    public int getItemViewLayoutId(int viewType)
    {
        return getItemViewDelegate(viewType).getItemViewLayoutId();
    }

    /**
     * 获取itemView的类型
     * @param itemViewDelegate
     * @return
     */
    public int getItemViewType(ItemViewDelegate itemViewDelegate)
    {
        return delegates.indexOfValue(itemViewDelegate);
    }
}
