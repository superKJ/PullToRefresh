package com.xgn.vly.client.commonui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xgn.vly.client.commonui.R;

/**
 * Created by Administrator on 2017/1/13.
 */

public class EditTableView extends RelativeLayout {
    private TextView tv_left_title;
    private EditText et_right;
    private View table_root;

    public EditTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.edit_table_view, this);
        findView();
    }

    public EditText getEdit(){
        return et_right;
    }

    public String getEditText() {
        if (null != et_right && null != et_right.getText()) {
            return et_right.getText().toString();
        }
        return null;
    }

    public void setRightEditMaxLength(int length){
        InputFilter[] filters = {new InputFilter.LengthFilter(length)};
        if(null != et_right){
            et_right.setFilters(filters);
        }
    }

    private void findView(){
        tv_left_title = (TextView) findViewById(R.id.tv_left_title);
        et_right = (EditText) findViewById(R.id.et_right);
        table_root = findViewById(R.id.table_root);
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
            et_right.setTextColor(resId);
        }
    }

    public void setRightTitle(String title){
        if(!TextUtils.isEmpty(title) && null != et_right){
            et_right.setText(title);
            et_right.setVisibility(View.VISIBLE);
        }
    }

    public void setRightHint(String title){
        if(!TextUtils.isEmpty(title) && null != et_right){
            et_right.setHint(title);
            et_right.setVisibility(View.VISIBLE);
        }
    }

    public void setOnClickListener(OnClickListener listener){
        if(null != listener && null != table_root){
            table_root.setOnClickListener(listener);
        }
    }
}
