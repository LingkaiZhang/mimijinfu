package com.nongjinsuo.mimijinfu.util;

import android.content.Context;
import android.content.res.Resources;

/**
 * 
* @title: ResUtiles.java 
* @package com.sinoglobal.app.util 
* @description: 获取资源工具类 
* @author zrh
* @date 2014-11-25 下午12:36:24
 */
public class ResUtiles {
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2014年6月13日 下午5:39:56
	 * @Description: 根据资源图片名加载资源图片。
	 * @param context 
	 * @param drawableName 对应资源图片名
	 * @return 图片资源id， 如果资源图片未找到会返回  0
	 *
	 */
	public static int getDrwableForName(Context context,String drawableName) {
		 Resources resource = context.getResources();
	     String pkgName = context.getPackageName();
	     int resId = resource.getIdentifier(drawableName, "drawable", pkgName);
		 return resId;
	}
	
	/**
	 * 
	* @author zrh
	* @methods getDrwableForName 
	* @description 根据资源图片名加载资源图片
	* @date 2014-11-20 下午1:04:02
	* @param context
	* @param drawableName 对应资源图片名
	* @param failResId 未找到资源图片时的默认加载图片资源
	* @return 图片资源id， 如果资源图片未找到会返回  failResId
	 */
	public static int getDrwableForName(Context context,String drawableName,int failResId) {
		Resources resource = context.getResources();
		String pkgName = context.getPackageName();
		int resId = resource.getIdentifier(drawableName, "drawable", pkgName);
		return resId==0?failResId:resId;
	}
	/**
	 * 
	* @author zrh
	* @methods getIdForName 
	* @description 根据 控件的id名称返回 控件的id
	* @date 2014-11-25 下午12:37:08
	* @param context
	* @param idResName 控件的id名称
	* @return 控件的id 如果返回0 表示未找到对应id名称的id
	 */
	public static int getIdForName(Context context,String idResName) {
		Resources resource = context.getResources();
		String pkgName = context.getPackageName();
		int resId = resource.getIdentifier(idResName, "id", pkgName);
		return resId;
	}
}