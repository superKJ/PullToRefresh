package com.xgn.vly.client.commonui.widget.refreshload;

/**
 * 下拉刷新头部布局需要实现的接口
 * @author tanghh
 *
 */
public interface IRefreshHeader {
	
	/**
	 * 改变到初始状态时回调
	 */
	public void change2Init();
	
	/**
	 * 改变到释放刷新状态时回调
	 */
	public void change2ReleaseToRefresh();
	
	/**
	 * 改变到刷新中状态时回调
	 */
	public void change2Refreshing();
	
	/**
	 * 改变到刷新完成状态时回调
	 */
	public void change2Done();
	
	/**
	 * 刷新成功时回调
	 */
	public void onRefreshSuccess();
	
	/**
	 * 刷新失败时回调
	 */
	public void onRefreshFail();
	
	/**
	 * 当下拉时回调
	 * @param pullDownY 当前下拉的y值
	 */
	public void onPullDown(float pullDownY);
	
	/**
	 * 获取下拉刷新的距离
	 */
	public float getRefreshDist();

}
