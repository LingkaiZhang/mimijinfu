package com.nongjinsuo.mimijinfu.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.BaseVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
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
import com.nongjinsuo.mimijinfu.widget.ValidationCode;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.greenrobot.event.EventBus;

/**
 * @author czz
 * @Description: (注册页面)
 */
public class RegisterActivity extends AbstractActivity implements IBase, View.OnClickListener, View.OnLayoutChangeListener {
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.ivTopIcon)
    ImageView ivTopIcon;
    @BindView(R.id.rlTop)
    RelativeLayout rlTop;
    @BindView(R.id.etPhone)
    MyEditText etPhone;
    @BindView(R.id.etCode)
    MyEditText etCode;
    @BindView(R.id.tvHqCode)
    TextView tvHqCode;
    @BindView(R.id.progressSmall)
    ProgressWheel progressSmall;
    @BindView(R.id.llHqyzmView)
    LinearLayout llHqyzmView;
    @BindView(R.id.llCodeView)
    LinearLayout llCodeView;
    @BindView(R.id.etPwd)
    MyEditText etPwd;
    @BindView(R.id.llPwdView)
    LinearLayout llPwdView;
    @BindView(R.id.etPwdReqest)
    MyEditText etPwdReqest;
    @BindView(R.id.tvYyCode)
    TextView tvYyCode;
    @BindView(R.id.llYzm)
    LinearLayout llYzm;
    @BindView(R.id.tvQdl)
    TextView tvQdl;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tvAmxy)
    TextView tvAmxy;
    @BindView(R.id.rlLoginView)
    LinearLayout rlLoginView;
    @BindView(R.id.tvProblem)
    TextView tvProblem;
    @BindView(R.id.relative)
    RelativeLayout relative;
    private int recLen = 60;
    private boolean zoomflag = true;
    private boolean largenflag = false;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private int imgStartHeight;
    private float translationY;
    private TimerTask task;
    private MyProgressDialog progressDialog;
    private PopupWindow popupWindow;
    private ValidationCode validationCode;
    private boolean sendCodeFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTemplate = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();
        addListener();
    }

    @Override
    public void init() {
        sendCodeFirst = SharedPreferenceUtil.getBoolean(this, SharedPreferenceUtil.SEND_CODE);
        etPhone.requestFocus();

        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivTopIcon.getLayoutParams();
        imgStartHeight = layoutParams.height;
        // X轴方向上的坐标
        translationY = rlLoginView.getTranslationY();

        progressDialog = new MyProgressDialog(RegisterActivity.this);
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
        findViewById(R.id.tvYyCode).setOnClickListener(this);
        findViewById(R.id.tvLogin).setOnClickListener(this);
        tvQdl.setOnClickListener(this);
        tvAmxy.setOnClickListener(this);

        tvHqCode.setOnClickListener(this);
        tvProblem.setOnClickListener(this);
        etPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomView(ivTopIcon);
            }
        });
        etCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomView(ivTopIcon);
            }
        });
    }


    private void haveTime() {
        tvHqCode.setClickable(false);
        final Timer timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { // UI thread
                    public void run() {
                        recLen--;
                        progressSmall.setVisibility(View.GONE);
                        tvHqCode.setVisibility(View.VISIBLE);
                        tvHqCode.setText(recLen + "秒");
                        if (recLen < 1) {
                            timer.cancel();
                            tvHqCode.setClickable(true);
                            recLen = 60;
                            tvHqCode.setText("重新发送");
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    @Override
    public void onClick(View view) {
        String phont;
        String validPhone;
        switch (view.getId()) {
            case R.id.ibBack:
                finish();
                break;
            case R.id.tvHqCode:
                phont = etPhone.getText().toString().trim();
                validPhone = ValidUtil.validPhone(phont);
                if (TextUtil.IsNotEmpty(validPhone)) {
                    showShortToastMessage(validPhone);
                    return;
                }
                etCode.requestFocus();
                if (!NetworkUtils.isNetworkAvailable(RegisterActivity.this)) {
                    showShortToastMessage(getString(R.string.str_no_network));
                    return;
                }
                //验证是否是第一次获取，第一次获取不显示验证码框
                if (sendCodeFirst) {
                    showPopWindow(phont,1);
                    return;
                }
                dxCode(phont);
                break;
            case R.id.tvYyCode:
                phont = etPhone.getText().toString().trim();
                validPhone = ValidUtil.validPhone(phont);
                if (TextUtil.IsNotEmpty(validPhone)) {
                    showShortToastMessage(validPhone);
                    return;
                }
                if (!NetworkUtils.isNetworkAvailable(RegisterActivity.this)) {
                    showShortToastMessage(getString(R.string.str_no_network));
                    return;
                }
                //验证是否是第一次获取，第一次获取不显示验证码框
                if (sendCodeFirst) {
                    showPopWindow(phont,0);
                    return;
                }
                yyCode(phont);
                break;
            case R.id.tvLogin:
                phont = etPhone.getText().toString().trim();
                validPhone = ValidUtil.validPhone(phont);
                if (TextUtil.IsNotEmpty(validPhone)) {
                    showShortToastMessage(validPhone);
                    return;
                }
                String code = etCode.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                String pwdReqest = etPwdReqest.getText().toString().trim();
                String validPassword = ValidUtil.validPassword(pwd);
                if (TextUtil.IsEmpty(code)) {
                    showShortToastMessage("请输入验证码");
                    return;
                }
                if (code.length() < 5) {
                    showShortToastMessage("请输入正确的验证码");
                    return;
                }
                if (TextUtil.IsNotEmpty(validPassword)) {
                    showShortToastMessage(validPassword);
                    return;
                }
                if (TextUtil.IsEmpty(pwdReqest)) {
                    showShortToastMessage("请再次输入密码");
                    return;
                }
                if (!pwd.equals(pwdReqest)) {
                    showShortToastMessage("两次密码输入不一致");
                    return;
                }
                register(phont, code, pwd);
                break;
            case R.id.tvProblem:
                Intent intent = new Intent(RegisterActivity.this, ZxkfWebViewActivity.class);
                intent.putExtra(ZxkfWebViewActivity.URL, Urls.ZXKF);
                intent.putExtra(ZxkfWebViewActivity.NOZXKF, false);
                startActivity(intent);
                break;
            case R.id.tvQdl:
                finish();
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.tvAmxy:
                Intent intent2 = new Intent(RegisterActivity.this, WebViewActivity.class);
                intent2.putExtra(WebViewActivity.URL, Urls.FWXY);
                startActivity(intent2);
                break;
        }
    }

    private void yyCode(String phont) {
        etCode.requestFocus();
        getCode(phont, 0);
    }

    private void dxCode(String phont) {
        progressSmall.setVisibility(View.VISIBLE);
        tvHqCode.setVisibility(View.INVISIBLE);
        getCode(phont, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    /**
     * @param phone
     * @param sta   1为短信0为语音
     */
    public void getCode(String phone, final int sta) {
        if (sta == 0) {
            progressDialog.setMessage("正在发送语音");
            progressDialog.show();
        }
        JacksonRequest<BaseVo> jacksonRequest = new JacksonRequest<>(RequestMap.getCodeMap(phone, sta, 1), BaseVo.class, new Response.Listener<BaseVo>() {
            @Override
            public void onResponse(BaseVo baseVo) {
                dismissDialog();
                showShortToastMessage(baseVo.getText());
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    sendCodeFirst=true;
                    SharedPreferenceUtil.saveBoolean(RegisterActivity.this,SharedPreferenceUtil.SEND_CODE,true);
                    if (sta == 1) {
                        llYzm.setVisibility(View.VISIBLE);
                        haveTime();
                    }
                } else {
                    progressSmall.setVisibility(View.GONE);
                    tvHqCode.setVisibility(View.VISIBLE);
                    tvHqCode.setClickable(true);
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
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    private void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void register(final String phone, String code, String pwd) {
        if (!NetworkUtils.isNetworkAvailable(RegisterActivity.this)) {
            showShortToastMessage(getString(R.string.str_no_network));
            return;
        }
        if (progressDialog != null) {
            progressDialog.setMessage("正在注册");
            progressDialog.show();
        }
        JacksonRequest<LoginModel> jacksonRequest = new JacksonRequest<>(RequestMap.getRegister(phone, code, pwd), LoginModel.class, new Response.Listener<LoginModel>() {
            @Override
            public void onResponse(LoginModel baseVo) {
                dismissDialog();
                showShortToastMessage(baseVo.text);
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    Util.savaUserInfo(RegisterActivity.this,baseVo.result.userId,baseVo.result.token);
//                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_LOGIN_VIEW));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROPERTY_FIRST));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.LOGIN_UPDATE_WEB));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_HOMEPAGE_MESSAGE));
                    Intent intent = new Intent(RegisterActivity.this, RegisterSuccessActivity.class);
                    startActivity(intent);
                    finish();
                    //设置别名
                    JPushInterface.setAlias(RegisterActivity.this, Constants.userid, new TagAliasCallback() {
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
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
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

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    protected void showPopWindow(final String phone, final int type) {
        // TODO Auto-generated method stub
        View popupWindow_view = getLayoutInflater().inflate(R.layout.popupwindow_code, null, false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果
        //popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 这里是位置显示方式,在屏幕的左侧
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        backgroundAlpha(0.3f);
        ColorDrawable dw = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAtLocation(mainBody, Gravity.CENTER, 0, -200);
        // 点击其他地方消失
        ImageView iv_cancel = (ImageView) popupWindow_view.findViewById(R.id.pay_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });
        validationCode = (ValidationCode) popupWindow_view.findViewById(R.id.validationCode);
        final EditText input = (EditText) popupWindow_view.findViewById(R.id.input);
        Button button = (Button) popupWindow_view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationCode.isEqualsIgnoreCase(input.getText().toString())) {
                    popupWindow.dismiss();
                    if (type == 1){
                        dxCode(phone);
                    }else{
                        yyCode(phone);
                    }
                } else {
                    showShortToastMessage("验证码输入有误");
                }
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }
}
