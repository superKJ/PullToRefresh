package com.xgn.vly.client.commonui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xgn.vly.client.commonui.R;

/**
 * Created by Administrator on 2016/12/20.
 */

public class TableView extends RelativeLayout {

    private ImageView iv_left_icon;
    private ImageView iv_right_icon;
    private ImageView mImageHead;
    private TextView tv_left_title, tv_right_title;
    private View table_root;

    private Drawable mHeadIconDrawable;
    private boolean mShowRightArrowIcon;

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.table_view, this);
        findView();

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TableView);
        setLeftTitle(a.getString(R.styleable.TableView_leftTitle));
        setRightTitle(a.getString(R.styleable.TableView_rightTitle));
        setLeftIcon(a.getResourceId(R.styleable.TableView_leftIcon, 0));
        setRightIcon(a.getResourceId(R.styleable.TableView_rightIcon, 0));
        setLeftTitleColor(a.getColor(R.styleable.TableView_leftTitleColor, -1));
        setRightTitleColor(a.getColor(R.styleable.TableView_rightTitleColor, -1));

        mShowRightArrowIcon = a.getBoolean(R.styleable.TableView_showRightArrowIcon, false);
        if (mShowRightArrowIcon) {
            iv_right_icon.setVisibility(VISIBLE);
        }

        mHeadIconDrawable = a.getDrawable(R.styleable.TableView_headIcon);
        if (null != mHeadIconDrawable) {
            mImageHead.setVisibility(VISIBLE);
            mImageHead.setImageDrawable(mHeadIconDrawable);
            tv_right_title.setVisibility(GONE);
        }

        a.recycle();
    }

    public TextView getRightTextView() {
        return tv_right_title;
    }

    private void findView(){
        iv_left_icon = (ImageView) findViewById(R.id.iv_left_icon);
        iv_right_icon = (ImageView) findViewById(R.id.iv_right_icon);
        tv_left_title = (TextView) findViewById(R.id.tv_left_title);
        tv_right_title = (TextView) findViewById(R.id.tv_right_title);
        mImageHead = (ImageView) findViewById(R.id.image_head);
        table_root = findViewById(R.id.table_root);
    }

    public void setLeftIcon(@DrawableRes int res){
        if(null != iv_left_icon && 0 != res){
            iv_left_icon.setImageResource(res);
            iv_left_icon.setVisibility(View.VISIBLE);
        }
    }

    public void setLeftTitle(String title){
        if(! TextUtils.isEmpty(title) && null != tv_left_title) {
            tv_left_title.setText(title);
            tv_left_title.setVisibility(View.VISIBLE);
        }
    }

    public void setLeftTitleColor(@ColorInt int resId) {
        if (-1 != resId) {
            tv_left_title.setTextColor(resId);
        }
    }

    public void setRightTitleColor(@ColorInt int resId) {
        if (-1 != resId) {
            tv_right_title.setTextColor(resId);
        }
    }

    public void setRightIcon(@DrawableRes int res) {
        if(null != iv_right_icon && mShowRightArrowIcon && 0 != res){
            iv_right_icon.setImageResource(res);
            iv_right_icon.setVisibility(View.VISIBLE);
        }
    }

    public void setRightTitle(String title){
        if(!TextUtils.isEmpty(title) && null != tv_right_title && null == mHeadIconDrawable){
            tv_right_title.setText(title);
            tv_right_title.setVisibility(View.VISIBLE);
        }
    }

    public String getRightTitle() {
        if (null != tv_right_title) {
            return tv_right_title.getText().toString();
        }
        return null;
    }

    public void setOnClickListener(OnClickListener listener){
        if(null != listener && null != table_root){
            table_root.setOnClickListener(listener);
        }
    }


}
