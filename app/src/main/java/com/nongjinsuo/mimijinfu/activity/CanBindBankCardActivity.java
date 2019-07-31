package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.adapter.BankCardAdapter;
import com.nongjinsuo.mimijinfu.beans.BankVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.httpmodel.BanksModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author czz
 * @Description: (可以绑定的银行卡)
 */
public class CanBindBankCardActivity extends AbstractActivity {
    @BindView(R.id.listView)
    ListView listView;
    private  BankCardAdapter bankCardAdapter;
    public static final String BANK_VO = "bankVo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_can_bind_bankcard);
        titleView.setText("支持银行");
        ButterKnife.bind(this);
        init();
        addListener();
    }
    @Override
    public void init() {
        loadData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    private void loadData() {
        showLoading();
        JacksonRequest<BanksModel> jacksonRequest = new JacksonRequest<>(RequestMap.getSupportbank(), BanksModel.class, new Response.Listener<BanksModel>() {
            @Override
            public void onResponse(BanksModel baseVo) {
                cancleLoading();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    bankCardAdapter= new BankCardAdapter(true, CanBindBankCardActivity.this, baseVo.result);
                    listView.setAdapter(bankCardAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                cancleLoading();
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }
    @Override
    public void addListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (bankCardAdapter!=null){
                    BankVo item = bankCardAdapter.getItem(i);
                    Intent intent = new Intent();
                    intent.putExtra(BANK_VO,item);
                    setResult(AccountSettingActivity.SELECT_BANK,intent);
                    finish();
                }
            }
        });
    }
}
