package com.nongjinsuo.mimijinfu.beans;

import java.io.Serializable;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class MessageVo implements Serializable{

    /**
     * messageId : 9
     * content : 123asdfas阿三地方啦视频地方哈斯卡拉东方哈稍等发色的发挥阿斯蒂芬哈额的发挥啊
     * pubTime : 1482839952
     * classId : 2
     * classes :
     * projectBm :
     * url :
     * image : /Public/upfile/message/message2.png
     * classname : 通知
     * projectname :
     */

    private String messageId;
    private String content;
    private String pubTime;
    private int classId;
    private String classes;
    private String projectBm;
    private String url;
    private String image;
    private String classname;
    private String projectname;
    private int isRead;

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }
}
