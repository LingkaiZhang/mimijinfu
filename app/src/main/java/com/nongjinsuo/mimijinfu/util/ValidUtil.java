package com.nongjinsuo.mimijinfu.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
* @title: ValidUtil.java 
* @package com.sinoglobal.app.util 
* @description: 校验工具类
* @author zrh
* @date 2014-11-21 上午10:21:52
 */
public class ValidUtil {

	/**
	 * 
	* @author zrh
	* @methods validPhone 
	* @description 校验手机号
	* @date 2014-11-21 上午9:57:15
	* @param phone
	* @return 提示信息 如果为空串表示校验通过
	 */
	public static String validPhone(String phone) {
		String message = "";
		if (TextUtil.IsEmpty(phone)) {
			message = "请输入手机号码！";
		} else if (phone.length() < 11) {
			message = "请输入11位手机号码！";
		} else {
			String phoneRule = "^((14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9])|(16[0-9]))\\d{8}$";
			Pattern p = Pattern.compile(phoneRule);
			Matcher match = p.matcher(phone);
			if (!match.matches()) {
				message = "请输入正确的手机号码！";
			}
		}
		return message;
	}

	/**
	 * 
	* @author zrh
	* @methods validEmail 
	* @description 校验邮箱
	* @date 2014-11-21 上午10:07:12
	* @param email
	* @return 提示信息 如果为空串表示校验通
	 */
	public static String validEmail(String email) {
		String message = "";
		String emailRule = "^\\w+@\\w+\\.\\w+$";
		Matcher match = Pattern.compile(emailRule).matcher(email);
		if (!match.matches()) {
			message = "请输入正确的邮箱！";
		}
		return message;
	}
	/**
	 * 
	* @author zrh
	* @methods validPassword 
	* @description 校验密码  最好根据实际业务需求更改此方法的校验规则
	* @date 2014-11-21 上午10:04:56
	* @param pwd 8-18位密码
	* @return  提示信息 如果为空串表示校验通（6-20位数字和字母组合）
	 */
	public static String validPassword(String pwd) {
		String message = "";
		if (pwd.contains(" ")) {
			message = "密码中不能含有空格！";
			return message;
		}
		String pwdRule = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
		Matcher match = Pattern.compile(pwdRule).matcher(pwd);
		if (!match.matches()){
			message ="6-20位数字和字母组合";
		}
		return message;
	}
	/**
	 * @author czz
	 * @createdate 2012-9-23 下午3:33:38
	 * @Description: 校验身份证号码
	 * @param userIdCode
	 * @return
	 */
	public static String validUserIdCode(String userIdCode) {
		String message = "";
		if (userIdCode.contains(" ")) {
			message = "证件号码中不能含有空格！";
			return message;
		}
		if (userIdCode.contains(" ")) {
			message = "证件号码中不能含有空格！";
			return message;
		}
		String rule;
		if (TextUtil.IsEmpty(userIdCode)) {
			message = "请填写证件号码！";
			return message;
		}
		String subId = "";
		if (userIdCode.length() != 18) {
			message = "请输入正确的身份证号码！";
			return message;
		} else {
			rule = "^\\d{17}(\\d|x|X)$";
			Matcher match = Pattern.compile(rule).matcher(userIdCode);
			if (!match.matches()) {
				message = "请输入正确的身份证号码！";
				return message;
			} else {
				subId = userIdCode.substring(0, 17);
				message = validDistricAndDate(message, subId);
				if (!TextUtil.IsEmpty(message)) {
					return message;
				} else {
					// 校验码验证
					String[] valCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" }; // 验证码数组
					String[] wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" }; // 加权因子数组

					int TotalmulAiWi = 0;
					for (int i = 0; i < 17; i++) {
						TotalmulAiWi += Integer.parseInt(String.valueOf(subId.charAt(i))) * Integer.parseInt(wi[i]);
					}
					String strVerifyCode = valCodeArr[TotalmulAiWi % 11]; // 得到验证码
					subId += strVerifyCode;
					if (!subId.equals(userIdCode.toLowerCase())) {
						message = "请输入正确的身份证号码！";
						return message;
					}
				}
			}
		}
		// String subNumber = userIdCode.substring(6, 10);
		// int parseInt = Integer.parseInt(subNumber);
		// if (parseInt > 2001) {
		// message = "儿童请选择儿童";
		// }

		return message;
	}
	/**
	 * @author ty
	 * @createdate 2012-11-14 下午5:19:12
	 * @Description: 身份证号码中的地区和生日验证
	 * @param message
	 * @param subId
	 * @return
	 */
	private static String validDistricAndDate(String message, String subId) {
		if (subId.contains(" ")) {
			message = "证件号码中不能含有空格！";
			return message;
		}
		// 地区验证
		Map<String, String> map = GetAreaCode();
		if (map.get(subId.substring(0, 2)) == null) {
			message = "请输入正确的身份证号码！";
			return message;
		}
		// 日期验证
		String srtDate = subId.substring(6, 10) + "-" + subId.substring(10, 12) + "-" + subId.substring(12, 14);
		long time = TimeUtil.parseStringToLong(TimeUtil.sdf1, srtDate);
		Date date = new Date(time);
		String srtDate2 = TimeUtil.parseDateToString(TimeUtil.sdf1, date);
		if (!srtDate.equals(srtDate2)) {
			message = "请输入正确的身份证号码！";
			return message;

		}
		return message;
	}

	/**
	 * @author ty
	 * @createdate 2012-11-15 上午10:06:29
	 * @Description:设置身份证号中前两位的地区编码
	 * @return
	 */
	private static Map<String, String> GetAreaCode() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("11", "北京");
		map.put("12", "天津");
		map.put("13", "河北");
		map.put("14", "山西");
		map.put("15", "内蒙古");
		map.put("21", "辽宁");
		map.put("22", "吉林");
		map.put("23", "黑龙江");
		map.put("31", "上海");
		map.put("32", "江苏");
		map.put("33", "浙江");
		map.put("34", "安徽");
		map.put("35", "福建");
		map.put("36", "江西");
		map.put("37", "山东");
		map.put("41", "河南");
		map.put("42", "湖北");
		map.put("43", "湖南");
		map.put("44", "广东");
		map.put("45", "广西");
		map.put("46", "海南");
		map.put("50", "重庆");
		map.put("51", "四川");
		map.put("52", "贵州");
		map.put("53", "云南");
		map.put("54", "西藏");
		map.put("61", "陕西");
		map.put("62", "甘肃");
		map.put("63", "青海");
		map.put("64", "宁夏");
		map.put("65", "新疆");
		map.put("71", "台湾");
		map.put("81", "香港");
		map.put("82", "澳门");
		map.put("91", "国外");
		return map;
	}
}
