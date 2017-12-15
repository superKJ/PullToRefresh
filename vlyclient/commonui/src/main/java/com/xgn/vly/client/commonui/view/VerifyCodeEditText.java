package com.xgn.vly.client.commonui.view;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xgn.vly.client.commonui.R;


/**
 * Created by hua on 2017/1/11.
 */

public class VerifyCodeEditText extends RelativeLayout {

    private static int WAITING_TIME = 60;

    private EditText mEditText;
    private TextView mTextView;
    private Handler mHandler = new Handler();

    private int mTime = WAITING_TIME;

    private Runnable runnable = new Runnable() {
        public void run() {
            if (mTime <= 0) {
                mHandler.removeCallbacks(runnable);           //停止Timer
                mTextView.setEnabled(true);
                mTextView.setText(getResources().getString(R.string.get_identifying_code_time_str));
                mTime = WAITING_TIME;
                return;
            }
            updateTime();
            mTime --;
            mHandler.postDelayed(this, 1000);     //postDelayed(this,1000)方法安排一个Runnable对象到主线程队列中
        }
    };

    public interface OnVerifyCodeClickListener {
        void onVerifyCodeClick();
    }

    private OnVerifyCodeClickListener mListener;

    public VerifyCodeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.verify_edit_layout, this);

        mEditText = (EditText) findViewById(R.id.edit);
        mTextView = (TextView) findViewById(R.id.text_verify_code);
        mTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onVerifyCodeClick();
                }
            }
        });

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VerifyCodeEditText);

        setHint(a.getResourceId(R.styleable.VerifyCodeEditText_hint, -1));
        setHintColor(a.getColor(R.styleable.VerifyCodeEditText_hintColor, -1));

        Drawable drawable = a.getDrawable(R.styleable.VerifyCodeEditText_editViewBackground);
        mEditText.setBackgroundDrawable(drawable);
        drawable = a.getDrawable(R.styleable.VerifyCodeEditText_verifyCodeBackground);
        if (null != drawable) {
            mTextView.setBackgroundDrawable(drawable);
        }
        ColorStateList colorStateList = a.getColorStateList(R.styleable.VerifyCodeEditText_verifyCodeColor);
        if (null != colorStateList) {
            mTextView.setTextColor(colorStateList);
        }
        int editHeight = a.getDimensionPixelSize(R.styleable.VerifyCodeEditText_editHeight, -1);
        if (-1 != editHeight) {
            mEditText.getLayoutParams().height = editHeight;
        }
        int verifyCodeMarginBottom = a.getDimensionPixelSize(R.styleable.VerifyCodeEditText_verifyCodeMarginBottom, -1);
        if (-1 != verifyCodeMarginBottom) {
            ((MarginLayoutParams) mTextView.getLayoutParams()).bottomMargin = verifyCodeMarginBottom;
        }
        boolean textViewCenterVertical = a.getBoolean(R.styleable.VerifyCodeEditText_verifyCodeAlignCenterVertical, false);
        if (textViewCenterVertical) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mTextView.getLayoutParams();
            lp.addRule(RelativeLayout.CENTER_VERTICAL);
        }
        boolean editCenterVertical = a.getBoolean(R.styleable.VerifyCodeEditText_editAlignCenterVertical, false);
        if (editCenterVertical) {
            mEditText.setGravity(Gravity.CENTER_VERTICAL);
        }
        a.recycle();
    }

    public EditText getEditText() {
        return mEditText;
    }

    public void setHint(String hint) {
        if (! TextUtils.isEmpty(hint)) {
            mEditText.setHint(hint);
        }
    }

    public void setHint(@StringRes int resId) {
        if (-1 != resId) {
            mEditText.setHint(resId);
        }
    }

    public void setHintColor(@ColorInt int resId) {
        if (-1 != resId) {
            mEditText.setHintTextColor(resId);
        }
    }

    public void setHintSize(int sp) {
        SpannableString ss = new SpannableString(mEditText.getHint());
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(sp, true);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mEditText.setHint(new SpannedString(ss));
    }

    public void setOnVerifyCodeClickListener(OnVerifyCodeClickListener l) {
        mListener = l;
    }

    private void updateTime() {
        if (null != mTextView) {
            mTextView.setEnabled(false);
            mTextView.setText("剩余"+mTime + "秒");
        }
    }

    public void startWaitUI() {
        if (! mTextView.isEnabled()) {
            return;
        }
        updateTime();
        if (null != mHandler) {
            mHandler.postDelayed(runnable, 1000);
        }
    }

    public void cancelWaitUI() {
        mTime = 0;
    }

    public void setVerifyBtnAble(boolean isAble) {
        if (mTextView != null) {
            mTextView.setEnabled(isAble);
        }
    }

}
