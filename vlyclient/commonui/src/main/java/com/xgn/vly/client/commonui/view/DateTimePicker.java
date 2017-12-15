package com.xgn.vly.client.commonui.view;

/**
 * Created by Administrator on 2017/1/20.
 */

import java.util.Calendar;
import android.content.Context;
import android.text.format.DateFormat;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

import com.xgn.vly.client.commonui.R;

public class DateTimePicker extends FrameLayout
{
    private final NumberPicker mDateSpinner;
    private final NumberPicker mHourSpinner;
    private final NumberPicker mMinuteSpinner;
    private Calendar mDate;
    private int mHour,mMinute;
    private String[] mDateDisplayValues = new String[7];
    private OnDateTimeChangedListener mOnDateTimeChangedListener;

    public DateTimePicker(Context context,int hourMin,int hourMax)
    {
        super(context);
        mDate = Calendar.getInstance();

        mHour=mDate.get(Calendar.HOUR_OF_DAY);
        mMinute=mDate.get(Calendar.MINUTE);

        inflate(context, R.layout.datedialog, this);

        mDateSpinner=(NumberPicker)this.findViewById(R.id.np_date);
        mDateSpinner.setMinValue(0);
        mDateSpinner.setMaxValue(6);
        updateDateControl();
        mDateSpinner.setOnValueChangedListener(mOnDateChangedListener);

        mHourSpinner=(NumberPicker)this.findViewById(R.id.np_hour);
        //限制是早上10点到晚上18点
        mHourSpinner.setMaxValue(hourMax);
        mHourSpinner.setMinValue(hourMin);
        mHourSpinner.setValue(mHour);
        mHourSpinner.setOnValueChangedListener(mOnHourChangedListener);

        mMinuteSpinner=(NumberPicker)this.findViewById(R.id.np_minute);
        mMinuteSpinner.setMaxValue(59);
        mMinuteSpinner.setMinValue(0);
        mMinuteSpinner.setValue(mMinute);
        mMinuteSpinner.setOnValueChangedListener(mOnMinuteChangedListener);
    }

    private NumberPicker.OnValueChangeListener mOnDateChangedListener=new OnValueChangeListener()
    {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal)
        {
            mDate.add(Calendar.DAY_OF_MONTH, newVal - oldVal);
            updateDateControl();
            onDateTimeChanged();
        }
    };

    private NumberPicker.OnValueChangeListener mOnHourChangedListener=new OnValueChangeListener()
    {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal)
        {
            mHour=mHourSpinner.getValue();
            onDateTimeChanged();
        }
    };

    private NumberPicker.OnValueChangeListener mOnMinuteChangedListener=new OnValueChangeListener()
    {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal)
        {
            mMinute=mMinuteSpinner.getValue();
            onDateTimeChanged();
        }
    };

    private void updateDateControl()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mDate.getTimeInMillis());
        cal.add(Calendar.DAY_OF_YEAR, -7 / 2 - 1);
        mDateSpinner.setDisplayedValues(null);
        for (int i = 0; i < 7; ++i)
        {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            mDateDisplayValues[i] = (String) DateFormat.format("MM.dd EEEE", cal);
        }
        mDateSpinner.setDisplayedValues(mDateDisplayValues);
        mDateSpinner.setValue(7 / 2);
        mDateSpinner.invalidate();
    }

    public interface OnDateTimeChangedListener
    {
        void onDateTimeChanged(DateTimePicker view, int year, int month, int day,int hour,int minute);
    }

    public void setOnDateTimeChangedListener(OnDateTimeChangedListener callback)
    {
        mOnDateTimeChangedListener = callback;
    }

    private void onDateTimeChanged()
    {
        if (mOnDateTimeChangedListener != null)
        {
            mOnDateTimeChangedListener.onDateTimeChanged(this, mDate.get(Calendar.YEAR),
                    mDate.get(Calendar.MONTH), mDate.get(Calendar.DAY_OF_MONTH),mHour, mMinute);
        }
    }
}
