package com.nongjinsuo.mimijinfu.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.util.Util;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.FundingRecordActivity;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.JinDu2Model;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 进度
 */
public class PactFragment2 extends Fragment {


    String projectBm;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.rlServiceFail)
    RelativeLayout rlServiceFail;
    @BindView(R.id.tvTopName1)
    TextView tvTopName1;
    @BindView(R.id.tvTopValue1)
    TextView tvTopValue1;
    @BindView(R.id.tvTopName2)
    TextView tvTopName2;
    @BindView(R.id.tvTopValue2)
    TextView tvTopValue2;
    @BindView(R.id.tvTopName3)
    TextView tvTopName3;
    @BindView(R.id.tvTopValue3)
    TextView tvTopValue3;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tv1Month)
    TextView tv1Month;
    @BindView(R.id.tv1Time)
    TextView tv1Time;
    @BindView(R.id.tv2Month)
    TextView tv2Month;
    @BindView(R.id.tv2Time)
    TextView tv2Time;
    @BindView(R.id.tv3Month)
    TextView tv3Month;
    @BindView(R.id.tv3Time)
    TextView tv3Time;
    @BindView(R.id.tv4Month)
    TextView tv4Month;
    @BindView(R.id.tv4Time)
    TextView tv4Time;
    @BindView(R.id.tv5Month)
    TextView tv5Month;
    @BindView(R.id.tv5Time)
    TextView tv5Time;
    @BindView(R.id.tv1Xmsx)
    TextView tv1Xmsx;
    @BindView(R.id.tv1YgTime)
    TextView tv1YgTime;
    @BindView(R.id.tv1MbTime)
    TextView tv1MbTime;
    @BindView(R.id.llView1)
    LinearLayout llView1;
    @BindView(R.id.tv2Zcwc)
    TextView tv2Zcwc;
    @BindView(R.id.tvZcjl)
    TextView tvZcjl;
    @BindView(R.id.tv2TzMoney)
    TextView tv2TzMoney;
    @BindView(R.id.tv2CyDay)
    TextView tv2CyDay;
    @BindView(R.id.llView2)
    LinearLayout llView2;
    @BindView(R.id.tv3Xscg)
    TextView tv3Xscg;
    @BindView(R.id.tv3DhkMoney)
    TextView tv3DhkMoney;
    @BindView(R.id.llView3)
    LinearLayout llView3;
    @BindView(R.id.tv4Ddhk)
    TextView tv4Ddhk;
    @BindView(R.id.tv4HkTime)
    TextView tv4HkTime;
    @BindView(R.id.llView4)
    LinearLayout llView4;
    @BindView(R.id.tv5Xmwc)
    TextView tv5Xmwc;
    @BindView(R.id.tvServiceRefresh)
    TextView tvServiceRefresh;
    @BindView(R.id.llTop1)
    LinearLayout llTop1;
    @BindView(R.id.llTop2)
    LinearLayout llTop2;
    @BindView(R.id.llTop3)
    LinearLayout llTop3;
    @BindView(R.id.ivStatus)
    ImageView ivStatus;
    @BindView(R.id.tvServiceFailContent)
    TextView tvServiceFailContent;
    JinDu2Model jinDu2Model;

    public PactFragment2() {
        // Required empty public constructor
    }


    public static PactFragment2 newInstance(String projectBm) {
        PactFragment2 fragment = new PactFragment2();
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

        View view = inflater.inflate(R.layout.fragment_comment2, container, false);
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
    public void firstLoadData(){
        if (jinDu2Model==null){
            loadData();
        }
    }

    /**
     * 加载进度数据
     */
    public void loadData() {
        rlServiceFail.setVisibility(View.GONE);
        progressWheel.setVisibility(View.VISIBLE);
        JacksonRequest<JinDu2Model> jacksonRequest = new JacksonRequest<>(RequestMap.getProjectScheduleMap(projectBm), JinDu2Model.class, new Response.Listener<JinDu2Model>() {
            @Override
            public void onResponse(JinDu2Model baseVo) {
                scrollView.setVisibility(View.VISIBLE);
                jinDu2Model = baseVo;
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
                scrollView.setVisibility(View.GONE);
                progressWheel.setVisibility(View.GONE);
                rlServiceFail.setVisibility(View.VISIBLE);
                tvServiceFailContent.setText(Util.getErrorMesg(volleyError));
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 0, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    private void netWorkFail() {
        scrollView.setVisibility(View.GONE);
        progressWheel.setVisibility(View.GONE);
        rlServiceFail.setVisibility(View.VISIBLE);
        tvServiceFailContent.setText(getString(R.string.str_fwrsgd));

    }

    /**
     * 根据状态显示
     *
     * @param baseVo
     */
    private void setDate(JinDu2Model baseVo) {
        if (getContext() == null) {
            return;
        }
        switch (baseVo.result.getStatus()) {
            case Constants.STATUS_0_ZBSX:
                ivStatus.setImageResource(R.drawable.img_jdtb);
                if (TextUtil.IsNotEmpty(baseVo.result.getBeginTime())){
                    String[] str = baseVo.result.getBeginTime().split(" ");
                    if (str.length == 2){
                        tvTopValue2.setText(str[1]);
                    }else{
                        tvTopValue2.setText(baseVo.result.getBeginTime());
                    }
                }
                setTime(baseVo.result.getBeginOnlineTime(), tv1Month, tv1Time);
                break;
            case Constants.STATUS_1_ZCZ:
                ivStatus.setImageResource(R.drawable.img_jdtb1);
                setTime(baseVo.result.getBeginOnlineTime(), tv1Month, tv1Time);
                tvStatus.setText("募集中");
                llTop1.setVisibility(View.VISIBLE);
                tvTopName1.setText("剩余可投");
                tvTopName2.setText("您已投");
                setContentStyle(baseVo.result.getAvialInvestMoney(), tvTopValue1);
                setContentStyle(baseVo.result.getUserInvestInfo().getInvestMoney(), tvTopValue2);
                break;
            case Constants.STATUS_2_DSZ:
                ivStatus.setImageResource(R.drawable.img_jdtb1);
                llTop1.setVisibility(View.VISIBLE);
                tvTopName1.setText("本期投资");
                tvTopName2.setText("持有天数");
                setContentStyle(baseVo.result.getDueDays(), tvTopValue2);
                setContentStyle(baseVo.result.getUserInvestInfo().getInvestMoney(), tvTopValue1);

                tvStatus.setText("募集完成");


                setDsz(baseVo);
                break;
            case Constants.STATUS_3_TPZ:
                break;
            case Constants.STATUS_4_TPWC:
                ivStatus.setImageResource(R.drawable.img_jdtb3);
                tvTopName2.setText(baseVo.result.getStatusDescr1());
                tvTopValue2.setText(baseVo.result.getStatusDescr2());
                tvStatus.setText("等待回款");

                setDsz(baseVo);

                setDdhk(baseVo);
                break;
            case Constants.STATUS_5_ZCJS:
                ivStatus.setImageResource(R.drawable.img_jdtb4);
                llTop1.setVisibility(View.VISIBLE);
                llTop2.setVisibility(View.VISIBLE);
                llTop3.setVisibility(View.VISIBLE);
                tvStatus.setText("项目完成");
                tvTopName1.setText("本期投资");
                tvTopName2.setText("收益");
                tvTopName3.setText("项目历时");
                setContentStyle(baseVo.result.getUserInvestInfo().getInvestMoney(), tvTopValue1);
                setContentStyle(baseVo.result.getUserInvestInfo().getInvestInterest(), tvTopValue2);
                setContentStyle(baseVo.result.getDueDays(), tvTopValue3);

                setDsz(baseVo);

                setDdhk(baseVo);

                llView4.setVisibility(View.VISIBLE);
                tv4HkTime.setText(baseVo.result.getBackMoneyTime());

                setTime(baseVo.result.getActualOverTime(), tv5Month, tv5Time);
                tv5Xmwc.setTextColor(getResources().getColor(R.color.color_me_click_name));

                break;
            case Constants.STATUS_6_ZCSB:

                break;
            case Constants.STATUS_7_XSSB:

                break;
        }
    }

    private void setDdhk(JinDu2Model baseVo) {
        setTime(baseVo.result.getActualSaleTime(), tv3Month, tv3Time);
        tv3Xscg.setTextColor(getResources().getColor(R.color.color_me_click_name));
        setTime(baseVo.result.getActualVoteOverTime(), tv4Month, tv4Time);
        tv4Ddhk.setTextColor(getResources().getColor(R.color.color_me_click_name));

        llView2.setVisibility(View.VISIBLE);
        tv2TzMoney.setText(baseVo.result.getUserInvestInfo().getInvestMoney());
        tv2CyDay.setText(baseVo.result.getDueDays());

        llView3.setVisibility(View.VISIBLE);
        tv3DhkMoney.setText(baseVo.result.getBackMoney());
    }

    private void setDsz(JinDu2Model baseVo) {
        setTime(baseVo.result.getBeginOnlineTime(), tv1Month, tv1Time);
        setTime(baseVo.result.getActualInvestOverTime(), tv2Month, tv2Time);
        tv2Zcwc.setTextColor(getResources().getColor(R.color.color_me_click_name));

        tvZcjl.setVisibility(View.VISIBLE);
        llView1.setVisibility(View.VISIBLE);
        tv1YgTime.setText(baseVo.result.getBeginOnlineDescr());
        tv1MbTime.setText(baseVo.result.getActualInvestTime());
    }

    private void setTime(String time, TextView tvMonth, TextView tvTime) {
        if (TextUtil.IsNotEmpty(time)) {
            String[] times = time.split(" ");
            if (times.length == 2) {
                tvMonth.setText(times[0]);
                tvTime.setText(times[1]);
            }
        }
    }

    /**
     * @param content
     * @param textView
     */
    public void setContentStyle(String content, TextView textView) {
        //检索字符串中含有非数字
        if (TextUtil.IsNotEmpty(content)) {
            int index = content.lastIndexOf("万");
            int index1 = content.lastIndexOf("%");
            int index2 = content.lastIndexOf("天");
            int index3 = content.lastIndexOf("元");
            try {
                if (index != -1 | index1 != -1 | index2 != -1| index3 != -1) {
                    SpannableString styledText = new SpannableString(content);
                    styledText.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_jd_big_text_white), 0, content.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_jd_smile_text_white), content.length()- 1, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.setText(styledText, TextView.BufferType.SPANNABLE);
                } else {
                    SpannableString styledText = new SpannableString(content);
                    styledText.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_jd_big_text_white), 0, content.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_jd_smile_text_white), content.length() - 1, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.setText(styledText, TextView.BufferType.SPANNABLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.tvServiceRefresh)
    public void onClick() {
        loadData();
    }


    @OnClick(R.id.tvZcjl)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), FundingRecordActivity.class);
        intent.putExtra(Constants.PROJECTBM, projectBm);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
