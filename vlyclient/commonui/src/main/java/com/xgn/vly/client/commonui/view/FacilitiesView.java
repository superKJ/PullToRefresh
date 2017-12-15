package com.xgn.vly.client.commonui.view;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.xgn.vly.client.commonui.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/15.
 */

public class FacilitiesView extends LinearLayout{
    private FixedGridView mGview;
    private Context mContext;
    private List<Map<String, Object>> toggleData = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> extendData = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    private SimpleAdapter mSimpleAdapter;
    private RelativeLayout mRlShow;
    private TextView tv_show_all;
    private boolean mIsExtend = false;
    public FacilitiesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View root = LayoutInflater.from(context).inflate(R.layout.facilities_view, this);
        findView(root);
        initView();
    }

    private void findView(View root){
        mGview = (FixedGridView) root.findViewById(R.id.gview);
        mRlShow = (RelativeLayout) root.findViewById(R.id.rl_show_all);
        tv_show_all = (TextView) root.findViewById(R.id.tv_show_all);
    }

    private void getToggleData(){
        this.data.clear();
        int size = toggleData.size();
        if(size > 0){
            for(int i=0;i<size;i++){
                this.data.add(toggleData.get(i));
            }
        }
    }

    private void getExtendData(){
        this.data.clear();
        int size = extendData.size();
        if(size > 0){
           for(int i=0;i<size;i++){
               this.data.add(extendData.get(i));
           }
        }
    }


    private void showArrowDown(boolean show){
        if(null != tv_show_all){
            if(show){
                Drawable drawable = getResources().getDrawable(R.mipmap.arrow_down);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                tv_show_all.setCompoundDrawables(drawable,null,null,null);
            }else{
                tv_show_all.setCompoundDrawables(null,null,null,null);
            }
        }
    }

    public void setData(List<Map<String, Object>> data){
        this.data.clear();
        this.extendData.clear();
        this.toggleData.clear();
        int size = data.size();
        for(int i=0;i<size;i++){
            this.extendData.add(data.get(i));
        }
        if(null != data && size > 10){
            for(int i=0;i<10;i++){
                toggleData.add(data.get(i));
            }
        }
        if(size > 10){
            mIsExtend = false;
            getToggleData();
            if(null != tv_show_all){
                tv_show_all.setText("点击展开全部");
                showArrowDown(true);
            }
            mRlShow.setVisibility(View.VISIBLE);
            if(null != mRlShow){
                mRlShow.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mIsExtend){
                           if(null != tv_show_all){
                               tv_show_all.setText("点击展开全部");
                               showArrowDown(true);
                           }
                            mIsExtend = false;
                            getToggleData();
                            if(null != mSimpleAdapter){
                                mSimpleAdapter.notifyDataSetChanged();
                            }
                        }else{
                            if(null != tv_show_all){
                                tv_show_all.setText("点击折叠内容");
                                showArrowDown(false);
                            }
                            mIsExtend = true;
                            getExtendData();
                            if(null != mSimpleAdapter){
                                mSimpleAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        }else{
            getExtendData();
            mRlShow.setVisibility(View.GONE);
        }
        if(null != mSimpleAdapter){
            mSimpleAdapter.notifyDataSetChanged();
        }
    }

    private void initView(){
        /** 在这里可以实现自定义视图的功能 */
        //新建适配器
        String [] from ={"text","img"};
        int [] to = {R.id.text,R.id.img};
        mSimpleAdapter = new SimpleAdapter(mContext, data, R.layout.item_facilities, from, to);
        //配置适配器
        mGview.setAdapter(mSimpleAdapter);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
