package com.nongjinsuo.mimijinfu.otherclass;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import cn.jpush.android.api.JPushInterface;

/**
 * description: (开机自启动服务)
 * autour: czz
 * date: 2017/4/24 0024 上午 11:17
 * update: 2017/4/24 0024
 */

public class StartService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this); // 初始化 JPush
    }

}