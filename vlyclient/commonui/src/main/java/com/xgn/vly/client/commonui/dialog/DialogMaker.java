package com.xgn.vly.client.commonui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

/**
 * Created by Administrator on 2016/12/9.
 */

public class DialogMaker {

    private static EasyProgressDialog progressDialog;

    public static EasyProgressDialog showProgressDialog(Context context,
            String message) {
        return showProgressDialog(context, message, true, null);
    }

    public static EasyProgressDialog showProgressDialog(Context context,
            String message, DialogInterface.OnCancelListener listener) {
        return showProgressDialog(context, message, true, listener);
    }

    public static EasyProgressDialog showProgressDialog(Context context,
            String message, boolean cancelable) {
        return showProgressDialog(context, message, cancelable, null);
    }

    public static EasyProgressDialog showProgressDialog(Context context,
            String message, boolean canCancelable, DialogInterface.OnCancelListener listener) {

        if (progressDialog == null) {
            progressDialog = new EasyProgressDialog(context, message);
        } else if (progressDialog.getContext() != context) {
            // maybe existing dialog is running in a destroyed activity cotext
            // we should recreate one
            dismissProgressDialog();
            progressDialog = new EasyProgressDialog(context, message);
        }

        progressDialog.setCancelable(canCancelable);
        progressDialog.setOnCancelListener(listener);

        progressDialog.show();

        return progressDialog;
    }

    public static void dismissProgressDialog() {
        if (null == progressDialog) {
            return;
        }
        if (progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
                progressDialog = null;
            } catch (Exception e) {
                // maybe we catch IllegalArgumentException here.
            }
        }
    }

    public static void setMessage(String message) {
        if (null != progressDialog && progressDialog.isShowing()
                && !TextUtils.isEmpty(message)) {
            progressDialog.setMessage(message);
        }
    }
}

