package com.nongjinsuo.mimijinfu.colorful;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import com.haibin.calendarview.BaseCalendarCardView;
import com.haibin.calendarview.Calendar;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.util.LogUtil;

/**
 * 高仿魅族日历布局
 * Created by huanghaibin on 2017/11/15.
 */

public class SimpleCalendarCardView extends BaseCalendarCardView {

    private int mRadius;

    public SimpleCalendarCardView(Context context) {
        super(context);
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
//        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLoopStart(int x, int y) {

    }

    @Override
    protected void onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        if (calendar.getMonth() == mMonth)
            canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        LogUtil.d("calendar", calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay());
        if (calendar.getMonth() == mMonth)
            canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;
        if (calendar.isCurrentDay() && isSelected) {
            mCurDayTextPaint.setColor(Color.WHITE);
        } else {
            mCurDayTextPaint.setColor(this.getResources().getColor(R.color.color_ff3947));
        }
        if (hasScheme) {
            canvas.drawText(calendar.isCurrentDay() ? "今天" : String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(calendar.isCurrentDay() ? "今天" : String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }
}
