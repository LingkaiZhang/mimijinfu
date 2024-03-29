/*
 * Copyright (C) 2016 huanghaibin_dev <huanghaibin_dev@163.com>
 * WebSite https://github.com/MiracleTimes-Dev
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haibin.calendarview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * 日历布局
 * 各个类使用包权限，避免不必要的public
 */
@SuppressWarnings("deprecation")
public class CalendarView extends FrameLayout {

    /**
     * 使用google官方推荐的方式抽取自定义属性
     */
    private CustomCalendarViewDelegate mDelegate;

    /**
     * 自定义自适应高度的ViewPager
     */
    private MonthViewPager mMonthPager;

    /**
     * 日历周视图
     */
    private WeekViewPager mWeekPager;

    /**
     * 月份快速选取
     */
    private MonthSelectLayout mSelectLayout;

    /**
     * 星期栏
     */
    private WeekBar mWeekBar;

    /**
     * 日历外部收缩布局
     */
    CalendarLayout mParentLayout;


    public CalendarView(@NonNull Context context) {
        this(context, null);
    }

    public CalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mDelegate = new CustomCalendarViewDelegate(context, attrs);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context context
     */
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.cv_layout_calendar_view, this, true);
        FrameLayout frameContent = (FrameLayout) findViewById(R.id.frameContent);
        this.mWeekPager = (WeekViewPager) findViewById(R.id.vp_week);
        this.mWeekPager.setup(mDelegate);

        if (TextUtils.isEmpty(mDelegate.getWeekBarClass())) {
            this.mWeekBar = new WeekBar(getContext());
        } else {
            try {
                Class cls = Class.forName(mDelegate.getWeekBarClass());
                @SuppressWarnings("unchecked")
                Constructor constructor = cls.getConstructor(Context.class);
                mWeekBar = (WeekBar) constructor.newInstance(getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        frameContent.addView(mWeekBar, 2);
        mSelectLayout = (MonthSelectLayout) findViewById(R.id.selectLayout);
        mWeekBar.setup(mDelegate);


        this.mMonthPager = (MonthViewPager) findViewById(R.id.vp_calendar);
        this.mMonthPager.mWeekPager = mWeekPager;

        mSelectLayout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mWeekPager.getVisibility() == VISIBLE) {
                    return;
                }
                if (mDelegate.mDateChangeListener != null) {
                    mDelegate.mDateChangeListener.onYearChange(position + mDelegate.getMinYear());
                }
                if (mDelegate.mYearChangeListener != null) {
                    mDelegate.mYearChangeListener.onYearChange(position + mDelegate.getMinYear());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mDelegate.mInnerListener = new OnInnerDateSelectedListener() {
            @Override
            public void onDateSelected(Calendar calendar) {
                if (calendar.getYear() == mDelegate.getCurrentDay().getYear() &&
                        calendar.getMonth() == mDelegate.getCurrentDay().getMonth()
                        && mMonthPager.getCurrentItem() != mDelegate.mCurrentMonthViewItem) {
                    return;
                }
                mDelegate.mSelectedCalendar = calendar;
                mWeekPager.updateSelected(mDelegate.mSelectedCalendar);
                mMonthPager.updateSelected();
            }

            @Override
            public void onWeekSelected(Calendar calendar) {
                mDelegate.mSelectedCalendar = calendar;
                int y = calendar.getYear() - mDelegate.getMinYear();
                int position = 12 * y + mDelegate.mSelectedCalendar.getMonth() - 1;
                mMonthPager.setCurrentItem(position);
                mMonthPager.updateSelected();
            }
        };

        mDelegate.mSelectedCalendar = mDelegate.createCurrentDate();

        int mCurYear = mDelegate.mSelectedCalendar.getYear();
        if (mDelegate.getMinYear() >= mCurYear) mDelegate.setMinYear(mCurYear);
        if (mDelegate.getMaxYear() <= mCurYear) mDelegate.setMaxYear(mCurYear + 2);
        mSelectLayout.setYearSpan(mDelegate.getMinYear(), mDelegate.getMaxYear());
        int y = mDelegate.mSelectedCalendar.getYear() - mDelegate.getMinYear();
        mDelegate.mCurrentMonthViewItem = 12 * y + mDelegate.mSelectedCalendar.getMonth() - 1;
        mMonthPager.setup(mDelegate);
        mMonthPager.setCurrentItem(mDelegate.mCurrentMonthViewItem);
        mSelectLayout.setOnMonthSelectedListener(new MonthRecyclerView.OnMonthSelectedListener() {
            @Override
            public void onMonthSelected(int year, int month) {
                int position = 12 * (year - mDelegate.getMinYear()) + month - 1;
                closeSelectLayout(position);
            }
        });
        mSelectLayout.setSchemeColor(mDelegate.getSchemeThemeColor());
        mWeekPager.updateSelected(mDelegate.mSelectedCalendar);

    }


    /**
     * 获取当天
     *
     * @return 返回今天
     */
    public int getCurDay() {
        return mDelegate.getCurrentDay().getDay();
    }

    /**
     * 获取本月
     *
     * @return 返回本月
     */
    public int getCurMonth() {
        return mDelegate.getCurrentDay().getMonth();
    }

    /**
     * 获取本年
     *
     * @return 返回本年
     */
    public int getCurYear() {
        return mDelegate.getCurrentDay().getYear();
    }

    /**
     * 打开日历月份快速选择
     *
     * @param year 年
     */
    public void showSelectLayout(final int year) {
        if (mParentLayout != null && mParentLayout.mContentView != null) {
            mParentLayout.expand();
            mParentLayout.mContentView.setVisibility(GONE);
        }
        mWeekPager.setVisibility(GONE);

        mWeekBar.animate()
                .translationY(-mWeekBar.getHeight())
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mWeekBar.setVisibility(GONE);
                        mSelectLayout.setVisibility(VISIBLE);
                        mSelectLayout.init(year);
                    }
                });

        mMonthPager.animate()
                .scaleX(0)
                .scaleY(0)
                .setDuration(180)
                .setInterpolator(new LinearInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mMonthPager.setVisibility(GONE);
                    }
                });
    }

    /**
     * 滚动到当前
     */
    public void scrollToCurrent() {
        mDelegate.mSelectedCalendar = mDelegate.createCurrentDate();
        if (mDelegate.mDateSelectedListener != null &&
                mDelegate.mCurrentWeekViewItem == mWeekPager.getCurrentItem()) {
            mDelegate.mDateSelectedListener.onDateSelected(mDelegate.createCurrentDate());
        }
        mWeekPager.scrollToCurrent();
        mMonthPager.scrollToCurrent();
        if (mDelegate.mDateChangeListener != null) {
            mDelegate.mDateChangeListener.onDateChange(mDelegate.createCurrentDate());
        }

    }

    /**
     * 滚动到某一年
     *
     * @param year 快速滚动的年份
     */
    @SuppressWarnings("unused")
    public void scrollToYear(int year) {
        mMonthPager.setCurrentItem(12 * (year - mDelegate.getMinYear()) + mDelegate.getCurrentDay().getMonth() - 1);
    }

    /**
     * 跳转到上个月
     */
    public void lastMonth() {
            mMonthPager.setCurrentItem(mMonthPager.getCurrentItem()-1);
    }

    /**
     * 跳转到下个月
     */
    public void nextMonth() {
        mMonthPager.setCurrentItem(mMonthPager.getCurrentItem()+1);
    }
    /**
     * 关闭日历布局，同时会滚动到指定的位置
     *
     * @param position 某一年
     */
    public void closeSelectLayout(final int position) {
        mSelectLayout.setVisibility(GONE);
        mWeekBar.setVisibility(VISIBLE);
        mMonthPager.setVisibility(VISIBLE);
        if (position == mMonthPager.getCurrentItem()) {
            Calendar calendar = new Calendar();
            calendar.setYear(position / 12 + mDelegate.getMinYear());
            calendar.setMonth(position % 12 + 1);
            calendar.setDay(1);
            calendar.setLunar(LunarCalendar.numToChineseDay(LunarCalendar.solarToLunar(calendar.getYear(), calendar.getMonth(), 1)[2]));
            mDelegate.mSelectedCalendar = calendar;
            if (mDelegate.mDateChangeListener != null) {
                mDelegate.mDateChangeListener.onDateChange(calendar);
            }
            if (mDelegate.mDateSelectedListener != null) {
                mDelegate.mDateSelectedListener.onDateSelected(calendar);
            }
        } else {
            mMonthPager.setCurrentItem(position, true);
        }
        mWeekBar.animate()
                .translationY(0)
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mWeekBar.setVisibility(VISIBLE);
                        if (mParentLayout != null && mParentLayout.mContentView != null) {
                            mParentLayout.mContentView.setVisibility(VISIBLE);
                        }
                    }
                });
        mMonthPager.animate()
                .scaleX(1)
                .scaleY(1)
                .setDuration(180)
                .setInterpolator(new LinearInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mMonthPager.setVisibility(VISIBLE);

                    }
                });
    }

    /**
     * 日期改变监听器
     *
     * @param listener 监听
     */
    @Deprecated
    public void setOnDateChangeListener(OnDateChangeListener listener) {
        this.mDelegate.mDateChangeListener = listener;
        if (mDelegate.mDateChangeListener != null) {
            mDelegate.mDateChangeListener.onDateChange(mDelegate.mSelectedCalendar);
        }

    }


    /**
     * 年份改变事件
     *
     * @param listener listener
     */
    @SuppressWarnings("unused")
    public void setOnYearChangeListener(OnYearChangeListener listener) {
        this.mDelegate.mYearChangeListener = listener;
    }

    /**
     * 设置日期选中事件
     *
     * @param listener 日期选中事件
     */
    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        this.mDelegate.mDateSelectedListener = listener;
        if (mDelegate.mDateSelectedListener != null) {
            mDelegate.mDateSelectedListener.onDateSelected(mDelegate.mSelectedCalendar);
        }
    }

    /**
     * 初始化时初始化日历卡默认选择位置
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getParent() != null && getParent() instanceof CalendarLayout) {
            mParentLayout = (CalendarLayout) getParent();
            mParentLayout.mItemHeight = mDelegate.getCalendarItemHeight();
            mMonthPager.mParentLayout = mParentLayout;
            mWeekPager.mParentLayout = mParentLayout;
            mParentLayout.initCalendarPosition(mDelegate.mSelectedCalendar);
            mParentLayout.initStatus();
        }
    }


    /**
     * 标记哪些日期有事件
     *
     * @param mSchemeDate mSchemeDate 通过自己的需求转换即可
     */
    public void setSchemeDate(List<Calendar> mSchemeDate) {
        this.mDelegate.mSchemeDate = mSchemeDate;
        mSelectLayout.setSchemes(mSchemeDate);
        mMonthPager.updateScheme();
        mWeekPager.updateScheme();
    }

    /**
     * 设置背景色
     *
     * @param monthLayoutBackground 月份卡片的背景色
     * @param weekBackground        星期栏背景色
     * @param lineBg                线的颜色
     */
    @SuppressWarnings("unused")
    public void setBackground(int monthLayoutBackground, int weekBackground, int lineBg) {
        mWeekBar.setBackgroundColor(weekBackground);
        mSelectLayout.setBackgroundColor(monthLayoutBackground);
        findViewById(R.id.line).setBackgroundColor(lineBg);
    }


    /**
     * 设置文本颜色
     *
     * @param curMonthTextColor        当前月份字体颜色
     * @param otherMonthColor          其它月份字体颜色
     * @param curMonthLunarTextColor   当前月份农历字体颜色
     * @param otherMonthLunarTextColor 其它农历字体颜色
     */
    @Deprecated
    public void setTextColor(int curMonthTextColor,
                             int otherMonthColor,
                             int curMonthLunarTextColor,
                             int otherMonthLunarTextColor) {
        mDelegate.setTextColor(mDelegate.getCurDayTextColor(), curMonthTextColor, otherMonthColor, curMonthLunarTextColor, otherMonthLunarTextColor);
    }


    /**
     * 设置文本颜色
     *
     * @param currentDayTextColor      今天字体颜色
     * @param curMonthTextColor        当前月份字体颜色
     * @param otherMonthColor          其它月份字体颜色
     * @param curMonthLunarTextColor   当前月份农历字体颜色
     * @param otherMonthLunarTextColor 其它农历字体颜色
     */
    @SuppressWarnings("unused")
    public void setTextColor(
            int currentDayTextColor,
            int curMonthTextColor,
            int otherMonthColor,
            int curMonthLunarTextColor,
            int otherMonthLunarTextColor) {
        mDelegate.setTextColor(currentDayTextColor, curMonthTextColor, otherMonthColor, curMonthLunarTextColor, otherMonthLunarTextColor);
    }

    /**
     * 设置选择的效果
     *
     * @param selectedThemeColor 选中的标记颜色
     * @param selectedTextColor  选中的字体颜色
     */
    @Deprecated
    public void setSelectedColor(int selectedThemeColor, int selectedTextColor) {
        mDelegate.setSelectColor(selectedThemeColor, selectedTextColor, mDelegate.getSelectedLunarTextColor());
    }


    /**
     * 设置选择的效果
     *
     * @param selectedThemeColor     选中的标记颜色
     * @param selectedTextColor      选中的字体颜色
     * @param selectedLunarTextColor 选中的农历字体颜色
     */
    @Deprecated
    public void setSelectedColor(int selectedThemeColor, int selectedTextColor, int selectedLunarTextColor) {
        mDelegate.setSelectColor(selectedThemeColor, selectedTextColor, selectedLunarTextColor);
    }


    /**
     * 设置标记的色
     *
     * @param schemeColor     标记背景色
     * @param schemeTextColor 标记字体颜色
     */
    @Deprecated
    public void setSchemeColor(int schemeColor, int schemeTextColor) {
        mDelegate.setSchemeColor(schemeColor, schemeTextColor, mDelegate.getSchemeLunarTextColor());
        mSelectLayout.setSchemeColor(schemeColor);
    }

    /**
     * 设置标记的色
     *
     * @param schemeColor     标记背景色
     * @param schemeTextColor 标记字体颜色
     */
    @Deprecated
    public void setSchemeColor(int schemeColor, int schemeTextColor, int schemeLunarTextColor) {
        mDelegate.setSchemeColor(schemeColor, schemeTextColor, schemeLunarTextColor);
        mSelectLayout.setSchemeColor(schemeColor);
    }

    /**
     * 设置星期栏的背景和字体颜色
     *
     * @param weekBackground 背景色
     * @param weekTextColor  字体颜色
     */
    @SuppressWarnings("unused")
    public void setWeeColor(int weekBackground, int weekTextColor) {
        mWeekBar.setBackgroundColor(weekBackground);
        mWeekBar.setTextColor(weekTextColor);
    }


    /**
     * 更新界面，
     * 重新设置颜色等都需要调用该方法
     */
    @SuppressWarnings("unused")
    public void update() {
        mSelectLayout.update();
        mMonthPager.updateScheme();
        mWeekPager.updateScheme();
    }

    /**
     * 获取选择的日期
     */
    @SuppressWarnings("unused")
    public Calendar getSelectedCalendar() {
        return mDelegate.mSelectedCalendar;
    }


    /**
     * 年份改变事件，快速年份切换
     */
    public interface OnYearChangeListener {
        void onYearChange(int year);
    }

    /**
     * 日期改变、左右切换、快速年份、月份切换
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public interface OnDateChangeListener {
        /**
         * 这个方法是准确传递的，但和onDateSelected一样会跟新日历选中状态，造成误区，故新版本建议弃用，
         * 统一使用onDateSelected
         */
        @Deprecated
        void onDateChange(Calendar calendar);

        @Deprecated
        void onYearChange(int year);
    }


    /**
     * 内部日期选择，不暴露外部使用
     */
    interface OnInnerDateSelectedListener {
        void onDateSelected(Calendar calendar);

        void onWeekSelected(Calendar calendar);
    }

    /**
     * 外部日期选择事件
     */
    public interface OnDateSelectedListener {
        void onDateSelected(Calendar calendar);
    }
}
