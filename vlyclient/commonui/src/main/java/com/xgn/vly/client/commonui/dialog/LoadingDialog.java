package com.xgn.vly.client.commonui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;
import android.widget.ImageView;

import com.xgn.vly.client.commonui.R;
/**
 * 页面网路请求加载效果
 *
 * @author liuchao
 * created at 2017/7/8 16:06
 */

/**
 *
 */

public class LoadingDialog extends Dialog {
    ImageView img = null;

    public LoadingDialog(Context context, int resourceId, int style) {
        super(context, style);
        WindowManager.LayoutParams Params = getWindow().getAttributes();
        Params.width = WindowManager.LayoutParams.MATCH_PARENT;
        Params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(Params);
    }

    public LoadingDialog(Context context, int style) {
        this(context, -1, style);
    }

    public LoadingDialog(Context context) {
        this(context, R.style.DialogStyle);
        /**设置对话框背景透明*/
        setContentView(R.layout.loading_dialog);
        img = (ImageView) findViewById(R.id.img);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.1f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        AnimationDrawable drawable = (AnimationDrawable) img.getBackground();
        drawable.start();
        setCanceledOnTouchOutside(false);


    }
}
