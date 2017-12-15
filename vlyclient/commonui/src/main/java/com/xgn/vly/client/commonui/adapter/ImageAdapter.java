package com.xgn.vly.client.commonui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xgn.vly.client.commonui.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/21.
 */

public class ImageAdapter extends BaseAdapter {
    private static final String TAG = "ImageAdapter";
    //上下文对象
    private Context context;
    //图片数组
    private ArrayList<String> imgs = new ArrayList<String>();
    private LayoutInflater mInflater = null;
    private OnImgItemClickListener mOnImgItemClickListener;
    private int mMaxCount = 5;
    private boolean mCanAdd;

    public interface OnImgItemClickListener {
        public void onAdd();

        public void onTouch(int pos);

        public void onRemove(int pos);
    }

    public void setMax(int count) {
        mMaxCount = count;
    }

    public int getMax() {
        return mMaxCount;
    }

    public ImageAdapter(Context context, OnImgItemClickListener onImgItemClickListener, ArrayList<String> imgs, boolean canAdd) {
        this.context = context;
        //根据context上下文加载布局
        this.mInflater = LayoutInflater.from(context);
        mOnImgItemClickListener = onImgItemClickListener;
        this.imgs = imgs;
        this.mCanAdd = canAdd;
    }

    public void setData(ArrayList<String> imgs) {
        this.imgs = imgs;
        notifyDataSetChanged();
    }

    public int getCount() {
        if (null != imgs) {
            if (mCanAdd) {
                return imgs.size() + 1;//注意此处
            } else {
                return imgs.size();
            }
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != imgs) {
            return imgs.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //创建View方法
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_images, null);
            holder.root = (RelativeLayout) convertView.findViewById(R.id.root);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.close = (ImageView) convertView.findViewById(R.id.close);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (null != imgs) {
            int size = imgs.size();
            if (mCanAdd) {
                if (position < size) {
                    holder.root.setBackgroundResource(R.color.transparent);
                    holder.close.setVisibility(View.VISIBLE);
                    holder.img.setVisibility(View.VISIBLE);
                    holder.close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != mOnImgItemClickListener) {
                                mOnImgItemClickListener.onRemove(position);
                            }
                        }
                    });
                    Glide.with(context).
                            load(imgs.get(position)).
                            placeholder(R.color.image_bg).
                            error(R.color.image_bg).
                            into(holder.img);
                    holder.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != mOnImgItemClickListener) {
                                mOnImgItemClickListener.onTouch(position);
                            }
                        }
                    });
                } else {
                    //最后一个
                    holder.close.setVisibility(View.GONE);
                    holder.img.setVisibility(View.GONE);
                    if((imgs.size() == mMaxCount)){
                        holder.root.setVisibility(View.INVISIBLE);
                        holder.root.setClickable(false);
                    }else {
                        holder.root.setVisibility(View.VISIBLE);
                        holder.root.setClickable(true);
                        holder.root.setBackgroundResource(R.mipmap.set_add_image);
                        holder.root.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //选择图片
                                if (null != mOnImgItemClickListener && position >= imgs.size()) {
                                    if (!(imgs.size() == mMaxCount)) {
                                        mOnImgItemClickListener.onAdd();
                                    } else {
                                        Toast.makeText(context, "最多支持" + mMaxCount + "张图片", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                    }

                }
            } else {
                holder.root.setBackgroundResource(R.color.transparent);
                holder.close.setVisibility(View.GONE);
                holder.img.setVisibility(View.VISIBLE);
                Glide.with(context).
                        load(imgs.get(position)).
                        placeholder(R.color.image_bg).
                        error(R.color.image_bg).
                        into(holder.img);
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mOnImgItemClickListener) {
                            mOnImgItemClickListener.onTouch(position);
                        }
                    }
                });
            }
        }
        return convertView;
    }

    static class ViewHolder {
        public RelativeLayout root;
        public ImageView img;
        public ImageView close;
    }
}
