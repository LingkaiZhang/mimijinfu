package com.nongjinsuo.mimijinfu.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.FundingRecordActivity;
import com.nongjinsuo.mimijinfu.activity.LoginActivity;
import com.nongjinsuo.mimijinfu.activity.VoteActivity;
import com.nongjinsuo.mimijinfu.beans.TouPiaoJinduVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.JinDuModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.ViewHolder;
import com.nongjinsuo.mimijinfu.widget.MyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 进度
 */
public class PactFragment extends Fragment {


    String projectBm;
    @BindView(R.id.ivStatus1)
    ImageView ivStatus1;
    @BindView(R.id.tv1Xmsx)
    TextView tv1Xmsx;
    @BindView(R.id.tv1XmsxTime)
    TextView tv1XmsxTime;
    @BindView(R.id.ivStatus2)
    ImageView ivStatus2;
    @BindView(R.id.tv2Zcwc)
    TextView tv2Zcwc;
    @BindView(R.id.tv2ZcwcTime)
    TextView tv2ZcwcTime;
    @BindView(R.id.ivStatus3)
    ImageView ivStatus3;
    @BindView(R.id.tv3Xmtp)
    TextView tv3Xmtp;
    @BindView(R.id.tv3XmtpTime)
    TextView tv3XmtpTime;
    @BindView(R.id.lvStatus3)
    MyListView lvStatus3;
    @BindView(R.id.ivStatus4)
    ImageView ivStatus4;
    @BindView(R.id.tv4Tptg)
    TextView tv4Tptg;
    @BindView(R.id.tv4TptgTime)
    TextView tv4TptgTime;
    @BindView(R.id.ivStatus5)
    ImageView ivStatus5;
    @BindView(R.id.tv5Xmwc)
    TextView tv5Xmwc;
    @BindView(R.id.tv5XmwcTime)
    TextView tv5XmwcTime;
    @BindView(R.id.tvXiangqing)
    TextView tvXiangqing;
    @BindView(R.id.rlZhanwei)
    RelativeLayout rlZhanwei;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.iv2ZcwcLb)
    ImageView iv2ZcwcLb;

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.rlServiceFail)
    RelativeLayout rlServiceFail;

    public PactFragment() {
        // Required empty public constructor
    }


    public static PactFragment newInstance(String projectBm) {
        PactFragment fragment = new PactFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PROJECTBM, projectBm);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            projectBm = getArguments().getString(Constants.PROJECTBM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ButterKnife.bind(this, view);
//        loadDate();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    public void loadDate() {
        rlServiceFail.setVisibility(View.GONE);
        progressWheel.setVisibility(View.VISIBLE);
        JacksonRequest<JinDuModel> jacksonRequest = new JacksonRequest<>(RequestMap.getScheduleMap(projectBm), JinDuModel.class, new Response.Listener<JinDuModel>() {
            @Override
            public void onResponse(JinDuModel baseVo) {
                if (baseVo == null) {
                    return;
                }
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    progressWheel.setVisibility(View.GONE);
                    setDate(baseVo);
                } else {
                    netWorkFail();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                netWorkFail();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 0, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }

    private void netWorkFail() {
        scrollView.setVisibility(View.GONE);
        progressWheel.setVisibility(View.GONE);
        rlServiceFail.setVisibility(View.VISIBLE);
    }

    /**
     * 根据状态显示
     *
     * @param baseVo
     */
    private void setDate(JinDuModel baseVo) {
        if (getContext() == null) {
            return;
        }
        switch (baseVo.result.getStatus()) {
            case Constants.STATUS_0_ZBSX:
                ivStatus1.setImageResource(R.drawable.img_zhh);
                tv1Xmsx.setText("项目上线");
                tv1Xmsx.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv1XmsxTime.setVisibility(View.GONE);
                tvXiangqing.setVisibility(View.GONE);

                ivStatus2.setImageResource(R.drawable.img_zhh);
                tv2Zcwc.setText("募集完成");
                tv2Zcwc.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv2ZcwcTime.setVisibility(View.GONE);

                ivStatus3.setImageResource(R.drawable.img_zhh);
                tv3Xmtp.setText("项目投票");
                tv3Xmtp.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv3XmtpTime.setVisibility(View.GONE);
                rlZhanwei.setVisibility(View.GONE);

                ivStatus4.setImageResource(R.drawable.img_zhh);
                tv4Tptg.setText("投票通过");
                tv4Tptg.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv4TptgTime.setVisibility(View.GONE);

                ivStatus5.setImageResource(R.drawable.img_zhh);
                tv5Xmwc.setText("项目完成");
                tv5Xmwc.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv5XmwcTime.setVisibility(View.GONE);
                break;
            case Constants.STATUS_1_ZCZ:
                status1(baseVo);
                ivStatus1.setImageResource(R.drawable.img_zrr);

                ivStatus2.setImageResource(R.drawable.img_zhh);
                tv2Zcwc.setText("募集完成");
                tv2Zcwc.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv2ZcwcTime.setVisibility(View.GONE);

                ivStatus3.setImageResource(R.drawable.img_zhh);
                tv3Xmtp.setText("项目投票");
                tv3Xmtp.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv3XmtpTime.setVisibility(View.GONE);
                rlZhanwei.setVisibility(View.GONE);

                ivStatus4.setImageResource(R.drawable.img_zhh);
                tv4Tptg.setText("投票通过");
                tv4Tptg.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv4TptgTime.setVisibility(View.GONE);

                ivStatus5.setImageResource(R.drawable.img_zhh);
                tv5Xmwc.setText("项目完成");
                tv5Xmwc.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv5XmwcTime.setVisibility(View.GONE);
                break;
            case Constants.STATUS_2_DSZ:
                status1(baseVo);

                status2(baseVo, "募集完成【待售】");
                ivStatus2.setImageResource(R.drawable.img_zrr);

                ivStatus3.setImageResource(R.drawable.img_zhh);
                tv3Xmtp.setText("项目投票");
                tv3Xmtp.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv3XmtpTime.setVisibility(View.GONE);
                rlZhanwei.setVisibility(View.GONE);

                ivStatus4.setImageResource(R.drawable.img_zhh);
                tv4Tptg.setText("投票通过");
                tv4Tptg.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv4TptgTime.setVisibility(View.GONE);

                ivStatus5.setImageResource(R.drawable.img_zhh);
                tv5Xmwc.setText("项目完成");
                tv5Xmwc.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv5XmwcTime.setVisibility(View.GONE);
                break;
            case Constants.STATUS_3_TPZ:
                status1(baseVo);

                status2(baseVo, "募集完成【待售】");

                status3(baseVo);
                ivStatus3.setImageResource(R.drawable.img_zrr);

                ivStatus4.setImageResource(R.drawable.img_zhh);
                tv4Tptg.setText("投票通过");
                tv4Tptg.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv4TptgTime.setVisibility(View.GONE);

                ivStatus5.setImageResource(R.drawable.img_zhh);
                tv5Xmwc.setText("项目完成");
                tv5Xmwc.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv5XmwcTime.setVisibility(View.GONE);
                break;
            case Constants.STATUS_4_TPWC:
                status1(baseVo);

                status2(baseVo, "募集完成【待售】");

                status3(baseVo);

                status4(baseVo);
                ivStatus4.setImageResource(R.drawable.img_zrr);

                ivStatus5.setImageResource(R.drawable.img_zhh);
                tv5Xmwc.setText("项目完成");
                tv5Xmwc.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv5XmwcTime.setVisibility(View.GONE);
                break;
            case Constants.STATUS_5_ZCJS:
                status1(baseVo);
                status2(baseVo, "募集完成【待售】");
                status3(baseVo);
                status4(baseVo);
                status5(baseVo);
                ivStatus5.setImageResource(R.drawable.img_zrr);
                break;
            case Constants.STATUS_6_ZCSB:
                status1(baseVo);
                ivStatus1.setImageResource(R.drawable.img_zrr);

                status2(baseVo, "募集失败");
                ivStatus2.setImageResource(R.drawable.img_zrr);
                iv2ZcwcLb.setVisibility(View.VISIBLE);

                ivStatus3.setImageResource(R.drawable.img_zhh);
                tv3Xmtp.setText("项目投票");
                tv3Xmtp.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv3XmtpTime.setVisibility(View.GONE);
                rlZhanwei.setVisibility(View.GONE);

                ivStatus4.setImageResource(R.drawable.img_zhh);
                tv4Tptg.setText("投票通过");
                tv4Tptg.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv4TptgTime.setVisibility(View.GONE);

                ivStatus5.setImageResource(R.drawable.img_zhh);
                tv5Xmwc.setText("项目完成");
                tv5Xmwc.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv5XmwcTime.setVisibility(View.GONE);
                break;
            case Constants.STATUS_7_XSSB:
                status1(baseVo);

                status2(baseVo, "募集完成【销售失败】");
                ivStatus2.setImageResource(R.drawable.img_zrr);

                ivStatus3.setImageResource(R.drawable.img_zhh);
                tv3Xmtp.setText("项目投票");
                tv3Xmtp.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv3XmtpTime.setVisibility(View.GONE);
                rlZhanwei.setVisibility(View.GONE);

                ivStatus4.setImageResource(R.drawable.img_zhh);
                tv4Tptg.setText("投票通过");
                tv4Tptg.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv4TptgTime.setVisibility(View.GONE);

                ivStatus5.setImageResource(R.drawable.img_zhh);
                tv5Xmwc.setText("项目完成");
                tv5Xmwc.setTextColor(getResources().getColor(R.color.color_project_detail_jindu_hui));
                tv5XmwcTime.setVisibility(View.GONE);
                break;
        }
    }

    private void status5(JinDuModel baseVo) {
        ivStatus5.setImageResource(R.drawable.img_bb);
        tv5Xmwc.setText("项目完成");
        tv5Xmwc.setTextColor(getResources().getColor(R.color.color_homepage_quanbu));
        tv5XmwcTime.setTextColor(getResources().getColor(R.color.color_homepage_quanbu));
        tv5XmwcTime.setText(baseVo.result.getActualOverTime());
    }

    private void status4(JinDuModel baseVo) {
        ivStatus4.setImageResource(R.drawable.img_bb);
        tv4Tptg.setText("投票通过【回款中】");
        tv4Tptg.setTextColor(getResources().getColor(R.color.color_homepage_quanbu));
        tv4TptgTime.setTextColor(getResources().getColor(R.color.color_homepage_quanbu));
        tv4TptgTime.setText(baseVo.result.getActualVoteOverTime());
    }

    private void status3(JinDuModel baseVo) {
        ivStatus3.setImageResource(R.drawable.img_bb);
        tv3Xmtp.setText("项目投票");
        tv3Xmtp.setTextColor(getResources().getColor(R.color.color_homepage_quanbu));
        tv3XmtpTime.setTextColor(getResources().getColor(R.color.color_homepage_quanbu));
        tv3XmtpTime.setText(baseVo.result.getActualSaleTime());
        if (baseVo.result.getArrvote() != null && baseVo.result.getArrvote().size() > 0) {
            lvStatus3.setFocusable(false);
            TouPiaoJiluAdapter touPiaoJiluAdapter = new TouPiaoJiluAdapter(baseVo.result.getArrvote());
            lvStatus3.setAdapter(touPiaoJiluAdapter);
        }
    }

    private void status2(JinDuModel baseVo, String zcwcStr) {
        ivStatus2.setImageResource(R.drawable.img_bb);
        tv2Zcwc.setText(zcwcStr);
        tv2Zcwc.setTextColor(getResources().getColor(R.color.color_homepage_quanbu));
        tv2ZcwcTime.setText(baseVo.result.getActualInvestOverTime());
        tv2ZcwcTime.setTextColor(getResources().getColor(R.color.color_homepage_quanbu));
    }

    private void status1(JinDuModel baseVo) {
        ivStatus1.setImageResource(R.drawable.img_bb);
        tv1Xmsx.setText("项目上线");
        tv1Xmsx.setTextColor(getResources().getColor(R.color.color_homepage_quanbu));
        tv1XmsxTime.setText(baseVo.result.getBeginTime());
        tv1XmsxTime.setTextColor(getResources().getColor(R.color.color_homepage_quanbu));
        tvXiangqing.setVisibility(View.VISIBLE);
        tvXiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FundingRecordActivity.class);
                intent.putExtra(Constants.PROJECTBM, projectBm);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.tvServiceRefresh)
    public void onClick() {
        loadDate();
    }

    private class TouPiaoJiluAdapter extends BaseAdapter {
        private List<TouPiaoJinduVo.ArrvoteBean> arrvote;

        public TouPiaoJiluAdapter(List<TouPiaoJinduVo.ArrvoteBean> arrvote) {
            this.arrvote = arrvote;
        }

        @Override
        public int getCount() {
            return arrvote.size();
        }

        @Override
        public TouPiaoJinduVo.ArrvoteBean getItem(int i) {
            return arrvote.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(getContext(), R.layout.item_tupiaojilu, null);
            }
            TouPiaoJinduVo.ArrvoteBean item = getItem(i);
            TextView tvTptext = ViewHolder.get(view, R.id.tvTptext);
            TextView tvTpTime = ViewHolder.get(view, R.id.tvTpTime);
            TextView tvQtp = ViewHolder.get(view, R.id.tvQtp);
            TextView tvTpXzmj = ViewHolder.get(view, R.id.tvTpXzmj);
            tvTptext.setText("年回报" + item.getActualRate() + ",赞成票数" + item.getSupportNum() + "%");
            tvTpTime.setText(item.getPubTime());
            float supportNum = 0;
            try {
                supportNum = Float.valueOf(item.getSupportNum());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (i == getCount() - 1) {
                tvTpXzmj.setVisibility(View.VISIBLE);
                tvQtp.setVisibility(View.VISIBLE);
                tvQtp.setText("详情");
                if (supportNum >= 50) {
                    tvTpXzmj.setText("成交");
                } else {
                    tvTpXzmj.setText("寻找买家中...");
                }
            } else {
                tvTpXzmj.setVisibility(View.GONE);
                tvQtp.setVisibility(View.GONE);
            }
            tvQtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!SharedPreferenceUtil.isLogin(getContext())) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        return;
                    }
                    Intent intent = new Intent(getActivity(), VoteActivity.class);
                    intent.putExtra(Constants.PROJECTBM, projectBm);
                    startActivity(intent);
                }
            });
            return view;
        }
    }
}
