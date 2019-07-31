package com.nongjinsuo.mimijinfu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
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
import com.flyco.tablayout.SlidingTabLayout;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.dialog.ShareDialog;
import com.nongjinsuo.mimijinfu.fragment.ContentDetailFragment;
import com.nongjinsuo.mimijinfu.fragment.PactFragment2;
import com.nongjinsuo.mimijinfu.fragment.ProjectDetailFragment;
import com.nongjinsuo.mimijinfu.httpmodel.ProjectDetailV2Model;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.Util;
import com.nongjinsuo.mimijinfu.widget.CustomViewPager;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;
import com.nongjinsuo.mimijinfu.widget.PullUpToLoadMore;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * @Description: (项目详情页面)
 */
public class ProjectDetilsActivity extends AbstractActivity {
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
    @BindView(R.id.tvServiceFailContent)
    TextView tvServiceFailContent;
    @BindView(R.id.rlServiceFail)
    RelativeLayout rlServiceFail;
    @BindView(R.id.ProgressWheel)
    ProgressWheel progressWheelDetils;
    private String projectBm;
    public static final String PAGE = "page";
    private MinePagerAdapter minePagerAdapter;
    private String[] titles = new String[]{"项目", "详情", "进度"};
    private ProjectDetailV2Model.ResultBean resultBean;
    private ProjectDetailFragment projectDetailFragment;
    private ContentDetailFragment contentDetailFragment;
    private PactFragment2 pactFragment2;
    MyProgressDialog myProgressDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTemplate = false;
        isStatusBarTransparent = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        ButterKnife.bind(this);
        projectBm = getIntent().getStringExtra(Constants.PROJECTBM);
        init();
        addListener();
        loadDate(true,false);
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
        } else if (event.getType() == EventBarEntity.LOGIN_UPDATE_WEB) {
            reload();
        }
    }

    private void updateTitle1() {
        tabs.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.GONE);
        viewpager.setPagingEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
    private void progressDismiss() {
        if (myProgressDialog!=null&&myProgressDialog.isShowing()){
            myProgressDialog.dismiss();
        }
    }

    private void loadDate(final boolean isFirst, final boolean isShowLoading) {
        if (isFirst && !NetworkUtils.isNetworkAvailable(this)) {
            ibShare.setVisibility(View.GONE);
            llBottomView.setVisibility(View.GONE);
            progressWheelDetils.setVisibility(View.GONE);
            rlNoDataView.setVisibility(View.VISIBLE);
            ivNoData.setImageResource(R.drawable.img_wwl);
            tvNoData.setText(getString(R.string.str_no_network));
            tvRefresh.setVisibility(View.VISIBLE);
            Toast.makeText(this, getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            viewpager.setPagingEnabled(false);
            return;
        }
        if (!isFirst && !NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            viewpager.setPagingEnabled(false);
            return;
        }
        if (isFirst && NetworkUtils.isNetworkAvailable(this)) {
            rlServiceFail.setVisibility(View.GONE);
            rlNoDataView.setVisibility(View.GONE);
            progressWheelDetils.setVisibility(View.VISIBLE);
        }
        if (isShowLoading){
            myProgressDialog= new MyProgressDialog(ProjectDetilsActivity.this);
            myProgressDialog.show();
        }
        JacksonRequest<ProjectDetailV2Model> jacksonRequest = new JacksonRequest<>(RequestMap.projectDetailV2(projectBm), ProjectDetailV2Model.class, new Response.Listener<ProjectDetailV2Model>() {
            @Override
            public void onResponse(ProjectDetailV2Model baseVo) {
                progressWheelDetils.setVisibility(View.GONE);
                llBottomView.setVisibility(View.VISIBLE);
                progressDismiss();
                if (baseVo.getCode().equals(Constants.SUCCESS)) {
                    resultBean = baseVo.getResult();
                    viewpager.setPagingEnabled(true);
                    if (resultBean.getStatus() == Constants.STATUS_6_ZCSB) {
                        Toast.makeText(ProjectDetilsActivity.this, "该项目已下线", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                    projectDetailFragment.setData(resultBean);
                    projectDetailFragment.loadUrl(Urls.PROJECT_URL + resultBean.getDetailUrl());
//                    contentDetailFragment.loadUrl(Urls.PROJECT_URL + resultBean.getDetailUrl());
                    setBottomView();
                    //判断当前账户是否在其它设备登录
                    Util.loginPrompt(ProjectDetilsActivity.this, baseVo.getResult().getLoginState(), baseVo.getResult().getLoginDescr());
                } else {
                    if (isFirst){
                        viewpager.setPagingEnabled(false);
                        projectDetailFragment.ptlm.setVisibility(View.GONE);
                        rlServiceFail.setVisibility(View.VISIBLE);
                        tvServiceFailContent.setText(getString(R.string.str_fwrsgd));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressDismiss();
                if (isFirst){
                    viewpager.setPagingEnabled(false);
                    progressWheelDetils.setVisibility(View.GONE);
                    tvServiceFailContent.setText(Util.getErrorMesg(volleyError));
                    rlServiceFail.setVisibility(View.VISIBLE);
                    projectDetailFragment.ptlm.setVisibility(View.GONE);
                }
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    @Override
    public void init() {
        projectDetailFragment = ProjectDetailFragment.newInstance(projectBm);
        contentDetailFragment = ContentDetailFragment.newInstance();
        pactFragment2 = PactFragment2.newInstance(projectBm);
        Fragment[] fragments = new Fragment[]{projectDetailFragment, contentDetailFragment, pactFragment2};
        minePagerAdapter = new MinePagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setOffscreenPageLimit(3);
        viewpager.setAdapter(minePagerAdapter);
        tabs.setViewPager(viewpager, titles);
        int page = getIntent().getIntExtra(PAGE, 0);
        viewpager.setCurrentItem(page);
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
                    ProjectDetailFragment projectDetailFragment = ((ProjectDetailFragment) (minePagerAdapter.getFragments()[0]));
                    if (position == 0) {
                        projectDetailFragment.startAuto();
                    } else if (position == 1) {
                        projectDetailFragment.stopAuto();
                        if (resultBean != null) {
                            ContentDetailFragment contentDetailFragment = ((ContentDetailFragment) (minePagerAdapter.getFragments()[1]));
                            contentDetailFragment.loadUrl(Urls.PROJECT_URL + resultBean.getDetailUrl());
                        }
//                    ((ContentDetailFragment) (minePagerAdapter.getFragments()[1])).loadUrl("http://www.qiushibaike.com/imgrank/");
                    } else {
                        if (pactFragment2!=null){
                            pactFragment2.firstLoadData();
                        }
                        projectDetailFragment.stopAuto();
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
                ProjectDetailFragment projectDetailFragment = (ProjectDetailFragment) minePagerAdapter.getFragments()[0];
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
                ShareDialog shareDialog = new ShareDialog(ProjectDetilsActivity.this);
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
        oks.setTitle(resultBean.getShareTitle());
        oks.setText(resultBean.getShareSummary());
        oks.setSilent(silent);
        oks.setDialogMode();
        oks.setTitleUrl(Urls.PROJECT_URL + resultBean.getShareUrl());
        oks.setUrl(Urls.PROJECT_URL + resultBean.getShareUrl());
        oks.setSiteUrl(Urls.PROJECT_URL + resultBean.getShareUrl());
//        oks.setImageUrl(Urls.PROJECT_URL+"/Public/upfile/temp/2017-01-22/1485070783_768048327.png?125*84");
        oks.setImageUrl(Urls.PROJECT_URL + resultBean.getShareImage());
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

    public void setBottomView() {
        if (resultBean == null) {
            return;
        }
        final View inflate;

        if (llBottomView != null) {
            llBottomView.removeAllViews();
        }
        inflate = View.inflate(this, R.layout.view_bottom_status_ljrg, null);
        TextView tvBtn = (TextView) inflate.findViewById(R.id.tvBtn);
        tvBtn.setText(resultBean.getStatusname());
        switch (resultBean.getStatus()) {
            case Constants.STATUS_0_ZBSX:
                tvBtn = (TextView) inflate.findViewById(R.id.tvBtn);
                tvBtn.setBackgroundColor(getResources().getColor(R.color.color_project_detail_status_hui));
                break;
            case Constants.STATUS_1_ZCZ:
                tvBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        if (!SharedPreferenceUtil.isLogin(ProjectDetilsActivity.this)) {
                            noLogin();
                            return;
                        }
                        if (resultBean.getVerificationstatus() == Constants.TYPE_KHCG) {
                            intent = new Intent(ProjectDetilsActivity.this, ProjectBuyActivity.class);
                            intent.putExtra(Constants.PROJECTBM, projectBm);
                            startActivity(intent);
                        } else {
                            noKaihu(resultBean.getVerificationstatus());
                        }
                    }
                });
                break;
            case Constants.STATUS_2_DSZ:
                tvBtn.setBackgroundColor(getResources().getColor(R.color.color_project_detail_status_hui));
                break;
            case Constants.STATUS_4_TPWC:
                tvBtn.setBackgroundColor(getResources().getColor(R.color.color_project_detail_status_hui));
                break;
            case Constants.STATUS_5_ZCJS:
                tvBtn.setBackgroundColor(getResources().getColor(R.color.color_project_detail_status_hui));
                break;
//            case Constants.STATUS_6_ZCSB:
//                inflate = View.inflate(this, R.layout.view_bottom_status_qtp, null);
//                tvMessage = (TextView) inflate.findViewById(R.id.tvMessage);
//                tvBtn = (TextView) inflate.findViewById(R.id.tvBtn);
//                tvMessage.setVisibility(View.GONE);
//                llBottomView.addView(inflate, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                tvBtn.setBackgroundColor(getResources().getColor(R.color.color_project_detail_status_hui));
//                int isbackStatus = resultBean.getIsbackStatus();
//                if (isbackStatus == 0) {
//                    tvBtn.setText("项目结束，本金返还中");
//                } else {
//                    tvBtn.setText("项目结束，本金已返还");
//                }
//                break;
//            case Constants.STATUS_7_XSSB:
//                inflate = View.inflate(this, R.layout.view_bottom_status_qtp, null);
//                tvMessage = (TextView) inflate.findViewById(R.id.tvMessage);
//                tvBtn = (TextView) inflate.findViewById(R.id.tvBtn);
//                tvMessage.setVisibility(View.GONE);
//                llBottomView.addView(inflate, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                tvBtn.setBackgroundColor(getResources().getColor(R.color.color_project_detail_status_hui));
//                int isbackStatus1 = bottomView.result.getStatus_data().getIsbackStatus();
//                if (isbackStatus1 == 0) {
//                    tvBtn.setText("销售失败，将按照最低收益率" + bottomView.result.getStatus_data().getActualRate() + "回款");
//                } else {
//                    tvBtn.setText("销售失败，回款成功");
//                }
//                break;
        }
        llBottomView.addView(inflate, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    private void noLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProjectDetilsActivity.this);
        builder.setMessage("您还没有登录");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ProjectDetilsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }

    @OnClick({R.id.tvRefresh, R.id.tvServiceRefresh})
    public void onClick() {
        loadDate(true,false);
    }

    public void reload() {
        loadDate(false,true);
        if (pactFragment2!=null){
            pactFragment2.loadData();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (minePagerAdapter == null) {
                return super.onKeyDown(keyCode, event);
            }
            ProjectDetailFragment projectDetailFragment = (ProjectDetailFragment) minePagerAdapter.getFragments()[0];
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

    private void noKaihu(int verificationstatus) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProjectDetilsActivity.this);
        builder.setNegativeButton("取消", null);
        if (verificationstatus == Constants.TYPE_JBK) {
            builder.setMessage(getString(R.string.str_no_band_bankcard));
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("去绑卡", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(ProjectDetilsActivity.this, AccountSettingActivity.class);
                    intent.putExtra(AccountSettingActivity.IS_KAI_HU, false);
                    startActivity(intent);
                }
            });
            builder.show();
        } else if (verificationstatus == Constants.TYPE_SMRZ) {
            builder.setMessage(getString(R.string.str_wssmrzxx));
            builder.setPositiveButton("去实名", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(ProjectDetilsActivity.this, AccountSettingActivity.class);
                    startActivity(intent);
                }
            });
        } else if (verificationstatus == Constants.TYPE_BYHK) {
            builder.setMessage(getString(R.string.str_no_band_bankcard));
            builder.setPositiveButton("去绑卡", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(ProjectDetilsActivity.this, AccountSettingActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            builder.setMessage(getString(R.string.str_ktdsfzjtg));
            builder.setPositiveButton("去开通", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(ProjectDetilsActivity.this, AccountSettingActivity.class);
                    startActivity(intent);
                }
            });
        }
        builder.show();
    }
}
