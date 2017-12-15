package com.xgn.vly.client.commonui.view;


import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xgn.vly.client.commonui.R;


/**
 * Created by hua on 2017/1/11.
 */

public class CommonEditArea extends RelativeLayout {
    private int DEFAULT_MAX_LENGTH = 400;
    private int mMaxLength = DEFAULT_MAX_LENGTH;
    private EditText mEditText;
    private TextView mTextLength;

    public CommonEditArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.edit_area_layout, this);
        mEditText = (EditText) findViewById(R.id.edit);
        mTextLength = (TextView) findViewById(R.id.text_max_length);
        initView();
    }

    private void initView() {
        mEditText.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                int num = temp.length();
                mTextLength.setText(getNum(num));
            }
        });
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
        mTextLength.setText(getNum(0));
    }

    public void setHint(String hint) {
        if (!TextUtils.isEmpty(hint)) {
            if (null != mEditText) {
                mEditText.setHint(hint);
            }
        }
    }

    private String getNum(int num) {
        StringBuilder builder = new StringBuilder();
        builder.append(num).append("/").append(mMaxLength);
        return builder.toString();
    }

    public void setMaxLength(int max) {
        mMaxLength = max;
        if (null != mEditText) {
            mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxLength)});
        }
    }

    public String getText() {
        if (null != mEditText) {
            return mEditText.getText().toString();
        }
        return null;
    }

    public int getMaxLength() {
        return mMaxLength;
    }
}
