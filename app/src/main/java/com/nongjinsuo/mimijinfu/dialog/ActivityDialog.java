package com.nongjinsuo.mimijinfu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.IBase;
import com.nongjinsuo.mimijinfu.activity.WebViewAndJsActivity;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.util.PxAndDip;
import com.nongjinsuo.mimijinfu.util.TextUtil;

/**
 * @author czz
 * @createdate 2016-1-5 下午5:56:19
 * @Description: TODO(活动dialog)
 */
public class ActivityDialog extends Dialog implements IBase{

    ImageView ivImage;
    ImageView ivDjck;
    ImageView ivCancel;
    private String url;
    public ActivityDialog(Context context) {
        super(context, R.style.buy_dialog);
        setContentView(R.layout.dialog_activity);
        setingDialog();
        init();
        addListener();
    }

    private void setingDialog() {
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
        localLayoutParams.width = LayoutParams.WRAP_CONTENT;
        localLayoutParams.height = LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(localLayoutParams);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void init() {
        ivImage = (ImageView) findViewById(R.id.ivImage);
        ivDjck = (ImageView) findViewById(R.id.ivDjck);
        ivCancel= (ImageView) findViewById(R.id.ivCancel);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void addListener() {
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (TextUtil.IsNotEmpty(url)){
                    Intent intent = new Intent(getContext(),WebViewAndJsActivity.class);
                    intent.putExtra(WebViewAndJsActivity.URL,url);
                    getContext().startActivity(intent);
                }
            }
        });
    }

    /**
     * 设置广告图片
     * @param bitmap
     */
    public void setGgBitmap(Bitmap bitmap){
        if (bitmap!=null){
            ivImage.setImageBitmap(bitmap);
            int imgWidth = Constants.WINDOW_WIDTH - PxAndDip.dip2px(getContext(),80);
            int imgHeight = imgWidth*bitmap.getHeight()/bitmap.getWidth();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imgWidth,imgHeight);
            ivImage.setLayoutParams(layoutParams);
        }
    }
    /**
     * 设置跳转url
     * @param url
     */
    public void setDjCkUrl(String url){
        this.url = url;
    }
}