package com.nongjinsuo.mimijinfu.dao.http;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.util.Md5;
import com.nongjinsuo.mimijinfu.util.MobileInfoUtils;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.TimeUtil;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author czz
 * @createdate 2016-2-25 下午4:33:12
 * @Description: TODO(获取请求参数集合)
 */
public class RequestMap {

    public static final String INTERFACE = "interface";
    public static final String CLIENTID = "clientId";
    public static final String CLIENTFROM = "clientFrom";
    public static final String CALLDATE = "callDate";
    public static final String USERID = "userId";
    public static final String MDSTR = "mdStr";
    public static final String TOKEN = "token";
    public static final String SUBSTR = "&%$#@*!";

    /*版本信息*/
    public static Map<String, String> getStartMap() {
        Map<String, String> map = getStringStringMap();
        setPublicParams(map, "start");
        return map;
    }

    @NonNull
    private static Map<String, String> getStringStringMap() {
        return new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj1.compareTo(obj2);
                    }
                });
    }

    /*同步clientId和JPushRegId*/
    public static Map<String, String> getSynchoroniza(Context context, String jPushRegId) {
        Map<String, String> map = getStringStringMap();
        map.put("JPushRegId", jPushRegId);
        if (TextUtil.IsNotEmpty(Constants.longitude)) {
            map.put("POI", Constants.longitude + "," + Constants.latitude);
        } else {
            map.put("POI", "");
        }
        if (TextUtil.IsNotEmpty(Constants.city)) {
            map.put("city", Constants.city);
            map.put("province", Constants.province);
        } else {
            map.put("city", Constants.DEFAULT_CITY);
        }
        map.put("deviceName", MobileInfoUtils.getDeviceBrand() + " " + MobileInfoUtils.getSystemModel());
        map.put("osVersion", MobileInfoUtils.getSystemVersion());
        map.put("carrier", MobileInfoUtils.getCarrier(context));
        setPublicParams(map, "upedit");
        return map;
    }

    /*首页数据*/
    public static Map<String, String> getHomefilefundMap() {
        Map<String, String> map = getStringStringMap();
        setPublicParams(map, "homeFileFund");
        return map;
    }

    /*项目详情*/
    public static Map<String, String> getProjectdetailsMap(String projectBm) {
        Map<String, String> map = getStringStringMap();
        map.put("projectBm", projectBm);
        setPublicParams(map, "projectDetails");
        return map;
    }
    /*项目详情*/
    public static Map<String, String> projectDetailV2(String projectBm) {
        Map<String, String> map = getStringStringMap();
        map.put("projectBm", projectBm);
        setPublicParams(map, "projectDetailV2");
        return map;
    }

    /*项目状态*/
    public static Map<String, String> getProjectstatusMap(String projectBm) {
        Map<String, String> map = getStringStringMap();
        map.put("projectBm", projectBm);
        setPublicParams(map, "projectStatus");
        return map;
    }

    /*改版弃用接口（2017-6-8）: 项目进展*/
    public static Map<String, String> getScheduleMap(String projectBm) {
        Map<String, String> map = getStringStringMap();
        map.put("projectBm", projectBm);
        setPublicParams(map, "schedule");
        return map;
    }

    /*项目进展*/
    public static Map<String, String> getProjectScheduleMap(String projectBm) {
        Map<String, String> map = getStringStringMap();
        map.put("projectBm", projectBm);
        setPublicParams(map, "projectSchedule");
        return map;
    }

    /*项目列表*/
    public static Map<String, String> getProjectlistMap(int page, int status, int classId, String order) {
        Map<String, String> map = getStringStringMap();
        map.put("status", status + "");
        map.put("order", order);
        map.put("classId", classId + "");
        map.put("page", page + "");
        setPublicParams(map, "projectList");
        return map;
    }


    /*登录*/
    public static Map<String, String> getLogin(String mobile, String password) {
        Map<String, String> map = getStringStringMap();
        map.put("mobile", mobile);
        map.put("password", password);
        setPublicParams(map, "login");
        return map;
    }

    /*获取验证码*/
    public static Map<String, String> getCodeMap(String mobile, int sta, int isUser) {
        Map<String, String> map = getStringStringMap();
        map.put("mobile", mobile);
        map.put("sta", sta + "");
        map.put("isUser", isUser + "");
        setPublicParams(map, "sendCode");
        return map;
    }

    /*注册*/
    public static Map<String, String> getRegister(String mobile, String code, String password) {
        Map<String, String> map = getStringStringMap();
        map.put("mobile", mobile);
        map.put("password", password);
        map.put("code", code);
        if (TextUtil.IsNotEmpty(Constants.city)) {
            map.put("city", Constants.city);
        } else {
            map.put("city", Constants.DEFAULT_CITY);
        }
        setPublicParams(map, "register");
        return map;
    }

    /*个人中心*/
    public static Map<String, String> getCentralhome() {
        Map<String, String> map = getStringStringMap();
        setPublicParams(map, "centralHome");
        return map;
    }

    /*判断手机号验证码是否正确*/
    public static Map<String, String> getMobilecode(String mobile, String code) {
        Map<String, String> map = getStringStringMap();
        map.put("mobile", mobile);
        map.put("code", code);
        setPublicParams(map, "mobileCode");
        return map;
    }

    /*重置密码*/
    public static Map<String, String> getEditpassword(String mobile, String code, String newpassword) {
        Map<String, String> map = getStringStringMap();
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("newpassword", newpassword);
        setPublicParams(map, "editPassword");
        return map;
    }

    /*登出接口*/
    public static Map<String, String> getlogout() {
        Map<String, String> map = getStringStringMap();
        setPublicParams(map, "logout");
        return map;
    }

    /*检测银行卡所属银行*/
    public static Map<String, String> getBank(String cardNo) {
        Map<String, String> map = getStringStringMap();
        map.put("cardNo", cardNo);
        setPublicParams(map, "bank");
        return map;
    }

    /*支持的所有银行*/
    public static Map<String, String> getSupportbank() {
        Map<String, String> map = getStringStringMap();
        setPublicParams(map, "supportBank");
        return map;
    }

    /*是否实名认证和绑卡和支付密码*/
    public static Map<String, String> getUserverification(int isReturnUrl) {
        Map<String, String> map = getStringStringMap();
        map.put("isReturnUrl", isReturnUrl+"");
        setPublicParams(map, "userVerification");
        return map;
    }

    /*实名认证*/
    public static Map<String, String> getCertification(String identityName, String identityId) {
        Map<String, String> map = getStringStringMap();
        map.put("identityName", identityName);
        map.put("identityId", identityId);
        setPublicParams(map, "certification");
        return map;
    }

    /*验证身份*/
    public static Map<String, String> getIsverifyidentity(String identityName, String identityNo) {
        Map<String, String> map = getStringStringMap();
        map.put("identityName", identityName);
        map.put("identityNo", identityNo);
        setPublicParams(map, "isVerifyIdentity");
        return map;
    }

    /*验证绑卡人身份*/
    public static Map<String, String> getIsbankidentity(String bankName, String province, String city, String cardNo, String mobile) {
        Map<String, String> map = getStringStringMap();
        map.put("bankName", bankName);
        map.put("province", province);
        map.put("city", city);
        map.put("cardNo", cardNo);
        map.put("mobile", mobile);
        setPublicParams(map, "isBankIdentity");
        return map;
    }

    /*绑定银行卡*/
    public static Map<String, String> getBindbank(String bankName, String province, String city, String cardNo, String mobile, String ticket, String valid_code) {
        Map<String, String> map = getStringStringMap();
        map.put("bankName", bankName);
        map.put("province", province);
        map.put("city", city);
        map.put("cardNo", cardNo);
        map.put("mobile", mobile);
        map.put("ticket", ticket);
        map.put("valid_code", valid_code);
        setPublicParams(map, "bindBank");
        return map;
    }

    /*检测是否设置交易密码*/
    public static Map<String, String> ispaypwd() {
        Map<String, String> map = getStringStringMap();
        setPublicParams(map, "isPayPwd");
        return map;
    }

    /*修改手机号*/
    public static Map<String, String> editmobile(String mobile, String code) {
        Map<String, String> map = getStringStringMap();
        map.put("mobile", mobile);
        map.put("code", code);
        setPublicParams(map, "editMobile");
        return map;
    }


    /*用户修改密码*/
    public static Map<String, String> getRevisepassword(String mobile, String code, String newpassword) {
        Map<String, String> map = getStringStringMap();
        map.put("newpassword", newpassword);
        map.put("mobile", mobile);
        map.put("code", code);
        setPublicParams(map, "revisePassword");
        return map;
    }

/*    *//*重置交易密码*//*
    public static Map<String, String> geteditpayPwdMap(String mobile, String code, String idNumber, String newpayPwd) {
        Map<String, String> map = getStringStringMap();
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("idNumber", idNumber);
        map.put("newpayPwd", newpayPwd);
        setPublicParams(map,  "editpayPwd");
        return map;
    }*/

    /*充值*/
    public static Map<String, String> getRecharge(String amount, String cardId) {
        Map<String, String> map = getStringStringMap();
        map.put("amount", amount);
        map.put("cardId", cardId);
        setPublicParams(map, "recharge");
        return map;
    }

    /*提现*/
    public static Map<String, String> getWithdraw(String amount, String cardId, String ispiggybank) {
        Map<String, String> map = getStringStringMap();
        map.put("amount", amount);
        map.put("cardId", cardId);
        map.put("ispiggybank", ispiggybank);
        setPublicParams(map, "withdraw");
        return map;
    }

    /*购买*/
    public static Map<String, String> getPurchase(String interfaceStr,String amount, String cardId, String projectBm, String orderFrom, String redPacketId) {
        Map<String, String> map = getStringStringMap();
        map.put("amount", amount);
        map.put("cardId", cardId);
        map.put("projectBm", projectBm);
        map.put("orderFrom", orderFrom);
        map.put("redPacketId", redPacketId);
        setPublicParams(map, interfaceStr);
        return map;
    }

    /*用户全部投资*/
    public static Map<String, String> usercrowdfunding(int page, int backStatus) {
        Map<String, String> map = getStringStringMap();
        map.put("backStatus", backStatus + "");
        map.put("page", page + "");
        setPublicParams(map, "userCrowdFunding");
        return map;
    }

    /*交易记录*/
    public static Map<String, String> traderecode(int page, String classId) {
        Map<String, String> map = getStringStringMap();
        map.put("classId", classId);
        map.put("page", page + "");
        setPublicParams(map, "traderecode");
        return map;
    }

    /*购买时请求计算项目可投金额和用户余额*/
    public static Map<String, String> availableamount(String projectBm) {
        Map<String, String> map = getStringStringMap();
        map.put("projectBm", projectBm);
        setPublicParams(map, "availableAmount");
        return map;
    }

    /*投票信息*/
    public static Map<String, String> projectvote(String projectBm) {
        Map<String, String> map = getStringStringMap();
        map.put("projectBm", projectBm);
        setPublicParams(map, "projectvote");
        return map;
    }

    /*投票*/
    public static Map<String, String> vote(String projectBm, String vote) {
        Map<String, String> map = getStringStringMap();
        map.put("projectBm", projectBm);
        map.put("vote", vote);
        setPublicParams(map, "vote");
        return map;
    }

    /*募集记录*/
    public static Map<String, String> projectinvestrecode(String projectBm) {
        Map<String, String> map = getStringStringMap();
        map.put("projectBm", projectBm);
        setPublicParams(map, "projectInvesTrecode");
        return map;
    }

    /*解绑银行卡*/
    public static Map<String, String> unbindbank(String cardId, String ticket, String valid_code) {
        Map<String, String> map = getStringStringMap();
        map.put("cardId", cardId);
        map.put("ticket", ticket);
        map.put("valid_code", valid_code);
        setPublicParams(map, "unbindBank");
        return map;
    }

    /*解绑银行卡发送验证码*/
    public static Map<String, String> isunbindbank(String cardId) {
        Map<String, String> map = getStringStringMap();
        map.put("cardId", cardId);
        setPublicParams(map, "isUnbindBank");
        return map;
    }

    /*用户消息*/
    public static Map<String, String> getcentralMap(int page) {
        Map<String, String> map = getStringStringMap();
        map.put("page", page + "");
        setPublicParams(map, "message");
        return map;
    }

    /*消息详情*/
    public static Map<String, String> messagecontent(String messageId) {
        Map<String, String> map = getStringStringMap();
        map.put("messageId", messageId);
        setPublicParams(map, "messageContent");
        return map;
    }

    /*公告列表*/
    public static Map<String, String> notice(int page) {
        Map<String, String> map = getStringStringMap();
        map.put("page", page + "");
        setPublicParams(map, "notice");
        return map;
    }

    /*公告详情*/
    public static Map<String, String> noticecontent(String noticeId) {
        Map<String, String> map = getStringStringMap();
        map.put("noticeId", noticeId);
        setPublicParams(map, "noticeContent");
        return map;
    }

    /*意见反馈*/
    public static Map<String, String> suggest(String content, String contact) {
        Map<String, String> map = getStringStringMap();
        map.put("content", content);
        map.put("contact", contact);
        setPublicParams(map, "suggest");
        return map;
    }

    /*获取用户可用余额*/
    public static Map<String, String> userAsset() {
        Map<String, String> map = getStringStringMap();
        setPublicParams(map, "userAsset");
        return map;
    }

    /*查看用户是否有消息*/
    public static Map<String, String> getMessageNum() {
        Map<String, String> map = getStringStringMap();
        setPublicParams(map, "getMessageNum");
        return map;
    }

    /*查看消息*/
    public static Map<String, String> messagedetail(String messageId) {
        Map<String, String> map = getStringStringMap();
        map.put("messageId", messageId);
        setPublicParams(map, "messageDetail");
        return map;
    }

    /*更新用户是否委托代扣*/
    public static Map<String, String> isauthority() {
        Map<String, String> map = getStringStringMap();
        setPublicParams(map, "isAuthority");
        return map;
    }

    /*我的红包*/
    public static Map<String, String> redPacket(int status) {
        Map<String, String> map = getStringStringMap();
        map.put("status", status + "");
        setPublicParams(map, "redPacket");
        return map;
    }
    /*P2P项目列表*/
    public static Map<String, String> listProduct(int page,int backMoneyClass,int projectDay) {
        Map<String, String> map = getStringStringMap();
        map.put("page", page + "");
        map.put("backMoneyClass", backMoneyClass+"");
        map.put("projectDay", projectDay+"");
        setPublicParams(map, "listProduct");
        return map;
    }
    /*P2P项目详情*/
//    public static Map<String, String> productDetail(String id,String projectBm) {
//        Map<String, String> map = getStringStringMap();
//        map.put("id", id);
//        map.put("projectBm",projectBm);
//        setPublicParams(map, "productDetail");
//        return map;
//    }
    /*P2P项目详情*/
    public static Map<String, String> productDetailV2(String id) {
        Map<String, String> map = getStringStringMap();
        map.put("id", id);
        setPublicParams(map, "productDetailV2");
        return map;
    }
    /*根据projectBm获取募集项目*/
    public static Map<String, String> getOneCar(String projectBm) {
        Map<String, String> map = getStringStringMap();
        map.put("projectBm", projectBm);
        setPublicParams(map, "getOneCar");
        return map;
    }
    /*我的定投宝*/
    public static Map<String, String> userNetloan(int page,int backStatus) {
        Map<String, String> map = getStringStringMap();
        map.put("page", page+"");
        map.put("backStatus", backStatus+"");
        setPublicParams(map, "userNetloan");
        return map;
    }
    /*发现*/
    public static Map<String, String> find() {
        Map<String, String> map = getStringStringMap();
        setPublicParams(map, "find");
        return map;
    }
    /*回款计划*/
    public static Map<String, String> backMoney(String projectBm,int status) {
        Map<String, String> map = getStringStringMap();
        map.put("projectBm", projectBm);
        map.put("status", status+"");//0 总金额回款计划 1 当前用户回款计划
        setPublicParams(map, "backMoney");
        return map;
    }
    /*回款计划日历*/
    public static Map<String, String> userBackMoney(String backDate) {
        Map<String, String> map = getStringStringMap();
        map.put("backDate", backDate);
        setPublicParams(map, "userBackMoney");
        return map;
    }
    /*新闻、活动列表*/
    public static Map<String, String> findList(int page,String status) {
        Map<String, String> map = getStringStringMap();
        map.put("status", status);
        map.put("page", page+"");
        setPublicParams(map, "findList");
        return map;
    }
    /*我的全部待收*/
    public static Map<String, String> waitBackMoney() {
        Map<String, String> map = getStringStringMap();
        setPublicParams(map, "waitBackMoney");
        return map;
    }
    /*修改资产隐藏状态*/
    public static Map<String, String> isHideAsset(int assetHiddenStatus) {
        Map<String, String> map = getStringStringMap();
        map.put("assetHiddenStatus", assetHiddenStatus+"");
        setPublicParams(map, "isHideAsset");
        return map;
    }
    /*定投 活投  各十条 列表*/
    public static Map<String, String> listAll() {
        Map<String, String> map = getStringStringMap();
        setPublicParams(map, "listAll");
        return map;
    }

    /**
     * 设置公用的参数
     *
     * @param map
     * @param action
     */
    public static void setPublicParams(Map<String, String> map, String action) {
        if (map != null) {
            map.put(INTERFACE, action);
            map.put(USERID, Constants.userid);
            String timeStr = TimeUtil.getTimeStr();
            map.put(CALLDATE, timeStr);
            map.put(CLIENTFROM, Constants.ANDROID);
            map.put(CLIENTID, Constants.clientid);
            map.put(TOKEN,Constants.token);

            Set<String> keySet = map.keySet();
            Iterator<String> iter = keySet.iterator();
            StringBuffer sb = new StringBuffer();
            while (iter.hasNext()) {
                String key = iter.next();
                sb.append(key + "=" + map.get(key) + "&");
                System.out.println(key + ":" + map.get(key));
            }
            String newStr = sb.replace(sb.toString().length() - 1, sb.toString().length(), "").toString();
//            LogUtil.e(RequestMap.class.getSimpleName(), "排序拼接参数:" + newStr);
            map.put(MDSTR, Md5.encode(Md5.encode(newStr) + SUBSTR));
        }
    }
}
