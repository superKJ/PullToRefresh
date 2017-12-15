package com.xgn.utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xgn.vly.client.commonui.R;

public class ImageViewUtil {

    public static final int FOUR_RADIUS_PIXELS = 4;
    public static final int CIRCLE_RADIUS_PIXELS = 200;

    public static DisplayImageOptions getDefaultGeekDisplayImageOptions(int resId){
        return new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(resId)
                .showImageOnFail(resId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true).build();
    }

    public static DisplayImageOptions getDefaultDisplayImageOptions(){

        return new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.drawable.shape_grey_bg)
                .showImageForEmptyUri(R.drawable.shape_grey_bg)
                .showImageOnFail(R.drawable.shape_grey_bg)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true).build();
    }

    public static DisplayImageOptions getDefaultDisplayImageOptions(int resId){

        return new DisplayImageOptions.Builder()
                .showImageOnLoading(resId)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(resId)
                .showImageOnFail(resId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true).build();
    }

    /**
     *
     * @param cornerRadiusPixels
     * @param resId 异常时用什么图
     * @return
     */
    public static DisplayImageOptions getDefaultDisplayImageOptions(int cornerRadiusPixels,int resId){

        return new DisplayImageOptions.Builder()
                .showImageOnLoading(resId)
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageForEmptyUri(resId)
                .showImageOnFail(resId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(cornerRadiusPixels)).build();
    }


    public static DisplayImageOptions getDefaultDisplayImageOptions(int cornerRadiusPixels,int resId,int marginPixels){

        return new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(resId)
                .showImageForEmptyUri(resId)
                .showImageOnFail(resId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(cornerRadiusPixels,marginPixels)).build();
    }
}
