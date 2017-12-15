package com.xgn.vly.client.commonui.view;

/**
 * Created by Administrator on 2017/1/20.
 */

import com.xgn.vly.client.commonui.view.DateTimePicker.OnDateTimeChangedListener;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.util.Calendar;

public class DateTimePickerDialog extends AlertDialog implements OnClickListener
{
    private DateTimePicker mDateTimePicker;
    private Calendar mDate = Calendar.getInstance();
    private OnDateTimeSetListener mOnDateTimeSetListener;

    @SuppressWarnings("deprecation")
    public DateTimePickerDialog(Context context, long date,int hourMin,int hourMax)
    {
        super(context);
        mDateTimePicker = new DateTimePicker(context,hourMin,hourMax);
        setView(mDateTimePicker);
        mDateTimePicker.setOnDateTimeChangedListener(new OnDateTimeChangedListener()
        {
            @Override
            public void onDateTimeChanged(DateTimePicker view, int year, int month, int day, int hour, int minute)
            {
                mDate.set(Calendar.YEAR, year);
                mDate.set(Calendar.MONTH, month);
                mDate.set(Calendar.DAY_OF_MONTH, day);
                mDate.set(Calendar.HOUR_OF_DAY, hour);
                mDate.set(Calendar.MINUTE, minute);
                mDate.set(Calendar.SECOND, 0);
            }
        });

        setButton("确定", this);
        setButton2("取消", (OnClickListener)null);
        mDate.setTimeInMillis(date);
    }

    public interface OnDateTimeSetListener
    {
        void OnDateTimeSet(AlertDialog dialog, long date);
    }


    public void setOnDateTimeSetListener(OnDateTimeSetListener callBack)
    {
        mOnDateTimeSetListener = callBack;
    }

    public void onClick(DialogInterface arg0, int arg1)
    {
        if (mOnDateTimeSetListener != null)
        {
            mOnDateTimeSetListener.OnDateTimeSet(this, mDate.getTimeInMillis());
        }
    }
}

