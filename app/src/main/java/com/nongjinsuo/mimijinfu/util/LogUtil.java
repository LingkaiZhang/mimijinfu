package com.nongjinsuo.mimijinfu.util;

import android.util.Log;

public class LogUtil {

	private static final String TAG="--LogUtil--";
	public static final boolean DEBUG=true;  //测试环境为true,生产环境为false
	private static int LOG_MAXLENGTH = 4000;
	public static void i(String message){
		if(DEBUG){
			if (TextUtil.IsNotEmpty(message)){
				Log.i(TAG, message);
			}
		}
	}
	public static void i(String tag,String message){
		if(DEBUG){
			if (TextUtil.IsNotEmpty(message)){
				Log.i(tag, message);
			}
		}
	}
	
	public static void e(String message){
		if(DEBUG){
			if (TextUtil.IsNotEmpty(message)) {
				Log.e(TAG, message);
			}
		}
	}
	public static void e(String tag,String message){
		if(DEBUG){
			int strLength = message.length();
			int start = 0;
			int end = LOG_MAXLENGTH;
			for (int i = 0; i < 100; i++) {
				if (strLength > end) {
					Log.e(tag + i, message.substring(start, end));
					start = end;
					end = end + LOG_MAXLENGTH;
				} else {
					Log.e(tag + i, message.substring(start, strLength));
					break;
				}
			}
		}
	}
	
	public static void d(String message){
		if(DEBUG){
			if (TextUtil.IsNotEmpty(message)){
				Log.d(TAG, message);
			}
		}
	}
	public static void d(String tag,String message){
		if(DEBUG){
			if (TextUtil.IsNotEmpty(message)){
				Log.d(tag, message);
			}
		}
	}
	
	public static void w(String message){
		if(DEBUG){
			if (TextUtil.IsNotEmpty(message)){
				Log.w(TAG, message);
			}
		}
	}
	public static void w(String tag,String message){
		if(DEBUG){
			if (TextUtil.IsNotEmpty(message)){
				Log.w(tag, message);
			}
		}
	}
	
	public static void w(String message,Throwable tr){
		if(DEBUG){
			if (TextUtil.IsNotEmpty(message)){
				Log.w(TAG, message,tr);
			}
		}
	}
	public static void w(String tag,String message,Throwable tr){
		if(DEBUG){
			if (TextUtil.IsNotEmpty(message)){
				Log.w(tag, message,tr);
			}
		}
	}
}
