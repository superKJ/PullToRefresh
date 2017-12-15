package com.xgn.vlv.client.base.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * recyclerview的 多itemType 通用adapter
 */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

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
    protected ItemViewDelegateManager mItemViewDelegateManager;

    /**
     * item点击监听器
     */
    protected OnItemClickListener mOnItemClickListener;

    /**
     * 构造器
     * @param context 上下文
     * @param datas 数据集合
     */
    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    /**
     * 获取itemView类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }

    /**
     * 创建viewHolder回调
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder,holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    /**
     * onCreateViewHolder方法中创建了viewHolder后 会回调此方法
     * @param holder
     * @param itemView
     */
    public void onViewHolderCreated(ViewHolder holder,View itemView){

    }

    /**
     * 更新数据
     * @param holder
     * @param t
     */
    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    /**
     * 是否是启用的
     * @param viewType
     * @return
     */
    protected boolean isEnabled(int viewType) {
        return true;
    }

    /**
     * 设置监听器
     * @param parent
     * @param viewHolder
     * @param viewType
     */
    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder , position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    /**
     * 更新数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    /**
     * 获取item数量
     * @return
     */
    @Override
    public int getItemCount() {
        int itemCount = mDatas.size();
        return itemCount;
    }


    /**
     * 获取数据集合
     * @return
     */
    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 添加itemView代表
     * @param itemViewDelegate
     * @return
     */
    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    /**
     * 添加itemView代表
     * @param viewType
     * @param itemViewDelegate
     * @return
     */
    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    /**
     * 是否使用itemView代表管理器
     * @return
     */
    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    /**
     * item点击监听器
     */
    public interface OnItemClickListener {

        /**
         * item单击回调
         * @param view
         * @param holder
         * @param position
         */
        void onItemClick(View view, RecyclerView.ViewHolder holder,  int position);

        /**
         * item长击回调
         * @param view
         * @param holder
         * @param position
         * @return
         */
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,  int position);
    }

    /**
     * 设置item点击监听器
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 更新数据
     * @param datas
     */
    public void updateDatas(List<T> datas){
        if(mDatas != null){
            mDatas.clear();
            mDatas.addAll(datas);
        }else{
            mDatas = datas;
        }
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param datas
     */
    public void addDatas(List<T> datas){
        if(mDatas != null){
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

}
