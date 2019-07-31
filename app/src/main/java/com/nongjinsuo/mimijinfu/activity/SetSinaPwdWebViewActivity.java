package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.httpmodel.UserverificationModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import de.greenrobot.event.EventBus;

/**
 * 设置新浪支付密码
 */
public class SetSinaPwdWebViewActivity extends AbstractActivity {
    private WebView webView;
    private ProgressBar progressBar;
    public static final String URL = "url";
    public static final String VERIFICATION = "Verification";
    public static final String IS_UPDATE_PWD = "isUpdatePwd";
    public static final int UPDATE_PWD = 1;//修改密码
    public static final int UPDATE_DAIKOU = 2;//修改代扣
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String url = intent.getStringExtra(URL);
        type= intent.getIntExtra(IS_UPDATE_PWD, 0);
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.webview_progressBar);
        progressBar.setMax(100);
        WebSettings settings =webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setBlockNetworkImage(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Toast.makeText(WebViewActivity.this, url, Toast.LENGTH_SHORT).show();
                if (url.startsWith("tel:")){//支持拨打电话
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                    return  true;
                }
                if (type == 0&&url.startsWith(Urls.PROJECT_URL)) {
                    ispaypwd();
                    return true;
                }else if(type == UPDATE_PWD&&url.startsWith(Urls.PROJECT_URL)){
                    finish();
                    return true;
                }else if(type == UPDATE_DAIKOU&&url.startsWith(Urls.PROJECT_URL)){
                    isauthority();
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                // Log.d("ANDROID_LAB", "TITLE=" title);
                // title 就是网页的title
                titleView.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setProgress(100);

                    new Thread() {
                        public void run() {
                            // 这儿是耗时操作，完成之后更新UI；
                            try {
                                sleep(200);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    // 更新UI
                                    progressBar.setVisibility(View.GONE);
                                }

                            });
                        }
                    }.start();
                } else {
                    if (View.GONE == progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        };
        webView.setWebChromeClient(wvcc);
        webView.loadUrl(url);
        init();
        addListener();
    }

    /**
     * 检测是否设置交易密码
     */
    public void ispaypwd() {
        JacksonRequest<UserverificationModel> jacksonRequest = new JacksonRequest<>(RequestMap.ispaypwd(), UserverificationModel.class, new Response.Listener<UserverificationModel>() {
            @Override
            public void onResponse(UserverificationModel baseVo) {
                showShortToastMessage(baseVo.getText());
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_ANQUAN_SETTING));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.LOGIN_UPDATE_WEB));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROPERTY));
                    Intent intent = new Intent();
                    intent.putExtra(VERIFICATION, baseVo.result);
                    setResult(AccountSettingActivity.UPDATE_SETPWD, intent);
                    finish();
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
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
    /**
     * 更新委托代扣
     */
    public void isauthority() {
        JacksonRequest<UserverificationModel> jacksonRequest = new JacksonRequest<>(RequestMap.isauthority(), UserverificationModel.class, new Response.Listener<UserverificationModel>() {
            @Override
            public void onResponse(UserverificationModel baseVo) {
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_XMRG));
                    finish();
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
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }


    @Override
    public void init() {

    }

    @Override
    public void addListener() {
        templateButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoBack()) {
                    templateTextViewLeft.setVisibility(View.VISIBLE);
                    webView.goBack();
                }else{
                    finish();
                }
            }
        });
        templateTextViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            templateTextViewLeft.setVisibility(View.VISIBLE);
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
