package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
 * 充值跳转页面
 */
public class RechargeWebViewActivity extends AbstractActivity {
    private WebView webView;
    private ProgressBar progressBar;
    public static final String URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String url = intent.getStringExtra(URL);
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.webview_progressBar);
        progressBar.setMax(100);
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings settings =webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setBlockNetworkImage(false);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Toast.makeText(WebViewActivity.this, url, Toast.LENGTH_SHORT).show();
                if (url.startsWith("tel:")){//支持拨打电话
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                    return  true;
                }
                if (url.startsWith(Urls.PROJECT_URL)) {
                    ispaypwd();
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
    /**
     * 充值提现验证
     */
    public void ispaypwd() {
        JacksonRequest<UserverificationModel> jacksonRequest = new JacksonRequest<>(RequestMap.ispaypwd(), UserverificationModel.class, new Response.Listener<UserverificationModel>() {
            @Override
            public void onResponse(UserverificationModel baseVo) {
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROPERTY));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_XMRG));
                    setResult(RechargeActivity.FINISH);
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
                } else {
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
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
