package com.xgn.vlv.client.base.adapter.abslistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.List;

/**
 * 支持多个ItemType的通用Adapter
 *
 * @param <T>
 */
public class MultiItemTypeAdapter<T> extends BaseAdapter {
    /**
     * 上下文
     */
    protected Context mContext;

    /**
     * 数据集合
     */
    protected List<T> mDatas;

    /**
     * itemView代表管理器
     */
    private ItemViewDelegateManager mItemViewDelegateManager;


    /**
     * 构造器
     *
     * @param context 上下文
     * @param datas   数据集合
     */
    public MultiItemTypeAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    /**
     * 添加itemView代表
     *
     * @param itemViewDelegate
     * @return
     */
    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    /**
     * 是否使用itemView代表管理器
     *
     * @return
     */
    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    /**
     * 获取viewType数量
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        if (useItemViewDelegateManager())
            return mItemViewDelegateManager.getItemViewDelegateCount();
        return super.getViewTypeCount();
    }

    /**
     * 获取itemViewType
     *
     * @param position item的position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (useItemViewDelegateManager()) {
            int viewType = mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
            return viewType;
        }
        return super.getItemViewType(position);
    }

    /**
     * 获取view
     *
     * @param position    item的position
     * @param convertView convertView
     * @param parent      parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int layoutId = mItemViewDelegateManager.getItemViewLayoutId(mDatas.get(position), position);

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
                layoutId, position);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    /**
     * 更新item数据
     *
     * @param viewHolder item的ViewHolder
     * @param item       item
     * @param position   item的position
     */
    protected void convert(ViewHolder viewHolder, T item, int position) {
        mItemViewDelegateManager.convert(viewHolder, item, position);
    }

    /**
     * 获取数量
     *
     * @return
     */
    @Override
    public int getCount() {
        return mDatas.size();
    }

    /**
     * 获取item
     *
     * @param position
     * @return
     */
    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    /**
     * 获取itemId
     *
     * @param position item的position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 更新数据
     *
     * @param datas
     */
    public void updateDatas(List<T> datas) {
        if (mDatas != null) {
            mDatas.clear();
            mDatas.addAll(datas);
        } else {
            mDatas = datas;
        }
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param datas
     */
    public void addDatas(List<T> datas) {
        if (mDatas != null) {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

}
