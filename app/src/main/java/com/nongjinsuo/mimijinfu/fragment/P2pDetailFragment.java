package com.nongjinsuo.mimijinfu.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.FundingRecordActivity;
import com.nongjinsuo.mimijinfu.activity.IBase;
import com.nongjinsuo.mimijinfu.activity.RepaymentScheduleActivity;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.httpmodel.ProductDetailModel;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;
import com.nongjinsuo.mimijinfu.widget.MyScrollView;
import com.nongjinsuo.mimijinfu.widget.PullUpToLoadMore;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.youth.banner.loader.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class P2pDetailFragment extends Fragment implements IBase {


    @BindView(R.id.tvZzc)
    TextView tvZzc;
    @BindView(R.id.tvStrZzc)
    TextView tvStrZzc;
    @BindView(R.id.llZcmx)
    LinearLayout llZcmx;
    @BindView(R.id.my_progress)
    ProgressBar myProgress;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.tvZcNum)
    TextView tvZcNum;
    @BindView(R.id.llZcjl)
    LinearLayout llZcjl;
    @BindView(R.id.tvXmmc)
    TextView tvXmmc;
    @BindView(R.id.tvZcje)
    TextView tvZcje;
    @BindView(R.id.tvZdhgj)
    TextView tvZdhgj;
    @BindView(R.id.tvRgdfje)
    TextView tvRgdfje;
    @BindView(R.id.tvZcsj)
    TextView tvZcsj;
    @BindView(R.id.tvYjxssj)
    TextView tvYjxssj;
    @BindView(R.id.ivMore)
    ImageView ivMore;
    @BindView(R.id.tvMore)
    TextView tvMore;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.scrollView2)
    MyScrollView scrollView2;
    @BindView(R.id.ptlm)
    public PullUpToLoadMore ptlm;
    @BindView(R.id.tvServiceRefresh)
    TextView tvServiceRefresh;
    @BindView(R.id.rlServiceFail)
    RelativeLayout rlServiceFail;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.tvXxhb)
    TextView tvXxhb;
    @BindView(R.id.tvJgdc)
    TextView tvJgdc;
    @BindView(R.id.tvFwdy)
    TextView tvFwdy;
    @BindView(R.id.llHkjh)
    LinearLayout llHkjh;
    @BindView(R.id.tvZe)
    TextView tvZe;
    @BindView(R.id.tvQx)
    TextView tvQx;
    @BindView(R.id.tvSy)
    TextView tvSy;
    @BindView(R.id.tvZeDanWei)
    TextView tvZeDanWei;
    @BindView(R.id.tvSyDanWei)
    TextView tvSyDanWei;
    @BindView(R.id.tvDcjg)
    TextView tvDcjg;
    @BindView(R.id.tvQxDanWei)
    TextView tvQxDanWei;
    @BindView(R.id.tvQtje)
    TextView tvQtje;
    @BindView(R.id.tvMjzq)
    TextView tvMjzq;
    @BindView(R.id.tvJkyt)
    TextView tvJkyt;
    private ProductDetailModel productDetailModel;

    public P2pDetailFragment() {

    }


    public static P2pDetailFragment newInstance() {
        P2pDetailFragment fragment = new P2pDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_p2p, container, false);
        ButterKnife.bind(this, view);
        init();
        addListener();
        return view;
    }

    public void setData(ProductDetailModel productDetailModel) {
        if (productDetailModel == null) {
            return;
        }
        this.productDetailModel = productDetailModel;
        ProductDetailModel.ResultBean productBean = productDetailModel.getResult();
        tvZzc.setText(productBean.getProjectRate() + "");
        tvXxhb.setText(productBean.getBackMoneyClassName());
        tvJgdc.setText(productBean.getAssure());
        tvFwdy.setText(productBean.getCaseClassName());
        myProgress.setProgress(productBean.getRatioMoney());
        tvProgress.setText(productBean.getRatioMoney() + "%");
        tvZe.setText(productBean.getNeedMoney());
        tvZeDanWei.setText(productBean.getNeedUnit());
        tvQx.setText(productBean.getProjectDay() + "");
        tvQxDanWei.setText(productBean.getProductUnit());
        tvSy.setText(productBean.getResidueMoney());
        tvSyDanWei.setText(productBean.getResidueUnit());
        tvZcNum.setText(productBean.getInvestPeopleNum() + "人投资");

        tvXmmc.setText(productBean.getName());
        tvZcje.setText(productBean.getNeedMoney() + productBean.getNeedUnit());
        tvRgdfje.setText(productBean.getProjectDay() + productBean.getProductUnit());
        tvZcsj.setText(productBean.getProjectRate() + "%");
        tvYjxssj.setText(productBean.getIncome() + "元");
        tvDcjg.setText(productBean.getCompany());

        tvQtje.setText(productBean.getMinBuyMoney());//起投金额
        tvMjzq.setText(productBean.getInvestDuraDays());//募集周期

        tvJkyt.setText(productBean.getBorrowerUse());
        loadUrl(Urls.PROJECT_URL + productDetailModel.getResult().getDetailUrl());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
//        loadData(false);
    }

    @OnClick({R.id.llZcjl, R.id.tvServiceRefresh, R.id.llHkjh})
    public void onClick(View view) {
        if (productDetailModel == null) {
            return;
        }
        Intent intent;
        switch (view.getId()) {
            case R.id.llZcjl:
                intent = new Intent(getActivity(), FundingRecordActivity.class);
                intent.putExtra(Constants.PROJECTBM, productDetailModel.getResult().getBm());
                intent.putExtra(FundingRecordActivity.ISRENGOU, true);
                startActivity(intent);
                break;
            case R.id.tvServiceRefresh:
                break;
            case R.id.llHkjh:
                intent = new Intent(getActivity(), RepaymentScheduleActivity.class);
                intent.putExtra(Constants.PROJECTBM, productDetailModel.getResult().getBm());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             常用的图片加载库：
             Universal Image Loader：一个强大的图片加载库，包含各种各样的配置，最老牌，使用也最广泛。
             Picasso: Square出品，必属精品。和OkHttp搭配起来更配呦！
             Volley ImageLoader：Google官方出品，可惜不能加载本地图片~
             Fresco：Facebook出的，天生骄傲！不是一般的强大。
             Glide：Google推荐的图片加载库，专注于流畅的滚动。
             */
            Glide.with(context).load(Urls.PROJECT_URL + path).placeholder(R.drawable.img_banner).crossFade().into(imageView);
        }
    }

    @JavascriptInterface
    public void resize(final float height) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getActivity(), height + "", Toast.LENGTH_LONG).show();
                webview.setLayoutParams(new RelativeLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
    }

    public void loadUrl(String url) {
        if (webview != null) {
            webview.loadUrl(url);
        }
    }

    @Override
    public void init() {
//        swipeRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(getContext(), true));
//        swipeRefreshLayout.setDelegate(this);
        WebSettings settings = webview.getSettings();
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webview.setFocusable(false);

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Toast.makeText(WebViewActivity.this, url, Toast.LENGTH_SHORT).show();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                webview.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
                super.onPageFinished(view, url);
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
                        }
                    }.start();
                }
                super.onProgressChanged(view, newProgress);
            }
        };
        webview.setWebChromeClient(wvcc);
        webview.addJavascriptInterface(this, "App");
        webview.loadUrl(Urls.PROJECT_DETAILS);
    }

    @Override
    public void addListener() {
        ptlm.setOnScrollToCurrPosition(new PullUpToLoadMore.OnScrollToCurrPosition() {
            @Override
            public void currPosition(int position) {
                //通知Activity修改TitleBar
                EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROJECT_DETAILS, position));
                if (position == 0) {
                    ivMore.setImageResource(R.drawable.img_sl);
                    tvMore.setText("上拉查看图文详情");
                } else {
                    ivMore.setImageResource(R.drawable.img_xl);
                    tvMore.setText("下拉查看项目");
                }
            }
        });
//        ptlm.setOnCanRefresh(new PullUpToLoadMore.OnCanRefresh() {
//            @Override
//            public void isRefresh(boolean boolRefresh) {
//                swipeRefreshLayout.setEnabled(boolRefresh);
//            }
//        });
    }
}
