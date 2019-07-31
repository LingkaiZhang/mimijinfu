package com.nongjinsuo.mimijinfu.beans;

/**
 * description: (描述)
 * autour: czz
 * date: 2017/12/13 0013 下午 6:08
 * update: 2017/12/13 0013
 */

public class ActivityBean {
    /**
     * id : 4
     * activityName : 活动4
     * activityUrl : http://baidu.com
     * activityImage : /Public/upfile/activity/15128053901889.png
     */

    private String id;
    private String activityName;
    private String activityUrl;
    private String activityImage;
    private String title;
    private String content;
    private String coverImage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public String getActivityImage() {
        return activityImage;
    }

    public void setActivityImage(String activityImage) {
        this.activityImage = activityImage;
    }
}
