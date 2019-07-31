package com.nongjinsuo.mimijinfu.beans;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.DingTouBaoListActivity;
import com.nongjinsuo.mimijinfu.activity.HuoTouBaoListActivity;
import com.nongjinsuo.mimijinfu.activity.LoginActivity;
import com.nongjinsuo.mimijinfu.activity.P2pDetilsActivity;
import com.nongjinsuo.mimijinfu.activity.ProjectDetilsActivity;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import de.greenrobot.event.EventBus;

/**
 * @author czz
 * @Description: (用一句话描述)
 */

public class JsObj {
    private Context con;
    public static final String SHARE_ENTITY = "shareEntity";
    public static final String TYPE = "type";

    public JsObj(Context con) {
        this.con = con;
    }


    //        public String userLogin() {
//            Intent intent = new Intent(con, LoginActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.drawable.push_left_in, R.drawable.push_left_out);
//            return "1";
//        }
//        public void openPost(String json) throws JSONException {
//            JSONObject jsonObject = new JSONObject(json);
//            Intent intent = new Intent(WebViewActivity.this, TieDetailActivity.class);
//            intent.putExtra("quanBm", jsonObject.optString("quanBm"));
//            Tie tie = new Tie();
//            tie.setTitle(jsonObject.optString("title"));
//            tie.setId(jsonObject.optString("id"));
//            tie.setPostBm(jsonObject.optString("postBm"));
//            tie.setQuanName(jsonObject.optString("quanName"));
//            tie.setQuanBm(jsonObject.optString("quanBm"));
//            tie.setBm(jsonObject.optString("postBm"));
//            intent.putExtra("detail", tie);
//            intent.putExtra("name", "yes");
//            startActivity(intent);
//            overridePendingTransition(R.drawable.push_left_in, R.drawable.push_left_out);
//        }
    @JavascriptInterface
    public String getUserInfo() {
        return "{'userId':'" + Constants.userid + "'}";//"{ userid:"+Constants.userid+"}"
    }

    @JavascriptInterface
    public void IntentQQ(String json) {
        final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=" + json + "&version=1";
        con.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
    }

    @JavascriptInterface
    public void loginState() {
        if (!SharedPreferenceUtil.isLogin(con)) {
            Intent intent = new Intent(con, LoginActivity.class);
            con.startActivity(intent);
        }
    }

    @JavascriptInterface
    public void webShare(String json) throws JSONException {
        LogUtil.d("WebViewAndJsActivity", "分享内容:" + json);
        if (TextUtil.IsNotEmpty(json)) {
            Gson gson = new Gson();
            ShareEntity shareVo = gson.fromJson(json, ShareEntity.class);
            if (shareVo.getShareId().equals("1")) {
                showShare(shareVo, Wechat.NAME);
            } else if (shareVo.getShareId().equals("2")) {
                showShare(shareVo, WechatMoments.NAME);
            } else if (shareVo.getShareId().equals("3")) {
                showShare(shareVo, QQ.NAME);
            } else {
                showShare(shareVo, QZone.NAME);
            }
        }
    }

    @JavascriptInterface
    public void openProject(String json) {
        Intent intent;
        if (!SharedPreferenceUtil.isLogin(con)) {
            intent = new Intent(con, LoginActivity.class);
            con.startActivity(intent);
            return;
        }
        if (TextUtil.IsNotEmpty(json)) {
            Gson gson = new Gson();
            OpenProjectBean openProjectBean = gson.fromJson(json, OpenProjectBean.class);
            if (openProjectBean.getClassId().equals(Constants.PROJECT)) {
                intent = new Intent(con, ProjectDetilsActivity.class);
                intent.putExtra(Constants.PROJECTBM, openProjectBean.getProjectBm());
                con.startActivity(intent);
            } else if (openProjectBean.getClassId().equals(Constants.PRODUCT)) {
                intent = new Intent(con, P2pDetilsActivity.class);
                intent.putExtra(Constants.PROJECTBM, openProjectBean.getProductBm());
                intent.putExtra(Constants.ID, openProjectBean.getProductId());
                con.startActivity(intent);
            }
        }
    }

    @JavascriptInterface
    public void showShareBtn(String json) throws JSONException {
        if (TextUtil.IsNotEmpty(json)) {
            Gson gson = new Gson();
            ShareEntity shareEntity = gson.fromJson(json, ShareEntity.class);
            EventBarEntity eventBarEntity = new EventBarEntity(EventBarEntity.SHOWSHAREBTN, shareEntity);
            EventBus.getDefault().post(eventBarEntity);
        }
    }

    @JavascriptInterface
    public void listProject(String json) throws JSONException {
        if (TextUtil.IsNotEmpty(json)) {
            JSONObject jsonObject = new JSONObject(json);
            String classId = jsonObject.optString("classId");
            if (classId.equals(Constants.PROJECT)) {
                Intent intent = new Intent(con, HuoTouBaoListActivity.class);
                con.startActivity(intent);
            } else if (classId.equals(Constants.PRODUCT)) {
                Intent intent = new Intent(con, DingTouBaoListActivity.class);
                con.startActivity(intent);
            }else{
                ((Activity)con).finish();
                EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROJECT_VIEW));
            }
        }
    }

    //快捷分享的文档：http://wiki.mob.com/Android_%E5%BF%AB%E6%8D%B7%E5%88%86%E4%BA%AB
    private void showShare(ShareEntity shareVo, String platform) {
        final OnekeyShare oks = new OnekeyShare();
        oks.setCallback(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onComplete(Platform arg0, int arg1,
                                   HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
//                shareSuccess();
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {

            }
        });
        //不同平台的分享参数，请看文档
        oks.setTitle(shareVo.getTitle());
        oks.setText(shareVo.getContent());
        oks.setSilent(true);
        oks.setDialogMode();
        oks.setTitleUrl(shareVo.getUrl());
        oks.setUrl(shareVo.getUrl());
        oks.setSiteUrl(shareVo.getUrl());
//        oks.setImageUrl(Urls.PROJECT_URL+"/Public/upfile/temp/2017-01-22/1485070783_768048327.png?125*84");
        oks.setImageUrl(shareVo.getImage());
        oks.setComment("");
        oks.setSite(con.getString(R.string.app_name));
        oks.disableSSOWhenAuthorize();
        if (platform != null) {
            oks.setPlatform(platform);
        }
        // 去自定义不同平台的字段内容
        // http://wiki.mob.com/Android_%E5%BF%AB%E6%8D%B7%E5%88%86%E4%BA%AB#.E4.B8.BA.E4.B8.8D.E5.90.8C.E5.B9.B3.E5.8F.B0.E5.AE.9A.E4.B9.89.E5.B7.AE.E5.88.AB.E5.8C.96.E5.88.86.E4.BA.AB.E5.86.85.E5.AE.B9
//        oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
        oks.show(con);
    }
}
