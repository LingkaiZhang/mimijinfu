package com.nongjinsuo.mimijinfu.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.httpmodel.LoginModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.Util;
import com.nongjinsuo.mimijinfu.util.ValidUtil;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;
import com.nongjinsuo.mimijinfu.widget.MyEditText;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.greenrobot.event.EventBus;

/**
 * @author czz
 * @Description: (登录页面)
 */
public class LoginActivity extends AbstractActivity implements IBase, View.OnClickListener, View.OnLayoutChangeListener {
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.tvProblem)
    TextView tvProblem;
    @BindView(R.id.ivTopIcon)
    ImageView ivTopIcon;
    @BindView(R.id.rlTop)
    RelativeLayout rlTop;
    @BindView(R.id.etPhone)
    MyEditText etPhone;
    @BindView(R.id.etPwd)
    MyEditText etPwd;
    @BindView(R.id.llPwdView)
    LinearLayout llPwdView;
    @BindView(R.id.tvWjmm)
    TextView tvWjmm;
    @BindView(R.id.tvQzc)
    TextView tvQzc;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.rlLoginView)
    LinearLayout rlLoginView;
    @BindView(R.id.relative)
    RelativeLayout relative;
    private boolean zoomflag = true;
    private boolean largenflag = false;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private int imgStartHeight;
    private float translationY;
    private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTemplate = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
        addListener();
    }

    @Override
    public void init() {
        etPhone.requestFocus();
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivTopIcon.getLayoutParams();
        imgStartHeight = layoutParams.height;
        // X轴方向上的坐标
        translationY = rlLoginView.getTranslationY();

        progressDialog = new MyProgressDialog(LoginActivity.this);
    }

    private void largenView(final ImageView ivTopIcon) {
        if (largenflag) {
            zoomflag = true;
            largenflag = false;
            final AnimatorSet animatorSet = new AnimatorSet();//组合动画
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivTopIcon, "scaleY", 0.8f, 1f);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivTopIcon, "scaleX", 0.8f, 1f);
            animatorSet.play(scaleY).with(scaleX);//两个动画同时开始
            animatorSet.setDuration(500);
            animatorSet.start();
            scaleY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Float animatedValue = (Float) valueAnimator.getAnimatedValue();
                    valueAnimator.getStartDelay();
//                ivTopIcon.setLayoutParams(new RelativeLayout.LayoutParams(100+animatedValue,100+animatedValue));
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivTopIcon.getLayoutParams();
                    layoutParams.height = (int) (imgStartHeight * animatedValue);
                    layoutParams.width = layoutParams.height;
                    LogUtil.e("animatedValue=" + animatedValue + "---layoutParams.height=" + layoutParams.height + "");
                    ivTopIcon.setLayoutParams(layoutParams);
                }
            });
        }
    }
    private void zoomView(final ImageView ivTopIcon) {
        if (zoomflag) {
            zoomflag = false;
            largenflag = true;
            final AnimatorSet animatorSet = new AnimatorSet();//组合动画
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivTopIcon, "scaleY", 1f, 0.8f);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivTopIcon, "scaleX", 1f, 0.8f);
//            ObjectAnimator transY = ObjectAnimator.ofFloat(rlLoginView, "translationY", translationY, translationY-100);

            animatorSet.play(scaleY).with(scaleX);//两个动画同时开始
            animatorSet.setDuration(500);
            animatorSet.start();
            scaleY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Float animatedValue = (Float) valueAnimator.getAnimatedValue();
                    valueAnimator.getStartDelay();
//                ivTopIcon.setLayoutParams(new RelativeLayout.LayoutParams(100+animatedValue,100+animatedValue));
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivTopIcon.getLayoutParams();
                    layoutParams.height = (int) (imgStartHeight * animatedValue);
                    layoutParams.width = (int) (imgStartHeight * animatedValue);
                    LogUtil.e("animatedValue=" + animatedValue + "---layoutParams.height=" + layoutParams.height + "");
                    ivTopIcon.setLayoutParams(layoutParams);
                }
            });
        }
    }

    @Override
    public void addListener() {
        findViewById(R.id.ibBack).setOnClickListener(this);
        findViewById(R.id.tvLogin).setOnClickListener(this);
        tvProblem.setOnClickListener(this);
        etPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomView(ivTopIcon);
            }
        });
        tvQzc.setOnClickListener(this);
        tvWjmm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String phont;
        String validPhone;
        Intent intent;
        switch (view.getId()) {
            case R.id.ibBack:
                finish();
                break;
            case R.id.tvLogin:
                phont = etPhone.getText().toString().trim();
                validPhone = ValidUtil.validPhone(phont);
                if (TextUtil.IsNotEmpty(validPhone)) {
                    showShortToastMessage(validPhone);
                    return;
                }
                String pwd = etPwd.getText().toString().trim();
                String validPassword = ValidUtil.validPassword(pwd);
                if (TextUtil.IsNotEmpty(validPassword)) {
                    showShortToastMessage(validPassword);
                    return;
                }
                login(phont, pwd);
                break;
            case R.id.tvProblem:
                intent= new Intent(LoginActivity.this, ZxkfWebViewActivity.class);
                intent.putExtra(ZxkfWebViewActivity.URL, Urls.ZXKF);
                intent.putExtra(ZxkfWebViewActivity.NOZXKF,false);
                startActivity(intent);
                break;
            case R.id.tvQzc:
                finish();
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent );
                break;
            case R.id.tvWjmm:
                intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
    public void login(final String phone, String pwd) {
        if (!NetworkUtils.isNetworkAvailable(LoginActivity.this)){
            showShortToastMessage(getString(R.string.str_no_network));
            return;
        }
        progressDialog.setMessage("正在登录");
        progressDialog.show();
        JacksonRequest<LoginModel> jacksonRequest = new JacksonRequest<>(RequestMap.getLogin(phone, pwd), LoginModel.class, new Response.Listener<LoginModel>() {
            @Override
            public void onResponse(LoginModel baseVo) {
                dismissDialog();
                showShortToastMessage(baseVo.text);
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    Util.savaUserInfo(LoginActivity.this,baseVo.result.userId,baseVo.result.token);
//                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_LOGIN_VIEW));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROPERTY_FIRST));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.LOGIN_UPDATE_WEB));
                    finish();
                    //设置别名
                    JPushInterface.setAlias(LoginActivity.this, Constants.userid, new TagAliasCallback() {
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissDialog();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        //添加layout大小发生改变监听器
        relative.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            tvProblem.setVisibility(view.GONE);
            zoomView(ivTopIcon);
//            Toast.makeText(LoginActivity.this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            tvProblem.setVisibility(view.VISIBLE);
            largenView(ivTopIcon);
//            Toast.makeText(LoginActivity.this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();

        }
    }
}
