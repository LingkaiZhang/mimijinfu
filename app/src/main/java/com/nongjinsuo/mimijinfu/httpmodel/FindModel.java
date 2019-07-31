package com.nongjinsuo.mimijinfu.httpmodel;

import com.nongjinsuo.mimijinfu.beans.ActivityBean;

import java.util.List;

/**
 * description: (描述)
 * autour: czz
 * date: 2017/12/11 0011 下午 6:02
 * update: 2017/12/11 0011
 */

public class FindModel {

    /**
     * code : 200
     * text : 操作成功
     * result : {"platformTotalDealNum":"16.8万","platformTotalInterest":"70","news":[{"id":"47","title":"爱米募集：你实现梦想的引路者","source":"爱米募集"},{"id":"46","title":"爱米募集：想投资先要会识别平台","source":"爱米募集"},{"id":"45","title":"爱米募集首推汽车募集系统已上线","source":"爱米募集"},{"id":"44","title":"爱米：汽车募集打造新的生活模式","source":"爱米募集"},{"id":"43","title":"爱米带领你一起了解汽车募集系统","source":"爱米募集"}],"activity":[{"id":"4","activityName":"活动4","activityUrl":"http://baidu.com","activityImage":"/Public/upfile/activity/15128053901889.png"},{"id":"3","activityName":"活动3","activityUrl":"http://www.sogou.com","activityImage":"/Public/upfile/activity/15128050362838.png"},{"id":"1","activityName":"活动1","activityUrl":"http://www.baidu.com","activityImage":"/Public/upfile/activity/15128048976062.jpg"}],"realDataUrl":"/Wap/page/dealdata","helpUrl":"/Wap/html/help","kfUrl":"/Wap/html/kf"}
     */

    private String code;
    private String text;
    private ResultBean result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * platformTotalDealNum : 16.8万
         * platformTotalInterest : 70
         * news : [{"id":"47","title":"爱米募集：你实现梦想的引路者","source":"爱米募集"},{"id":"46","title":"爱米募集：想投资先要会识别平台","source":"爱米募集"},{"id":"45","title":"爱米募集首推汽车募集系统已上线","source":"爱米募集"},{"id":"44","title":"爱米：汽车募集打造新的生活模式","source":"爱米募集"},{"id":"43","title":"爱米带领你一起了解汽车募集系统","source":"爱米募集"}]
         * activity : [{"id":"4","activityName":"活动4","activityUrl":"http://baidu.com","activityImage":"/Public/upfile/activity/15128053901889.png"},{"id":"3","activityName":"活动3","activityUrl":"http://www.sogou.com","activityImage":"/Public/upfile/activity/15128050362838.png"},{"id":"1","activityName":"活动1","activityUrl":"http://www.baidu.com","activityImage":"/Public/upfile/activity/15128048976062.jpg"}]
         * realDataUrl : /Wap/page/dealdata
         * helpUrl : /Wap/html/help
         * kfUrl : /Wap/html/kf
         */

        private String platformTotalDealNum;
        private String platformTotalInterest;
        private String realDataUrl;
        private String helpUrl;
        private String kfUrl;
        private List<NewsBean> news;
        private List<ActivityBean> activity;

        public String getPlatformTotalDealNum() {
            return platformTotalDealNum;
        }

        public void setPlatformTotalDealNum(String platformTotalDealNum) {
            this.platformTotalDealNum = platformTotalDealNum;
        }

        public String getPlatformTotalInterest() {
            return platformTotalInterest;
        }

        public void setPlatformTotalInterest(String platformTotalInterest) {
            this.platformTotalInterest = platformTotalInterest;
        }

        public String getRealDataUrl() {
            return realDataUrl;
        }

        public void setRealDataUrl(String realDataUrl) {
            this.realDataUrl = realDataUrl;
        }

        public String getHelpUrl() {
            return helpUrl;
        }

        public void setHelpUrl(String helpUrl) {
            this.helpUrl = helpUrl;
        }

        public String getKfUrl() {
            return kfUrl;
        }

        public void setKfUrl(String kfUrl) {
            this.kfUrl = kfUrl;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public List<ActivityBean> getActivity() {
            return activity;
        }

        public void setActivity(List<ActivityBean> activity) {
            this.activity = activity;
        }

        public static class NewsBean {
            /**
             * id : 47
             * title : 爱米募集：你实现梦想的引路者
             * source : 爱米募集
             */

            private String id;
            private String title;
            private String source;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }
        }

    }
}
