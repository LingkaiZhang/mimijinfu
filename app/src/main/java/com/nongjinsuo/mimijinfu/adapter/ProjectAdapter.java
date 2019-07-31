package com.nongjinsuo.mimijinfu.adapter;

import android.content.Context;
import android.os.CountDownTimer;
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
import com.nongjinsuo.mimijinfu.util.UtilDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author czz
 * @time 2016/10/24$ 11:10$
 * @Description: (热卖Adapter)
 */
public class ProjectAdapter extends BaseAdapter {

    private Context context;
    private List<TrailerVo> caseList = new ArrayList<>();

    public ProjectAdapter(Context context) {
        this.context = context;
        this.countDownCounters = new SparseArray<>();
    }

    //用于退出 Activity,避免 Countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownCounters;

    public ProjectAdapter(Context context, List<TrailerVo> caseList) {
        this.context = context;
        this.caseList = caseList;
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
        public ImageView ivStatus;
        public ImageView ivYwcBg;
        public ImageView ivPic;
        public TextView tvName;
        public TextView tvZdhgj;
        public TextView tvZdhgjDanWei;
        public TextView tvMbje;
        public TextView tvMbjeDanWei;
        public TextView tvStrSyl;
        public View viewLine;
        public View llOhter;
        public View llCountDown;
        private TextView tv_timeLeft0, tv_timeLeft, tv_timeMid, tv_timeRight;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final MyViewHolder myViewHolder;
        if (view == null) {
            myViewHolder = new MyViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_project, null);
            myViewHolder.ivPic = (ImageView) view.findViewById(R.id.ivPic);
            myViewHolder.ivYwcBg = (ImageView) view.findViewById(R.id.ivYwcBg);
            myViewHolder.ivStatus = (ImageView) view.findViewById(R.id.ivStatus);
            myViewHolder.tvName = (TextView) view.findViewById(R.id.tvName);

            myViewHolder.tvZdhgj = (TextView) view.findViewById(R.id.tvZdhgj);
            myViewHolder.tvZdhgjDanWei = (TextView) view.findViewById(R.id.tvZdhgjDanWei);

            myViewHolder.tvMbje =(TextView) view.findViewById(R.id.tvMbje);
            myViewHolder.tvMbjeDanWei = (TextView) view.findViewById(R.id.tvMbjeDanWei);

            myViewHolder.tvStrSyl =(TextView) view.findViewById(R.id.tvStrSyl);
            myViewHolder.viewLine =  view.findViewById(R.id.viewLine);

            myViewHolder.viewLine =  view.findViewById(R.id.viewLine);

            myViewHolder.llOhter =  view.findViewById(R.id.llOhter);
            myViewHolder.llCountDown =  view.findViewById(R.id.llCountDown);


            myViewHolder.tv_timeLeft0 = (TextView) view.findViewById(R.id.tv_timeLeft0);
            myViewHolder.tv_timeLeft = (TextView) view.findViewById(R.id.tv_timeLeft);
            myViewHolder.tv_timeMid = (TextView) view.findViewById(R.id.tv_timeMid);
            myViewHolder.tv_timeRight = (TextView) view.findViewById(R.id.tv_timeRight);


            view.setTag(myViewHolder);
        }else{
            myViewHolder = (MyViewHolder) view.getTag();
        }
        final TrailerVo item = getItem(i);

        Glide.with(AiMiCrowdFundingApplication.context()).load(Urls.PROJECT_URL + item.getCarImageCover()).placeholder(R.drawable.img_ltbmr).crossFade().into(myViewHolder.ivPic);
        myViewHolder.tvName.setText(item.getName());

        myViewHolder.tvZdhgj.setText(item.getNeedMoney());
        myViewHolder.tvZdhgjDanWei.setText(item.getNeedUnit());

        myViewHolder.ivStatus.setImageResource(Constants.STATUS_IMGS[item.getStatus()]);
        myViewHolder.tvStrSyl.setText(item.getDescr());
        switch (item.getStatus()) {
            case Constants.STATUS_0_ZBSX:
                myViewHolder.llOhter.setVisibility(View.GONE);
                myViewHolder.llCountDown.setVisibility(View.VISIBLE);
                myViewHolder.tvMbjeDanWei.setVisibility(View.GONE);
                startCountDown(item.getSurplustime(), myViewHolder,item,i);
                myViewHolder.ivStatus.setVisibility(View.VISIBLE);
                myViewHolder.ivYwcBg.setVisibility(View.GONE);
                break;
            case Constants.STATUS_1_ZCZ:
                myViewHolder.llOhter.setVisibility(View.VISIBLE);
                myViewHolder.llCountDown.setVisibility(View.GONE);
                myViewHolder.tvMbje.setText(item.getResidueMoney());
                myViewHolder.tvMbjeDanWei.setText(item.getResidueUnit());
                myViewHolder.tvMbjeDanWei.setVisibility(View.VISIBLE);
                myViewHolder.ivStatus.setVisibility(View.VISIBLE);
                myViewHolder.ivYwcBg.setVisibility(View.GONE);
                break;
            case Constants.STATUS_2_DSZ:
                myViewHolder.llOhter.setVisibility(View.VISIBLE);
                myViewHolder.llCountDown.setVisibility(View.GONE);
                myViewHolder.tvMbje.setText(item.getInvestUsedTime());
                myViewHolder.tvMbjeDanWei.setVisibility(View.GONE);
                myViewHolder.ivStatus.setVisibility(View.VISIBLE);
                myViewHolder.ivYwcBg.setVisibility(View.GONE);
                break;
            case Constants.STATUS_4_TPWC:
                myViewHolder.llOhter.setVisibility(View.VISIBLE);
                myViewHolder.llCountDown.setVisibility(View.GONE);
                myViewHolder.tvMbje.setText(item.getDueDays());
                myViewHolder.tvMbjeDanWei.setText("天");
                myViewHolder.tvMbjeDanWei.setVisibility(View.VISIBLE);
                myViewHolder.ivStatus.setVisibility(View.VISIBLE);
                myViewHolder.ivYwcBg.setVisibility(View.GONE);
                break;
            case Constants.STATUS_5_ZCJS:
                myViewHolder.llOhter.setVisibility(View.VISIBLE);
                myViewHolder.llCountDown.setVisibility(View.GONE);
                myViewHolder.tvMbje.setText(item.getActualRate());
                myViewHolder.tvMbjeDanWei.setText("%");
                myViewHolder.tvMbjeDanWei.setVisibility(View.VISIBLE);
                myViewHolder.ivStatus.setVisibility(View.GONE);
                myViewHolder.ivYwcBg.setVisibility(View.VISIBLE);
                break;
        }
        myViewHolder.viewLine.setVisibility(i == getCount() - 1 ? View.GONE : View.VISIBLE);
        return view;
    }

    public void setCountDownTask(CountDownTask countDownTask) {
        if (!Objects.equals(mCountDownTask, countDownTask)) {
            mCountDownTask = countDownTask;
            notifyDataSetChanged();
        }
    }
    private CountDownTask mCountDownTask;
    private void startCountDown(String time, final MyViewHolder t, final TrailerVo trailerVo, final int i) {
        long longTime = 0;
        if (mCountDownTask != null) {
            try {
                longTime = Long.parseLong(time);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            mCountDownTask.until(t.tv_timeLeft, CountDownTask.elapsedRealtime() + 1000 * longTime, 1000, new CountDownTimers.OnCountDownListener() {
                @Override
                public void onTick(View view, long millisUntilFinished) {
                    long sec = (millisUntilFinished - 1) / 1000;
                    LogUtil.d("millisUntilFinished:"+millisUntilFinished+"---sec:"+sec);
                    String[] s = UtilDate.formatDuring(sec);
                    t.tv_timeLeft.setText(s[1]);
                    t.tv_timeMid.setText(s[2]);
                    t.tv_timeRight.setText(s[3]);
                    if (sec == 0){
                        //请求接口更新单条数据
//                        Toast.makeText(context,"请求接口更新单条数据",Toast.LENGTH_SHORT).show();
                        getOneCar(trailerVo,i);
                    }
                }

                @Override
                public void onFinish(View view) {

                }
            });
        }
    }
    public void getOneCar(TrailerVo trailerVo, final int position){
        JacksonRequest<TrailerOneModel> jacksonRequest = new JacksonRequest<>(RequestMap.getOneCar(trailerVo.getBm()), TrailerOneModel.class, new Response.Listener<TrailerOneModel>() {
            @Override
            public void onResponse(TrailerOneModel baseVo) {
                if (baseVo != null) {
                    if (baseVo.code.equals(Constants.SUCCESS)) {
                        if (caseList!=null){
                            caseList.set(position,baseVo.getResult());
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