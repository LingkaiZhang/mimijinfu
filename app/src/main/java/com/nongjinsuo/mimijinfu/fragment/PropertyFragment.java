package com.nongjinsuo.mimijinfu.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.AccountSettingActivity;
import com.nongjinsuo.mimijinfu.activity.FinancialDetails2Activity;
import com.nongjinsuo.mimijinfu.activity.IBase;
import com.nongjinsuo.mimijinfu.activity.MyCrowdFundingActivity;
import com.nongjinsuo.mimijinfu.activity.MyP2pTouZiActivity;
import com.nongjinsuo.mimijinfu.activity.MyRedPacketActivity;
import com.nongjinsuo.mimijinfu.activity.RechargeActivity;
import com.nongjinsuo.mimijinfu.activity.ReturnedMoneyPlanActivity;
import com.nongjinsuo.mimijinfu.activity.SettingActivity;
import com.nongjinsuo.mimijinfu.activity.TansactionRecordActivity;
import com.nongjinsuo.mimijinfu.activity.WithdrawCashActivity;
import com.nongjinsuo.mimijinfu.beans.BaseVo;
import com.nongjinsuo.mimijinfu.beans.CentralVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.CentralModel;
import com.nongjinsuo.mimijinfu.httpmodel.UserverificationModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.Util;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.EventBus;

/**
 * 资产
 */
@SuppressWarnings("unused")
public class PropertyFragment extends BaseFragment implements IBase, View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {


    @BindView(R.id.ibSetting)
    ImageButton ibSetting;
    @BindView(R.id.tvZzc)
    TextView tvZzc;
    @BindView(R.id.tvLjsy)
    TextView tvLjsy;
    @BindView(R.id.tvZrsyStr)
    TextView tvZrsyStr;
    @BindView(R.id.tvKyye)
    TextView tvKyye;
    @BindView(R.id.llChongzhi)
    LinearLayout llChongzhi;
    @BindView(R.id.llTixian)
    LinearLayout llTixian;
    @BindView(R.id.llLookAll)
    LinearLayout llLookAll;
    @BindView(R.id.llJyjl)
    LinearLayout llJyjl;
    @BindView(R.id.swipeRefreshLayout)
    BGARefreshLayout swipeRefreshLayout;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.ivNoData)
    ImageView ivNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.rlNoDataView)
    RelativeLayout rlNoDataView;
    @BindView(R.id.ivStatusBarTop)
    ImageView ivStatusBarTop;
    @BindView(R.id.ivStatusBar)
    ImageView ivStatusBar;
    @BindView(R.id.tvStrZzc)
    TextView tvStrZzc;
    @BindView(R.id.tvP2pZtje)
    TextView tvP2pZtje;
    @BindView(R.id.tvP2pDsje)
    TextView tvP2pDsje;
    @BindView(R.id.tvZcZtje)
    TextView tvZcZtje;
    @BindView(R.id.tvZcXms)
    TextView tvZcXms;
    @BindView(R.id.tvHbNum)
    TextView tvHbNum;
    @BindView(R.id.tvHkNum)
    TextView tvHkNum;
    @BindView(R.id.llWsgrsmxx)
    LinearLayout llWsgrsmxx;
    @BindView(R.id.ivZzc)
    ImageView ivZzc;
    @BindView(R.id.llP2pTz)
    LinearLayout llP2pTz;
    @BindView(R.id.llWdhb)
    LinearLayout llWdhb;
    @BindView(R.id.llHkjh)
    LinearLayout llHkjh;
    @BindView(R.id.tvTsWsxx)
    TextView tvTsWsxx;
    private UserverificationModel.Verification verification;
    private double doubleZzc;
    private boolean isHideAssetLoading = true;

    private CentralModel centralModel;

    public static PropertyFragment newInstance() {
        PropertyFragment hotSaleFragment = new PropertyFragment();
        return hotSaleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_zichan, null);
        ButterKnife.bind(this, inflate);
        init();
        addListener();
        loadData(true);
        getStatus();
        EventBus.getDefault().register(this);
        return inflate;
    }

    public void onEventMainThread(EventBarEntity event) {
        if (event.getType() == EventBarEntity.UPDATE_PROPERTY) {
            loadData(false);
            getStatus();
        } else if (event.getType() == EventBarEntity.UPDATE_PROPERTY_FIRST) {
            loadData(true);
            getStatus();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    @Override
    public void init() {
        swipeRefreshLayout.setDelegate(this);
        swipeRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), true));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ivStatusBar.setVisibility(View.VISIBLE);
            Util.setStatusBarHeight(getContext(), ivStatusBar);
        } else {
            ivStatusBar.setVisibility(View.GONE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Util.isMIUI()) {
                ivStatusBarTop.setVisibility(View.GONE);
            } else if (Util.isFlyme()) {
                ivStatusBarTop.setVisibility(View.GONE);
            } else {
                ivStatusBarTop.setVisibility(View.VISIBLE);
                setStatusBarHeight(getContext(), ivStatusBarTop);
            }
        }

    }

    public void setStatusBarHeight(Context context, ImageView ivStatusBar) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.getStatusBarHeight(getActivity()));
        ivStatusBar.setLayoutParams(layoutParams);
    }

    private void loadData(boolean isFirst) {
        if (isFirst && !NetworkUtils.isNetworkAvailable(getContext())) {
            netWorkFail(0);
            return;
        }
        if (!isFirst && !NetworkUtils.isNetworkAvailable(getContext())) {
            swipeRefreshLayout.endRefreshing();
            Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            return;
        }
        if (isFirst && NetworkUtils.isNetworkAvailable(getContext())) {
            rlNoDataView.setVisibility(View.GONE);
            progressWheel.setVisibility(View.VISIBLE);
        }
        rlNoDataView.setVisibility(View.GONE);

        JacksonRequest<CentralModel> jacksonRequest = new JacksonRequest<>(RequestMap.getCentralhome(), CentralModel.class, new Response.Listener<CentralModel>() {
            @Override
            public void onResponse(CentralModel baseVo) {
                centralModel = baseVo;
                progressWheel.setVisibility(View.GONE);
                swipeRefreshLayout.endRefreshing();
                if (baseVo.getCode().equals(Constants.SUCCESS)) {
                    setData(centralModel.result);
                    Util.loginPrompt(getActivity(), baseVo.result.getLoginState(), baseVo.result.getLoginDescr());
                } else {
                    netWorkFail(1);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                swipeRefreshLayout.endRefreshing();
                progressWheel.setVisibility(View.GONE);
                netWorkFail(1);
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }


    private void netWorkFail(int flag) {
        swipeRefreshLayout.setVisibility(View.GONE);
        progressWheel.setVisibility(View.GONE);
        rlNoDataView.setVisibility(View.VISIBLE);
        ivNoData.setImageResource(R.drawable.img_wwl);
        if (flag == 0) {
            tvNoData.setText(getString(R.string.str_no_network));
//            Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
        } else {
            tvNoData.setText(getString(R.string.str_no_service));
//            Toast.makeText(getContext(), getString(R.string.str_no_service), Toast.LENGTH_SHORT).show();
        }
        tvRefresh.setVisibility(View.VISIBLE);
    }


    private void getStatus() {
        JacksonRequest<UserverificationModel> jacksonRequest = new JacksonRequest<>(RequestMap.getUserverification(0), UserverificationModel.class, new Response.Listener<UserverificationModel>() {
            @Override
            public void onResponse(UserverificationModel baseVo) {
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    verification = baseVo.result;
                    progressWheel.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    //判断是否完善个人实名信息
                    //判断当前账户是否在其它设备登录
//                    Util.loginPrompt(getActivity(),centralModel.result.loginPrompt);
                    if (verification.getVerificationstatus() == Constants.TYPE_KHCG) {
                        llWsgrsmxx.setVisibility(View.GONE);
                    } else if (verification.getVerificationstatus() == Constants.TYPE_SMRZ) {
                        tvTsWsxx.setText(getString(R.string.str_smxx));
                        llWsgrsmxx.setVisibility(View.VISIBLE);
                    } else if (verification.getVerificationstatus() == Constants.TYPE_BYHK || verification.getVerificationstatus() == Constants.TYPE_JBK) {
                        tvTsWsxx.setText(getString(R.string.str_bdtz));
                        llWsgrsmxx.setVisibility(View.VISIBLE);
                    } else {
                        tvTsWsxx.setText(getString(R.string.str_ktxlzftg));
                        llWsgrsmxx.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                swipeRefreshLayout.endRefreshing();
                progressWheel.setVisibility(View.GONE);
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    private void setData(CentralVo result) {
        if (result != null && result.getAsset() != null) {
            doubleZzc = Double.parseDouble(result.getAsset().getAsset());
        }
        CentralVo.AssetBean assetBean = result.getAsset();
        setHide(assetBean);
        tvZcZtje.setText(assetBean.getStayBackCapital());
        tvZcXms.setText(assetBean.getInvestProjectNum());
        tvP2pZtje.setText(assetBean.getStayBackCapitalNetloan());
        tvP2pDsje.setText(assetBean.getStayBackInterest());
        if (assetBean.getRedPacketCount() == 0)
            tvHbNum.setVisibility(View.GONE);
        else {
            tvHbNum.setVisibility(View.VISIBLE);
            tvHbNum.setText(assetBean.getRedPacketCount() + "个红包");
        }
        if (assetBean.getBackMoneyCount() == 0)
            tvHkNum.setVisibility(View.GONE);
        else {
            tvHkNum.setVisibility(View.VISIBLE);
            tvHkNum.setText(assetBean.getBackMoneyCount() + "个回款");
        }
    }

    @Override
    public void addListener() {

    }


    @OnClick({R.id.llChongzhi, R.id.llTixian, R.id.llLookAll, R.id.llJyjl, R.id.ibSetting, R.id.llZcmx, R.id.llWdhb, R.id.llP2pTz, R.id.llHkjh, R.id.llWsgrsmxx, R.id.ivZzc})
    public void onClick(View view) {
        if (!NetworkUtils.isNetworkAvailable(getContext())) {
            Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent;
        switch (view.getId()) {
            case R.id.llChongzhi://充值
                if (verification == null)
                    return;
                if (verification.getVerificationstatus() == Constants.TYPE_KHCG) {
                    intent = new Intent(getActivity(), RechargeActivity.class);
                    startActivity(intent);
                } else {
                    noKaihu();
                }
                break;
            case R.id.llTixian://提现
                if (verification == null)
                    return;
                if (verification.getVerificationstatus() == Constants.TYPE_KHCG) {
                    intent = new Intent(getActivity(), WithdrawCashActivity.class);
                    startActivity(intent);
                } else {
                    noKaihu();
                }
                break;
            case R.id.llLookAll://我的募集
                intent = new Intent(getActivity(), MyCrowdFundingActivity.class);
                startActivity(intent);
                break;
            case R.id.llP2pTz://p2p项目
                intent = new Intent(getActivity(), MyP2pTouZiActivity.class);
                CentralVo.AssetBean assetBean = centralModel.result.getAsset();
                String[] strings = {assetBean.getStayBackCapitalNetloan(), assetBean.getStayBackInterest(), assetBean.getTotalInvestMoney(), assetBean.getInterestNetloan()};
                intent.putExtra(MyP2pTouZiActivity.SHOUYI, strings);
                startActivity(intent);
                break;
            case R.id.llJyjl://交易记录
                intent = new Intent(getActivity(), TansactionRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.ibSetting://设置
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    Toast.makeText(getContext(), getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
                    return;
                }
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.llZcmx://资金明细
                if (doubleZzc > 0) {
                    intent = new Intent(getActivity(), FinancialDetails2Activity.class);
                    startActivity(intent);
                }
                break;
            case R.id.llWdhb://我的红包
                intent = new Intent(getActivity(), MyRedPacketActivity.class);
                startActivity(intent);
                break;
            case R.id.llHkjh://回款计划
                intent = new Intent(getActivity(), ReturnedMoneyPlanActivity.class);
                startActivity(intent);
                break;
            case R.id.llWsgrsmxx://完善个人实名信息
                if (verification == null)
                    return;
                if (verification.getVerificationstatus() == Constants.TYPE_JBK) {
                    intent = new Intent(getActivity(), AccountSettingActivity.class);
                    intent.putExtra(AccountSettingActivity.IS_KAI_HU, false);
                    startActivity(intent);
                } else if (verification.getVerificationstatus() < Constants.TYPE_JBK) {
                    intent = new Intent(getActivity(), AccountSettingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ivZzc://隐藏显示金额
                if (centralModel != null) {
                    if (centralModel.result.getAsset().getAssetHiddenStatus() == 0) {
                        isHideAsset(1);
                    } else {
                        isHideAsset(0);
                    }
                }

                break;
        }
    }

    private void noKaihu() {
        if (verification.getVerificationstatus() == Constants.TYPE_JBK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.str_no_band_bankcard));
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("去绑卡", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getActivity(), AccountSettingActivity.class);
                    intent.putExtra(AccountSettingActivity.IS_KAI_HU, false);
                    startActivity(intent);
                }
            });
            builder.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            if (verification.getVerificationstatus() == Constants.TYPE_SMRZ) {
                builder.setMessage(getString(R.string.str_wssmrzxx));
                builder.setPositiveButton("去实名", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), AccountSettingActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (verification.getVerificationstatus() == Constants.TYPE_BYHK) {
                builder.setMessage(getString(R.string.str_no_band_bankcard));
                builder.setPositiveButton("去绑卡", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), AccountSettingActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                builder.setMessage(getString(R.string.str_ktdsfzjtg));
                builder.setPositiveButton("去开通", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), AccountSettingActivity.class);
                        startActivity(intent);
                    }
                });
            }
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    }

    /**
     * 修改资产隐藏状态
     *
     * @param assetHiddenStatus
     */
    private void isHideAsset(final int assetHiddenStatus) {
        if (isHideAssetLoading) {
            isHideAssetLoading = false;
            JacksonRequest<BaseVo> jacksonRequest = new JacksonRequest<>(RequestMap.isHideAsset(assetHiddenStatus), BaseVo.class, new Response.Listener<BaseVo>() {
                @Override
                public void onResponse(BaseVo baseVo) {
                    isHideAssetLoading = true;
                    if (baseVo.getCode().equals(Constants.SUCCESS)) {
                        if (centralModel != null) {
                            centralModel.result.getAsset().setAssetHiddenStatus(assetHiddenStatus);
                            CentralVo.AssetBean assetBean = centralModel.result.getAsset();
                            setHide(assetBean);
                        }
                    } else {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    isHideAssetLoading = true;
                    if (volleyError != null && volleyError.getCause() != null) {
                        LogUtil.d("volleyError", volleyError.getCause().getMessage());
                    }
                }
            });
            jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
            AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
        }
    }

    /**
     * 设置金额隐藏显示
     *
     * @param assetBean
     */
    private void setHide(CentralVo.AssetBean assetBean) {
        if (assetBean.getAssetHiddenStatus() == 0) {
            ivZzc.setImageResource(R.drawable.icon_yca);
            tvZzc.setText(assetBean.getAsset());
            tvLjsy.setText(assetBean.getTotalInterest());
            tvKyye.setText(assetBean.getBalance());
        } else {
            ivZzc.setImageResource(R.drawable.icon_ycb);
            tvZzc.setText("******");
            tvLjsy.setText("******");
            tvKyye.setText("******");
        }
    }

    @OnClick(R.id.tvRefresh)
    public void onClick() {
        loadData(true);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        loadData(false);
        getStatus();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
