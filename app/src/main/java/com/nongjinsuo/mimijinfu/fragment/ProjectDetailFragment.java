package com.nongjinsuo.mimijinfu.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
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
import com.nongjinsuo.mimijinfu.activity.ProjectDetilsActivity;
import com.nongjinsuo.mimijinfu.activity.WebViewAndJsActivity;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.httpmodel.ProjectDetailV2Model;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;
import com.nongjinsuo.mimijinfu.widget.MyScrollView;
import com.nongjinsuo.mimijinfu.widget.PullUpToLoadMore;
import com.youth.banner.loader.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.EventBus;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectDetailFragment extends Fragment implements IBase, BGARefreshLayout.BGARefreshLayoutDelegate {


    @BindView(R.id.tvMbje)
    TextView tvMbje;
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
    @BindView(R.id.tvZzc)
    TextView tvZzc;
    @BindView(R.id.tvMbjeDanWei)
    TextView tvMbjeDanWei;
    @BindView(R.id.tvPjqxTitle)
    TextView tvPjqxTitle;
    @BindView(R.id.tvPjqx)
    TextView tvPjqx;
    @BindView(R.id.llPjqx)
    LinearLayout llPjqx;
    @BindView(R.id.ivJianTou)
    ImageView ivJianTou;
    @BindView(R.id.tvMjzq)
    TextView tvMjzq;
    @BindView(R.id.tvQx)
    TextView tvQx;
    @BindView(R.id.tvSyje)
    TextView tvSyje;
    @BindView(R.id.tvSyjeDanWei)
    TextView tvSyjeDanWei;

    private String projectBm;


    public ProjectDetailFragment() {

    }


    public static ProjectDetailFragment newInstance(String projectBm) {
        ProjectDetailFragment fragment = new ProjectDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PROJECTBM, projectBm);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            projectBm = getArguments().getString(Constants.PROJECTBM);
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_project, container, false);
        ButterKnife.bind(this, view);
        init();
        addListener();
        return view;
    }

    public void setData(ProjectDetailV2Model.ResultBean resultBean) {
        ptlm.setVisibility(View.VISIBLE);
        setViewForStatus(resultBean);
    }

    private void setViewForStatus(final ProjectDetailV2Model.ResultBean result) {
        tvMbje.setText(result.getNeedMoney());
        tvMbjeDanWei.setText(result.getNeedUnit());
        tvProgress.setText(result.getInvestprogress() + "%");
        myProgress.setProgress(result.getInvestprogress());

        tvZcNum.setText(result.getInvestPeopleNum() + "人投资");
        tvXmmc.setText(result.getName());
        tvZcje.setText(result.getNeedMoney() + result.getNeedUnit());

        tvRgdfje.setText(result.getMinBuyMoney());
        tvZcsj.setText(result.getRate());
        tvYjxssj.setText("90天内随时回款");
        tvMjzq.setText(result.getInvestDuraDays());//募集周期

        setContentStyle2(result.getEstimateSaledays(), tvQx);
        tvSyje.setText(result.getResidueMoney());
        tvSyjeDanWei.setText(result.getResidueUnit());
        setContentStyle(result.getRate(), tvZzc);
        //设置平均期限
        tvPjqxTitle.setText(result.getPartTitle());
        tvPjqx.setText(result.getPartSubTitle());
        if (TextUtil.IsNotEmpty(result.getPartUrl())) {
            ivJianTou.setVisibility(View.VISIBLE);
            llPjqx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WebViewAndJsActivity.class);
                    intent.putExtra(WebViewAndJsActivity.URL, result.getPartUrl());
                    startActivity(intent);
                }
            });
        } else {
            ivJianTou.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * @param content
     * @param textView
     */
    public void setContentStyle(String content, TextView textView) {
        //检索字符串中含有非数字
        if (TextUtil.IsNotEmpty(content)) {
            int index = content.lastIndexOf("万");
            int index1 = content.lastIndexOf("%");
            int index2 = content.lastIndexOf("天");
            int index3 = content.lastIndexOf("元");
            try {
                if (index != -1 | index1 != -1 | index2 != -1 | index3 != -1) {
                    SpannableString styledText = new SpannableString(content);
                    styledText.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_jd_big_text_white), 0, content.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_jd_smile_text_white), content.length() - 1, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.setText(styledText, TextView.BufferType.SPANNABLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * @param content
     * @param textView
     */
    public void setContentStyle2(String content, TextView textView) {
        //检索字符串中含有非数字
        if (TextUtil.IsNotEmpty(content)) {
            int index = content.lastIndexOf("万");
            int index1 = content.lastIndexOf("%");
            int index2 = content.lastIndexOf("天");
            int index3 = content.lastIndexOf("元");
            try {
                if (index != -1 | index1 != -1 | index2 != -1 | index3 != -1) {
                    SpannableString styledText = new SpannableString(content);
                    styledText.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_jd_big_text_blick), 0, content.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(getActivity(), R.style.style_jd_smile_text_blick), content.length() - 1, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.setText(styledText, TextView.BufferType.SPANNABLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAuto();
    }

    public void stopAuto() {
    }

    public void startAuto() {
    }

    @Override
    public void onResume() {
        super.onResume();
        startAuto();
//        loadData(false);
    }


    @OnClick({R.id.llZcjl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llZcjl:
                Intent intent = new Intent(getActivity(), FundingRecordActivity.class);
                intent.putExtra(Constants.PROJECTBM, projectBm);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        ((ProjectDetilsActivity) getActivity()).reload();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
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
            Glide.with(context).load(Urls.PROJECT_URL + path).placeholder(R.drawable.img_ltblbmr).centerCrop().crossFade().into(imageView);
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
