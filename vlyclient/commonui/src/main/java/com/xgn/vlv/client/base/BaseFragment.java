package com.xgn.vlv.client.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2016/12/9.
 */

public abstract class BaseFragment extends Fragment {
    protected static final String Tag = "BaseFragment";
    protected boolean loaded = false;
    protected abstract void onInit();

    public void onStay() {
        Log.e(Tag,"BaseFragment == onStay");
        if (!loaded ) {
            loaded = true;
            onInit();
        }
    }

    /**
     * 显示或隐藏输入法
     * @param isShow
     */
    protected void showKeyboard(boolean isShow) {
        if(getActivity()!= null){
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (isShow) {
                if (getActivity().getCurrentFocus() == null) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                } else {
                    imm.showSoftInput(getActivity().getCurrentFocus(), 0);
                }
            } else {
                if (getActivity().getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

