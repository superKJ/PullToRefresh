package com.xgn.vly.client.commonui.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 加删除线的TV
 * Created by Boqin on 2017/5/5.
 * Modified by Boqin
 *
 * @Version
 */
public class StrikeTextView extends AppCompatTextView{

    public StrikeTextView(Context context) {
        super(context);
        init();
    }

    public StrikeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StrikeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

}
