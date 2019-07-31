package com.nongjinsuo.mimijinfu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.flyco.tablayout.SlidingTabLayout;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.fragment.ProjectDetailFragment;
import com.nongjinsuo.mimijinfu.httpmodel.UserverificationModel;
import com.nongjinsuo.mimijinfu.util.Util;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dialog.CalculatorDialog;
import com.nongjinsuo.mimijinfu.dialog.ShareDialog;
import com.nongjinsuo.mimijinfu.fragment.P2pContentDetailFragment;
import com.nongjinsuo.mimijinfu.fragment.P2pDetailFragment;
import com.nongjinsuo.mimijinfu.fragment.P2pPactFragment;
import com.nongjinsuo.mimijinfu.httpmodel.ProductDetailModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.widget.CustomViewPager;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;
import com.nongjinsuo.mimijinfu.widget.PullUpToLoadMore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import de.greenrobot.event.EventBus;

/**
 * @author czz
 * @Description: (P2p项目详情页面)
 */
public class P2pDetilsActivity extends AbstractActivity {
    @BindView(R.id.ivStatusBar)
    ImageView ivStatusBar;
    @BindView(R.id.tabs)
    SlidingTabLayout tabs;
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.ibShare)
    ImageButton ibShare;
    @BindView(R.id.viewpager)
    CustomViewPager viewpager;
    @BindView(R.id.llBottomView)
    LinearLayout llBottomView;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivNoData)
    ImageView ivNoData;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
    @BindView(R.id.rlNoDataView)
    RelativeLayout rlNoDataView;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;

    @BindView(R.id.rlServiceFail)
    RelativeLayout rlServiceFail;
    @BindView(R.id.tvServiceFailContent)
    TextView tvServiceFailContent;
    @BindView(R.id.ProgressWheel)
    ProgressWheel progressWheelDetils;
    private ProductDetailModel productDetailModel;
    private String projectBm;
    private String id;
    public static final String PAGE = "page";
    private MinePagerAdapter minePagerAdapter;
    private String[] titles = new String[]{"项目", "详情", "进度"};
    private ProductDetailModel statusModel;
    P2pDetailFragment p2pDetailFragment;
    P2pContentDetailFragment p2pContentDetailFragment;
    P2pPactFragment p2pPactFragment;
    private double yjsy;//预计收益
    MyProgressDialog myProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTemplate = false;
        isStatusBarTransparent = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_p2p);
        ButterKnife.bind(this);
        projectBm = getIntent().getStringExtra(Constants.PROJECTBM);
        id = getIntent().getStringExtra(Constants.ID);
        init();
        addListener();
        loadDate(true, false);
        tabs.setClickable(false);
        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(EventBarEntity event) {
        if (event.getType() == EventBarEntity.UPDATE_PROJECT_DETAILS) {
            if (event.getUpdateType() == 0) {
                updateTitle1();
            } else {
                tabs.setVisibility(View.GONE);
                tvTitle.setVisibility(View.VISIBLE);
                viewpager.setPagingEnabled(false);
            }
        }
        if (event.getType() == EventBarEntity.LOGIN_UPDATE_WEB) {
            reload();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (minePagerAdapter == null) {
                return super.onKeyDown(keyCode, event);
            }
            P2pDetailFragment projectDetailFragment = (P2pDetailFragment) minePagerAdapter.getFragments()[0];
            if (projectDetailFragment == null) {
                return super.onKeyDown(keyCode, event);
            }
            PullUpToLoadMore pullUpToLoadMore = projectDetailFragment.ptlm;
            if (pullUpToLoadMore == null) {
                return super.onKeyDown(keyCode, event);
            }
            if (pullUpToLoadMore.getCurrPosition() == 1 && viewpager.getCurrentItem() == 0) {
                pullUpToLoadMore.scrollToTop();
                updateTitle1();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void updateTitle1() {
        tabs.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
        viewpager.setPagingEnabled(true);
        tabs.setClickable(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }


    private void loadDate(final boolean isFirst, final boolean isShowLoading) {
        if (isFirst && !NetworkUtils.isNetworkAvailable(this)) {
            ibShare.setVisibility(View.GONE);
            progressWheelDetils.setVisibility(View.GONE);
            llBottomView.setVisibility(View.GONE);
            rlNoDataView.setVisibility(View.VISIBLE);
            ivNoData.setImageResource(R.drawable.img_wwl);
            tvNoData.setText(getString(R.string.str_no_network));
            tvRefresh.setVisibility(View.VISIBLE);
            Toast.makeText(this, getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            viewpager.setPagingEnabled(false);
            tabs.setClickable(false);
            return;
        }
        if (!isFirst && !NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            viewpager.setPagingEnabled(false);
            tabs.setClickable(false);
            return;
        }
        if (isFirst && NetworkUtils.isNetworkAvailable(this)) {
            rlServiceFail.setVisibility(View.GONE);
            rlNoDataView.setVisibility(View.GONE);
            progressWheelDetils.setVisibility(View.VISIBLE);
        }
        if (isShowLoading) {
            myProgressDialog = new MyProgressDialog(P2pDetilsActivity.this);
            myProgressDialog.show();
        }
        JacksonRequest<ProductDetailModel> jacksonRequest = new JacksonRequest<>(RequestMap.productDetailV2(id), ProductDetailModel.class, new Response.Listener<ProductDetailModel>() {
            @Override
            public void onResponse(ProductDetailModel baseVo) {
                progressDismiss();
                productDetailModel = baseVo;
                progressWheelDetils.setVisibility(View.GONE);
                llBottomView.setVisibility(View.VISIBLE);
                viewpager.setVisibility(View.VISIBLE);
                if (baseVo.getCode().equals(Constants.SUCCESS)) {
                    if (baseVo.getResult().getState() == 6) {
                        Toast.makeText(P2pDetilsActivity.this, "该项目已下线", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                    if (statusModel == null) {
                        tabs.setViewPager(viewpager, titles);
                        viewpager.setPagingEnabled(true);
                    }
                    statusModel = baseVo;
                    p2pDetailFragment.setData(baseVo);
                    setBottomView(baseVo);
                    p2pPactFragment.setData(baseVo);
                    ibShare.setVisibility(View.VISIBLE);
                    //判断当前账户是否在其它设备登录
                    Util.loginPrompt(P2pDetilsActivity.this, baseVo.getResult().getLoginState(), baseVo.getResult().getLoginDescr());
                } else {
                    if (isFirst) {
                        tvServiceFailContent.setText(getString(R.string.str_fwrsgd));
                        rlServiceFail.setVisibility(View.VISIBLE);
                        viewpager.setVisibility(View.GONE);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDismiss();
                if (isFirst) {
                    tvServiceFailContent.setText(Util.getErrorMesg(volleyError));
                    progressWheelDetils.setVisibility(View.GONE);
                    rlServiceFail.setVisibility(View.VISIBLE);
                    viewpager.setVisibility(View.GONE);
                }
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    private void progressDismiss() {
        if (myProgressDialog != null && myProgressDialog.isShowing()) {
            myProgressDialog.dismiss();
        }
    }


    @Override
    public void init() {
        p2pDetailFragment = P2pDetailFragment.newInstance();
        p2pContentDetailFragment = P2pContentDetailFragment.newInstance();
        p2pPactFragment = P2pPactFragment.newInstance();
        Fragment[] fragments = new Fragment[]{p2pDetailFragment, p2pContentDetailFragment, p2pPactFragment};
        minePagerAdapter = new MinePagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setOffscreenPageLimit(3);
        viewpager.setAdapter(minePagerAdapter);
    }


    @Override
    public void addListener() {
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (minePagerAdapter != null) {
                    if (position == 1) {
                        if (productDetailModel != null && p2pContentDetailFragment != null) {
                            p2pContentDetailFragment.loadUrl(Urls.PROJECT_URL + productDetailModel.getResult().getDetailUrl());
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (minePagerAdapter == null) {
                    finish();
                    return;
                }
                P2pDetailFragment projectDetailFragment = (P2pDetailFragment) minePagerAdapter.getFragments()[0];
                PullUpToLoadMore pullUpToLoadMore = projectDetailFragment.ptlm;
                if (projectDetailFragment == null || pullUpToLoadMore == null || viewpager == null) {
                    finish();
                    return;
                }
                if (pullUpToLoadMore.getCurrPosition() == 1 && viewpager.getCurrentItem() == 0) {
                    pullUpToLoadMore.scrollToTop();
                    updateTitle1();
                } else {
                    finish();
                }
            }
        });
        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareDialog shareDialog = new ShareDialog(P2pDetilsActivity.this);
                shareDialog.show();
                shareDialog.setShareClick(new ShareDialog.ShareClick() {
                    @Override
                    public void Wechat() {
                        showShare(true, Wechat.NAME);
                    }

                    @Override
                    public void WechatMoments() {
                        showShare(true, WechatMoments.NAME);
                    }

                    @Override
                    public void Qzone() {
                        showShare(true, QZone.NAME);
                    }

                    @Override
                    public void Tencentqq() {
                        showShare(true, QQ.NAME);
                    }
                });
            }
        });

    }

    //快捷分享的文档：http://wiki.mob.com/Android_%E5%BF%AB%E6%8D%B7%E5%88%86%E4%BA%AB
    private void showShare(boolean silent, String platform) {
        final OnekeyShare oks = new OnekeyShare();
        oks.setCallback(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onComplete(Platform arg0, int arg1,
                                   HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                shareSuccess();
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {

            }
        });
        //不同平台的分享参数，请看文档
        //http://wiki.mob.com/Android_%E4%B8%8D%E5%90%8C%E5%B9%B3%E5%8F%B0%E5%88%86%E4%BA%AB%E5%86%85%E5%AE%B9%E7%9A%84%E8%AF%A6%E7%BB%86%E8%AF%B4%E6%98%8E
        String text = "" + "http://www.mob.com";
        oks.setTitle(statusModel.getResult().getShareTitle());
        oks.setText(statusModel.getResult().getShareSummary());
        oks.setSilent(silent);
        oks.setDialogMode();
        oks.setTitleUrl(Urls.PROJECT_URL + statusModel.getResult().getShareUrl());
        oks.setUrl(Urls.PROJECT_URL + statusModel.getResult().getShareUrl());
        oks.setSiteUrl(Urls.PROJECT_URL + statusModel.getResult().getShareUrl());
        oks.setImageUrl(Urls.PROJECT_URL + "/Public/upfile/temp/2017-01-22/1485070783_768048327.png?125*84");
        oks.setImageUrl(Urls.PROJECT_URL + statusModel.getResult().getShareImage());
        oks.setComment("");
        oks.setSite(getString(R.string.app_name));
        oks.disableSSOWhenAuthorize();
        if (platform != null) {
            oks.setPlatform(platform);
        }
        // 去自定义不同平台的字段内容
        // http://wiki.mob.com/Android_%E5%BF%AB%E6%8D%B7%E5%88%86%E4%BA%AB#.E4.B8.BA.E4.B8.8D.E5.90.8C.E5.B9.B3.E5.8F.B0.E5.AE.9A.E4.B9.89.E5.B7.AE.E5.88.AB.E5.8C.96.E5.88.86.E4.BA.AB.E5.86.85.E5.AE.B9
//        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        oks.show(this);
    }

    private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }

    private void shareSuccess() {
//        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
//        JacksonRequest<BaseVo> jacksonRequest = new JacksonRequest<>(RequestMap.getShareMap(fundDetailsModel.result.id), BaseVo.class, new Response.Listener<BaseVo>() {
//            @Override
//            public void onResponse(BaseVo baseVo) {
//                if (baseVo.code.equals(Constants.SUCCESS)) {
//                    showShortToastMessage("分享成功");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                if (volleyError != null && volleyError.getCause() != null) {
//                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
//                }
//            }
//        });
//       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }

    public void setBottomView(ProductDetailModel bottomView) {
        if (bottomView == null || bottomView.getResult() == null) {
            return;

        }
        final View inflate;
        if (llBottomView != null) {
            llBottomView.removeAllViews();
        }
        llBottomView.setVisibility(View.VISIBLE);
        final ProductDetailModel.ResultBean productBean = bottomView.getResult();
        TextView tvLjrg;
        if (productBean.getState() == 1) {
            inflate = View.inflate(this, R.layout.p2p_view_bottom_status_ljrg, null);
            TextView tvYtje = (TextView) inflate.findViewById(R.id.tvYtje);
            ImageView ivCalculator = (ImageView) inflate.findViewById(R.id.ivCalculator);
            tvYtje.setText(productBean.getUserInvestMoney() + productBean.getUserInvestUnit());
            tvLjrg = (TextView) inflate.findViewById(R.id.tvLjrg);
            tvLjrg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (!SharedPreferenceUtil.isLogin(P2pDetilsActivity.this)) {
                        intent = new Intent(P2pDetilsActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return;
                    }
                    if (productDetailModel != null) {
                        if (productDetailModel.getResult().getVerificationstatus() == Constants.TYPE_KHCG) {
                            intent = new Intent(P2pDetilsActivity.this, ProjectBuyActivity.class);
                            intent.putExtra(Constants.PROJECTBM, projectBm);
                            intent.putExtra(ProjectBuyActivity.PRODUCTBEAN, productBean);
                            intent.putExtra(ProjectBuyActivity.BUYTYPE, ProjectBuyActivity.BUY_P2P);
                            startActivity(intent);
                        } else {
                            noKaihu();
                        }
                    }

                }
            });
            ivCalculator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CalculatorDialog calculatorDialog = new CalculatorDialog(P2pDetilsActivity.this);
                    calculatorDialog.getTvYqnh().setText("期待年回报：" + productBean.getProjectRate() + "%");
                    calculatorDialog.getTvQx().setText("期限：" + productBean.getProjectDay() + productBean.getProductUnit());
                    calculatorDialog.getEtGmje().setHint("100元起购，最多可购买" + productBean.getResidueMoney() + productBean.getResidueUnit());
                    calculatorDialog.getEtGmje().addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            //100元一下不计预计收益
                            if (TextUtil.IsNotEmpty(s.toString().trim())) {
                                double doubleGmje = Double.parseDouble(s.toString());
                                if (doubleGmje >= 100) {
                                    yjsy = Util.calBackMoney(productBean.getBackMoneyClass(), productBean.getProjectRate(), doubleGmje, productBean.getProjectDay());
                                    calculatorDialog.getTvYqsy().setText(yjsy + "元");
                                } else {
                                    yjsy = 0;
                                    calculatorDialog.getTvYqsy().setText(yjsy + "元");
                                }
                            }
                        }
                    });
                    calculatorDialog.setiLjtzClick(new CalculatorDialog.ILjtzClick() {
                        @Override
                        public void litz(String gmje) {
                            Intent intent;
                            if (!SharedPreferenceUtil.isLogin(P2pDetilsActivity.this)) {
                                intent = new Intent(P2pDetilsActivity.this, LoginActivity.class);
                                startActivity(intent);
                                return;
                            }
                            if (TextUtil.IsEmpty(gmje)) {
                                showShortToastMessage("请输入购买金额");
                                return;
                            }
                            double money = Double.parseDouble(gmje);
                            if (money < 100) {
                                showShortToastMessage("最低购买金额为100元");
                                return;
                            }
                            calculatorDialog.dismiss();
                            intent = new Intent(P2pDetilsActivity.this, ProjectBuyActivity.class);
                            intent.putExtra(ProjectBuyActivity.BUYTYPE, ProjectBuyActivity.BUY_P2P);
                            intent.putExtra(Constants.PROJECTBM, statusModel.getResult().getBm());
                            intent.putExtra(ProjectBuyActivity.GMJE, gmje);
                            intent.putExtra(ProjectBuyActivity.YJSY, yjsy);
                            intent.putExtra(ProjectBuyActivity.PRODUCTBEAN, productBean);
                            startActivity(intent);
                        }
                    });

                    calculatorDialog.show();
                }
            });
        } else {
            inflate = View.inflate(this, R.layout.p2p_view_bottom_status_mjwc, null);
            tvLjrg = (TextView) inflate.findViewById(R.id.tvMjwc);
        }
        tvLjrg.setText(productBean.getInvestBtn());
        llBottomView.addView(inflate, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    private void noKaihu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(P2pDetilsActivity.this);
        if (productDetailModel.getResult().getVerificationstatus() == Constants.TYPE_JBK) {
            builder.setMessage(getString(R.string.str_no_band_bankcard));
            builder.setPositiveButton("去绑卡", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(P2pDetilsActivity.this, AccountSettingActivity.class);
                    intent.putExtra(AccountSettingActivity.IS_KAI_HU, false);
                    startActivity(intent);
                }
            });
        } else if (productDetailModel.getResult().getVerificationstatus() == Constants.TYPE_SMRZ) {
            builder.setMessage(getString(R.string.str_wssmrzxx));
            builder.setPositiveButton("去实名", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(P2pDetilsActivity.this, AccountSettingActivity.class);
                    startActivity(intent);
                }
            });
        } else if (productDetailModel.getResult().getVerificationstatus() == Constants.TYPE_BYHK) {
            builder.setMessage(getString(R.string.str_no_band_bankcard));
            builder.setPositiveButton("去绑卡", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(P2pDetilsActivity.this, AccountSettingActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            builder.setMessage(getString(R.string.str_ktdsfzjtg));
            builder.setPositiveButton("去开通", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(P2pDetilsActivity.this, AccountSettingActivity.class);
                    startActivity(intent);
                }
            });
        }
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void noLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(P2pDetilsActivity.this);
        builder.setMessage("您还没有登录");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(P2pDetilsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }

    @OnClick({R.id.tvRefresh, R.id.tvServiceRefresh})
    public void onClick() {
        loadDate(true, false);
    }

    public void reload() {
        loadDate(false, true);
    }


    /**
     * ViewPager的PagerAdapter
     */
    public class MinePagerAdapter extends FragmentPagerAdapter {
        Fragment[] fragments;

        public Fragment[] getFragments() {
            return fragments;
        }

        public void setFragments(Fragment[] fragments) {
            this.fragments = fragments;
        }

        public MinePagerAdapter(FragmentManager fm, Fragment[] fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }

}
