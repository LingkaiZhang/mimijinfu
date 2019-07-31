package com.nongjinsuo.mimijinfu.beans;

import java.io.Serializable;

/**
 * @author czz
 * @Description: (公告)
 */
public class NoticeVo implements Serializable{


    /**
     * noticeId : 5
     * title : 你实现梦想的引路者字符长度超出
     * pubTime : 2016-12-29 19:47:35
     * classes :
     * projectBm :
     * url :
     * projectname :
     */

    private String noticeId;
    private String title;
    private String pubTime;
    private String classes;
    private String projectBm;
    private String url;
    private String projectname;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getProjectBm() {
        return projectBm;
    }

    public void setProjectBm(String projectBm) {
        this.projectBm = projectBm;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }
}
