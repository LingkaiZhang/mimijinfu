package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.JsObj;
import com.nongjinsuo.mimijinfu.beans.ShareEntity;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dialog.ShareDialog;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import java.util.HashMap;

import butterknife.BindView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import de.greenrobot.event.EventBus;


public class WebViewAndJsActivity extends AbstractActivity {
    private WebView webView;
    private ProgressBar progressBar;
    public static final String URL = "url";
    public static final String NOZXKF = "noZxkf";
    public boolean noZxkf;
    ShareDialog shareDialog;
    ShareEntity shareEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String url = intent.getStringExtra(URL);
        webView = (WebView) findViewById(R.id.webview);
        templateButtonRight.setImageResource(R.drawable.img_fx);
        progressBar = (ProgressBar) findViewById(R.id.webview_progressBar);
        progressBar.setMax(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsObj(this), "android");
        String ua = webView.getSettings().getUserAgentString();
        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setUserAgentString(ua.replace(Constants.ANDROID, "Android.caiyan"));
        settings.setBlockNetworkImage(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        noZxkf = getIntent().getBooleanExtra(NOZXKF, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Toast.makeText(WebViewActivity.this, url, Toast.LENGTH_SHORT).show();
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
        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(EventBarEntity entity) {
        if (entity.getType() == EventBarEntity.LOGIN_UPDATE_WEB) {
            webView.loadUrl("javascript:getUser()");
        }
        shareEntity = entity.getShareEntity();
        if (entity.getType() == EventBarEntity.SHOWSHAREBTN) {//showShareBtn
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // 更新UI
                    templateButtonRight.setVisibility(View.VISIBLE);
                }

            });
        }
    }

    private void showShareDialog() {
        if (shareDialog != null && shareDialog.isShowing()) {
            return;
        }
        shareDialog = new ShareDialog(WebViewAndJsActivity.this);
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
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {

            }
        });
        //不同平台的分享参数，请看文档
        //http://wiki.mob.com/Android_%E4%B8%8D%E5%90%8C%E5%B9%B3%E5%8F%B0%E5%88%86%E4%BA%AB%E5%86%85%E5%AE%B9%E7%9A%84%E8%AF%A6%E7%BB%86%E8%AF%B4%E6%98%8E
        if (shareEntity == null) {
            return;
        }
        String text = "" + "http://www.mob.com";
        oks.setTitle(shareEntity.getTitle());
        oks.setText(shareEntity.getContent());
        oks.setSilent(silent);
        oks.setDialogMode();
        oks.setTitleUrl(shareEntity.getUrl());
        oks.setUrl(shareEntity.getUrl());
        oks.setSiteUrl(shareEntity.getUrl());
        String imgUrl = shareEntity.getImage();
        oks.setImageUrl(imgUrl);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        synCookies();
    }


    @Override
    public void init() {

    }

    @Override
    public void addListener() {
        templateButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoBack() && noZxkf) {
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
        templateButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareDialog();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack() && noZxkf) {
            templateTextViewLeft.setVisibility(View.VISIBLE);
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
}
