package com.nongjinsuo.mimijinfu.widget;

import com.nongjinsuo.mimijinfu.beans.RedPacketBean;
import com.nongjinsuo.mimijinfu.beans.ShareEntity;

public class EventBarEntity {
	     
	private int type;
	private int updateType;
	private RedPacketBean redPacketBean;
	private ShareEntity shareEntity;
	public static final int UPDATE_PROJECT_DETAILS = 1;//更改详情页面
	public static final int UPDATE_PROPERTY = 2;//刷新资产页面
	public static final int UPDATE_PROPERTY_FIRST = 22;//第一次刷新资产页面
	public static final int UPDATE_LOGOUT_VIEW = 3;//退出登录后跳转页面
	public static final int UPDATE_LOGIN_VIEW = 4;//登录
	public static final int UPDATE_ANQUAN_SETTING = 5;//刷新安全设置页面
	public static final int UPDATE_SET_PWD = 6;//刷新设置密码页面
	public static final int FINISH_ACTIVITY = 7;//开户成功销毁页面
	public static final int UPDATE_PROJECT_VIEW = 8;//跳转到项目页面
	public static final int UPDATE_XMRG = 10;//刷新项目认购
	public static final int UPDATE_HOMEPAGE_MESSAGE = 11;//刷新首页消息
	public static final int UPDATE_HOMEPAGE = 12;//刷新首页数据
	public static final int UPDATE_HOMEPAGE_FIRST = 112;//刷新首页数据
	public static final int UPDATE_SETTING = 14;//刷新设置页面
	public static final int LOGIN_UPDATE_WEB = 16;//登录成功更新web页面
	public static final int SELECT_HB = 17;//选择红包
	public static final int SELECT_BSYHB = 18;//不使用红包
	public static final int PROJECT_ZC = 19;//项目列表页切换到募集中

	public static final int  SHOWSHAREBTN = 20;//显示分享按钮

	public EventBarEntity(){
	    	 
	}

	public RedPacketBean getRedPacketBean() {
		return redPacketBean;
	}

	public void setRedPacketBean(RedPacketBean redPacketBean) {
		this.redPacketBean = redPacketBean;
	}

	public EventBarEntity(int type){
		this.type = type;
	}
	public EventBarEntity(int type,RedPacketBean redPacketBean){
        this.type = type;
		this.redPacketBean = redPacketBean;
	}
	public EventBarEntity(int type,ShareEntity shareEntity){
        this.type = type;
		this.shareEntity = shareEntity;
	}

	public ShareEntity getShareEntity() {
		return shareEntity;
	}

	public void setShareEntity(ShareEntity shareEntity) {
		this.shareEntity = shareEntity;
	}

	public EventBarEntity(int type, int updateType){
		this.type = type;
		this.updateType = updateType;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getUpdateType() {
		return updateType;
	}
	public void setUpdateType(int updateType) {
		this.updateType = updateType;
	}  
	
}
