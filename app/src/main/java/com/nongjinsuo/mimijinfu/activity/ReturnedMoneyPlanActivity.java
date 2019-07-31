package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.ReturnedMoneyModel;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: (回款计划)
 * autour: czz
 * date: 2017/12/4 0004 上午 10:21
 * update: 2017/12/4 0004
 */

public class ReturnedMoneyPlanActivity extends AbstractActivity implements
        CalendarView.OnDateSelectedListener, CalendarView.OnDateChangeListener {
    @BindView(R.id.ivlastMonth)
    ImageView ivlastMonth;
    @BindView(R.id.ivnextMonth)
    ImageView ivnextMonth;
    @BindView(R.id.tvHdjr)
    TextView tvHdjr;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.llTzr)
    LinearLayout llTzr;
    @BindView(R.id.tvLookAll)
    TextView tvLookAll;
    @BindView(R.id.ivNoData)
    ImageView ivNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.rlNoDataView)
    RelativeLayout rlNoDataView;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.tvMonthZd)
    TextView tvMonthZd;
    @BindView(R.id.tvMonthYsbx)
    TextView tvMonthYsbx;
    @BindView(R.id.tvMonthDsbx)
    TextView tvMonthDsbx;
    @BindView(R.id.tvMonthXztz)
    TextView tvMonthXztz;
    @BindView(R.id.tvDayZd)
    TextView tvDayZd;
    @BindView(R.id.tvDayYsbx)
    TextView tvDayYsbx;
    @BindView(R.id.tvDayDsbx)
    TextView tvDayDsbx;
    @BindView(R.id.tvDayXztz)
    TextView tvDayXztz;
    private HashMap<Integer, List<ReturnedMoneyModel.ResultBean>> yearHashMap = new HashMap<>(); //根据年获取 年份对应的数据
    private HashMap<String, ReturnedMoneyModel.ResultBean> monthHashMap;//根据年月获取 月份对应的数据
    private HashMap<String, ReturnedMoneyModel.ResultBean.DayBean> dayHashMap;//根据年月日获取 日对应的数据

    private HashMap<Integer, Boolean> isExistYearCalendar;//根据年 判断当前年份对应的回款数据是否加载过。

    private HashMap<String, Boolean> isExistMonthCalendar;//根据年月 判断当前年月对应的回款数据是否加载过。

    private List<Calendar> schemes;

    @Override
    public void init() {
        monthHashMap = new HashMap<>();
        dayHashMap = new HashMap<>();

        isExistYearCalendar = new HashMap<Integer, Boolean>();
        isExistMonthCalendar = new HashMap<>();
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void addListener() {
        mCalendarView.setOnDateChangeListener(this);
        mCalendarView.setOnDateSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returned_money_plan);
        ButterKnife.bind(this);
        titleView.setText("回款日历");
        init();
        addListener();
//        loadData(true, mCalendarView.getCurYear(), mCalendarView.getCurMonth());
    }

    private void loadData(boolean isFirst, final int year, final int month) {
        tvMonthZd.setText(mCalendarView.getCurMonth() + "月账单");
        tvDayZd.setText(mCalendarView.getCurDay() + "日账单");
        if (!NetworkUtils.isNetworkAvailable(this)) {
            rlNoDataView.setVisibility(View.VISIBLE);
            tvNoData.setText(getString(R.string.str_no_network));
            ivNoData.setImageResource(R.drawable.img_wwl);
            return;
        }
        if (isFirst)
            showLoading();
        rlNoDataView.setVisibility(View.GONE);
        JacksonRequest<ReturnedMoneyModel> jacksonRequest = new JacksonRequest<>(RequestMap.userBackMoney(year + ""), ReturnedMoneyModel.class, new Response.Listener<ReturnedMoneyModel>() {
            @Override
            public void onResponse(ReturnedMoneyModel baseVo) {
                cancleLoading();
                if (baseVo.getCode().equals(Constants.SUCCESS)) {
                    //记录年对应的回款数据加载过
                    isExistYearCalendar.put(year, true);
                    //保存年对应的回款数据
                    yearHashMap.put(year, baseVo.getResult());

                    //根据年回款数据设置，对应月份的数据到日历页面。
                    setCalendarForMonth(year, month);
                } else {
//                    noData();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                cancleLoading();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    /**
     * 根据年月份设置日期选中
     */
    private void setCalendarForMonth(int year, int month) {
        //根据年份获取到年份对应的数据集
        List<ReturnedMoneyModel.ResultBean> resultBeans;
        if (yearHashMap != null) {
            resultBeans = yearHashMap.get(year);
        } else {
            resultBeans = new ArrayList<>();
        }
        List<Calendar> linShischemes = new ArrayList<>();
        if (resultBeans!=null&&resultBeans.size() > 0) {
            for (int i = 0; i < resultBeans.size(); i++) {
                ReturnedMoneyModel.ResultBean resultBean = resultBeans.get(i);
                monthHashMap.put(resultBean.getDate(), resultBean);
                List<ReturnedMoneyModel.ResultBean.DayBean> day = resultBean.getDay();
                //判断当天日期，显示账单
                if (isExistMonthCalendar.get(year + "-" + month)==null) { //如果此月份没有在日历上加载过 ，才去加载。
                    String[] monthDay = resultBean.getDate().split("-");
                    String tMonth = month + "";
                    if (month < 10) {
                        tMonth = "0" + month;
                    }
                    if (monthDay[0].equals(year + "") && monthDay[1].equals(tMonth)) {
                        isExistMonthCalendar.put(year + "-" + month, true);
                        for (int j = 0; j < day.size(); j++) {
                            ReturnedMoneyModel.ResultBean.DayBean dayBean = day.get(j);
                            dayHashMap.put(dayBean.getDate(), dayBean);
                            String[] date = dayBean.getDate().split("-");
                            int yearNum = Integer.parseInt(date[0]);
                            int monthNum = Integer.parseInt(date[1]);
                            int dayNum = Integer.parseInt(date[2]);
                            if (dayBean.getColor().equals("blue"))
                                linShischemes.add(getSchemeCalendar(yearNum, monthNum, dayNum, 0xFF74b2ff, ""));
                            else
                                linShischemes.add(getSchemeCalendar(yearNum, monthNum, dayNum, 0xFFFFc655, ""));

                            int todayDay = mCalendarView.getCurDay();

                            if (month < 10) {
                                tMonth = "0" + month;
                            }
                            String tDay = todayDay + "";
                            if (todayDay < 10) {
                                tDay = "0" + todayDay;
                            }

                            if (date[0].equals(year + "") && date[1].equals(tMonth) && date[2].equals(tDay)) {
                                setDayZd(dayBean, todayDay);
                            } /*else {
                                setDayZd(null, todayDay);
                            }*/
                        }
                        setMonthZd(resultBean, month);
                    } else {
                        setMonthZd(null, month);
                    }
                    if (schemes == null) {
                        schemes = linShischemes;
                    } else {
                        schemes.addAll(linShischemes);
                    }

                }

            }
        } else {

        }
        mCalendarView.setSchemeDate(schemes);
    }

    /**
     * 设置日账单
     *
     * @param resultBean
     * @param todayDay
     */
    private void setDayZd(ReturnedMoneyModel.ResultBean.DayBean resultBean, int todayDay) {
        tvDayZd.setText(todayDay + "日账单");
        if (resultBean != null) {
            tvDayYsbx.setText(resultBean.getReceiveBackMoney() + resultBean.getReceiveBackUnit());
            tvDayDsbx.setText(resultBean.getCollectBackMoney() + resultBean.getCollectBackUnit());
            tvDayXztz.setText(resultBean.getInvestMoney() + resultBean.getInvestUnit());
        } else {
            tvDayYsbx.setText("0元");
            tvDayDsbx.setText("0元");
            tvDayXztz.setText("0元");
        }
    }

    /**
     * 设置月账单
     *
     * @param resultBean
     * @param todayMonth
     */
    private void setMonthZd(ReturnedMoneyModel.ResultBean resultBean, int todayMonth) {
        tvMonthZd.setText(todayMonth + "月账单");
        if (resultBean != null) {
            tvMonthYsbx.setText(resultBean.getReceiveBackMoney() + resultBean.getReceiveBackUnit());
            tvMonthDsbx.setText(resultBean.getCollectBackMoney() + resultBean.getCollectBackUnit());
            tvMonthXztz.setText(resultBean.getInvestMoney() + resultBean.getInvestUnit());
        } else {
            tvMonthYsbx.setText("0元");
            tvMonthDsbx.setText("0元");
            tvMonthXztz.setText("0元");
        }
    }

    private void noData() {
        rlNoDataView.setVisibility(View.VISIBLE);
        tvNoData.setText(getString(R.string.str_no_data));
        ivNoData.setImageResource(R.drawable.img_wnr_49);
        tvRefresh.setVisibility(View.GONE);
    }

    @OnClick({R.id.ivlastMonth, R.id.ivnextMonth, R.id.tvHdjr, R.id.tvLookAll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivlastMonth:
                mCalendarView.lastMonth();
                break;
            case R.id.ivnextMonth:
                mCalendarView.nextMonth();
                break;
            case R.id.tvHdjr:
                mCalendarView.scrollToCurrent();
                break;
            case R.id.tvLookAll:
                Intent intent = new Intent(this, AllDueInActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDateChange(Calendar calendar) {
        title.setText(calendar.getYear() + "年" + calendar.getMonth() + "月");
        if (yearHashMap != null) {
            //如果切换的年份数据是空的，并且年份数据没加载过。
            List<ReturnedMoneyModel.ResultBean> resultBeans = yearHashMap.get(calendar.getYear());
            if (resultBeans == null && isExistYearCalendar.get(calendar.getYear())==null) {
                loadData(false, calendar.getYear(), calendar.getMonth());
            } else {
                setCalendarForMonth(calendar.getYear(), calendar.getMonth());
            }
        }


        //根据年月日获取到对象
        String month = calendar.getMonth() + "";
        if (calendar.getMonth() < 10) {
            month = "0" + calendar.getMonth();
        }
        String day = calendar.getDay() + "";
        if (calendar.getDay() < 10) {
            day = "0" + calendar.getDay();
        }
        ReturnedMoneyModel.ResultBean resultBean = monthHashMap.get(calendar.getYear() + "-" + month);
        ReturnedMoneyModel.ResultBean.DayBean dayBean = dayHashMap.get(calendar.getYear() + "-" + month + "-" + day);
        setMonthZd(resultBean, calendar.getMonth());
        setDayZd(dayBean, calendar.getDay());
    }

    @Override
    public void onYearChange(int year) {

    }

    @Override
    public void onDateSelected(Calendar calendar) {
        onDateChange(calendar);
    }
}
