package com.nongjinsuo.mimijinfu.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.httpmodel.ProductDetailModel;
import com.nongjinsuo.mimijinfu.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 进度
 */
public class P2pPactFragment extends Fragment {


    @BindView(R.id.tvTopValue1)
    TextView tvTopValue1;
    @BindView(R.id.tvTopName1)
    TextView tvTopName1;
    @BindView(R.id.llTop1)
    LinearLayout llTop1;
    @BindView(R.id.tvTopValue2)
    TextView tvTopValue2;
    @BindView(R.id.tvTopName2)
    TextView tvTopName2;
    @BindView(R.id.llTop2)
    LinearLayout llTop2;
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
    @BindView(R.id.ivStatus)
    ImageView ivStatus;
    @BindView(R.id.tv1Xmsx)
    TextView tv1Xmsx;
    @BindView(R.id.tv2Zcwc)
    TextView tv2Zcwc;
    @BindView(R.id.tv2TzMoney)
    TextView tv2TzMoney;
    @BindView(R.id.tv2CyDay)
    TextView tv2CyDay;
    @BindView(R.id.llView2)
    LinearLayout llView2;
    @BindView(R.id.tv3Xscg)
    TextView tv3Xscg;
    @BindView(R.id.tv4Ddhk)
    TextView tv4Ddhk;
    @BindView(R.id.tv3DhkMoney)
    TextView tv3DhkMoney;
    @BindView(R.id.llView4)
    LinearLayout llView4;
    @BindView(R.id.tv5Xmwc)
    TextView tv5Xmwc;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tvServiceRefresh)
    TextView tvServiceRefresh;
    @BindView(R.id.rlServiceFail)
    RelativeLayout rlServiceFail;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.tvTopValue1Danwei)
    TextView tvTopValue1Danwei;
    @BindView(R.id.tvTopValue2Danwei)
    TextView tvTopValue2Danwei;
    @BindView(R.id.tvNhsy)
    TextView tvNhsy;
    @BindView(R.id.tvJxr)
    TextView tvJxr;
    @BindView(R.id.tvHkfs)
    TextView tvHkfs;

    public static P2pPactFragment newInstance() {
        P2pPactFragment fragment = new P2pPactFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_p2p_comment2, container, false);
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

    public void setData(ProductDetailModel productDetailModel) {
        if (productDetailModel==null){
            return;
        }
        ProductDetailModel.ResultBean productBean = productDetailModel.getResult();
        tvTopValue1.setText(productBean.getNeedMoney());
        tvTopValue1Danwei.setText(productBean.getNeedUnit());
        tvTopValue2.setText(productBean.getProjectDay()+"");
        tvTopValue2Danwei.setText(productBean.getProductUnit());
        tvNhsy.setText(productBean.getProjectRate()+"%");
        tvJxr.setText(productBean.getBeginInterestTime());
        tvHkfs.setText(productBean.getBackMoneyClassName());
        setDate(productBean);
    }

    /**
     * 根据状态显示
     *
     * @param baseVo
     */
    private void setDate(ProductDetailModel.ResultBean baseVo) {
        if (getContext() == null) {
            return;
        }
        switch (baseVo.getState()) {
            case Constants.STATUS_0_ZBSX:

                break;
            case Constants.STATUS_1_ZCZ:
                ivStatus.setImageResource(R.drawable.img_p2p_jda);
                state1(baseVo);
                break;
            case Constants.STATUS_2_DSZ:
                ivStatus.setImageResource(R.drawable.img_p2p_jdb);
                state1(baseVo);
                state2(baseVo);
                break;
            case Constants.STATUS_3_TPZ:
                ivStatus.setImageResource(R.drawable.img_p2p_jdc);
                state1(baseVo);
                state2(baseVo);
                state3(baseVo);
                break;
            case Constants.STATUS_4_TPWC:
                ivStatus.setImageResource(R.drawable.img_p2p_jdd);
                state1(baseVo);
                state2(baseVo);
                state3(baseVo);
                state4(baseVo);
                break;
            case Constants.STATUS_5_ZCJS:
                ivStatus.setImageResource(R.drawable.img_p2p_jde);
                state1(baseVo);
                state2(baseVo);
                state3(baseVo);
                state4(baseVo);
                state5(baseVo);
                break;
            case Constants.STATUS_6_ZCSB:

                break;
            case Constants.STATUS_7_XSSB:

                break;
        }
    }

    /**
     * 项目上线
     * @param baseVo
     */
    private void state1(ProductDetailModel.ResultBean baseVo) {
        tv1Xmsx.setTextColor(getResources().getColor(R.color.color_me_click_name));
        setTime(baseVo.getOnlineDate(), tv1Month, tv1Time);
    }

    /**
     * 募集完成
     * @param baseVo
     */
    private void state2(ProductDetailModel.ResultBean baseVo) {
        setTime(baseVo.getInvestOverDate(), tv2Month, tv2Time);
        tv2Zcwc.setTextColor(getResources().getColor(R.color.color_me_click_name));
    }

    /**
     * 开始计息
     * @param baseVo
     */
    private void state3(ProductDetailModel.ResultBean baseVo) {
        setTime(baseVo.getBeginInterestDate(), tv3Month, tv3Time);
        tv3Xscg.setTextColor(getResources().getColor(R.color.color_me_click_name));

    }

    /**
     * 回款中
     * @param baseVo
     */
    private void state4(ProductDetailModel.ResultBean baseVo) {
        setTime(baseVo.getBackMoneyDate(), tv4Month, tv4Time);
        tv4Ddhk.setTextColor(getResources().getColor(R.color.color_me_click_name));
        llView4.setVisibility(View.VISIBLE);
        tv3DhkMoney.setText(baseVo.getBackMoney());
        tv4Ddhk.setText("回款中");
    }

    /**
     * 收益完成
     * @param baseVo
     */
    private void state5(ProductDetailModel.ResultBean baseVo) {
        setTime(baseVo.getOverDate(), tv5Month, tv5Time);
        tv5Xmwc.setTextColor(getResources().getColor(R.color.color_me_click_name));
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




    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
