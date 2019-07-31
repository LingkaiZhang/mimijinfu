package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.JsObj;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import de.greenrobot.event.EventBus;


public class WebViewActivity extends AbstractActivity {
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
        WebSettings settings =webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsObj(this), "android");
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setBlockNetworkImage(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

//        webView.addJavascriptInterface(new JsObj(this), "android");
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")){//支持拨打电话
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                    return  true;
                }
                view.loadUrl(url);
                return true;
            }
        });
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
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
        EventBus.getDefault().register(this);
    }
    public void onEventMainThread(EventBarEntity entity) {
        if (entity.getType() == EventBarEntity.LOGIN_UPDATE_WEB){
            webView.loadUrl("javascript:getUser()");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        synCookies();
    }

    /**
     * 清除webview中的cookie
     */
    public void synCookies() {
        try {
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();//移除
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
            webView.clearHistory();
            webView.clearFormData();
            getCacheDir().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            templateTextViewLeft.setVisibility(View.VISIBLE);
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
