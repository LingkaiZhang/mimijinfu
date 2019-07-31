package com.nongjinsuo.mimijinfu.dao.http;

import com.nongjinsuo.mimijinfu.config.Constants;

/**
 * @author czz
 * @createdate 2016-2-25 下午4:33:12
 * @Description: TODO(请求URL)
 */
public class Urls {

    public static final String PROJECT_URL = "http://newcar.caiyanlicai.com"; //测试地址
//  public static final String PROJECT_URL = "https://www.mimijinfu.com";//正式地址
    public static final String SUB_URL = "/Api/Index/index";
    public static String url = PROJECT_URL + SUB_URL;
    public static final String BAO_ZHANG = PROJECT_URL+"/Wap/html/safe/";//保障
    public static final String BZZX = PROJECT_URL+"/Wap/html/help/";//帮助中心
    public static final String HTXQ = PROJECT_URL+"/Wap/html/agreement/tag/";//活投宝合同详情
    public static final String HTXQ_DTB = PROJECT_URL+"/Wap/html/agreementNetloan/tag/";//定投宝合同详情
    public static final String FWXY = PROJECT_URL+"/Wap/html/privacy";//服务协议
    public static final String TXSM = PROJECT_URL+"/Wap/html/cashout";//提现说明
    public static final String XSZY = PROJECT_URL+"/Wap/html/newdirect";//新手指引
    public static final String ZXKF = PROJECT_URL+"/Pc/Service/chat/from/wap";//在线客服
    public static final String YQJL = PROJECT_URL+"/Wap/html/invite/userId/"+ Constants.userid;//邀请奖励
    public static final String PROJECT_DETAILS = PROJECT_URL+"/H5/index.php?tag=0";//项目详情
    public static final String ZI_QI_DONG = PROJECT_URL+"/Wap/html/push/company/";//自启动引导页面
    public static final String WHATCAR = PROJECT_URL+"/Wap/page/whatcar";//什么是活投宝
    public static final String AGREEMENT = PROJECT_URL+"/About/agreement";//定投宝借款服务合同
    public static final String AGREEMENT2 = PROJECT_URL+"/About/agreement2";//活投宝借款服务合同
    public static final String REDPACKET = PROJECT_URL+"/Wap/html/redpacket";//红包说明

}