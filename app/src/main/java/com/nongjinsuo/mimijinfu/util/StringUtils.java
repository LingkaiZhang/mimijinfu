package com.nongjinsuo.mimijinfu.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description: (描述)
 * autour: czz
 * date: 2017/6/21 0021 下午 3:22
 * update: 2017/6/21 0021
 */

public class StringUtils {
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
