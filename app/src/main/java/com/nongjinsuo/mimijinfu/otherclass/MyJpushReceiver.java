package com.nongjinsuo.mimijinfu.otherclass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nongjinsuo.mimijinfu.activity.MainActivity;
import com.nongjinsuo.mimijinfu.activity.P2pDetilsActivity;
import com.nongjinsuo.mimijinfu.activity.ProjectDetilsActivity;
import com.nongjinsuo.mimijinfu.activity.WebViewActivity;
import com.nongjinsuo.mimijinfu.activity.WebViewAndJsActivity;
import com.nongjinsuo.mimijinfu.beans.BaseVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.SystemUtils;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

public class MyJpushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private Context context;
    private String idd;
    private static Map<String, String> map;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        printBundle(bundle);
        this.context = context;
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            LogUtil.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            idd = regId;
            synchronizClientIdAndJPushRegId(regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtil.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            //processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtil.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            LogUtil.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_HOMEPAGE_MESSAGE));

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtil.d(TAG, "[MyReceiver] 用户点击打开了通知");

            //判断app进程是否存活
            if(SystemUtils.isAppAlive(context, context.getPackageName())){
                //如果存活的话，就直接启动DetailActivity，但要考虑一种情况，就是app的进程虽然仍然在
                //但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
                //DetailActivity,再按Back键就不会返回MainActivity了。所以在启动
                //DetailActivity前，要先启动MainActivity。
                Log.i("NotificationReceiver", "the app process is alive");
                Intent mainIntent = new Intent(context, MainActivity.class);
                //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
                //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
                //如果Task栈不存在MainActivity实例，则在栈顶创建
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent detailIntent;
                if (map!=null){
                    if (map.get(Constants.CLASSID).equals(Constants.PROJECT)) {
                        detailIntent= new Intent(context, ProjectDetilsActivity.class);
                        detailIntent.putExtra(Constants.PROJECTBM, map.get(Constants.PROJECTBM));
                    } else if (map.get(Constants.CLASSID).equals(Constants.URL)) {
                        detailIntent = new Intent(context, WebViewActivity.class);
                        detailIntent.putExtra(WebViewActivity.URL, map.get(Constants.URL));
                    } else if (map.get(Constants.CLASSID).equals(Constants.INTER_URL)) {
                        detailIntent = new Intent(context, WebViewAndJsActivity.class);
                        detailIntent.putExtra(WebViewActivity.URL, map.get(Constants.URL));
                    }else if (map.get(Constants.CLASSID).equals(Constants.PRODUCT)) {
                        detailIntent= new Intent(context, P2pDetilsActivity.class);
                        detailIntent.putExtra(Constants.PROJECTBM, map.get(Constants.PRODUCTBM));
                        detailIntent.putExtra(Constants.ID, map.get(Constants.PRODUCTID));
                    }else{
                        context.startActivity(mainIntent);
                        return;
                    }
                    Intent[] intents = {mainIntent, detailIntent};
                    context.startActivities(intents);
                }

            }else {
                //如果app进程已经被杀死，先重新启动app，将DetailActivity的启动参数传入Intent中，参数经过
                //SplashActivity传入MainActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入             //参数跳转到DetailActivity中去了
                Log.i("NotificationReceiver", "the app process is dead");
                Intent launchIntent = context.getPackageManager().
                        getLaunchIntentForPackage(context.getPackageName());
                launchIntent.setFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                Bundle args = new Bundle();
                args.putString(Constants.CLASSID,map.get(Constants.CLASSID));
                args.putString(Constants.PROJECTBM,map.get(Constants.PROJECTBM));
                args.putString(Constants.URL,map.get(Constants.URL));
                launchIntent.putExtra(Constants.EXTRA_BUNDLE, args);
                context.startActivity(launchIntent);
            }
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
//            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtil.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            LogUtil.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据

    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        map = new HashMap<>();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                        map.put(myKey, json.optString(myKey));
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        Log.i(TAG, map.toString());
        return sb.toString();
    }

    //send msg to MainActivity
//	private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (null != extraJson && extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			context.sendBroadcast(msgIntent);
//		}
//	}


    /**
     * 同步clientId和JPushRegId
     */
    public void synchronizClientIdAndJPushRegId(String regId) {
        JacksonRequest<BaseVo> jacksonRequest = new JacksonRequest<>(RequestMap.getSynchoroniza(context,regId), BaseVo.class, new Response.Listener<BaseVo>() {
            @Override
            public void onResponse(BaseVo baseVo) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
       AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest,getClass().getName());
    }
}
