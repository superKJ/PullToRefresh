package com.xgn.vly.client.commonui.widget.refreshload;

/**
 * 底部自动加载布局需要实现的接口
 * Created by tanghh on 2017/6/29.
 */
public interface ILoadFooter {

    /**
     * 改变到初始状态时回调
     */
    public void change2Init();

    /**
     * 改变到加载中状态时回调
     */
    public void change2Loading();

    /**
     * 改变到没有更多数据状态时回调
     */
    public void change2NoMoreData();

}
