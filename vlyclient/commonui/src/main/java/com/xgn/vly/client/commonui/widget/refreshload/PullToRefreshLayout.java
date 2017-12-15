package com.xgn.vly.client.commonui.widget.refreshload;

import java.lang.reflect.Constructor;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xgn.vly.client.commonui.R;

/**
 * 支持下拉刷新的布局
 *
 * @author tanghh
 */
public class PullToRefreshLayout extends RelativeLayout {

    /**
     * TAG
     */
    public static final String TAG = "PullToRefreshLayout";

    /**
     * 初始状态
     */
    public static final int INIT = 0;

    /**
     * 释放刷新
     */
    public static final int RELEASE_TO_REFRESH = 1;

    /**
     * 正在刷新
     */
    public static final int REFRESHING = 2;


    /**
     * 操作完毕
     */
    public static final int DONE = 3;

    /**
     * 刷新头的className
     */
    private String refreshHeaderClassName;

    /**
     * 可下拉的子控件id
     */
    private int pullableChildId;

    /**
     * 当前状态
     */
    private int state = INIT;

    /**
     * 刷新回调接口
     */
    private OnRefreshListener mListener;

    /**
     * 刷新成功
     */
    public static final int SUCCEED = 0;

    /**
     * 刷新失败
     */
    public static final int FAIL = 1;

    /**
     * 按下Y坐标，上一个事件点Y坐标
     */
    private float downY, lastY;

    /**
     * 下拉的距离。注意：pullDownY和pullUpY不可能同时不为0
     */
    public float pullDownY = 0;

    /**
     * 释放刷新的距离
     */
    private float refreshDist = 200;

    /**
     * 定时器：用于不停的给handler发消息
     */
    private MyTimer timer;

    /**
     * 回滚速度
     */
    public float MOVE_SPEED = 8;


    /**
     * 第一次执行布局
     */
    private boolean isLayout = false;

    /**
     * 在刷新过程中滑动操作
     */
    private boolean isTouch = false;

    /**
     * 手指滑动距离与下拉头的滑动距离比，中间会随正切函数变化
     */
    private float radio = 2;

    /**
     * 下拉头
     */
    private View refreshView;

    /**
     * 下拉头需要完成的事情
     */
    private IRefreshHeader refreshHeader;

    /**
     * 第二个直接子view
     */
    private View secondChild;

    /**
     * 控制是否可下拉
     */
    private Pullable pullable;

    /**
     * 过滤多点触碰
     */
    private int mEvents;

    /**
     * 定义是否允许下拉
     */
    private boolean allowDownPull = false;

    /**
     * 当前滑动状态
     */
    private ActionStatus actionStatus = ActionStatus.INIT;

    /**
     * 执行自动回滚的handler
     */
    Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // 回弹速度随下拉距离moveDeltaY增大而增大
            MOVE_SPEED = (float) (8 + 20 * Math.tan(Math.PI / 2
                    / getMeasuredHeight() * (pullDownY)));
            if (!isTouch) {
                // 正在刷新，且没有往上推的话则悬停，显示"正在刷新..."
                if (state == REFRESHING && pullDownY <= refreshDist) {
                    pullDownY = refreshDist;
                    timer.cancel();
                }

            }
            if (pullDownY > 0)
                pullDownY -= MOVE_SPEED;
            if (pullDownY <= 0) {
                // 已完成回弹
                pullDownY = 0;
                // 隐藏下拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
                if (state != REFRESHING)
                    changeState(INIT);
                timer.cancel();
            }
            // 刷新布局,会自动调用onLayout
            requestLayout();
        }

    };

    private int scaledTouchSlop;

    private int downRawX;
    private int downRawY;

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    /**
     * 构造器
     *
     * @param context
     */
    public PullToRefreshLayout(Context context) {
        super(context);
        initView(context);
    }

    /**
     * 构造器
     *
     * @param context
     */
    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //尝试获取自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToRefreshLayoutAttrs);
        pullableChildId = a.getResourceId(R.styleable.PullToRefreshLayoutAttrs_pullableChildId, 0);
        refreshHeaderClassName = a.getString(R.styleable.PullToRefreshLayoutAttrs_refreshHeader);
        a.recycle();
        try {
            Class<?> refreshHeaderClass = Class.forName(refreshHeaderClassName);
            Constructor<?> refreshHeaderConstructor = refreshHeaderClass.getConstructor(Context.class);
            Object o = refreshHeaderConstructor.newInstance(context);
            if (o instanceof View) {
                refreshView = (View) o;
            }
            if (o instanceof IRefreshHeader) {
                refreshHeader = (IRefreshHeader) o;
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            addView(refreshView, 0, lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView(context);
    }

    /**
     * 构造器
     *
     * @param context
     */
    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    /**
     * 初始化view
     *
     * @param context
     */
    private void initView(Context context) {
        timer = new MyTimer(updateHandler);
        // 触发移动事件的最小距离
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    /**
     * 隐藏
     */
    private void hide() {
        timer.schedule(5);
    }

    /**
     * 完成刷新操作，显示刷新结果。注意：刷新完成后一定要调用这个方法
     *
     * @param refreshResult PullToRefreshLayout.SUCCEED代表成功，PullToRefreshLayout.FAIL代表失败
     */
    public void refreshFinish(int refreshResult) {
        if (refreshHeader == null) {
            return;
        }
        switch (refreshResult) {
            case SUCCEED:
                // 刷新成功
                refreshHeader.onRefreshSuccess();
                break;
            case FAIL:
            default:
                // 刷新失败
                refreshHeader.onRefreshFail();
                break;
        }
        // 刷新结果停留1秒
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                changeState(DONE);
                if (!isTouch) {
                    hide();
                }
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }


    /**
     * 改变状态
     *
     * @param to
     */
    private void changeState(int to) {
        state = to;
        switch (state) {
            case INIT:
                // 下拉布局初始状态
                refreshHeader.change2Init();
                break;
            case RELEASE_TO_REFRESH:
                // 释放刷新状态
                refreshHeader.change2ReleaseToRefresh();
                break;
            case REFRESHING:
                // 正在刷新状态
                refreshHeader.change2Refreshing();
                break;
            case DONE:
                // 刷新完毕
                refreshHeader.change2Done();
                break;
        }
    }

    /*
     * （非 Javadoc）由父控件决定是否分发事件，防止事件冲突
     *
     * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                lastY = downY;
                timer.cancel();
                mEvents = 0;
                downRawY = (int) ev.getRawY();
                downRawX = (int) ev.getRawX();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                // 过滤多点触碰
                mEvents = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mEvents == 0) {
                    if (actionStatus == ActionStatus.INIT) {
                        int curRawY = (int) ev.getRawY();
                        int curRawX = (int) ev.getRawX();
                        int curDistX = curRawX - downRawX;
                        int curDistY = curRawY - downRawY;
//                        if (Math.abs(curDistX) >= scaledTouchSlop || Math.abs(curDistY) >= scaledTouchSlop) {
//                            if (Math.abs(curDistX) >= Math.abs(curDistY)) {
//                                actionStatus = ActionStatus.LEFT_RIGHT;
//                            } else {
//                                actionStatus = ActionStatus.TOP_BOTTOM;
//                            }
//                        }
                        if (Math.abs(curDistY) >= scaledTouchSlop) {
                                actionStatus = ActionStatus.TOP_BOTTOM;
                        }
                    }

                    if (actionStatus != ActionStatus.TOP_BOTTOM) {
                        lastY = ev.getY();
                        // 根据下拉距离改变比例
                        radio = (float) (2 + 3 * Math.tan(Math.PI / 2 / getMeasuredHeight()
                                * (pullDownY)));
                        break;
                    }

                    if (allowDownPull || pullable.canPullDown()) {
                        // 可以下拉，正在加载时不能下拉
                        // 对实际滑动距离做缩小，造成用力拉的感觉
                        pullDownY = pullDownY + (ev.getY() - lastY) / radio;
                        if (ev.getY() - lastY < 0) {
                            pullDownY = pullDownY + (ev.getY() - lastY);
                        }
                        if (pullDownY < 0) {
                            pullDownY = 0;
                        }
                        if (pullDownY > getMeasuredHeight())
                            pullDownY = getMeasuredHeight();
                        if (state == REFRESHING) {
                            // 正在刷新的时候触摸移动
                            isTouch = true;
                        }
                        requestLayout();
                        refreshHeader.onPullDown(pullDownY);
                        if (pullDownY <= refreshDist && (state == RELEASE_TO_REFRESH || state == DONE)) {
                            // 如果下拉距离没达到刷新的距离且当前状态是释放刷新，改变状态为下拉刷新
                            changeState(INIT);
                        }
                        if (pullDownY >= refreshDist && state == INIT) {
                            // 如果下拉距离达到刷新的距离且当前状态是初始状态刷新，改变状态为释放刷新
                            changeState(RELEASE_TO_REFRESH);
                        }
                        // 因为刷新和加载操作不能同时进行，所以pullDownY和pullUpY不会同时不为0，因此这里用(pullDownY +
                        // Math.abs(pullUpY))就可以不对当前状态作区分了
                        if (pullDownY > 1) {
                            // 防止下拉过程中误触发长按事件和点击事件
                            ev.setAction(MotionEvent.ACTION_CANCEL);
                        }
                    }
                } else {
                    mEvents = 0;
                }
                lastY = ev.getY();
                // 根据下拉距离改变比例
                radio = (float) (2 + 3 * Math.tan(Math.PI / 2 / getMeasuredHeight()
                        * (pullDownY)));

                break;
            case MotionEvent.ACTION_UP:
                actionStatus = ActionStatus.INIT;
                if (pullDownY > refreshDist)
                    // 正在刷新时往下拉（正在加载时往上拉），释放后下拉头（上拉头）不隐藏
                    isTouch = false;
                if (state == RELEASE_TO_REFRESH) {
                    changeState(REFRESHING);
                    // 刷新操作
                    if (mListener != null)
                        mListener.onRefresh(this);
                }
                hide();
            default:
                break;
        }
//        if (pullDownY <= dp2px(1)) {
        // 事件分发交给父类
        super.dispatchTouchEvent(ev);
//        }

        return true;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!isLayout) {
            // 这里是第一次进来的时候做一些初始化
            if (refreshView == null) {
                refreshView = getChildAt(0);
            }
            if (secondChild == null) {
                secondChild = getChildAt(1);
            }
            if (refreshView instanceof IRefreshHeader) {
                refreshHeader = (IRefreshHeader) refreshView;
            }
            isLayout = true;
            refreshDist = refreshHeader.getRefreshDist();
            View tempv = findViewById(pullableChildId);
            if (tempv != null) {
                pullable = (Pullable) tempv;
            } else if (secondChild instanceof Pullable) {
                pullable = (Pullable) secondChild;
            } else {
                pullable = new Pullable() {
                    @Override
                    public boolean canPullDown() {
                        return false;
                    }

                    @Override
                    public boolean canPullUp() {
                        return false;
                    }
                };
            }
        }
        // 改变子控件的布局，这里直接用(pullDownY + pullUpY)作为偏移量，这样就可以不对当前状态作区分
        refreshView.layout(0,
                (int) (pullDownY) - refreshView.getMeasuredHeight(),
                refreshView.getMeasuredWidth(), (int) (pullDownY));
        secondChild.layout(0, (int) (pullDownY),
                secondChild.getMeasuredWidth(), (int) (pullDownY)
                        + secondChild.getMeasuredHeight());
    }


    public View getRefreshView() {
        return refreshView;
    }

    public void setRefreshView(View refreshView) {
        if (this.refreshView != null) {
            removeViewAt(0);
        }
        this.refreshView = refreshView;
        addView(refreshView, 0);
        this.refreshHeader = (IRefreshHeader) refreshView;
    }


    public Pullable getPullable() {
        return pullable;
    }

    public void setPullable(Pullable pullable) {
        this.pullable = pullable;
    }

    public boolean isAllowDownPull() {
        return allowDownPull;
    }

    public void setAllowDownPull(boolean allowDownPull) {
        this.allowDownPull = allowDownPull;
    }

    /**
     * 定时器：用于不停的向handler发送消息
     */
    class MyTimer {
        private Handler handler;
        private Timer timer;
        private MyTask mTask;

        public MyTimer(Handler handler) {
            this.handler = handler;
            timer = new Timer();
        }

        public void schedule(long period) {
            if (mTask != null) {
                mTask.cancel();
                mTask = null;
            }
            mTask = new MyTask(handler);
            timer.schedule(mTask, 0, period);
        }

        public void cancel() {
            if (mTask != null) {
                mTask.cancel();
                mTask = null;
            }
        }

        class MyTask extends TimerTask {
            private Handler handler;

            public MyTask(Handler handler) {
                this.handler = handler;
            }

            @Override
            public void run() {
                handler.obtainMessage().sendToTarget();
            }

        }
    }


    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    /**
     * 滑动的状态
     */
    private static enum ActionStatus {
        /**
         * 初始状态
         */
        INIT,

        /**
         * 上下滑动状态
         */
        TOP_BOTTOM,

        /**
         * 左右滑动状态
         */
        LEFT_RIGHT
    }

    /**
     * 刷新加载回调接口
     */
    public interface OnRefreshListener {
        /**
         * 刷新操作
         */
        void onRefresh(PullToRefreshLayout pullToRefreshLayout);
    }
}

