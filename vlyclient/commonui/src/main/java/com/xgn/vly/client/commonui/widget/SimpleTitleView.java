package com.xgn.vly.client.commonui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xgn.vly.client.commonui.R;


/**
 * 简单标题view
 * Created by tanghh on 2016/12/8.
 */
public class SimpleTitleView extends LinearLayout {


    /**
     * 标题根布局
     */
    private RelativeLayout rl_title_root;

    /**
     * 左边按钮布局
     */
    private LinearLayout ll_left;

    /**
     * 左边图片按钮
     */
    private ImageView iv_left;

    /**
     * 左边文字按钮
     */
    private TextView tv_left;


    /**
     * 中间标题
     */
    private TextView tv_title;

    /**
     * 中间小标题
     */
    private TextView tv_small_title;


    /**
     * 右边按钮布局
     */
    private LinearLayout ll_right;

    /**
     * 右边文字按钮
     */
    private TextView tv_right;

    /**
     * 右边图片按钮
     */
    private ImageView iv_right;

    /**
     * 标题底部分割线
     */
    private View view_divider;


    /**
     * 构造器
     *
     * @param context
     */
    public SimpleTitleView(Context context) {
        super(context);
        //初始化
        init(context);
    }

    /**
     * 从xml中创建调用的构造器
     *
     * @param context
     * @param attrs
     */
    public SimpleTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化
        init(context);
    }

    /**
     * 构造器
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SimpleTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        init(context);
    }

    /**
     * 获取标题根布局
     *
     * @return
     */
    public RelativeLayout getRl_title_root() {
        return rl_title_root;
    }

    /**
     * 获取左边图片按钮
     *
     * @return
     */
    public ImageView getIv_left() {
        return iv_left;
    }

    /**
     * 获取左边文字按钮
     *
     * @return
     */
    public TextView getTv_left() {
        return tv_left;
    }

    /**
     * 获取中间标题
     *
     * @return
     */
    public TextView getTv_title() {
        return tv_title;
    }

    /**
     * 获取中间小标题
     *
     * @return
     */
    public TextView getTv_small_title() {
        return tv_small_title;
    }

    /**
     * 获取右边文字按钮
     *
     * @return
     */
    public TextView getTv_right() {
        return tv_right;
    }

    /**
     * 获取右边图片按钮
     *
     * @return
     */
    public ImageView getIv_right() {
        return iv_right;
    }

    /**
     * 获取标题底部分割线
     *
     * @return
     */
    public View getView_divider() {
        return view_divider;
    }


    /**
     * 初始化
     *
     * @param context 上下文
     */
    private void init(Context context) {
        //填充布局并加入到自身子view中
        View.inflate(context, R.layout.simple_title_view, this);
        //获取所有view
        this.rl_title_root = findView(R.id.rl_title_root);
        this.ll_left = findView(R.id.ll_left);
        this.tv_left = findView(R.id.tv_left);
        this.iv_left = findView(R.id.iv_left);
        this.tv_title = findView(R.id.tv_title);
        this.tv_small_title = findView(R.id.tv_small_title);
        this.ll_right = findView(R.id.ll_right);
        this.tv_right = findView(R.id.tv_right);
        this.iv_right = findView(R.id.iv_right);
        this.view_divider = findView(R.id.view_divider);
    }


    /**
     * 显示左边图片按钮 和 中间标题
     *
     * @param ll_left_listener 左边布局单击监听器
     * @param titleStr         标题字符串
     */
    public void showLeftImgAndTitle(OnClickListener ll_left_listener, String titleStr) {
        setViewShowOrHide(true, false, true, false, false, false);
        this.ll_left.setOnClickListener(ll_left_listener);
        this.tv_title.setText(titleStr);
    }

    /**
     * 显示左边文字按钮 和 中间标题
     *
     * @param ll_left_listener 左边布局单击监听器
     * @param titleStr         标题字符串
     */
    public void showLeftTxtAndTitle(OnClickListener ll_left_listener, String titleStr) {
        setViewShowOrHide(false, true, true, false, false, false);
        this.ll_left.setOnClickListener(ll_left_listener);
        this.tv_title.setText(titleStr);
    }

    /**
     * 显示右边图片按钮 和 中间标题
     *
     * @param ll_right_listener 右边布局单击监听器
     * @param titleStr          标题字符串
     */
    public void showRightImgAndTitle(OnClickListener ll_right_listener, String titleStr) {
        setViewShowOrHide(false, false, true, false, false, true);
        this.ll_right.setOnClickListener(ll_right_listener);
        this.tv_title.setText(titleStr);
    }

    /**
     * 显示右边文字按钮 和 中间标题
     *
     * @param ll_right_listener 右边布局单击监听器
     * @param titleStr          标题字符串
     */
    public void showRightTxtAndTitle(OnClickListener ll_right_listener, String titleStr) {
        setViewShowOrHide(false, false, true, false, true, false);
        this.ll_right.setOnClickListener(ll_right_listener);
        tv_title.setText(titleStr);
    }

    /**
     * 只显示中间标题
     *
     * @param titleStr 标题字符串
     */
    public void showTitle(String titleStr) {
        setViewShowOrHide(false, false, true, false, false, false);
        this.tv_title.setText(titleStr);
    }


    /**
     * 显示 左右图片按钮 和 中间标题
     *
     * @param ll_left_listener  左边布局单击监听器
     * @param ll_right_listener 右边布局单击监听器
     * @param titleStr          标题字符串
     */
    public void showLeftImgRightImgAndTitle(OnClickListener ll_left_listener, OnClickListener ll_right_listener, String titleStr) {
        setViewShowOrHide(true, false, true, false, false, true);
        this.ll_left.setOnClickListener(ll_left_listener);
        this.ll_right.setOnClickListener(ll_right_listener);
        this.tv_title.setText(titleStr);
    }


    /**
     * 显示左右文字按钮 和 中间标题
     *
     * @param ll_left_listener  左边布局单击监听器
     * @param ll_right_listener 右边布局单击监听器
     * @param titleStr          标题字符串
     */
    public void showLeftTxtRightTxtAndTitle(OnClickListener ll_left_listener, OnClickListener ll_right_listener, String titleStr) {
        setViewShowOrHide(false, true, true, false, true, false);
        this.ll_left.setOnClickListener(ll_left_listener);
        this.ll_right.setOnClickListener(ll_right_listener);
        this.tv_title.setText(titleStr);
    }

    /**
     * 显示 左边图片按钮、右边文字按钮 和 中间标题
     *
     * @param ll_left_listener  左边布局单击监听器
     * @param ll_right_listener 右边布局单击监听器
     * @param titleStr          标题字符串
     */
    public void showLeftImgRightTxtAndTitle(OnClickListener ll_left_listener, OnClickListener ll_right_listener, String titleStr) {
        setViewShowOrHide(true, false, true, false, true, false);
        this.ll_left.setOnClickListener(ll_left_listener);
        this.ll_right.setOnClickListener(ll_right_listener);
        this.tv_title.setText(titleStr);
    }

    /**
     * 显示 左边文字按钮、右边图片按钮 和 中间标题
     *
     * @param ll_left_listener  左边布局单击监听器
     * @param ll_right_listener 右边布局单击监听器
     * @param titleStr          标题字符串
     */
    public void showLeftTxtRightImgAndTitle(OnClickListener ll_left_listener, OnClickListener ll_right_listener, String titleStr) {
        setViewShowOrHide(false, true, true, false, false, true);
        this.ll_left.setOnClickListener(ll_left_listener);
        this.ll_right.setOnClickListener(ll_right_listener);
        this.tv_title.setText(titleStr);
    }

    /**
     * 设置view的显示和隐藏
     *
     * @param isShowIvLeft
     * @param isShowTvLeft
     * @param isShowTvTitle
     * @param isShowTvSmalTitle
     * @param isShowTvRight
     * @param isShowIvRight
     */
    public void setViewShowOrHide(boolean isShowIvLeft, boolean isShowTvLeft, boolean isShowTvTitle, boolean isShowTvSmalTitle, boolean isShowTvRight, boolean isShowIvRight) {
        this.iv_left.setVisibility(isShowIvLeft ? View.VISIBLE : View.GONE);
        this.tv_left.setVisibility(isShowTvLeft ? View.VISIBLE : View.GONE);
        this.tv_title.setVisibility(isShowTvTitle ? View.VISIBLE : View.GONE);
        this.tv_small_title.setVisibility(isShowTvSmalTitle ? View.VISIBLE : View.GONE);
        this.tv_right.setVisibility(isShowTvRight ? View.VISIBLE : View.GONE);
        this.iv_right.setVisibility(isShowIvRight ? View.VISIBLE : View.GONE);
    }

    /**
     * 寻找view
     *
     * @param viewId view的id
     * @param <T>
     * @return
     */
    private <T extends View> T findView(int viewId) {
        return (T) findViewById(viewId);
    }

}
