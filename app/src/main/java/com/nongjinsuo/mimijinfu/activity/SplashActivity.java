package com.nongjinsuo.mimijinfu.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.httpmodel.VersionModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.Util;
import com.nongjinsuo.mimijinfu.util.update.UpdateVersionService;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

/**
 * 欢迎页面
 */
public class SplashActivity extends Activity {
    @BindView(R.id.gifImageView)
    GifImageView gifImageView;
    @BindView(R.id.ivNetImg)
    ImageView ivNetImg;
    @BindView(R.id.tvNext)
    TextView tvNext;
    private static SplashActivity splashActivity;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    public static SplashActivity getActivity() {
        return splashActivity;
    }


    private VersionModel versionModel;
    Timer timer = new Timer();
    private int recLen = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.getUserInfo(this);
//        if (TextUtil.IsEmpty(Constants.clientid)) {
//            Constants.clientid = FMAgent.onEvent(this);
//            SharedPreferenceUtil.saveString(SplashActivity.this, SharedPreferenceUtil.CLIENTID, Constants.clientid);
//        }
        View inflate = LayoutInflater.from(this).inflate(R.layout.splash_view, null);
        setContentView(inflate);
        ButterKnife.bind(this);
        if (!NetworkUtils.isNetworkAvailable(this)) {
            String imgUrl = SharedPreferenceUtil.getString(this, SharedPreferenceUtil.GUANG_GAO);
            if (TextUtil.IsNotEmpty(imgUrl)) {
                String fileName = imgUrl.substring(imgUrl.lastIndexOf("/"));
                final File file = new File(getFilesDir().getAbsolutePath(), fileName);
                if (file.exists()) {
                    tvNext.setVisibility(View.VISIBLE);
                    ivNetImg.setVisibility(View.VISIBLE);
                    gifImageView.setVisibility(View.GONE);
                    timer.schedule(task, 1000, 1000);       // timeTask
                    ivNetImg.setImageBitmap(Util.getBitmap(file));

                    AlphaAnimation myAnimation_Alpha = new AlphaAnimation(0.1f, 1.0f);
                    myAnimation_Alpha.setDuration(500);
                    ivNetImg.startAnimation(myAnimation_Alpha);
                    LogUtil.d("img", "广告图片存在");
                } else {
                    startGifImg();
                }
            } else {
                startGifImg();
            }
            return;
        }
        JacksonRequest<VersionModel> jacksonRequest = new JacksonRequest<>(RequestMap.getStartMap(), VersionModel.class, new Response.Listener<VersionModel>() {
            @Override
            public void onResponse(final VersionModel baseVo) {
                versionModel = baseVo;
                if (baseVo == null) {
                    startGifImg();
                    return;
                }
                if (baseVo.result.getUpdateStatus() == 1) {
                    builder = new AlertDialog.Builder(SplashActivity.this);
                    builder.setMessage(baseVo.result.getUpdateDescr());
                    builder.setTitle("服务器升级提示");
//                    builder.setCancelable(false);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            finish();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                    return;
                }
                if (!baseVo.getCode().equals(Constants.SUCCESS)) {
                    startGifImg();
                    return;
                }
                if (baseVo.result.getOpenAdStatus() == 0) {
                    startGifImg();
                    return;
                }
                if (TextUtil.IsEmpty(baseVo.result.getOpenAdImage())) {
                    startGifImg();
                    return;
                }
//                    Glide.with(AiMiCrowdFundingApplication.context()).load(Urls.PROJECT_URL + baseVo.result.getOpenAdImage()).crossFade().into(ivNetImg);
                //保存图片的路径
                String imgUrl = baseVo.result.getOpenAdImage();
                SharedPreferenceUtil.saveString(SplashActivity.this, SharedPreferenceUtil.GUANG_GAO, imgUrl);
                String fileName = imgUrl.substring(imgUrl.lastIndexOf("/"));
                final File file = new File(getFilesDir().getAbsolutePath(), fileName);
                if (file.exists()) {
                    tvNext.setVisibility(View.VISIBLE);
                    ivNetImg.setVisibility(View.VISIBLE);
                    gifImageView.setVisibility(View.GONE);
                    timer.schedule(task, 1000, 1000);       // timeTask
                    ivNetImg.setImageBitmap(Util.getBitmap(file));

                    AlphaAnimation myAnimation_Alpha = new AlphaAnimation(0.1f, 1.0f);
                    myAnimation_Alpha.setDuration(500);
                    ivNetImg.startAnimation(myAnimation_Alpha);
                    LogUtil.d("img", "广告图片存在");
                } else {
                    LogUtil.d("img", "广告图片不存在，去下载");
                    downloadImg(imgUrl, file);
                    startGifImg();
                }
            }

        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.getMessage());
                intentMain(0);
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
        splashActivity = this;
    }

    private void downloadImg(String imgUrl, final File file) {
        ImageRequest imageRequest = new ImageRequest(Urls.PROJECT_URL + imgUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
//                                        ivNetImg.setImageBitmap(response);
                        //保存图片到内部存储
                        if (file.exists()) {
                            file.delete();
                        }
                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            response.compress(Bitmap.CompressFormat.PNG, 90, out);
                            out.flush();
                            out.close();
                            LogUtil.d("img", "广告图片下载成功");
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AiMiCrowdFundingApplication.context().addToRequestQueue(imageRequest, getClass().getName());
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    recLen--;
                    tvNext.setText(recLen + "s 跳过");
                    if (recLen == 0) {
                        intentMain(0);
                    }
                }
            });
        }
    };

    private void startGifImg() {
        gifImageView.setVisibility(View.VISIBLE);
        ivNetImg.setVisibility(View.GONE);
        tvNext.setVisibility(View.GONE);
        gifImageView.setBackgroundResource(R.drawable.img_hy);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intentMain(0);
            }
        }, 2700);
    }

    private void intentMain(int type) {
        Intent intent = new Intent();
        if (UpdateVersionService.getVersionCode(SplashActivity.this) > SharedPreferenceUtil.getInt(SplashActivity.this, SharedPreferenceUtil.VERSION_CODE)) {
            intent.setClass(SplashActivity.this, IndexGuideActivity.class);
        } else {
            intent.setClass(SplashActivity.this, MainActivity.class);
            if (type == 0) {
                if (getIntent().getBundleExtra(Constants.EXTRA_BUNDLE) != null) {
                    intent.putExtra(Constants.EXTRA_BUNDLE,
                            getIntent().getBundleExtra(Constants.EXTRA_BUNDLE));
                }
            } else {
                if (versionModel != null && versionModel.result != null) {
                    if (TextUtil.IsNotEmpty(versionModel.result.getOpenAdUrl())) {
                        intent.putExtra("openAdUrl", versionModel.result.getOpenAdUrl());
                    }
                }
            }
        }
        if (type==1){
            if (NetworkUtils.isNetworkAvailable(this)&&versionModel != null && versionModel.result != null&&TextUtil.IsNotEmpty(versionModel.result.getOpenAdUrl())){
                if (timer != null) {
                    timer.cancel();
                }
                startActivity(intent);
                overridePendingTransition(R.anim.activity_close_in_alpha_anim, R.anim.activity_close_out_alpha_anim);
                finish();
            }
        }else{
            if (timer != null) {
                timer.cancel();
            }
            startActivity(intent);
            overridePendingTransition(R.anim.activity_close_in_alpha_anim, R.anim.activity_close_out_alpha_anim);
            finish();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }


    @OnClick({R.id.ivNetImg, R.id.tvNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivNetImg:
                intentMain(1);
                break;
            case R.id.tvNext:
                intentMain(0);
                break;
        }
    }
}
