package com.xgn.vly.client.commonui.view;

import com.xgn.vly.client.commonui.R;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/12/14.
 * Modify by BoQin on 2017-05-08
 */

public class ToolbarTool {
    private Activity mActivity;
    protected ImageView mTitleIV;
    protected ImageView mHeadBackIV;
    protected ImageView mHeadTextBackIV;
    protected TextView mTitleTV;
    protected TextView mHeadBackTV;
    protected ImageView mMoreDoIV;
    protected TextView mTvHeadDo;
    protected View mHeadLine;
    private View mTitleArrow;
    private View mHeadView;

    public View getHeadView(){
        return mHeadView;
    }

    public interface OnBackClickListener {
        void onBackBtnPressed();
    }

    public ToolbarTool (Activity context){
        mActivity = context;
    }

    public void destoryToolBar(ViewGroup rootView){
        if(null != rootView && null != mHeadView){
            rootView.removeView(mHeadView);
        }
    }

    public void setBackgroundColor(int res){
        if(null != mHeadView){
            mHeadView.setBackgroundResource(res);
        }
    }

    public void initToolbar(final OnBackClickListener listener,ViewGroup rootView){
        if(mActivity == null){
            return;
        }

        if(null == rootView){
            return;
        }
        if(null == mHeadView){
            mHeadView = mActivity.getLayoutInflater().inflate(R.layout.merge_activity_head,null);
        }else{
            rootView.removeView(mHeadView);
        }

        if ((RelativeLayout)mHeadView.findViewById(R.id.rl_head_activity) != null){
            mHeadBackIV = (ImageView) mHeadView.findViewById(R.id.iv_head_back);
            mHeadTextBackIV = (ImageView) mHeadView.findViewById(R.id.iv_head_back_after_tv);
            mTitleTV = (TextView) mHeadView.findViewById(R.id.tv_head_title);
            mMoreDoIV = (ImageView) mHeadView.findViewById(R.id.iv_head_do);
            mTvHeadDo = (TextView) mHeadView.findViewById(R.id.tv_head_do);
            mTitleIV = (ImageView) mHeadView.findViewById(R.id.iv_head_title);
            mHeadBackTV = (TextView) mHeadView.findViewById(R.id.tv_head_back);
            mHeadLine = mHeadView.findViewById(R.id.head_line);
            mTitleArrow = mHeadView.findViewById(R.id.head_arrow);
            if(mTvHeadDo != null){
                mTvHeadDo.setVisibility(View.GONE);
            }
            View backView = mHeadView.findViewById(R.id.rl_head_back);
            if(backView != null){
                backView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(null != listener){
                            listener.onBackBtnPressed();
                        }
                    }
                });
            }
        }
        ViewGroup.LayoutParams layoutParams = new  ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.addView(mHeadView,layoutParams);
    }

    public void setBackText(String str){
        if(null != mHeadBackTV && !TextUtils.isEmpty(str)){
            mHeadBackTV.setText(str);
            mHeadBackTV.setVisibility(View.VISIBLE);
        }
    }

    public void setBackTextImage(@DrawableRes int resId){
        if(null != mHeadTextBackIV){
            mHeadTextBackIV.setImageResource(resId);
            mHeadTextBackIV.setVisibility(View.VISIBLE);
        }
    }

    public void hideRightText() {
        mHeadBackTV.setVisibility(View.GONE);
    }

    public void hideLine(boolean hide){
        if(null != mHeadLine){
            if(hide){
                mHeadLine.setVisibility(View.GONE);
            }else{
                mHeadLine.setVisibility(View.VISIBLE);
            }
        }
    }

    public void showBackView(){
        if(mActivity == null){
            return;
        }
        if(mHeadView == null){
            return;
        }
        View backView = mHeadView.findViewById(R.id.rl_head_back);
        if(backView != null){
            backView.setVisibility(View.VISIBLE);
        }
    }

    public void hideBackView(){
        if(mActivity == null){
            return;
        }
        if(mHeadView == null){
            return;
        }
        View backView = mHeadView.findViewById(R.id.rl_head_back);
        if(backView != null){
            backView.setVisibility(View.GONE);
        }
    }

    public void setBackIcon(int resid) {
        if (mHeadBackIV != null) {
            mHeadBackIV.setVisibility(View.VISIBLE);
            mHeadBackIV.setBackgroundResource(resid);
        }
    }
//
//    public void showBackIcon(int visiable) {
//        if (mHeadBackIV != null) {
//            mHeadBackIV.setVisibility(View.GONE);
//        }
//    }

    public void setRightIconListener(View.OnClickListener listener) {
        if (mMoreDoIV != null) {
            mMoreDoIV.setOnClickListener(listener);
        }
    }

    public void setRightTextListener(View.OnClickListener listener) {
        if (mTvHeadDo != null) {
            mTvHeadDo.setOnClickListener(listener);
        }
    }

    public void setRightIcon(int resid) {
        if (mMoreDoIV != null) {
            mMoreDoIV.setImageResource(resid);
            mMoreDoIV.setVisibility(View.VISIBLE);
        }
        if (mTvHeadDo != null){
            mTvHeadDo.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏右边的icon
     */
    public void hideRightIcon() {
        if (mMoreDoIV != null) {
            mMoreDoIV.setVisibility(View.GONE);
        }
    }

    public void setRightText(String text){
        if (mTvHeadDo != null){
            mTvHeadDo.setText(text);
            mTvHeadDo.setVisibility(View.VISIBLE);
        }
        if (mMoreDoIV != null){
            mMoreDoIV.setVisibility(View.GONE);
        }
    }

    public void hideRight(){
        if (mTvHeadDo != null){
            mTvHeadDo.setVisibility(View.GONE);
        }
    }

    public void setRightTextColor(int color){
        if (mTvHeadDo != null){
            if(-1 != color){
                mTvHeadDo.setTextColor(color);
                mTvHeadDo.setVisibility(View.VISIBLE);
            }
        }
        if (mMoreDoIV != null){
            mMoreDoIV.setVisibility(View.GONE);
        }
    }

    public void setRightTextColor(ColorStateList colorStateList) {
        if (mTvHeadDo != null) {
            mTvHeadDo.setTextColor(colorStateList);
            mTvHeadDo.setVisibility(View.VISIBLE);
        }
        if (mMoreDoIV != null){
            mMoreDoIV.setVisibility(View.GONE);
        }
    }

    public void setTitle(String title){
        if (mTitleTV != null){
            mTitleTV.setText(title);
            mTitleTV.setVisibility(View.VISIBLE);
        }
        if(mTitleIV != null){
            mTitleIV.setVisibility(View.GONE);
        }
    }

    public void setTitleIcon(int res){
        if(mTitleIV != null){
            mTitleIV.setImageResource(res);
            mTitleIV.setVisibility(View.VISIBLE);
        }
        if (mTitleTV != null){
            mTitleTV.setVisibility(View.GONE);
        }
    }

    public void setTitleListener(View.OnClickListener listener){
        if(mTitleTV != null){
            mTitleTV.setOnClickListener(listener);
        }

    }

    public void showTitleArrow(boolean isShow){
        if (mTitleArrow!=null) {
            mTitleArrow.clearAnimation();
            mTitleArrow.setVisibility(isShow?View.VISIBLE:View.INVISIBLE);
        }
    }

    public void isArrowPackUp(boolean isPackUp){
        Animation animation = new RotateAnimation(isPackUp?180:0, isPackUp?360:180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        mTitleArrow.startAnimation(animation);
    }
}
