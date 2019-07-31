package com.nongjinsuo.mimijinfu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.IBase;
import com.nongjinsuo.mimijinfu.config.Constants;

/**
 * @author czz
 * @createdate 2016-1-5 下午5:56:19
 * @Description: TODO(分享)
 */
public class ShareDialog extends Dialog implements IBase, OnClickListener {

    private ImageView tvWechat;
    private ImageView tvWechatMoments;
    private ImageView tvQzone;
    private ImageView tvTencentqq;
    private ShareClick shareClick;
    private ImageView ivCancel;

    public ShareClick getShareClick() {
        return shareClick;
    }
    RotateAnimation rotateAnimation;
    public void setShareClick(ShareClick shareClick) {
        this.shareClick = shareClick;
    }

    public ShareDialog(Context context) {
        super(context, R.style.custom_dialog);
        setContentView(R.layout.layout_share);
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void init() {
        tvWechat = (ImageView) findViewById(R.id.tvWechat);
        tvWechatMoments = (ImageView) findViewById(R.id.tvWechatMoments);
        tvQzone = (ImageView) findViewById(R.id.tvQzone);
        tvTencentqq = (ImageView) findViewById(R.id.tvTencentqq);
        ivCancel = (ImageView) findViewById(R.id.ivCancel);
        rotateAnimation = new RotateAnimation(0f,180f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(400);
        rotateAnimation.setStartOffset(50);
        rotateAnimation.setFillAfter(true);
        ivCancel.setAnimation(rotateAnimation);
    }

    @Override
    public void show() {
        super.show();
        tvWechat.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.anim_1_weixin));
        tvTencentqq.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.anim_2_qq));
        tvWechatMoments.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.anim_3_pengyouquan));
        tvQzone.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.anim_4_kongjian));
        ivCancel.startAnimation(rotateAnimation);
    }

    @Override
    public void addListener() {
        tvWechat.setOnClickListener(this);
        tvWechatMoments.setOnClickListener(this);
        tvQzone.setOnClickListener(this);
        tvTencentqq.setOnClickListener(this);
        ivCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (shareClick == null) {
            dismiss();
            return;
        }
        switch (v.getId()) {
            case R.id.tvWechat:
                shareClick.Wechat();
                dismiss();
                break;
            case R.id.tvWechatMoments:
                shareClick.WechatMoments();
                dismiss();
                break;
            case R.id.tvQzone:
                shareClick.Qzone();
                dismiss();
                break;
            case R.id.tvTencentqq:
                shareClick.Tencentqq();
                dismiss();
                break;
            case R.id.ivCancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface ShareClick {
        void Wechat();

        void WechatMoments();

        void Qzone();

        void Tencentqq();
    }
}
