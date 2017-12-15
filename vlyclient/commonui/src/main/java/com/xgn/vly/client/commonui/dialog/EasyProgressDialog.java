package com.xgn.vly.client.commonui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.view.WindowManager.LayoutParams;

import com.xgn.vly.client.commonui.R;

/**
 * Created by Administrator on 2016/12/9.
 * 一个半透明窗口,包含一个Progressbar 和 Message部分. 其中Message部分可选. 可单独使用,也可以使用
 */


public class EasyProgressDialog extends Dialog {
    private Context mContext;
    private String mMessage;
    private int mLayoutId;

    public EasyProgressDialog(Context context, int style, int layout) {
        super(context, style);
        mContext = context;
        WindowManager.LayoutParams Params = getWindow().getAttributes();
        Params.width = LayoutParams.FILL_PARENT;
        Params.height = LayoutParams.FILL_PARENT;
        getWindow().setAttributes(Params);
        mLayoutId = layout;
    }

    public EasyProgressDialog(Context context, int layout, String msg) {
        this(context, R.style.DialogStyle, layout);
        setMessage(msg);
    }

    public EasyProgressDialog(Context context, String msg) {
        this(context, R.style.DialogStyle, R.layout.layout_easy_progress_dialog);
        setMessage(msg);
    }

    public EasyProgressDialog(Context context) {
        this(context, R.style.DialogStyle, R.layout.layout_easy_progress_dialog);
    }

    public void setMessage(String msg) {
        mMessage = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLayoutId);
        if (!TextUtils.isEmpty(mMessage)) {
            TextView message = (TextView) findViewById(R.id.easy_progress_dialog_message);
            message.setVisibility(View.VISIBLE);
            message.setText(mMessage);
        }
    }

    @Override
    public void show() {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            if (activity.isFinishing() || (Build.VERSION.SDK_INT >= 17 && activity.isDestroyed())) {
                return;
            }
        }
        super.show();
    }
}
