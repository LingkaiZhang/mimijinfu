package com.nongjinsuo.mimijinfu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.dao.http.Urls;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author czz
 * @Description: (注册成功)
 */
public class RegisterSuccessActivity extends AbstractActivity {
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.tvKaiTong)
    TextView tvKaiTong;
    @BindView(R.id.tvZhiyin)
    TextView tvZhiyin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTemplate = false;
        super.onCreate(savedInstanceState);
        isStatusBarTransparent = true;
        setContentView(R.layout.activity_register_success);
        ButterKnife.bind(this);
    }

    @Override
    public void init() {

    }

    @Override
    public void addListener() {

    }

    @OnClick({R.id.ibBack, R.id.tvKaiTong, R.id.tvZhiyin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibBack:
                finish();
                break;
            case R.id.tvKaiTong:
                finish();
                Intent intent = new Intent(RegisterSuccessActivity.this,AccountSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.tvZhiyin:
                Intent intent1 = new Intent(RegisterSuccessActivity.this,WebViewActivity.class);
                intent1.putExtra(WebViewActivity.URL, Urls.XSZY);
                startActivity(intent1);
                break;
        }
    }
}
