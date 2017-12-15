package com.xgn.vlv.client.base.adapter.abslistview;

import android.support.v4.util.SparseArrayCompat;


/**
 * itemView代表管理器，用于支持多itemViewType
 */
public class ItemViewDelegateManager<T> {
    /**
     * 保存所有itemView代表
     */
    SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat();

    /**
     * 获取itemView代表数量
     *
     * @return
     */
    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    /**
     * 添加代表
     *
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
        int viewType = delegates.size();
        if (delegate != null) {
            delegates.put(viewType, delegate);
            viewType++;
        }
        return this;
    }

    /**
     * 添加代表
     *
     * @param viewType 代表的viewType
     * @param delegate 代表
     * @return
     */
    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate) {
        if (delegates.get(viewType) != null) {
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
     * 移除代表
     *
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * 移除代表
     *
     * @param itemType 代表的itemType
     * @return
     */
    public ItemViewDelegateManager<T> removeDelegate(int itemType) {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * 获取itemViewType
     *
     * @param item     item
     * @param position position
     * @return
     */
    public int getItemViewType(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }


    /**
     * 更新item数据
     *
     * @param holder   item的ViewHolder
     * @param item     item
     * @param position item的position
     */
    public void convert(ViewHolder holder, T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);

            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }


    /**
     * 获取item的布局id
     * @param viewType item的viewType
     * @return
     */
    public int getItemViewLayoutId(int viewType) {
        return delegates.get(viewType).getItemViewLayoutId();
    }

    /**
     * 获取item的viewType
     * @param itemViewDelegate itemView代表
     * @return
     */
    public int getItemViewType(ItemViewDelegate itemViewDelegate) {
        return delegates.indexOfValue(itemViewDelegate);
    }

    /**
     * 获取itemView的布局id
     * @param item item
     * @param position position
     * @return
     */
    public int getItemViewLayoutId(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegate.getItemViewLayoutId();
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }
}
