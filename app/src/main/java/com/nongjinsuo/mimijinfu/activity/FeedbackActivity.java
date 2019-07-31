package com.nongjinsuo.mimijinfu.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.BaseVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author czz
 * @Description: (意见反馈)
 */
public class FeedbackActivity extends AbstractActivity {
    @BindView(R.id.etYj)
    EditText etYj;
    @BindView(R.id.etQq)
    EditText etQq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        titleView.setText("我要吐槽");
        templateTextViewRight.setText("提交");
        templateTextViewRight.setVisibility(View.VISIBLE);
        init();
        addListener();
    }

    @Override
    public void init() {

    }

    @Override
    public void addListener() {
        templateTextViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yijian = etYj.getText().toString().trim();
                if (TextUtil.IsEmpty(yijian)) {
                    showShortToastMessage("请填写您的宝贵意见");
                    return;
                }
                String qq = etQq.getText().toString().trim();
                if (TextUtil.IsEmpty(qq)) {
                    showShortToastMessage("请填写您的联系方式，方便我们联系你");
                    return;
                }
                yjfk(yijian, qq);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    /**
     * 意见反馈
     */
    void yjfk(String yj, String qq) {
        final MyProgressDialog myProgressDialog = new MyProgressDialog(FeedbackActivity.this, "正在提交");
        myProgressDialog.show();
        JacksonRequest<BaseVo> jacksonRequest = new JacksonRequest<>(RequestMap.suggest(yj, qq), BaseVo.class, new Response.Listener<BaseVo>() {
            @Override
            public void onResponse(BaseVo baseVo) {
                myProgressDialog.dismiss();
//                showShortToastMessage(baseVo.getText());/
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null && volleyError.getCause() != null) {
                    myProgressDialog.dismiss();
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }
}
