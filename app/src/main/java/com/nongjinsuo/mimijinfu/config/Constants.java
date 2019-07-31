package com.nongjinsuo.mimijinfu.config;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.RedPacketBean;

import java.util.ArrayList;

/**
 * @author zrh
 * @title: Constants.java
 * @package com.sinoglobal.app.config
 * @description: 一些常量的配置
 * @date 2014-11-17 下午5:50:26
 */
public class Constants {

    public static final String SUCCESS = "200";
    public static final String ERROR = "400";

    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    public static String clientid = "";//设备id
    public static String userid = "";//用户id
    public static String token = "";//用户token

    public static String latitude = "";//纬度
    public static String longitude = "";//经度
    public static String city = "";
    public static String province = "";
    public static String DEFAULT_CITY = "北京";


    //	0 准备上线，1 募集中/上线，2募集结束/待售中 3投票中 4投票完成/回款中 5募集结束，6募集失败/下架67
    public static final int STATUS_0_ZBSX = 0;
    public static final int STATUS_1_ZCZ = 1;
    public static final int STATUS_2_DSZ = 2;
    public static final int STATUS_3_TPZ = 3;
    public static final int STATUS_4_TPWC = 4;
    public static final int STATUS_5_ZCJS = 5;
    public static final int STATUS_6_ZCSB = 6;
    public static final int STATUS_7_XSSB = 7;
    public static final String[] STATUS_NAMES = {"准备上线", "募集中", "待售中", "投票中", "回款中", "已完成", "募集失败", "销售失败"};
    public static final int[] STATUS_IMGS = {R.drawable.img_z_yg, R.drawable.img_z_zcz, R.drawable.img_z_dsz, R.drawable.img_z_tpz, R.drawable.img_z_hkz, R.drawable.img_z_wc, R.drawable.img_z_yxx};
    public static final String PROJECTBM = "projectBm";
    public static final String ID = "id";
    public static final String PRODUCTID = "productId";
    public static final String PRODUCTBM = "productBm";
    public static final String STATUS  = "status";//项目状态

    public static final int TYPE_SMRZ = 0;//实名认证
    public static final int TYPE_BYHK = 1;//绑银行卡
    public static final int TYPE_ZFMM = 2;//支付密码
    public static final int TYPE_SYKF = 3;//是否授权扣费
    public static final int TYPE_KHCG = 4;//开户成功
    public static final int TYPE_JBK = 5;//解绑卡
    public static final String ANDROID = "Android";
    public static final String EXTRA_BUNDLE = "launchBundle";
    public static final String CLASSID = "classId";
    public static final String URL = "url";
    public static final String INTER_URL = "inter_url";
    public static final String PROJECT = "project";
    public static final String PRODUCT = "product";
    public static final String OPEN_MESSAGE = "openMessage";

    public static final String[] ALLSTATUS = {"全部", "募集中", "待售中", "回款中", "项目完成"};
    public static final String[] ALLZONGHE = {"综合推荐", "金额最多", "支持最多", "即将开始", "即将满标"};
    public static final String[] CAR_TYPE = {"全部", "二手车", "平行进口车", "国产车"};
    public static final String[] ORDER_ZHTJ = {"allorder", "money", "investNum", "beginTime", "endsurplus"};
    public static final String[] ZC_TYPE = {"全部","充值", "提现", "投资", "回款", "流标退款", "红包","其他"};
    public static final String[] CLASS_ID = {"allclass","deposit", "withdraw", "invest", "backmoney", "failbackmoney", "redPacket","other"};
    public static final String TYPE = "TYPE";
    public static final String[] HKFS = {"全部", "先息后本", "等额本息", "到期还本付息",};
    public static final String[] HKQX = {"全部", "1月期", "2月期", "3月期", "6月期", "12月期", "18月期", "24月期"};
    public static final Integer[] HKQX_NUM = {0,1,2,3,6,12,18,24};
    public static ArrayList<RedPacketBean> redPacketBeens;
}
