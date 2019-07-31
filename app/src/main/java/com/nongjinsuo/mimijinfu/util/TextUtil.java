package com.nongjinsuo.mimijinfu.util;


/**
 * @author zrh
 * @title: TextUtil.java
 * @package com.sinoglobal.app.util
 * @description: 文本工具类
 * @date 2014-11-20 下午12:40:48
 */
public class TextUtil {

    public static boolean IsEmpty(String value) {
        return value == null || value.trim().length() <= 0;
    }

    public static boolean IsNotEmpty(String value) {
        if (value == null) {
            return false;
        } else if (value.trim().length() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public static String Long2Double(Double money) {
        if (money == 0) {
            return "0.00";
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        return df.format(money);
    }
}
