package com.nongjinsuo.mimijinfu.activity;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.BaseVo;
import com.nongjinsuo.mimijinfu.beans.TouPiaoVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.httpmodel.TouPiaoModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.PxAndDip;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.TimeUtil;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.fanrunqi.waveprogress.WaveProgressView;
import de.greenrobot.event.EventBus;

/**
 * @author czz
 * @Description: (投票页面)
 */
public class VoteActivity extends AbstractActivity {
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvZcje)
    TextView tvZcje;
    @BindView(R.id.tvMfcj)
    TextView tvMfcj;
    @BindView(R.id.tvXmzq)
    TextView tvXmzq;
    @BindView(R.id.tvRgje)
    TextView tvRgje;
    @BindView(R.id.tvYjbqsy)
    TextView tvYjbqsy;
    @BindView(R.id.tvTpbz)
    TextView tvTpbz;
    @BindView(R.id.ivTpStatus)
    ImageView ivTpStatus;
    @BindView(R.id.waterWaveProgress_zc)
    WaveProgressView waterWaveProgressZc;
    @BindView(R.id.tvZc)
    TextView tvZc;
    @BindView(R.id.waterWaveProgress_fd)
    WaveProgressView waterWaveProgressFd;
    @BindView(R.id.tvFd)
    TextView tvFd;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.tvTimeStr)
    TextView tvTimeStr;
    private String projectBm;
    private long surplustime;
    Timer timer;
    TimerTask timerTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        ButterKnife.bind(this);
        projectBm = getIntent().getStringExtra(Constants.PROJECTBM);
        loadData(true);
        waterWaveProgressZc.setWaveColor("#FA4242");
        waterWaveProgressFd.setWaveColor("#47A2FF");
        waterWaveProgressZc.setText("#FFFFFF", PxAndDip.sp2px(VoteActivity.this, 14));
        waterWaveProgressFd.setText("#FFFFFF", PxAndDip.sp2px(VoteActivity.this, 14));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    private void loadData(boolean isFirst) {
        if (isFirst)
            showLoading();
        JacksonRequest<TouPiaoModel> jacksonRequest = new JacksonRequest<>(RequestMap.projectvote(projectBm), TouPiaoModel.class, new Response.Listener<TouPiaoModel>() {
            @Override
            public void onResponse(TouPiaoModel baseVo) {
                cancleLoading();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    tvZcje.setText(baseVo.result.getNeedMoney());
                    tvMfcj.setText(baseVo.result.getActualRate());
                    tvXmzq.setText(baseVo.result.getEndDays());
                    tvRgje.setText(baseVo.result.getInvestMoney());
                    if (baseVo.result.getIncome().equals("0")) {
                        tvYjbqsy.setText("未投资");
                    } else {
                        tvYjbqsy.setText(baseVo.result.getIncome());
                    }
                    tvTpbz.setText("投票比重" + baseVo.result.getVoteproportion());
                    if (TextUtil.IsNotEmpty(baseVo.result.getSupportNum())) {
                        float parseFloat = Float.parseFloat(baseVo.result.getSupportNum()) * 100;
                        int opposeNum = (int) parseFloat;
                        waterWaveProgressZc.setCurrent(opposeNum, opposeNum + "%");
                    }
                    if (TextUtil.IsNotEmpty(baseVo.result.getOpposeNum())) {
                        float parseFloat = Float.parseFloat(baseVo.result.getOpposeNum()) * 100;
                        int opposeNum = (int) parseFloat;
                        waterWaveProgressFd.setCurrent(opposeNum, opposeNum + "%");
                    }
                    titleView.setText(baseVo.result.getProjectname());
                    setTopStatusView(baseVo.result);
                    if (baseVo.result.getIsinvest() == 1) {//有投票权
                        if (baseVo.result.getIsvote() == 1) {//已投票
                            setVoteView(baseVo.result.getVote());
                        } else {
                            tvZc.setClickable(true);
                            tvFd.setClickable(true);
                            ivTpStatus.setVisibility(View.GONE);
                            tvZc.setText("赞成");
                            tvZc.setBackgroundResource(R.drawable.shape_login_btn);
                            tvFd.setText("反对");
                            tvFd.setBackgroundResource(R.drawable.shape_vote_blue_btn);
                        }
                    } else {
                        tvZc.setClickable(false);
                        tvFd.setClickable(false);
                        ivTpStatus.setVisibility(View.GONE);
                        tvZc.setText("赞成");
                        tvZc.setBackgroundResource(R.drawable.shape_login_btn_hui);
                        tvFd.setText("反对");
                        tvFd.setBackgroundResource(R.drawable.shape_login_btn_hui);
                    }
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
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }

    private void setVoteView(int action) {
        ivTpStatus.setVisibility(View.VISIBLE);
        tvZc.setBackgroundResource(R.drawable.shape_login_btn_hui);
        tvFd.setBackgroundResource(R.drawable.shape_login_btn_hui);
        tvZc.setClickable(false);
        tvFd.setClickable(false);
        if (action == 1) {
            tvZc.setText("已赞成");
            tvFd.setText("反对");
        } else {
            tvZc.setText("赞成");
            tvFd.setText("已反对");
        }
    }

    public void startCountdown(final TextView timeView) {
        final Handler handler;
        handler = new Handler() {
            public void handleMessage(Message msg) {
                long obj = (Long) msg.obj;
                if (obj > 0) {
                    TimeUtil.setDjs(obj, timeView);
                }
            }
        };
        //根据返回的秒数 获取时 分
        //启动倒计时

        timerTask = new TimerTask() {

            @Override
            public void run() {
                surplustime--;
                Message msg = new Message();
                msg.obj = surplustime;
                handler.sendMessage(msg);
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);

    }

    @Override
    public void init() {

    }

    @Override
    public void addListener() {

    }

    @OnClick({R.id.tvZc, R.id.tvFd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvZc:
                vote("support");
                break;
            case R.id.tvFd:
                vote("oppose");
                break;
        }
    }

    private void vote(final String action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(VoteActivity.this);
        if (action.equals("support")) {
            builder.setMessage("操作不可逆，确认赞成售出?");
        } else {
            builder.setMessage("操作不可逆，确认反对售出?");
        }
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final MyProgressDialog dialog = new MyProgressDialog(VoteActivity.this, "正在投票");
                dialog.show();
                JacksonRequest<BaseVo> jacksonRequest = new JacksonRequest<>(RequestMap.vote(projectBm, action), BaseVo.class, new Response.Listener<BaseVo>() {
                    @Override
                    public void onResponse(BaseVo baseVo) {
                        dialog.dismiss();
                        if (baseVo.code.equals(Constants.SUCCESS)) {
                            EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROPERTY));
                            EventBus.getDefault().post(new EventBarEntity(EventBarEntity.LOGIN_UPDATE_WEB));
                            loadData(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        dialog.dismiss();
                        if (volleyError != null && volleyError.getCause() != null) {
                            LogUtil.d("volleyError", volleyError.getCause().getMessage());
                        }
                    }
                });
                jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
               AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
            }
        });
        builder.show();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void setTopStatusView(TouPiaoVo result) {
        stopTimer();
        switch (result.getStatus()) {
            case Constants.STATUS_3_TPZ:
                try {
                    surplustime = Long.valueOf(result.getEndtime());
                    startCountdown(tvTime);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                tvTimeStr.setText("距投票结束还有");
                break;
            default:
                stopTimer();
                tvTime.setText("投票已结束");
                tvTime.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                tvTimeStr.setText("距投票结束还有");
        }
    }
}
