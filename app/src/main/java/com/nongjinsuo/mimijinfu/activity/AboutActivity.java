package com.nongjinsuo.mimijinfu.activity;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.util.MobileInfoUtils;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.Util;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 关于我们
 * autour: czz
 * date: 2017/4/21 0021 下午 5:17
 * update: 2017/4/21 0021
 * version:
 */
public class AboutActivity extends AbstractActivity implements OnPermissionCallback {
    private PermissionHelper permissionHelper;
    private final static String[] MULTI_PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleView.setText("关于米米");
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        init();
        addListener();
    }

    @Override
    public void init() {
    }

    @Override
    public void addListener() {

    }

    @OnClick({R.id.llXxpl, R.id.llBzzx, R.id.llKfrx, R.id.llGfqq, R.id.llYjfk, R.id.llSbxx})
    public void onViewClicked(View view) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, getString(R.string.str_no_network), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent;
        switch (view.getId()) {
            case R.id.llXxpl:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL, Urls.BAO_ZHANG);
                startActivity(intent);
                break;
            case R.id.llBzzx:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL, Urls.BZZX + MobileInfoUtils.getDeviceBrand());
                startActivity(intent);
                break;
            case R.id.llKfrx:
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                builder.setTitle("客服热线");
                builder.setMessage(getString(R.string.str_kfrx));
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + getString(R.string.str_kfrx));
                        intent.setData(data);
                        if (ActivityCompat.checkSelfPermission(AboutActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            permissionHelper = PermissionHelper.getInstance(AboutActivity.this);
                            permissionHelper
                                    .setForceAccepting(false) // default is false. its here so you know that it exists.
                                    .request(MULTI_PERMISSIONS);
                            return;
                        }
                        startActivity(intent);
                    }
                });
                builder.show();
                break;
            case R.id.llGfqq:
                copy(getString(R.string.str_gfqq),this);
                break;
            case R.id.llYjfk:
                intent = new Intent(this, FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.llSbxx:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("设备信息");
                builder1.setMessage("手机型号：" + Build.MODEL + "\n系统版本：Android" + Build.VERSION.RELEASE + "\nApp版本：" + Util.getAppVersionName(this));
                builder1.setPositiveButton("确定", null);
                builder1.show();
                break;
        }
    }

    /**
     * 实现文本复制功能
     * @param content
     */
    public  void copy(String content, Context context) {
// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        showShortToastMessage("已复制到剪切板");
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        permissionHelper.onActivityForResult(requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionGranted(@NonNull String[] permissionName) {
        Log.i("onPermissionGranted", "Permission(s) " + Arrays.toString(permissionName) + " Granted");
    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissionName) {
        Log.i("onPermissionDeclined", "Permission(s) " + Arrays.toString(permissionName) + " Declined");
    }

    @Override
    public void onPermissionPreGranted(@NonNull String permissionsName) {
        Log.i("onPermissionPreGranted", "Permission( " + permissionsName + " ) preGranted");
    }

    @Override
    public void onPermissionNeedExplanation(@NonNull String permissionName) {
        Log.i("NeedExplanation", "Permission( " + permissionName + " ) needs Explanation");
//        new AlertDialog.Builder(SettingActivity.this)
//                .setMessage("app需要开启拨打电话权限")
//                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        intent.setData(Uri.parse("package:" + getPackageName()));
//                        startActivity(intent);
//                    }
//                })
//                .setNegativeButton("取消", null)
//                .create()
//                .show();
    }

    @Override
    public void onPermissionReallyDeclined(@NonNull String permissionName) {
        Log.i("ReallyDeclined", "Permission " + permissionName + " can only be granted from settingsScreen");
        /** you can call  {@link PermissionHelper#openSettingsScreen(Context)} to open the settings screen */
        new AlertDialog.Builder(AboutActivity.this)
                .setMessage("app需要开启拨打电话权限")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    @Override
    public void onNoPermissionNeeded() {
        Log.i("onNoPermissionNeeded", "Permission(s) not needed");
    }
}
