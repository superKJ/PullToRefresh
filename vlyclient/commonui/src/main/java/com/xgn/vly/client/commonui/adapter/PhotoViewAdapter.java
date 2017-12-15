package com.xgn.vly.client.commonui.adapter;

/**
 * Created by Administrator on 2016/12/22.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import com.xgn.utils.ImageViewUtil;
import com.xgn.utils.UiUtil;
import com.xgn.vly.client.commonui.R;
import java.util.List;

public class PhotoViewAdapter extends PagerAdapter {

    private DisplayImageOptions options;
    private List<String> mlist;
    private Activity mActivity;
    private RotateAnimation anim;

    public PhotoViewAdapter(List<String> list, Activity activity) {
        options = ImageViewUtil.getDefaultDisplayImageOptions(R.color.black);
        mlist = list;
        mActivity = activity;
        anim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(1000);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setInterpolator(new LinearInterpolator());
    }


    @Override
    public int getCount() {
        if (null != mlist && mlist.size() > 0)
            return mlist.size();
        return 0;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_photo_view, null);
        final ImageView loading = (ImageView) itemView.findViewById(R.id.iv_loading);
        UiUtil.closeHardwareAccelerated(loading);
        if (null != itemView) {
            PhotoView photoView = (PhotoView) itemView.findViewById(R.id.pv_view);
            photoView.setScaleType(ImageView.ScaleType.CENTER);

            if (null != loading && null != anim) {
                loading.startAnimation(anim);
                loading.setVisibility(View.VISIBLE);
            }



            if(null != mlist && mlist.size() > position && null != mlist.get(position)) {
                ImageLoader.getInstance().displayImage(mlist.get(position), photoView, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        if (null != loading && null != anim) {
                            loading.startAnimation(anim);
                            loading.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        loading.clearAnimation();
                        loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        PhotoView photoView = (PhotoView) view;
                        if (loadedImage.getWidth() * 2 < loadedImage.getHeight()) {//获取image的尺寸动态设置ScaleType
                            photoView.setScaleType(ImageView.ScaleType.CENTER);
                        } else {
                            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        }
                        loading.clearAnimation();
                        loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        loading.clearAnimation();
                        loading.setVisibility(View.GONE);
                    }
                });
            }
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    loading.clearAnimation();
                    loading.setVisibility(View.GONE);
                    if (null != mActivity) {
                        mActivity.finish();
                    }
                }
            });
            // Now just add PhotoView to ViewPager and return it
            container.addView(itemView);
        }
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View itemView = (View) object;
        if (null != itemView) {
            ImageView loading = (ImageView) itemView.findViewById(R.id.iv_loading);
            loading.clearAnimation();
        }
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
