package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.httpmodel.UserverificationModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author czz
 * @Description: (手机号码)
 */
public class PhoneActivity extends AbstractActivity {
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvGgsjh)
    TextView tvGgsjh;
    private  UserverificationModel.Verification verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        ButterKnife.bind(this);
        verification = (UserverificationModel.Verification) getIntent().getSerializableExtra(SecuritySettingsActivity.VERIFICATION);
        init();
        addListener();
    }

    @Override
    public void init() {
        titleView.setText("手机号码");
        if (verification!=null){
            tvPhone.setText(verification.getMobile());
        }

    }

    @Override
    public void addListener() {

    }

    @OnClick(R.id.tvGgsjh)
    public void onClick() {
        if (verification!=null){
            finish();
            if (verification.getVerificationstatus() == Constants.TYPE_KHCG){
                Intent intent = new Intent(PhoneActivity.this,SfyzActivity.class);
                intent.putExtra(SfyzActivity.NAME,verification.getIdentityName());
                startActivity(intent);
            }else{
                Intent intent1 = new Intent(PhoneActivity.this,ReplacePhoneActivity.class);
                startActivity(intent1);
            }
        }
    }
}
