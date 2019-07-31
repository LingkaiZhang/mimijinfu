package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.httpmodel.UserverificationModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.ValidUtil;
import com.nongjinsuo.mimijinfu.widget.MyEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author czz
 * @Description: (身份验证)
 */
public class SfyzActivity extends AbstractActivity {
    @BindView(R.id.view1_etName)
    TextView view1EtName;
    @BindView(R.id.view1_etIDcard)
    MyEditText view1EtIDcard;
    @BindView(R.id.tvNext)
    TextView tvNext;
    public static final String NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sfyz);
        ButterKnife.bind(this);
        init();
        addListener();
    }

    @Override
    public void init() {
        titleView.setText("身份验证");
        view1EtName.setText(getIntent().getStringExtra(NAME));
    }

    @Override
    public void addListener() {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }
    /**
     * 验证身份
     */
    public void isverifyidentity(String identityName,String identityNo) {
        final MyProgressDialog myProgressDialog = new MyProgressDialog(this, "验证身份");
        myProgressDialog.show();
        JacksonRequest<UserverificationModel> jacksonRequest = new JacksonRequest<>(RequestMap.getIsverifyidentity(identityName,identityNo), UserverificationModel.class, new Response.Listener<UserverificationModel>() {
            @Override
            public void onResponse(UserverificationModel baseVo) {
                myProgressDialog.dismiss();
                showShortToastMessage(baseVo.getText());
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    finish();
                    Intent intent = new Intent(SfyzActivity.this,ReplacePhoneActivity.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                myProgressDialog.dismiss();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }
    @OnClick(R.id.tvNext)
    public void onClick() {
        String name = view1EtName.getText().toString().trim();
        String idCard = view1EtIDcard.getText().toString().trim();
        String validUserIdCode = ValidUtil.validUserIdCode(idCard);
        if (TextUtil.IsNotEmpty(validUserIdCode)){
            showShortToastMessage(validUserIdCode);
            return;
        }
        idCard = idCard.replaceAll("x","X");
        isverifyidentity(name,idCard);
    }
}
