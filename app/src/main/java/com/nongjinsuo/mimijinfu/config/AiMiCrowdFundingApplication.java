package com.nongjinsuo.mimijinfu.config;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.nongjinsuo.mimijinfu.util.Util;

import cn.jpush.android.api.JPushInterface;

public class AiMiCrowdFundingApplication extends Application {
    private static AiMiCrowdFundingApplication instance;
    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;
    /**
     * Log or request TAG
     */
    public static final String TAG = "VolleyPatterns";
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Util.getUserInfo(this);
//        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
//        crashHandler.init(getApplicationContext());
    }

    public static synchronized AiMiCrowdFundingApplication context() {
        return instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        System.exit(0);
    }


    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
