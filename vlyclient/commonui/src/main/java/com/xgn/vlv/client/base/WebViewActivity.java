package com.xgn.vlv.client.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xgn.utils.DeviceUtil;
import com.xgn.vly.client.commonui.R;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2017/1/18.
 */

public class WebViewActivity extends BaseActivity {
    private static final String KEY_URL = "KEY_URL";
    private String mUrl;
    private WebView mWebView;
    private ViewGroup mRootView;
    private View mNetExceptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getSourceData();
        mRootView = (ViewGroup) findViewById(R.id.root);
        initToolbar(mRootView, null);
        setBackIcon(R.mipmap.login_return);
        findView();
        initView();
    }

    private void findView() {
        mWebView = (WebView) findViewById(R.id.webView);
        mNetExceptionView = (View) findViewById(R.id.exception_net);
    }

    private void initView() {
        if (null != mWebView && !TextUtils.isEmpty(mUrl)) {
            mWebView.loadUrl(mUrl);
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            mWebView.getSettings().setDefaultFontSize(16);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setBuiltInZoomControls(false);
            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.getSettings().setDomStorageEnabled(true);//开启DOM storage API功能
            mWebView.setWebViewClient(new WebViewClient() {
                /**
                 * 对加载的url进行安全过滤
                 * @param view
                 * @param url
                 * @return 返回true代表禁止访问；返回false代表允许访问
                 */
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //这边允许访问任何站点（因为app中链接一般是我们公司人员配置的。如果后期有安全风险，需要过滤url！）
                    return false;
                }
            });
        }
        if (DeviceUtil.isNetworkAvailable(this)) {
        } else {
            mNetExceptionView.setVisibility(View.VISIBLE);
        }
    }

    private void getSourceData() {
        Intent i = getIntent();
        if (null != i) {
            mUrl = i.getStringExtra(KEY_URL);
        }
    }


    public static void start(Context context, String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent i = new Intent(context, WebViewActivity.class);
            i.putExtra(KEY_URL, url);
            context.startActivity(i);
        }
    }

//    @Override
//    protected void onDestroy() {
//       /* if(webview!=null) {
//            webview.setVisibility(View.GONE);
//            webview.removeAllViews();
//            webview.destroy();
//            releaseAllWebViewCallback();
//        }*/
//        if(mWebView != null) {
//            mWebView.getSettings().setBuiltInZoomControls(true);
//            mWebView.setVisibility(View.GONE);
//            long timeout = ViewConfiguration.getZoomControlsTimeout();//timeout ==3000
//            Log.i("time==",timeout+"");
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    // TODO Auto-generated method stub
//                    mWebView.destroy();
//                }
//            }, timeout);
//        }
//        super.onDestroy();
//    }

}
