package com.nongjinsuo.mimijinfu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.BaseVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.httpmodel.UserverificationModel;
import com.nongjinsuo.mimijinfu.httpmodel.VersionModel;
import com.nongjinsuo.mimijinfu.util.DataCleanManager;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.Util;
import com.nongjinsuo.mimijinfu.util.update.UpdateVersionService;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author czz
 * @Description: (设置)
 */
public class  SettingActivity extends AbstractActivity{
    @BindView(R.id.tvAqsz)
    TextView tvAqsz;
    @BindView(R.id.tvMmgl)
    TextView tvMmgl;
    @BindView(R.id.tvYkq)
    TextView tvYkq;
    @BindView(R.id.llDgsq)
    LinearLayout llDgsq;
    @BindView(R.id.llGg)
    LinearLayout llGg;
    @BindView(R.id.llMessage)
    LinearLayout llMessage;
    @BindView(R.id.tvLogout)
    TextView tvLogout;
    private static SettingActivity activity;
    @BindView(R.id.tvVersionName)
    TextView tvVersionName;
    @BindView(R.id.tvCache)
    TextView tvCache;
    @BindView(R.id.llQchc)
    LinearLayout llQchc;

    private UserverificationModel.Verification verification;

    private boolean uploadFlag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        init();
        addListener();
        loadData(true);
        EventBus.getDefault().register(this);
        activity = this;

    }

    public static SettingActivity getInstance() {
        return activity;
    }

    public void onEventMainThread(EventBarEntity entity) {
        if (entity.getType() == EventBarEntity.FINISH_ACTIVITY) {
            finish();
        } else if (entity.getType() == EventBarEntity.UPDATE_SETTING) {
            loadData(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    @Override
    public void init() {
        titleView.setText("设置");
        tvVersionName.setText("V" + Util.getAppVersionName(this));
        setCacheSize();
    }

    private void setCacheSize() {
        try {
            tvCache.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addListener() {

    }

    private void loadData(boolean isFirst) {
        if (isFirst)
            showLoading();
        JacksonRequest<UserverificationModel> jacksonRequest = new JacksonRequest<>(RequestMap.getUserverification(1), UserverificationModel.class, new Response.Listener<UserverificationModel>() {
            @Override
            public void onResponse(UserverificationModel baseVo) {
                cancleLoading();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    verification = baseVo.result;
                    if (baseVo.result.getVerificationstatus() >= Constants.TYPE_KHCG) {
                        tvYkq.setText("已开启");
                    } else {
                        tvYkq.setText("未开启");
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

    @OnClick({R.id.tvAqsz, R.id.tvMmgl, R.id.llDgsq, R.id.llGg, R.id.llMessage, R.id.tvLogout, R.id.llJcxbb,R.id.llQchc,R.id.llAbout,R.id.llZxkf,R.id.llYqjl})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tvAqsz:
                intent = new Intent(SettingActivity.this, SecuritySettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.tvMmgl:
                intent = new Intent(SettingActivity.this, PasswordManagementActivity.class);
                startActivity(intent);
                break;
            case R.id.llDgsq:
                if (verification != null) {
                    if (verification.getVerificationstatus() == Constants.TYPE_JBK) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                        builder.setMessage("请重新绑定银行卡");
                        builder.setNegativeButton("取消", null);
                        builder.setPositiveButton("去绑卡", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(SettingActivity.this, AccountSettingActivity.class);
                                intent.putExtra(AccountSettingActivity.IS_KAI_HU, false);
                                startActivity(intent);
                            }
                        });
                        builder.show();
                        return;
                    }
                    if (verification.getVerificationstatus() >= Constants.TYPE_KHCG) {
                        intent = new Intent(SettingActivity.this, SetSinaPwdWebViewActivity.class);
                        intent.putExtra(SetSinaPwdWebViewActivity.IS_UPDATE_PWD, SetSinaPwdWebViewActivity.UPDATE_DAIKOU);
                        intent.putExtra(SetSinaPwdWebViewActivity.URL, verification.getEditauthority_redirect_url());
                        startActivity(intent);
                    } else {
                        intent = new Intent(SettingActivity.this, AccountSettingActivity.class);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.llGg:
                intent = new Intent(SettingActivity.this, NoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.llMessage:
                intent = new Intent(SettingActivity.this, MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.tvLogout:
                AlertDialog.Builder logout = new AlertDialog.Builder(SettingActivity.this);
                logout.setMessage("是否退出登录");
                logout.setNegativeButton("取消", null);
                logout.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logOut();
                    }
                });
                logout.show();
                break;
            case R.id.llJcxbb:
                if (uploadFlag) {
                    uploadFlag = false;
                    updateVersion();
                } else {
                    showShortToastMessage("正在检查新版本...");
                }
                break;
            case R.id.llQchc:
                DataCleanManager.clearAllCache(SettingActivity.this);
                setCacheSize();
                break;
            case R.id.llAbout://关于我们
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.llZxkf://在线客服
                intent = new Intent(this, ZxkfWebViewActivity.class);
                intent.putExtra(ZxkfWebViewActivity.URL, Urls.ZXKF);
                intent.putExtra(ZxkfWebViewActivity.NOZXKF, false);
                startActivity(intent);
                break;
            case R.id.llYqjl://邀请有礼
                intent = new Intent(this, WebViewAndJsActivity.class);
                intent.putExtra(WebViewAndJsActivity.URL, Urls.YQJL);
                startActivity(intent);
                break;
        }
    }

    private void updateVersion() {
        JacksonRequest<VersionModel> jacksonRequest = new JacksonRequest<>(RequestMap.getStartMap(), VersionModel.class, new Response.Listener<VersionModel>() {
            @Override
            public void onResponse(VersionModel baseVo) {
                uploadFlag = true;
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    boolean flag = false;
                    if (baseVo.result.getAndroidForceUpdate().equals("1")) {
                        flag = true;
                    }
                    UpdateVersionService updateVersionService = new UpdateVersionService(SettingActivity.this, baseVo.result.getAndroidVersionInfo(), flag, baseVo.result.getDownload());// 创建更新业务对象
                    updateVersionService.checkUpdate(baseVo.result.getAndroidVersionCode(), baseVo.result.getAndroidVersionName(), false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                uploadFlag = true;
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }

    /**
     * 退出登录
     */
    void logOut() {
        final MyProgressDialog myProgressDialog = new MyProgressDialog(SettingActivity.this, "正在退出登录");
        myProgressDialog.show();
        JacksonRequest<BaseVo> jacksonRequest = new JacksonRequest<>(RequestMap.getlogout(), BaseVo.class, new Response.Listener<BaseVo>() {
            @Override
            public void onResponse(BaseVo baseVo) {
                myProgressDialog.dismiss();
                showShortToastMessage(baseVo.getText());
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    finish();
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_LOGOUT_VIEW));
                    Util.clearUserInfo(SettingActivity.this);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null && volleyError.getCause() != null) {
                    myProgressDialog.dismiss();
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }

}