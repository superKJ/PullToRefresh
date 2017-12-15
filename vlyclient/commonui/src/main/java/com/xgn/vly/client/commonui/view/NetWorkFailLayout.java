package com.xgn.vly.client.commonui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xgn.vly.client.commonui.R;

/**
 * Created by XG on 2017/11/2.
 * author by liuchao
 */

public class NetWorkFailLayout extends FrameLayout {
    private Context context;
    private View emptyView;
    private View exceptionView;
    private View systemFailView;
    private ViewGroup rootView;

    public NetWorkFailLayout(Context context) {
        this(context, null);
        this.context = context;
        init();
    }

    public NetWorkFailLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View v = LayoutInflater.from(context).inflate(R.layout.net_work_fail_layout, this);
        emptyView = (View) v.findViewById(R.id.layout_empty);
        exceptionView = (View) v.findViewById(R.id.layout_exception);
        systemFailView = (View) v.findViewById(R.id.system_service_busy);
        rootView = (ViewGroup) v.findViewById(R.id.root_view);
    }

    /**
     * 显示无数据页面
     */
    public void showEmptyView(String msg, PageExceptionReloadInterface pageExceptionReloadInterface) {
        this.setVisibility(View.VISIBLE);
        for (int i = 0; i < rootView.getChildCount(); i++) {
            View childView = rootView.getChildAt(i);
            childView.setVisibility(View.GONE);
        }
        if (msg != null) {
            TextView tv = (TextView) emptyView.findViewById(R.id.text_empty);
            tv.setText(msg);
        }
        emptyView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示网络异常页面页面
     */
    public void showNetExceptionView(final PageExceptionReloadInterface pageExceptionReloadInterface) {
        this.setVisibility(View.VISIBLE);
        for (int i = 0; i < rootView.getChildCount(); i++) {
            View childView = rootView.getChildAt(i);
            childView.setVisibility(View.GONE);
        }
        exceptionView.setVisibility(View.VISIBLE);
        Button reloadBt = (Button) exceptionView.findViewById(R.id.error_net_exception_refresh_bt);
        reloadBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageExceptionReloadInterface != null) {
                    pageExceptionReloadInterface.reload();
                }
            }
        });
    }

    /**
     * 显示系统错误页面
     */
    public void showSystemFailView(PageExceptionReloadInterface pageExceptionReloadInterface) {
        this.setVisibility(View.VISIBLE);
        for (int i = 0; i < rootView.getChildCount(); i++) {
            View childView = rootView.getChildAt(i);
            childView.setVisibility(View.GONE);
        }
        systemFailView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏所有异常页面
     */
    public void hideAllView() {
        for (int i = 0; i < rootView.getChildCount(); i++) {
            View childView = rootView.getChildAt(i);
            childView.setVisibility(View.GONE);
        }
        this.setVisibility(View.GONE);
    }

    public interface PageExceptionReloadInterface {
        public void reload();
    }
}
