package com.nongjinsuo.mimijinfu.util.update;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.util.Util;

import java.io.File;

/**
 * Created by chiclaim on 2016/05/18
 */
public class ApkUpdateUtils {
    public static final String TAG = ApkUpdateUtils.class.getSimpleName();

    private static final String KEY_DOWNLOAD_ID = "downloadId";

    public static void download(Context context, String url, String title) {
        long downloadId = SpUtils.getInstance(context).getLong(KEY_DOWNLOAD_ID, -1L);
        if (downloadId != -1L) {
            FileDownloadManager fdm = FileDownloadManager.getInstance(context);
            int status = fdm.getDownloadStatus(downloadId);
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                //启动更新界面
                Uri uri = fdm.getDownloadUri(downloadId);
                if (uri != null) {
                    if (compare(getApkInfo(context, uri.getPath()), context)) {
//                        startInstall(context, uri);
                        installApk(context,downloadId);
                        return;
                    } else {
                        fdm.getDm().remove(downloadId);
                    }
                }
                start(context, url, title);
            } else if (status == DownloadManager.STATUS_FAILED) {
                start(context, url, title);
            } else {
                Log.d(TAG, "apk is already downloading");
            }
        } else {
            start(context, url, title);
        }
    }

    private static void start(Context context, String url, String title) {
        long id = FileDownloadManager.getInstance(context).startDownload(url,
                title, "下载完成后点击打开");
        SpUtils.getInstance(context).putLong(KEY_DOWNLOAD_ID, id);
        Log.d(TAG, "apk start download " + id);
    }

    public static void startInstall(Context context, Uri uri) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }


    /**
     * 获取apk程序信息[packageName,versionName...]
     *
     * @param context Context
     * @param path    apk path
     */
    private static PackageInfo getApkInfo(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            //String packageName = info.packageName;
            //String version = info.versionName;
            //Log.d(TAG, "packageName:" + packageName + ";version:" + version);
            //String appName = pm.getApplicationLabel(appInfo).toString();
            //Drawable icon = pm.getApplicationIcon(appInfo);//得到图标信息
            return info;
        }
        return null;
    }


    /**
     * 下载的apk和当前程序版本比较
     *
     * @param apkInfo apk file's packageInfo
     * @param context Context
     * @return 如果当前应用版本小于apk的版本则返回true
     */
    private static boolean compare(PackageInfo apkInfo, Context context) {
        if (apkInfo == null) {
            return false;
        }
        String localPackage = context.getPackageName();
        if (apkInfo.packageName.equals(localPackage)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(localPackage, 0);
                if (apkInfo.versionCode > packageInfo.versionCode) {
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static class ApkInstallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                installApk(context, SpUtils.getInstance(context).getLong(KEY_DOWNLOAD_ID,-1L));

            }
        }
    }
    private static void installApk(Context context, long downloadApkId) {
        DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadFileUri = dManager.getUriForDownloadedFile(downloadApkId);
//        Intent install = new Intent(Intent.ACTION_VIEW);
//        if (downloadFileUri != null) {
//            LogUtil.d("DownloadManager", downloadFileUri.toString());
//            install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
//            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(install);
//        } else {
//            LogUtil.e("DownloadManager", "download error");
//        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String fileStr = Util.getRealPathFromURI(context,downloadFileUri);
            File file = new File(fileStr);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(AiMiCrowdFundingApplication.context(), "com.nongjinsuo.mimijinfu.fileprovider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (AiMiCrowdFundingApplication.context().getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            AiMiCrowdFundingApplication.context().startActivity(intent);
        }
    }

}


