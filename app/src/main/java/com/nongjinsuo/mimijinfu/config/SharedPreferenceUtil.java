package com.nongjinsuo.mimijinfu.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.nongjinsuo.mimijinfu.util.TextUtil;


/**
 * @author ty
 * @createdate 2012-8-17 下午12:22:19
 * @Description: SharedPreference
 */
public class SharedPreferenceUtil {
	public static final String SHARED_PREFERENCE_NAME="aimiZhongchou";   //SharedPreference操作的文件
	public static final String CLIENTID = "clientId";//设备id
	public static final String USERID = "userId";
	public static final String TOKEN = "token";
	private static final String ACCESSTOKEN = "accessToken";
	public static final String VERSION_CODE = "versionCode";
	public static final String  ZIQIDONG = "ziqidong";
	public static final String  GUANG_GAO = "guanggao";
	public static final String  SEND_CODE = "sendCode";
	public static final String  HB_IMG = "hb_img";//红包广告图片
	/**
	 * 手势密码tag
	 */
	public final static String GESTURE_PWD_TAG = "gesture_pwd_tag";

	/**
	 * @author ty
	 * @createdate 2012-8-17 下午12:26:01
	 * @Description: 保存int数值
	 * @param context
	 * @param key  
	 * @param value
	 */
	public static void saveInt(Context context,String key,int value){
		Editor editor = context.getSharedPreferences(
				SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	/**
	 * @author ty
	 * @createdate 2012-8-17 下午12:28:46
	 * @Description: 获取保存的int数值
	 * @param context
	 * @param key
	 * @return
	 */
	public static int getInt(Context context,String key){
		SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		int value=shared.getInt(key, 0);
		return value;
	}
	
	/**
	 * @author ty
	 * @createdate 2012-8-17 下午12:26:01
	 * @Description: 保存long数值
	 * @param context
	 * @param key  
	 * @param value
	 */
	public static void saveLong(Context context,String key,long value){
		Editor editor = context.getSharedPreferences(
				SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	/**
	 * @author ty
	 * @createdate 2012-8-17 下午12:28:46
	 * @Description: 获取保存的long数值
	 * @param context
	 * @param key
	 * @return
	 */
	public static long getLong(Context context,String key){
		SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		long value=shared.getLong(key, 0L);
		return value;
	}
	
	/**
	 * @author miaoxin.ye
	 * @createdate 2012-10-13 上午11:50:33
	 * @Description: 保存boolean值
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveBoolean(Context context,String key,boolean value){
		Editor editor = context.getSharedPreferences(
				SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	/**
	 * @author miaoxin.ye
	 * @createdate 2012-10-13 上午11:51:40
	 * @Description: 获取boolean值
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Context context,String key){
		SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		boolean value=shared.getBoolean(key, false);
		return value;
	}
	
	/**
	 * @author ty
	 * @createdate 2012-8-17 下午12:26:01
	 * @Description: 保存String数值
	 * @param context
	 * @param key  
	 * @param value
	 */
	public static void saveString(Context context,String key,String value){
		Editor editor = context.getSharedPreferences(
				SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * @author ty
	 * @createdate 2012-8-17 下午12:28:46
	 * @Description: 获取保存的String数值
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(Context context,String key){
		SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		String value=shared.getString(key, "");
		return value;
	}
	
	/**
	 * 
	 * @author bo.wang
	 * @createdate 2012-10-29 下午2:21:59
	 * @Description 清空本地的缓存
	 * @param context
	 * @param key 
	 */
	public static void removeString(Context context,String key){
		SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.remove(key);
		editor.commit();
	}
	/**
	 * 
	 * @author czz
	 * @createdate 2016-1-29 上午10:37:42
	 * @Description: (获取用户id)
	 * @param context
	 * @return
	 *
	 */
	public static String getUserId(Context context){
		SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		String value=shared.getString(USERID, "");
		return value;
	}
	/**
	 * 
	 * @author czz
	 * @createdate 2016-1-29 上午10:39:00
	 * @Description: (获取AccessToken)
	 * @param context
	 * @return
	 *
	 */
	public static String getAccessToken(Context context){
		SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		String value=shared.getString(ACCESSTOKEN, "");
		return value;
	}
	/**
	 * 
	 * @author czz
	 * @createdate 2015-9-10 下午1:36:11
	 * @Description: (判断是否登陆)
	 * @param context
	 * @return true 已登录  false 未登录
	 *
	 */
	public static boolean isLogin(Context context){
		String id = getString(context, USERID);
		if (TextUtil.IsEmpty(id)) {
			return false;
		}
		else{
			Constants.userid = id;
			return true;
		}
	}
}
