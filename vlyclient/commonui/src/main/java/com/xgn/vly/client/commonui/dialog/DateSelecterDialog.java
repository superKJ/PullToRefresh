package com.xgn.vly.client.commonui.dialog;


import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.xgn.vly.client.commonui.R;
import com.xgn.vly.client.commonui.view.StrongBottomSheetDialog;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * 时间选择器
 * Created by Yefeng on 2017/3/23.
 * Modified by xxx
 */

public class DateSelecterDialog {

    private static NumberPickerView pickerDate;
    private static NumberPickerView pickerMinute;
    private static StrongBottomSheetDialog sheetDialog;
    private static Calendar mDate;
    private String[] mDateDisplayValues = new String[30];
    private int mDelyDays = 0;
    private Context mContext;
    private String[] mHourArray;
    private String[] mCurrentDisplayHourArray;
    //是否需要改变起始时间
    private boolean mResetTime = false;

    public DateSelecterDialog(Context context,long currentTime,String title,int servingBeginHour,int servingBeginMin,
                              int servingEndHour,int servingEndMin) {

        this.mContext = context;
        mResetTime = true;
        mDate = Calendar.getInstance();
        if(0 != currentTime){
            mDate.setTimeInMillis(currentTime);
        }

        sheetDialog = new StrongBottomSheetDialog(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.date_selecter_dialog_layout, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        ImageView ivBack = (ImageView) view.findViewById(R.id.iv_back);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        pickerDate = (NumberPickerView) view.findViewById(R.id.picker_date);
        pickerMinute = (NumberPickerView) view.findViewById(R.id.picker_minute);
        sheetDialog.setContentView(view);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetDialog != null) {
                    sheetDialog.dismiss();
                }
            }
        });

        if(!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetDialog != null) {
                    String contentByCurrValueDate = pickerDate.getContentByCurrValue();
                    String contentByCurrValueMinute = pickerMinute.getContentByCurrValue();
                    String date = contentByCurrValueDate.substring(0, 6);
                    date = date.replace("月", "-").replace("日", "");
                    StringBuilder sb = new StringBuilder();
                    sb.append(mDate.get(Calendar.YEAR)).append("-").append(date).
                            append(" ").append(contentByCurrValueMinute);
                    notifyDateChange(sb.toString());
                    sheetDialog.dismiss();
                }
            }
        });

        //在这里需要判断时间,如果的当前时间大于6:00则6:00不显示,以此类推,如果大于18:00,需要显示下一天
        judgeTimeToUpdate(currentTime,servingBeginHour,servingBeginMin,
        servingEndHour,servingEndMin);
        updateTime();
        setScrollingListener();
        sheetDialog.show();
    }

    public DateSelecterDialog(Context context,long currentTime,String title) {

        this.mContext = context;
        mResetTime = false;
        mDate = Calendar.getInstance();
        if(0 != currentTime){
            mDate.setTimeInMillis(currentTime);
        }

        sheetDialog = new StrongBottomSheetDialog(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.date_selecter_dialog_layout, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        ImageView ivBack = (ImageView) view.findViewById(R.id.iv_back);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        pickerDate = (NumberPickerView) view.findViewById(R.id.picker_date);
        pickerMinute = (NumberPickerView) view.findViewById(R.id.picker_minute);
        sheetDialog.setContentView(view);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetDialog != null) {
                    sheetDialog.dismiss();
                }
            }
        });

        if(!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetDialog != null) {
                    String contentByCurrValueDate = pickerDate.getContentByCurrValue();
                    String contentByCurrValueMinute = pickerMinute.getContentByCurrValue();
                    String date = contentByCurrValueDate.substring(0, 6);
                    date = date.replace("月", "-").replace("日", "");
                    StringBuilder sb = new StringBuilder();
                    sb.append(mDate.get(Calendar.YEAR)).append("-").append(date).
                            append(" ").append(contentByCurrValueMinute);
                    notifyDateChange(sb.toString());
                    sheetDialog.dismiss();
                }
            }
        });

        //在这里需要判断时间,如果的当前时间大于6:00则6:00不显示,以此类推,如果大于18:00,需要显示下一天
        judgeTimeToUpdate(currentTime,0,0,0,0);
        updateTime();
        setScrollingListener();
        sheetDialog.show();
    }

    /**
     * 设置滑动监听
     */
    private void setScrollingListener() {
        pickerDate.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                if(mCurrentDisplayHourArray != null) {
                    if(newVal == 0) {
                        pickerMinute.refreshByNewDisplayedValues(mCurrentDisplayHourArray);
                    }else {
                        pickerMinute.refreshByNewDisplayedValues(mHourArray);
                    }
                }
            }
        });
    }

    private String getTimeStr(int hour, int min) {
        NumberFormat nFormat=NumberFormat.getNumberInstance();
        nFormat.setMinimumIntegerDigits(2);
        nFormat.setMaximumIntegerDigits(2);
        StringBuffer sb = new StringBuffer();
        sb.append(nFormat.format(hour));
        sb.append(":");
        sb.append(nFormat.format(min));
        return sb.toString();
    }


    private String[] getSubArray(String[] array, int start, int end) {
        if (end > start) {
            int length = end - start + 1;
            String[] res = new String[length];
            System.arraycopy(array, start, res, 0, length);
            return res;
        } else {
            return array;
        }
    }

    /**
     * 根据当前时间显示时间选择器
     */
    private void judgeTimeToUpdate(long currentTimeMillis,int servingBeginHour,int servingBeginMin,
                                   int servingEndHour,int servingEndMin) {
        String[] array = null;
        if (mResetTime) {
            if (servingBeginHour == 0 && servingBeginMin == 0
                    && servingEndHour == 0 && servingEndMin == 0) {
                array = mContext.getResources().getStringArray(R.array.hour_minute_display);
                mHourArray = array;
            } else {
                array = mContext.getResources().getStringArray(R.array.hour_full_minute_display);
                int length = array.length;
                int start = 0;
                int end = 0;
                String startStr = getTimeStr(servingBeginHour, servingBeginMin);
                String endStr = getTimeStr(servingEndHour, servingEndMin);
                for (int i = 0; i < length; i++) {
                    if (array[i].equals(startStr)) {
                        start = i;
                    } else if (array[i].equals(endStr)) {
                        end = i;
                    }
                }
                //总的需要显示的时间数组
                mHourArray = getSubArray(array, start, end);
            }
        } else {
            //总的需要显示的时间数组
            mHourArray = mContext.getResources().getStringArray(R.array.hour_minute_display);
        }
        long minTime = getSplitTime(mHourArray[0]);
        long maxTime = getSplitTime(mHourArray[mHourArray.length - 1]);    //在这里加1的原因是,获取的是月份的index,从0开始到11,所以要活的真正的月份,需要加1
        if (currentTimeMillis == 0) {
            currentTimeMillis = System.currentTimeMillis();
        }
        int arrayLength = mHourArray.length;
        for (int i = arrayLength - 1; i >= 0; i--) {
            //转化时间为时间戳
            if (currentTimeMillis < minTime) {  //小于最小时间
                //第一次之间大于直接跳出,延迟一天
                pickerMinute.setDisplayedValues(mHourArray, true);
                mDelyDays = 0;
                break;
            }
            else if (currentTimeMillis >= maxTime) {  //大于最大时间
                //第一次之间大于直接跳出,延迟一天
                pickerMinute.setDisplayedValues(mHourArray, true);
                mDelyDays = 1;
                break;
            } else if (currentTimeMillis > getSplitTime(mHourArray[i])) {  //第一次大于分割时间
                //定义一个数组,装在现在可以显示的时间数组
                mCurrentDisplayHourArray = new String[arrayLength - i - 1];
                System.arraycopy(mHourArray, i + 1, mCurrentDisplayHourArray, 0, mCurrentDisplayHourArray.length);
                pickerMinute.setDisplayedValues(mCurrentDisplayHourArray, true);
                mDelyDays = 0;
                break;
            }
        }
    }

    private long getSplitTime(String str) {

        StringBuilder sb = new StringBuilder();
        sb.append(mDate.get(Calendar.YEAR)).append("-");
        int month = mDate.get(Calendar.MONTH) + 1;
        int day = mDate.get(Calendar.DATE);

        if (month < 10) {
            sb.append("0").append(month).append("-");
        } else {
            sb.append(mDate.get(Calendar.MONTH)+1).append("-");
        }
        if (day < 10) {
            sb.append("0").append(mDate.get(Calendar.DATE)).append(" ").append(str);
        } else {
            sb.append(mDate.get(Calendar.DATE)).append(" ").append(str);
        }
        return transfromDate(sb.toString());
    }

    private long transfromDate(String dateStr) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        Date date;
        String stf = null;
        try {
            date = sdr.parse(dateStr);
            long l = date.getTime();
            stf = String.valueOf(l);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Long.parseLong(stf);
    }

    private void updateTime() {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();
        Calendar cal4 = Calendar.getInstance();
        Calendar cal5 = Calendar.getInstance();

        cal1.setTimeInMillis(mDate.getTimeInMillis());
        cal2.setTimeInMillis(mDate.getTimeInMillis());
        cal3.setTimeInMillis(mDate.getTimeInMillis());
        cal4.setTimeInMillis(mDate.getTimeInMillis());
        cal5.setTimeInMillis(mDate.getTimeInMillis());

        pickerDate.setWrapSelectorWheel(false);

        cal1.add(Calendar.DAY_OF_YEAR, -1 + mDelyDays);
        for (int i = 0; i < 7; ++i) {
            cal1.add(Calendar.DAY_OF_YEAR, 1);
            mDateDisplayValues[i] = (String) DateFormat.format("MM月dd日 EEEE", cal1);
        }
        cal2.add(Calendar.DAY_OF_YEAR, 7 + mDelyDays);
        for (int i = 7; i < 14; ++i) {
            cal2.add(Calendar.DAY_OF_YEAR, 1);
            mDateDisplayValues[i] = (String) DateFormat.format("MM月dd日 EEEE", cal2);
        }
        cal3.add(Calendar.DAY_OF_YEAR, 14 + mDelyDays);
        for (int i = 14; i < 21; ++i) {
            cal3.add(Calendar.DAY_OF_YEAR, 1);
            mDateDisplayValues[i] = (String) DateFormat.format("MM月dd日 EEEE", cal3);
        }
        cal4.add(Calendar.DAY_OF_YEAR, 21 + mDelyDays);
        for (int i = 21; i < 28; ++i) {
            cal4.add(Calendar.DAY_OF_YEAR, 1);
            mDateDisplayValues[i] = (String) DateFormat.format("MM月dd日 EEEE", cal4);
        }
        cal5.add(Calendar.DAY_OF_YEAR, 28 + mDelyDays);
        for (int i = 28; i < 30; ++i) {
            cal5.add(Calendar.DAY_OF_YEAR, 1);
            mDateDisplayValues[i] = (String) DateFormat.format("MM月dd日 EEEE", cal5);
        }

        pickerDate.setDisplayedValues(mDateDisplayValues, true);
    }

    public interface OnSelectDateListener {
        void onDateSelectListener(String date);
    }

    private OnSelectDateListener mOnSelectDateListener;

    public void setOnSelectedDateListener(OnSelectDateListener onSelectDateListener) {
        this.mOnSelectDateListener = onSelectDateListener;
    }

    public void notifyDateChange(String date) {
        if (mOnSelectDateListener != null) {
            mOnSelectDateListener.onDateSelectListener(date);
        }
    }
    /**
     * 获取北京时间
     */
    public static String getBeijingDate(long time) {
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        //显示月日
        simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
        result = simpleDateFormat.format(time);

        return result;
    }
}
