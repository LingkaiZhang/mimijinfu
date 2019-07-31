package com.nongjinsuo.mimijinfu.otherclass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nongjinsuo.mimijinfu.util.LogUtil;

/**
 * description: (开机自启动广播)
 * autour: czz
 * date: 2017/4/24 0024 上午 11:14
 * update: 2017/4/24 0024
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            LogUtil.d("yqb", "检测到开机启动，去启动服务");
            Intent newIntent = new Intent(context, StartService.class);
            context.startService(newIntent);
        }
    }
}