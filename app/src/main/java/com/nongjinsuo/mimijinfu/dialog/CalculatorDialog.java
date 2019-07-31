package com.nongjinsuo.mimijinfu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.IBase;
import com.nongjinsuo.mimijinfu.config.Constants;

/**
 * @author czz
 * @createdate 2016-1-5 下午5:56:19
 * @Description: TODO(计算器)
 */
public class CalculatorDialog extends Dialog implements IBase, OnClickListener {

    private TextView tvYqnh;
    private TextView tvQx;
    private  EditText etGmje;
    private TextView tvYqsy;
    private TextView tvLjrg;
    private ILjtzClick iLjtzClick;

    public ILjtzClick getiLjtzClick() {
        return iLjtzClick;
    }

    public void setiLjtzClick(ILjtzClick iLjtzClick) {
        this.iLjtzClick = iLjtzClick;
    }

    public CalculatorDialog(Context context) {
        super(context, R.style.custom_dialog);
        setContentView(R.layout.dialog_calculator);
        setingDialog();
        init();
        addListener();
    }

    private void setingDialog() {
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        localLayoutParams.x = Constants.WINDOW_WIDTH;
        localLayoutParams.y = LayoutParams.WRAP_CONTENT;
        localLayoutParams.width = Constants.WINDOW_WIDTH;
        localLayoutParams.height = LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(localLayoutParams);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public TextView getTvYqnh() {
        return tvYqnh;
    }

    public void setTvYqnh(TextView tvYqnh) {
        this.tvYqnh = tvYqnh;
    }

    public TextView getTvQx() {
        return tvQx;
    }

    public void setTvQx(TextView tvQx) {
        this.tvQx = tvQx;
    }

    public EditText getEtGmje() {
        return etGmje;
    }

    public void setEtGmje(EditText etGmje) {
        this.etGmje = etGmje;
    }

    public TextView getTvYqsy() {
        return tvYqsy;
    }

    public void setTvYqsy(TextView tvYqsy) {
        this.tvYqsy = tvYqsy;
    }

    @Override
    public void init() {
        tvYqnh = (TextView) findViewById(R.id.tvYqnh);
        tvQx = (TextView) findViewById(R.id.tvQx);
        etGmje = (EditText) findViewById(R.id.etGmje);
        tvYqsy = (TextView) findViewById(R.id.tvYqsy);
        tvLjrg = (TextView) findViewById(R.id.tvLjrg);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void addListener() {
        tvLjrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iLjtzClick.litz(etGmje.getText().toString().trim());
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    public interface ILjtzClick{
        void litz(String gmje);
    }
}
