package com.xgn.vlv.client.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;
import com.xgn.vlv.client.base.ActivityCollector;
import com.xgn.vly.client.commonui.R;

import java.io.Serializable;

/**
 * Activity基类
 * Created by tanghh on 2017/7/15.
 */
public class SimpleFragmentActivity extends AppCompatActivity {

    /**
     * action的key
     */
    public static final String KEY_ACTION = "action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 将打开的 Activity 收集起来，当注销时全部 finish 掉
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_simple);
        if (savedInstanceState == null) {
            Action action = (Action) getIntent().getSerializableExtra(KEY_ACTION);
            try {
                Class<?> clz = Class.forName(action.fragmentClassName);
                Fragment frag = (Fragment) clz.newInstance();
                Bundle fragArgs = new Bundle();
                fragArgs.putSerializable(KEY_ACTION, action);
                frag.setArguments(fragArgs);
                getSupportFragmentManager().beginTransaction().add(R.id.fl_container, frag).commit();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO 加一些埋点
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //TODO 加一些埋点
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


    /**
     * 启动fragment
     * @param activity
     * @param action
     */
    public static void startFragment(Activity activity, Action action){
        Intent intent = new Intent(activity, SimpleFragmentActivity.class);
        intent.putExtra(KEY_ACTION,action);
        activity.startActivity(intent);
    }

    /**
     * 保存启动界面动作的信息
     */
    public static class Action implements Serializable {

        /**
         * fragment的className
         */
        public String fragmentClassName;

        /**
         * object类型参数的值（需要实现序列化接口）
         */
        public Object objectVal;

        /**
         * string类型参数的值
         */
        public String stringVal;

        /**
         * int类型参数的值
         */
        public int intVal;

        /**
         * long类型参数的值
         */
        public long longVal;

        /**
         * float类型参数的值
         */
        public float floatVal;

        /**
         * double类型参数的值
         */
        public double doubleVal;

        /**
         * boolean类型参数的值
         */
        public boolean booleanVal;


    }

}
