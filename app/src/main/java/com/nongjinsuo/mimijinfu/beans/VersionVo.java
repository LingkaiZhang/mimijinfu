package com.nongjinsuo.mimijinfu.beans;

/**
 * @author czz
 * @createdate 2016-2-25 下午4:33:12
 * @Description: TODO(用一句话描述该类做什么)
 */
public class VersionVo {
//    "pubTime":"2015-12-29",
//    "androidVersionCode":"10",
//    "androidVersionName":"1.10",
//    "androidVersionInfo":"1.调整登录和注册页\n2.调整热贴样式",
//    "androidForceUpdate":"0",
//    "iosVersionName":"1.0.6",
//    "iosVersionInfo":"1.调整登录和注册页\n2.调整热贴样式",
//    "iosForceUpdate":"0"
    private String androidVersionCode;
    private String androidVersionName;
    private String androidVersionInfo;
    private String androidForceUpdate;
    private String download;
    private String openAdImage;
    private int openAdStatus;
    private String openAdUrl;
    private int jumpAdStatus;
    private String jumpAdImage;
    private String jumpAdUrl;
    private int updateStatus;//服务器升级状态：0不升级1升级中
    private String updateDescr;//服务器升级描述

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getUpdateDescr() {
        return updateDescr;
    }

    public void setUpdateDescr(String updateDescr) {
        this.updateDescr = updateDescr;
    }

    public int getJumpAdStatus() {
        return jumpAdStatus;
    }

    public void setJumpAdStatus(int jumpAdStatus) {
        this.jumpAdStatus = jumpAdStatus;
    }

    public String getJumpAdImage() {
        return jumpAdImage;
    }

    public void setJumpAdImage(String jumpAdImage) {
        this.jumpAdImage = jumpAdImage;
    }

    public String getJumpAdUrl() {
        return jumpAdUrl;
    }

    public void setJumpAdUrl(String jumpAdUrl) {
        this.jumpAdUrl = jumpAdUrl;
    }

    public String getOpenAdImage() {
        return openAdImage;
    }

    public void setOpenAdImage(String openAdImage) {
        this.openAdImage = openAdImage;
    }

    public int getOpenAdStatus() {
        return openAdStatus;
    }

    public void setOpenAdStatus(int openAdStatus) {
        this.openAdStatus = openAdStatus;
    }

    public String getOpenAdUrl() {
        return openAdUrl;
    }

    public void setOpenAdUrl(String openAdUrl) {
        this.openAdUrl = openAdUrl;
    }

    public String getAndroidVersionCode() {
        return androidVersionCode;
    }

    public void setAndroidVersionCode(String androidVersionCode) {
        this.androidVersionCode = androidVersionCode;
    }

    public String getAndroidVersionName() {
        return androidVersionName;
    }

    public void setAndroidVersionName(String androidVersionName) {
        this.androidVersionName = androidVersionName;
    }

    public String getAndroidVersionInfo() {
        return androidVersionInfo;
    }

    public void setAndroidVersionInfo(String androidVersionInfo) {
        this.androidVersionInfo = androidVersionInfo;
    }

    public String getAndroidForceUpdate() {
        return androidForceUpdate;
    }

    public void setAndroidForceUpdate(String androidForceUpdate) {
        this.androidForceUpdate = androidForceUpdate;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }
}

