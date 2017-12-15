package com.xgn.vly.client.commonui.view;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgn.vly.client.commonui.R;

/**
 * Created by Administrator on 2017/1/3.
 */

public class TableViewUnderline extends FrameLayout{

    private View mViewUnderLine;
    private ImageView mIvRightIcon;
    private ImageView mIvLeftIcon;
    private EditText mEtLeft;
    private TextView mTvIdentify;
    private Handler mHandler = new Handler();
    public TableViewUnderline(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.table_view_underline, this);
        findView();
    }

    public EditText getEdit(){
        return mEtLeft;
    }

    private static int WAITING_TIME = 60;
    private int time = WAITING_TIME;

    private Runnable runnable = new Runnable() {

        public void run() {
            if (time <= 0) {
                mHandler.removeCallbacks(runnable);           //停止Timer
                mTvIdentify.setEnabled(true);
                mTvIdentify.setText(getResources().getString(R.string.get_identifying_code_str));
                mTvIdentify.setTextColor(getResources().getColor(R.color.white));
                mTvIdentify.setBackgroundResource(R.drawable.selector_btn_blue);
                time = WAITING_TIME;
                return;
            }
            updateTime();
            time--;
            mHandler.postDelayed(this, 1000);     //postDelayed(this,1000)方法安排一个Runnable对象到主线程队列中
        }
    };

    private void updateTime() {
        if (null != mTvIdentify) {
            mTvIdentify.setVisibility(View.VISIBLE);
            mTvIdentify.setEnabled(false);
            mTvIdentify.setTextColor(getResources().getColor(R.color.color_3e4445));
            mTvIdentify.setBackgroundResource(R.drawable.selector_btn_grey_shape);
            mTvIdentify.setText(getResources().getString(R.string.get_identifying_code_time_str) + time);
            if(null != mIvRightIcon){
                mIvRightIcon.setVisibility(View.GONE);
            }
        }
    }

    public void setIdentifyShow(boolean show){
        if(null != mTvIdentify && show){
            mTvIdentify.setVisibility(View.VISIBLE);
        }else{
            mTvIdentify.setVisibility(View.GONE);
        }
    }

    public void startWaitUI() {
        if (!mTvIdentify.isEnabled()) {
            return;
        }
        updateTime();
        if (null != mHandler) {
            mHandler.postDelayed(runnable, 1000);         // 开始Timer
        }
    }

    public void setRightOnClickListener(OnClickListener listener){
        if(null != listener && null != mIvRightIcon){
            mIvRightIcon.setOnClickListener(listener);
        }
    }

    public void setRightIcon(int res){
        if(null != mIvRightIcon){
            mIvRightIcon.setImageResource(res);
            mIvRightIcon.setVisibility(View.VISIBLE);
        }
        setIdentifyShow(false);
    }

    public void setLeftHint(String hint){
        if(null != mEtLeft && !TextUtils.isEmpty(hint)){
            mEtLeft.setHint(hint);
            mEtLeft.setVisibility(View.VISIBLE);
        }
    }

    public void setLeftHint(@StringRes int stringResId) {
        setLeftHint(getContext().getString(stringResId));
    }

    public void setLeftIcon(int res){
         if(null != mIvLeftIcon){
             mIvLeftIcon.setImageResource(res);
             mIvLeftIcon.setVisibility(View.VISIBLE);
         }
    }

    public void showUnderLine(boolean show){
        if(null != mViewUnderLine && show){
            mViewUnderLine.setVisibility(View.VISIBLE);
        }else{
            mViewUnderLine.setVisibility(View.GONE);
        }
    }

    private void findView(){
        mViewUnderLine = (View) findViewById(R.id.under_line);
        mIvRightIcon = (ImageView) findViewById(R.id.iv_right_icon);
        mIvLeftIcon = (ImageView) findViewById(R.id.iv_left);
        mEtLeft = (EditText) findViewById(R.id.et_left);
        mTvIdentify = (TextView) findViewById(R.id.tv_identify);
    }
}
