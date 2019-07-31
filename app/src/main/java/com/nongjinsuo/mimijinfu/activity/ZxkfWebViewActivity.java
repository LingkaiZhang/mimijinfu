package com.nongjinsuo.mimijinfu.activity;

import android.Manifest;
import android.app.Activity;
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
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.JsObj;
import com.nongjinsuo.mimijinfu.util.MediaUtility;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import java.io.File;
import java.util.Arrays;

import de.greenrobot.event.EventBus;

/**
 * 在线客服
 */
public class ZxkfWebViewActivity extends AbstractActivity implements OnPermissionCallback {
    private WebView webView;
    private ProgressBar progressBar;
    public static final String URL = "url";
    public static final String NOZXKF = "noZxkf";
    public boolean noZxkf;
    private PermissionHelper permissionHelper;

    private final static String[] MULTI_PERMISSIONS = new String[]{
//            Manifest.permission.CALL_PHONE,
//            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    /**
     * 以下是webview的照片上传时候，用于在网页打开android图库文件
     */
    public OpenFileWebChromeClient mOpenFileWebChromeClient = new OpenFileWebChromeClient(
            this);

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == OpenFileWebChromeClient.REQUEST_FILE_PICKER) {
            if (mOpenFileWebChromeClient.mFilePathCallback != null) {
                Uri result = intent == null || resultCode != Activity.RESULT_OK ? null
                        : intent.getData();
                if (result != null) {
                    String path = MediaUtility.getPath(getApplicationContext(),
                            result);
                    Uri uri = Uri.fromFile(new File(path));
                    mOpenFileWebChromeClient.mFilePathCallback
                            .onReceiveValue(uri);
                } else {
                    mOpenFileWebChromeClient.mFilePathCallback
                            .onReceiveValue(null);
                }
            }
            if (mOpenFileWebChromeClient.mFilePathCallbacks != null) {
                Uri result = intent == null || resultCode != Activity.RESULT_OK ? null
                        : intent.getData();
                if (result != null) {
                    String path = MediaUtility.getPath(getApplicationContext(),
                            result);
                    Uri uri = Uri.fromFile(new File(path));
                    mOpenFileWebChromeClient.mFilePathCallbacks
                            .onReceiveValue(new Uri[]{uri});
                } else {
                    mOpenFileWebChromeClient.mFilePathCallbacks
                            .onReceiveValue(null);
                }
            }

            mOpenFileWebChromeClient.mFilePathCallback = null;
            mOpenFileWebChromeClient.mFilePathCallbacks = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String url = intent.getStringExtra(URL);
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.webview_progressBar);
        progressBar.setMax(100);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setBlockNetworkImage(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.addJavascriptInterface(new JsObj(this), "android");
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {//支持拨打电话
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
        noZxkf = getIntent().getBooleanExtra(NOZXKF, true);
        webView.setWebChromeClient(mOpenFileWebChromeClient);
        webView.loadUrl(url);
        init();
        addListener();
        permissionHelper = PermissionHelper.getInstance(ZxkfWebViewActivity.this);
        permissionHelper.setForceAccepting(false) // default is false. its here so you know that it exists.
                .request(MULTI_PERMISSIONS);
        EventBus.getDefault().register(this);
    }
    public void onEventMainThread(EventBarEntity entity) {
        if (entity.getType() == EventBarEntity.LOGIN_UPDATE_WEB){
            webView.loadUrl("javascript:getUser()");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        synCookies();
    }
    /**
     * 清除webview中的cookie
     */
    public void synCookies() {
        try {
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();//移除
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
            webView.clearHistory();
            webView.clearFormData();
            getCacheDir().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void init() {

    }

    @Override
    public void addListener() {
        templateButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoBack() && noZxkf) {
                    templateTextViewLeft.setVisibility(View.VISIBLE);
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });
        templateTextViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack() && noZxkf) {
            templateTextViewLeft.setVisibility(View.VISIBLE);
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    public class OpenFileWebChromeClient extends WebChromeClient {
        public static final int REQUEST_FILE_PICKER = 1;
        public ValueCallback<Uri> mFilePathCallback;
        public ValueCallback<Uri[]> mFilePathCallbacks;
        Activity mContext;

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            titleView.setText(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setProgress(100);

                new Thread() {
                    public void run() {
                        // 这儿是耗时操作，完成之后更新UI；
                        try {
                            sleep(200);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // 更新UI
                                progressBar.setVisibility(View.GONE);
                            }

                        });
                    }
                }.start();
            } else {
                if (View.GONE == progressBar.getVisibility()) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        public OpenFileWebChromeClient(Activity mContext) {
            super();
            this.mContext = mContext;
        }

        // Android < 3.0 调用这个方法
        public void openFileChooser(ValueCallback<Uri> filePathCallback) {
//            Toast.makeText(ZxkfWebViewActivity.this,"openFileChooser-<3.0",Toast.LENGTH_SHORT).show();
            mFilePathCallback = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
                    REQUEST_FILE_PICKER);
        }

        // 3.0 + 调用这个方法
        public void openFileChooser(ValueCallback filePathCallback,
                                    String acceptType) {
//            Toast.makeText(ZxkfWebViewActivity.this,"openFileChooser-3.0 + ",Toast.LENGTH_SHORT).show();
            mFilePathCallback = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
                    REQUEST_FILE_PICKER);
        }

        //  / js上传文件的<input type="file" name="fileField" id="fileField" />事件捕获
        // Android > 4.1.1 调用这个方法
        public void openFileChooser(ValueCallback<Uri> filePathCallback,
                                    String acceptType, String capture) {
//            Toast.makeText(ZxkfWebViewActivity.this,"openFileChooser-4.1.1",Toast.LENGTH_SHORT).show();
            mFilePathCallback = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
                    REQUEST_FILE_PICKER);
        }

        @Override
        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         WebChromeClient.FileChooserParams fileChooserParams) {
//            Toast.makeText(ZxkfWebViewActivity.this,"onShowFileChooser",Toast.LENGTH_SHORT).show();
            if (ActivityCompat.checkSelfPermission(ZxkfWebViewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
              new AlertDialog.Builder(ZxkfWebViewActivity.this)
                        .setMessage("打开相册需要读取存储权限")
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return false;
            }
            mFilePathCallbacks = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
                    REQUEST_FILE_PICKER);
            return true;
        }
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
        new AlertDialog.Builder(ZxkfWebViewActivity.this)
                .setMessage("打开相册需要读取存储权限")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);

                    }
                }).setNegativeButton("取消", null).show();
    }

    @Override
    public void onNoPermissionNeeded() {
        Log.i("onNoPermissionNeeded", "Permission(s) not needed");
    }
}
