package com.xgn.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xgn.vly.client.commonui.R;
import com.xgn.vly.client.commonui.dialog.EasyAlertDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UiUtil {

    public interface OnButtonActionListener {
        void actionPerformed();
    }

    //该方法用来关闭相应VIEW的硬件加速渲染，因为小米手机在硬件加速开启的情况下，WEBVIEW和动画一起渲染有问题
    public static void closeHardwareAccelerated(View view) {
        if (Build.MANUFACTURER.equals("Xiaomi")) {
            if(null != view){
                view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
        }
    }

    /**
     * 掉此方法输入所要转换的时间输入例如（"2017-06-14 16-09"）返回时间戳
     * @param time
     * @return
     */
    public static Long getLongData(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        Date date;
        String times = null;
        String stf = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            stf = String.valueOf(l);
            // times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Long.parseLong(stf);
    }

    public static void showToast(Context context, String text) {
        showToast(context, text, null);
    }

    /**
     * 显示 Toast 的便捷方法
     *
     * @param text 提示的信息
     * @param desc 详细的描述，如果不想设置该项可以传 null 或者调用 {@link #showToast(Context, String)}
     */
    public static void showToast(Context context, String text, String desc) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView tv_msg = (TextView) view.findViewById(R.id.text_message);
        TextView tv_desc = (TextView) view.findViewById(R.id.text_description);
        if (! TextUtils.isEmpty(desc)) {
            tv_desc.setVisibility(View.VISIBLE);
            tv_desc.setText(desc);
        }
        tv_msg.setText(text);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(Context context, @StringRes int resId) {
        showToast(context, context.getString(resId), null);
    }

    /**
     * 显示 Toast 的便捷方法
     *
     * @param msgResId 提示的信息的资源 id
     * @param descResId 详细的描述资源 id ，如果不想设置该项可以传 null 或者调用 {@link #showToast(Context, int)}
     */
    public static void showToast(Context context,
                                 @StringRes int msgResId, @StringRes int descResId) {
        showToast(context, context.getString(msgResId), context.getString(descResId));
    }

    public static String getText(EditText v) {
        if (null != v) {
            return v.getText().toString();
        }
        return null;
    }

    public static void showConfirmDialog(Context context, String msg,
                                         final OnButtonActionListener buttonAction) {
        final EasyAlertDialog dialog = new EasyAlertDialog(context);
        dialog.setMessageVisible(true);
        dialog.setMessage(TextUtils.isEmpty(msg) ? "" : msg);
        dialog.addPositiveButton(R.string.ok, new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction.actionPerformed();
                dialog.dismiss();
            }
        });
        dialog.addNegativeButton(R.string.cancel, new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showConfirmDialog(Context context, @StringRes int resId,
                                         OnButtonActionListener buttonAction) {
        showConfirmDialog(context, context.getString(resId), buttonAction);
    }

    /**
     * 拨打电话请调用该方法
     */
    public static void showConfirmCallDialog(final Context context, final String phone) {
        if (TextUtils.isEmpty(phone)) {
            return;
        }
        final EasyAlertDialog dialog = new EasyAlertDialog(context);
        dialog.setMessageVisible(true);
        dialog.setMessage("拨打 " + phone + " ？");
        dialog.addPositiveButton(R.string.ok, new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                context.startActivity(intent);
            }
        });
        dialog.addNegativeButton(R.string.cancel, new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
