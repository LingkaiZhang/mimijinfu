package com.nongjinsuo.mimijinfu.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.LoginActivity;
import com.nongjinsuo.mimijinfu.beans.LoginPromptVo;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import de.greenrobot.event.EventBus;

/**
 * @author czz
 * @createdate 2016-1-23 下午4:17:50
 * @Description: TODO(用一句话描述该类做什么)
 */
public class Util {
    private static String fileUri = android.os.Environment.getExternalStorageDirectory() + "/yourong.png";
    private static String fileName = "yourong.png";
    private static AlertDialog alertDialog;

    /**
     * @return
     * @author czz
     * @createdate 2015-10-28 下午2:13:29
     * @Description: (获取状态栏高度)
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void setStatusBarHeight(Context context, ImageView ivStatusBar) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(context));
        ivStatusBar.setLayoutParams(layoutParams);
    }

    public static void setFundNhColor(Context context, TextView tv, String arr, TextView tvBfh) {
        if (tv != null && TextUtil.IsNotEmpty(arr)) {
            String substring = arr.substring(0, 1);
            if (substring.equals("-")) {
                tvBfh.setTextColor(context.getResources().getColor(R.color.color_hot_sale_green));
                tv.setTextColor(context.getResources().getColor(R.color.color_hot_sale_green));
            } else {
                tvBfh.setTextColor(context.getResources().getColor(R.color.color_hot_sale_red));
                tv.setTextColor(context.getResources().getColor(R.color.color_hot_sale_red));
            }
        }
    }
    public static Bitmap getBitmap(File filePath) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = 1;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath(), options); //将图片的长和宽缩小味原来的1/2
        return bitmap;
    }
    public static String getDouble2Double(double f) {
        BigDecimal b = new BigDecimal(f);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1 + "";
    }

    public static String getImei(Context context) {
        String imei = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
//			Log.e(ExampleUtil.class.getSimpleName(), e.getMessage());
        }
        return imei;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
//			Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();
        return baos.toString();
    }

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }


    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    public static String saveIcon(Context context) {
        File file = new File(fileUri);
        if (file.exists()) {
            return fileUri;
        }
        //复制分享图标到sd卡:目前分享app的icon
        try {
            InputStream inputStream = context.getAssets().open("ic_launcher.png");
            //判断SDcard是否存在并且可读写
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                FileOutputStream fos = new FileOutputStream(fileUri);
                saveFile(inputStream, fos);
                LogUtil.d("shareSaveFile", "sd卡存在:" + fileUri);
            } else {
                FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
                saveFile(inputStream, fos);
                InputStream inputStream1 = context.openFileInput(fileName);
                fileUri = new String(InputStreamToByte(inputStream1));
                LogUtil.d("shareSaveFile", "sd卡不存在:" + fileUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileUri;
    }

    private static byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }

    private static void saveFile(InputStream inputStream, FileOutputStream fos) throws IOException {
        byte[] buffer = new byte[8192];
        int count = 0;
        while ((count = inputStream.read(buffer)) >= 0) {
            fos.write(buffer, 0, count);
        }
        fos.close();
        inputStream.close();
        fos.close(); // 关闭输出流
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /**
     * 验证其他设备登录
     */

    public static void loginPrompt(final Context context, int loginState,String loginDescr) {
            if (loginState == 1) {
                if (alertDialog != null && alertDialog.isShowing()) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(loginDescr);
                builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_LOGOUT_VIEW));
                        Util.clearUserInfo(context);
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                });
                builder.setCancelable(false);
                if (!((Activity) context).isFinishing()) {
                    alertDialog = builder.create();
                    alertDialog.show();
                }
        }
    }

    /**
     * 清空登录信息
     * @param context
     */
    public static void clearUserInfo(Context context) {
        Constants.userid = "";
        Constants.token = "";
        Constants.clientid = "";
        SharedPreferenceUtil.removeString(context, SharedPreferenceUtil.USERID);
        SharedPreferenceUtil.removeString(context,SharedPreferenceUtil.CLIENTID);
        SharedPreferenceUtil.removeString(context, SharedPreferenceUtil.TOKEN);
        SharedPreferenceUtil.removeString(context, SharedPreferenceUtil.GESTURE_PWD_TAG);
    }

    /**
     * 获取登录信息
     * @param context
     */
    public static void getUserInfo(Context context){
        Constants.clientid = SharedPreferenceUtil.getString(context,SharedPreferenceUtil.CLIENTID);
        Constants.userid = SharedPreferenceUtil.getString(context,SharedPreferenceUtil.USERID);
        Constants.token = SharedPreferenceUtil.getString(context,SharedPreferenceUtil.TOKEN);
    }
    public static void savaUserInfo(Context context,String userId,String token){
        Constants.userid = userId;
        Constants.token = token;
        SharedPreferenceUtil.saveString(context, SharedPreferenceUtil.USERID, userId);
        SharedPreferenceUtil.saveString(context, SharedPreferenceUtil.TOKEN, token);
    }

    //计算回款金额
    //backMoneyClass  回款方式:1先息后本2等额本息3到期还本付息
    //rate 用户年化收益
    //investMoney用户投资金额
    //month 月数
    public static double calBackMoney(int backMoneyClass, double rate, double investMoney, int month) {
        double newRate = rate * 0.01;
        double totalInterest = 0;

        //回款方式：1先息后本2等额本息3到期还本付息
        if (backMoneyClass == 1 || backMoneyClass == 3) {
            totalInterest = (investMoney * newRate * month / 12);
            totalInterest = get2Double(totalInterest);

        } else if (backMoneyClass == 2) {

            double backedMoney = investMoney;
            double monthMoney = investMoney * newRate / 12 * Math.pow(1 + newRate / 12, month) / (Math.pow(1 + newRate / 12, month) - 1); //每月回款金额
            monthMoney = get2Double(monthMoney);
            double monthBj;
            double totalBj = 0; //总本金
            for (int i = 1; i <= month; i++) {
                //计算每月所还利息
                double monthLx = get2Double(backedMoney * newRate / 12);
                if (i == month) {
                    //最后一个月
                    monthBj = investMoney - totalBj;
                } else {
                    monthBj = get2Double(monthMoney - monthLx);  //每月回款本金
                    totalBj = totalBj + monthBj;
                }
                backedMoney = get2Double(backedMoney - monthBj);
                totalInterest = get2Double(totalInterest + monthLx);


            }

        }
        return totalInterest;

    }
    public static double get2Double(double totalInterest) {
        double totalInterest2;
        BigDecimal b = new BigDecimal(totalInterest);
        totalInterest2 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return totalInterest2;
    }

    /**
     * 获取异常信息
     * @param volleyError
     * @return
     */
    public static  String getErrorMesg(VolleyError volleyError) {
        String errorMesg = "";
        if (volleyError instanceof NetworkError) {
            errorMesg = "网络连接失败,";
        } else if (volleyError instanceof ServerError) {
            errorMesg = "服务器的响应错误,";
        } else if (volleyError instanceof ParseError) {
            errorMesg = "服务器返回数据有误,";
        } else if (volleyError instanceof NoConnectionError) {
            errorMesg = "网络连接失败,";
        } else if (volleyError instanceof TimeoutError) {
            errorMesg = "服务器太忙或网络延迟,";
        }
        return errorMesg;
    }

}




