package com.nongjinsuo.mimijinfu.beans;

import java.io.Serializable;

/**
 * description: (描述)
 * autour: czz
 * date: 2017/9/19 0019 上午 11:45
 * update: 2017/9/19 0019
 */

public class ShareEntity implements Serializable{

    /**
     * title : '+title+'
     * description : 最长90天,保底年化8%,来跟我一起赚钱吧
     * url : '+url+'
     * image : http://app.aimizhongchou.com/Public/H5/images/share_img.png
     */
    private String shareId;
    private String title;
    private String url;
    private String image;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
