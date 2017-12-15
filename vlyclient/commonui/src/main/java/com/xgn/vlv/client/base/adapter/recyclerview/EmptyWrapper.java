package com.xgn.vlv.client.base.adapter.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * 支持空布局的 adapter 包装器
 */
public class EmptyWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;

    private RecyclerView.Adapter mInnerAdapter;
    private View mEmptyView;
    private int mEmptyLayoutId;


    public EmptyWrapper(RecyclerView.Adapter adapter)
    {
        mInnerAdapter = adapter;
    }

    private boolean isEmpty()
    {
        return (mEmptyView != null || mEmptyLayoutId != 0) && mInnerAdapter.getItemCount() == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (isEmpty())
        {
            ViewHolder holder;
            if (mEmptyView != null)
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), mEmptyView);
            } else
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    /**
     * 当adapter被附加到recyclerview时回调
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback()
        {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position)
            {
                if (isEmpty())
                {
                    return gridLayoutManager.getSpanCount();
                }
                if (oldLookup != null)
                {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });


    }

    /**
     * 当适配器创建的view（即列表项view）被附加到窗口（即滑动到当前窗口界面）就会被调用
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
    {
        mInnerAdapter.onViewAttachedToWindow(holder);
        if (isEmpty())
        {
            WrapperUtils.setFullSpan(holder);
        }
    }


    @Override
    public int getItemViewType(int position)
    {
        if (isEmpty())
        {
            return ITEM_TYPE_EMPTY;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (isEmpty())
        {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount()
    {
        if (isEmpty()) return 1;
        return mInnerAdapter.getItemCount();
    }



    public void setEmptyView(View emptyView)
    {
        mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId)
    {
        mEmptyLayoutId = layoutId;
    }

}
