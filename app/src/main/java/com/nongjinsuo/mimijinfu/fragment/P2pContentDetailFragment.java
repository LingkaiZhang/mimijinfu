package com.nongjinsuo.mimijinfu.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.IBase;


/**
 * p2p详情
 */
public class P2pContentDetailFragment extends Fragment implements IBase {

    private View view;
    private WebView webview;
    private ProgressWheel progressWheel;
    public boolean flag = true;
    public P2pContentDetailFragment() {
        // Required empty public constructor
    }

    public static P2pContentDetailFragment newInstance() {
        P2pContentDetailFragment fragment = new P2pContentDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_content_detail, container, false);
        init();
        addListener();
        return view;
    }

    public void loadUrl(String url) {
        if (flag&&webview != null) {
            flag = false;
            webview.loadUrl(url);
        }
    }

    @Override
    public void init() {
        webview = (WebView) view.findViewById(R.id.webview);
        progressWheel = (ProgressWheel) view.findViewById(R.id.progress_wheel);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webview.setWebViewClient(new WebViewClient() {
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
//                titleView.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    new Thread() {
                        public void run() {
                            // 这儿是耗时操作，完成之后更新UI；
                            try {
                                sleep(200);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 更新UI
                                        progressWheel.setVisibility(View.GONE);
                                    }

                                });
                            }
                        }
                    }.start();
                }
                super.onProgressChanged(view, newProgress);
            }
        };
        webview.setWebChromeClient(wvcc);
    }

    @Override
    public void addListener() {

    }
}
