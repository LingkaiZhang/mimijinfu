package com.nongjinsuo.mimijinfu.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.TrailerVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.httpmodel.TrailerOneModel;
import com.nongjinsuo.mimijinfu.util.CountDownTask;
import com.nongjinsuo.mimijinfu.util.CountDownTimers;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.PxAndDip;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.TimeUtil;
import com.nongjinsuo.mimijinfu.util.UtilDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author czz
 * @time 2016/10/24$ 11:10$
 * @Description: (热卖Adapter 2018.1.15第二版改版)
 */
public class Project2Adapter extends BaseAdapter {

    private Context context;
    private List<TrailerVo> caseList = new ArrayList<>();
    //用于退出 Activity,避免 Countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownCounters;

    public Project2Adapter(Context context) {
        this.context = context;
        this.countDownCounters = new SparseArray<>();
    }

    /**
     * 清空当前 CountTimeDown 资源
     */
    public void cancelAllTimers() {
        if (countDownCounters == null) {
            return;
        }
        LogUtil.e("TAG", "size :  " + countDownCounters.size());
        for (int i = 0, length = countDownCounters.size(); i < length; i++) {
            CountDownTimer cdt = countDownCounters.get(countDownCounters.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

    public List<TrailerVo> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<TrailerVo> caseList) {
        this.caseList = caseList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return caseList.size();
    }

    @Override
    public TrailerVo getItem(int i) {
        return caseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class MyViewHolder {
        public TextView tvNhsy;//年化收益率
        public TextView tvPlusRate;//加息的年化收益
        public TextView tvSylDw;//年化收益率单位
        public TextView tvTime;//90天还款
        public TextView tvShengyu;//剩余
        public TextView tvType; //期限
        public TextView tvQtz;//状态信息
        public View rlStatus;//状态信息

        public View viewLine;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final MyViewHolder myViewHolder;
        if (view == null) {
            myViewHolder = new MyViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_project2, null);

            myViewHolder.tvNhsy = (TextView) view.findViewById(R.id.tvNhsy);
            myViewHolder.tvPlusRate = (TextView) view.findViewById(R.id.tvPlusRate);
            myViewHolder.tvSylDw = (TextView) view.findViewById(R.id.tvSylDw);
            myViewHolder.tvTime = (TextView) view.findViewById(R.id.tvTime);
            myViewHolder.tvShengyu = (TextView) view.findViewById(R.id.tvShengyu);
            myViewHolder.tvType = (TextView) view.findViewById(R.id.tvType);
            myViewHolder.tvQtz = (TextView) view.findViewById(R.id.tvQtz);
            myViewHolder.rlStatus = view.findViewById(R.id.rlStatus);
            myViewHolder.viewLine = view.findViewById(R.id.viewLine);

            view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) view.getTag();
        }
        final TrailerVo item = caseList.get(i);

        myViewHolder.tvNhsy.setText(item.getBasicRate());
        if (TextUtil.IsNotEmpty(item.getPlusRate())) {
            myViewHolder.tvPlusRate.setText(item.getPlusRate());
            myViewHolder.tvPlusRate.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.tvPlusRate.setVisibility(View.GONE);
        }
        myViewHolder.tvType.setText(item.getDeadlineDescr());
        myViewHolder.tvShengyu.setText(item.getResidueMoneyDescr());
        myViewHolder.tvTime.setText(item.getDeadline());
        //显示倒计时
        CountDownTimer countDownTimer = countDownCounters.get(myViewHolder.tvQtz.hashCode());
        //将前一个缓存清除
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        myViewHolder.tvQtz.setTextSize(12);
        switch (item.getStatus()) {
            case Constants.STATUS_0_ZBSX:
                long timer = item.getBeginTimeStamp()*1000;
                timer = timer - System.currentTimeMillis();
                if (timer > 0) {
                    countDownTimer = new CountDownTimer(timer, 1000) {
                        public void onTick(long millisUntilFinished) {
                            myViewHolder.tvQtz.setText(TimeUtil.getCountTimeByLong(millisUntilFinished));
                            Log.e("TAG", item.getName() + " :  " + millisUntilFinished);
                        }
                        public void onFinish() {
                            myViewHolder.tvQtz.setText("00:00");
                            getOneCar(item, i);
                        }
                    }.start();
                    countDownCounters.put(myViewHolder.tvQtz.hashCode(), countDownTimer);
                } else {
                    getOneCar(item, i);
                }
                myViewHolder.tvQtz.setTextSize(14);
                myViewHolder.tvNhsy.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                myViewHolder.tvSylDw.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                myViewHolder.tvTime.setTextColor(context.getResources().getColor(R.color.color_homepage_quanbu));
                myViewHolder.rlStatus.setBackgroundResource(R.drawable.shape_qiangtou_btn);
                break;
            case Constants.STATUS_1_ZCZ:
                myViewHolder.tvQtz.setText(item.getStatusname());
                myViewHolder.tvNhsy.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                myViewHolder.tvSylDw.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                myViewHolder.tvTime.setTextColor(context.getResources().getColor(R.color.color_homepage_quanbu));

                myViewHolder.tvQtz.setTextColor(context.getResources().getColor(R.color.white));
                myViewHolder.rlStatus.setBackgroundResource(R.drawable.shape_qiangtou_btn);
                break;
            case Constants.STATUS_2_DSZ:
                myViewHolder.tvQtz.setText(item.getStatusname());
                myViewHolder.tvNhsy.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                myViewHolder.tvSylDw.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                myViewHolder.tvTime.setTextColor(context.getResources().getColor(R.color.color_homepage_quanbu));

                myViewHolder.tvQtz.setTextColor(context.getResources().getColor(R.color.white));
                myViewHolder.rlStatus.setBackgroundResource(R.drawable.shape_qiangtou_bg_hui_btn);
                break;
            case Constants.STATUS_4_TPWC:
                myViewHolder.tvQtz.setText(item.getStatusname());
                myViewHolder.tvNhsy.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                myViewHolder.tvSylDw.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                myViewHolder.tvTime.setTextColor(context.getResources().getColor(R.color.color_homepage_quanbu));

                myViewHolder.tvQtz.setTextColor(context.getResources().getColor(R.color.white));
                myViewHolder.rlStatus.setBackgroundResource(R.drawable.shape_qiangtou_bg_hui_btn);
                break;
            case Constants.STATUS_5_ZCJS:
                myViewHolder.tvQtz.setText(item.getStatusname());
                myViewHolder.tvQtz.setTextColor(context.getResources().getColor(R.color.color_8f8f8f));
                myViewHolder.tvNhsy.setTextColor(context.getResources().getColor(R.color.color_8f8f8f));
                myViewHolder.tvSylDw.setTextColor(context.getResources().getColor(R.color.color_8f8f8f));
                myViewHolder.tvTime.setTextColor(context.getResources().getColor(R.color.color_8f8f8f));
                myViewHolder.rlStatus.setBackgroundResource(R.drawable.shape_qiangtou_hui_btn);
                break;
        }
        myViewHolder.viewLine.setVisibility(i == getCount() - 1 ? View.GONE : View.VISIBLE);
        return view;
    }

    public void getOneCar(TrailerVo trailerVo, final int position) {
        JacksonRequest<TrailerOneModel> jacksonRequest = new JacksonRequest<>(RequestMap.getOneCar(trailerVo.getBm()), TrailerOneModel.class, new Response.Listener<TrailerOneModel>() {
            @Override
            public void onResponse(TrailerOneModel baseVo) {
                if (baseVo != null) {
                    if (baseVo.code.equals(Constants.SUCCESS)) {
                        if (caseList != null) {
                            caseList.set(position, baseVo.getResult());
                            notifyDataSetChanged();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }
}