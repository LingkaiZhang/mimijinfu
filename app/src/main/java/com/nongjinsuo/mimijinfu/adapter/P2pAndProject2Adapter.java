package com.nongjinsuo.mimijinfu.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextPaint;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.DingTouBaoListActivity;
import com.nongjinsuo.mimijinfu.activity.HuoTouBaoListActivity;
import com.nongjinsuo.mimijinfu.beans.DataBean;
import com.nongjinsuo.mimijinfu.beans.DingTouHuoTouModel;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.TrailerOneModel2;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.PxAndDip;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.TimeUtil;
import com.nongjinsuo.mimijinfu.util.ViewHolder;

import java.util.List;

//https://github.com/younfor/PinnedAndPulledHeaderListView
public class P2pAndProject2Adapter extends BaseExpandableListAdapter {
    private Context context;
    private List<DingTouHuoTouModel.ResultBean.ListBean> listBeanList;
    private LayoutInflater inflater;
    //用于退出 Activity,避免 Countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownCounters;
    public void setListBeanList(List<DingTouHuoTouModel.ResultBean.ListBean> listBeanList) {
        this.listBeanList = listBeanList;
        notifyDataSetChanged();
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
    public P2pAndProject2Adapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.countDownCounters = new SparseArray<>();
    }

    @Override
    public DataBean getChild(int groupPosition, int childPosition) {
        return listBeanList.get(groupPosition).getData().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_project2, null);
        }
        TextView tvNhsy = ViewHolder.get(convertView, R.id.tvNhsy);
        TextView tvTime = ViewHolder.get(convertView, R.id.tvTime);
        TextView tvShengyu = ViewHolder.get(convertView, R.id.tvShengyu);
        TextView tvType = ViewHolder.get(convertView, R.id.tvType);
        final TextView tvQtz = ViewHolder.get(convertView, R.id.tvQtz);
        TextView tvPlusRate = ViewHolder.get(convertView, R.id.tvPlusRate);

        TextView tvSylDw = ViewHolder.get(convertView, R.id.tvSylDw);
        View rlStatus = ViewHolder.get(convertView, R.id.rlStatus);

        final DataBean investVo = listBeanList.get(groupPosition).getData().get(childPosition);
        if (groupPosition == 0){
            tvQtz.setTextSize(12);
            tvQtz.setVisibility(View.VISIBLE);
            tvNhsy.setText(investVo.getBasicRate());
            if (TextUtil.IsNotEmpty(investVo.getPlusRate())){
                tvPlusRate.setText(investVo.getPlusRate());
                tvPlusRate.setVisibility(View.VISIBLE);
            }else{
                tvPlusRate.setVisibility(View.GONE);
            }
            tvTime.setText(investVo.getNameSimplify());
            tvShengyu.setText(investVo.getResidueMoneyDescr());
            tvQtz.setText(investVo.getStatusDescr());
            tvType.setText(investVo.getBackMoneyClassName());
            if (investVo.getStatus() == 1) {
                rlStatus.setBackgroundResource(R.drawable.shape_qiangtou_btn);
                tvQtz.setTextColor(context.getResources().getColor(R.color.white));
            }else if(investVo.getStatus() == 2){
                rlStatus.setBackgroundResource(R.drawable.shape_qiangtou_bg_hui_btn);
                tvQtz.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                rlStatus.setBackgroundResource(R.drawable.shape_qiangtou_hui_btn);
                tvQtz.setTextColor(context.getResources().getColor(R.color.color_8f8f8f));
            }
        }else if(groupPosition == 1){
            tvNhsy.setText(investVo.getBasicRate());
            if (TextUtil.IsNotEmpty(investVo.getPlusRate())){
                tvPlusRate.setText(investVo.getPlusRate());
                tvPlusRate.setVisibility(View.VISIBLE);
            }else{
                tvPlusRate.setVisibility(View.GONE);
            }
            tvType.setText(investVo.getDeadlineDescr());
            tvShengyu.setText(investVo.getResidueMoneyDescr());
            tvTime.setText(investVo.getDeadline());
            //显示倒计时
            CountDownTimer countDownTimer = countDownCounters.get(tvQtz.hashCode());
            //将前一个缓存清除
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            tvQtz.setTextSize(12);
            switch (investVo.getStatus()) {
                case Constants.STATUS_0_ZBSX:
                    long timer = investVo.getBeginTimeStamp()*1000;
                    timer = timer - System.currentTimeMillis();
                    if (timer > 0) {
                        countDownTimer = new CountDownTimer(timer, 1000) {
                            public void onTick(long millisUntilFinished) {
                                tvQtz.setText(TimeUtil.getCountTimeByLong(millisUntilFinished));
                                Log.e("TAG", investVo.getName() + " :  " + millisUntilFinished);
                            }
                            public void onFinish() {
                                tvQtz.setText("00:00");
                                getOneCar(investVo, groupPosition,childPosition);
                            }
                        }.start();
                        countDownCounters.put(tvQtz.hashCode(), countDownTimer);
                    } else {
                        getOneCar(investVo, groupPosition,childPosition);
                    }
                    tvQtz.setTextSize(14);
                    tvNhsy.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                    tvSylDw.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                    tvTime.setTextColor(context.getResources().getColor(R.color.color_homepage_quanbu));

                    rlStatus.setBackgroundResource(R.drawable.shape_qiangtou_btn);
                    break;
                case Constants.STATUS_1_ZCZ:
                    tvQtz.setText(investVo.getStatusname());
                    tvNhsy.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                    tvSylDw.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                    tvTime.setTextColor(context.getResources().getColor(R.color.color_homepage_quanbu));

                    tvQtz.setTextColor(context.getResources().getColor(R.color.white));
                    rlStatus.setBackgroundResource(R.drawable.shape_qiangtou_btn);
                    break;
                case Constants.STATUS_2_DSZ:
                    tvQtz.setText(investVo.getStatusname());
                    tvNhsy.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                    tvSylDw.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                    tvTime.setTextColor(context.getResources().getColor(R.color.color_homepage_quanbu));

                    tvQtz.setTextColor(context.getResources().getColor(R.color.white));
                    rlStatus.setBackgroundResource(R.drawable.shape_qiangtou_bg_hui_btn);
                    break;
                case Constants.STATUS_4_TPWC:
                    tvQtz.setText(investVo.getStatusname());
                    tvNhsy.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                    tvSylDw.setTextColor(context.getResources().getColor(R.color.color_ff3947));
                    tvTime.setTextColor(context.getResources().getColor(R.color.color_homepage_quanbu));

                    tvQtz.setTextColor(context.getResources().getColor(R.color.white));
                    rlStatus.setBackgroundResource(R.drawable.shape_qiangtou_bg_hui_btn);
                    break;
                case Constants.STATUS_5_ZCJS:
                    tvQtz.setText(investVo.getStatusname());
                    tvQtz.setTextColor(context.getResources().getColor(R.color.color_8f8f8f));
                    tvNhsy.setTextColor(context.getResources().getColor(R.color.color_8f8f8f));
                    tvSylDw.setTextColor(context.getResources().getColor(R.color.color_8f8f8f));
                    tvTime.setTextColor(context.getResources().getColor(R.color.color_8f8f8f));
                    rlStatus.setBackgroundResource(R.drawable.shape_qiangtou_hui_btn);
                    break;
            }

        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition < 0)
            return 0;
        return listBeanList.get(groupPosition).getData().size();
    }

    @Override
    public DingTouHuoTouModel.ResultBean.ListBean getGroup(int groupPosition) {
        return listBeanList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listBeanList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        //普通分组
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.group, null);
        }
        ImageView ivIcon = ViewHolder.get(convertView,R.id.ivIcon);
        TextView tvTitle = ViewHolder.get(convertView,R.id.tvTitle);
        TextView tvMore = ViewHolder.get(convertView,R.id.tvMore);
        DingTouHuoTouModel.ResultBean.ListBean listBean = getGroup(groupPosition);
        tvTitle.setText(listBean.getTitle());
        if (groupPosition == 0){
            ivIcon.setImageResource(R.drawable.icon_tba);
            tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DingTouBaoListActivity.class);
                    context.startActivity(intent);
                }
            });
        }else if(groupPosition == 1){
            ivIcon.setImageResource(R.drawable.icon_tbb);
            tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HuoTouBaoListActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public void getOneCar(DataBean trailerVo, final int groupPosition, final  int childPosition){
        JacksonRequest<TrailerOneModel2> jacksonRequest = new JacksonRequest<>(RequestMap.getOneCar(trailerVo.getBm()), TrailerOneModel2.class, new Response.Listener<TrailerOneModel2>() {
            @Override
            public void onResponse(TrailerOneModel2 baseVo) {
                if (baseVo != null) {
                    if (baseVo.code.equals(Constants.SUCCESS)) {
                        if (listBeanList!=null){
                            listBeanList.get(groupPosition).getData().set(childPosition,baseVo.getResult());
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
