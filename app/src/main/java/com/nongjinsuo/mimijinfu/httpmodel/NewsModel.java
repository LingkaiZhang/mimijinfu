package com.nongjinsuo.mimijinfu.httpmodel;

import com.nongjinsuo.mimijinfu.beans.ActivityBean;

import java.util.List;

/**
 * description: (描述)
 * autour: czz
 * date: 2017/12/13 0013 下午 6:01
 * update: 2017/12/13 0013
 */

public class NewsModel {

    /**
     * code : 200
     * text : 操作成功
     * result : {"data":[{"title":"爱米募集：你实现梦想的引路者","id":"47","content":"2017年3月1日，爱米募集PC端与移动端全面启动，正式与大家见面！爱米募集是上海雷先金融信息服务有限公司创立，旨在为梦","coverImage":"/Public/upfile/news/15124706005876.jpg"},{"title":"爱米募集：想投资先要会识别平台","id":"46","content":"在P2P网贷、募集平台倒闭、跑路事件频发的风口，投资之前先谨慎选择一家靠谱的平台显得尤为重要。如何才能让自己的血汗钱不会","coverImage":"/Public/upfile/news/15124705079339.jpg"},{"title":"爱米募集首推汽车募集系统已上线","id":"45","content":"汽车募集主要以对二手车以团购+预售的形式，向投资客户募集项目资金，后期通过对车辆的售卖获取利润+分红的高收益回报的投资消","coverImage":"/Public/upfile/news/15124704273637.jpg"},{"title":"爱米：汽车募集打造新的生活模式","id":"44","content":"在互联网金融快速发展的浪潮下，汽车募集对互联网金融的发展起着很大的推动作用。随着生活水平逐渐提高，汽车的销量不断增长，人","coverImage":"/Public/upfile/news/15124703897124.png"},{"title":"爱米带领你一起了解汽车募集系统","id":"43","content":"想了解汽车募集，首先要清楚它的发展背景。2015年国内汽车市场增速放缓，新车年交易量达2460万辆，二手车年交易量达94","coverImage":"/Public/upfile/news/15124703496010.jpg"},{"title":"爱米：汽车募集与P2P的优势对比","id":"42","content":"随着越来越多的P2P平台倒闭，越来越多的投资人在寻找新的投资理财渠道，而物权募集以其门槛低、周期短、收益高以及更安全等优","coverImage":"/Public/upfile/news/15124703107910.jpg"},{"title":"爱米：汽车募集你要知道的那些事","id":"41","content":"\u201c万事预则立，不预则废\u201d。这句古语可谓是从事任何一个行业，做任何一项业务的真理，而作为互联网金融与传统汽车产业碰撞出的火","coverImage":"/Public/upfile/news/15124702722778.jpg"},{"title":"爱米：汽车募集市场今年或将大爆发","id":"40","content":"自2015年下半年\u201c汽车募集\u201d这一概念兴起后，国内汽车募集市场发展迅速。回顾整个2016年汽车募集市场从平台数、交易额和","coverImage":"/Public/upfile/news/15124702355746.jpg"},{"title":"爱米募集上线，凭什么高收益？","id":"39","content":"众所期待的爱米募集本周三(2017.03.01)正式上线。（网站是www.aimizhongchou.com 或者到各大","coverImage":"/Public/upfile/news/15124679796701.jpg"},{"title":"爱米募集新策略 平行进口 好车集结","id":"38","content":"2017年3月1日爱米募集平台全面启动，3月3日平台第一个募集项目上线，仅用27秒筹款20万元，首战告捷！同年4月继二手","coverImage":"/Public/upfile/news/15124679065855.jpg"},{"title":"爱米募集平行进口车业务再创佳绩","id":"37","content":"2017年4月13日，爱米募集平台增加平行进口车新型业务模式，当日下午3款不同车型的募集项目分别上线，共计用时12分26","coverImage":"/Public/upfile/news/15124678657185.jpg"},{"title":"爱米募集累计交易额已突破一亿大关","id":"36","content":"截止至2017年8月17日12点47分，爱米募集累计交易额已突破一亿大关！爱米募集平台经营短短4个月，在同行业中可以说是","coverImage":"/Public/upfile/news/15124677937368.png"},{"title":"爱米募集祝全体投资人节日快乐","id":"35","content":"浓情金秋，硕果飘香，在国庆中秋双节来临之际，爱米募集在全体投资人的陪伴下已上线半年有余。自三月上线以来，爱米募集投资金额","coverImage":"/Public/upfile/news/15124676175091.png"}],"totalNum":"13"}
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
         * data : [{"title":"爱米募集：你实现梦想的引路者","id":"47","content":"2017年3月1日，爱米募集PC端与移动端全面启动，正式与大家见面！爱米募集是上海雷先金融信息服务有限公司创立，旨在为梦","coverImage":"/Public/upfile/news/15124706005876.jpg"},{"title":"爱米募集：想投资先要会识别平台","id":"46","content":"在P2P网贷、募集平台倒闭、跑路事件频发的风口，投资之前先谨慎选择一家靠谱的平台显得尤为重要。如何才能让自己的血汗钱不会","coverImage":"/Public/upfile/news/15124705079339.jpg"},{"title":"爱米募集首推汽车募集系统已上线","id":"45","content":"汽车募集主要以对二手车以团购+预售的形式，向投资客户募集项目资金，后期通过对车辆的售卖获取利润+分红的高收益回报的投资消","coverImage":"/Public/upfile/news/15124704273637.jpg"},{"title":"爱米：汽车募集打造新的生活模式","id":"44","content":"在互联网金融快速发展的浪潮下，汽车募集对互联网金融的发展起着很大的推动作用。随着生活水平逐渐提高，汽车的销量不断增长，人","coverImage":"/Public/upfile/news/15124703897124.png"},{"title":"爱米带领你一起了解汽车募集系统","id":"43","content":"想了解汽车募集，首先要清楚它的发展背景。2015年国内汽车市场增速放缓，新车年交易量达2460万辆，二手车年交易量达94","coverImage":"/Public/upfile/news/15124703496010.jpg"},{"title":"爱米：汽车募集与P2P的优势对比","id":"42","content":"随着越来越多的P2P平台倒闭，越来越多的投资人在寻找新的投资理财渠道，而物权募集以其门槛低、周期短、收益高以及更安全等优","coverImage":"/Public/upfile/news/15124703107910.jpg"},{"title":"爱米：汽车募集你要知道的那些事","id":"41","content":"\u201c万事预则立，不预则废\u201d。这句古语可谓是从事任何一个行业，做任何一项业务的真理，而作为互联网金融与传统汽车产业碰撞出的火","coverImage":"/Public/upfile/news/15124702722778.jpg"},{"title":"爱米：汽车募集市场今年或将大爆发","id":"40","content":"自2015年下半年\u201c汽车募集\u201d这一概念兴起后，国内汽车募集市场发展迅速。回顾整个2016年汽车募集市场从平台数、交易额和","coverImage":"/Public/upfile/news/15124702355746.jpg"},{"title":"爱米募集上线，凭什么高收益？","id":"39","content":"众所期待的爱米募集本周三(2017.03.01)正式上线。（网站是www.aimizhongchou.com 或者到各大","coverImage":"/Public/upfile/news/15124679796701.jpg"},{"title":"爱米募集新策略 平行进口 好车集结","id":"38","content":"2017年3月1日爱米募集平台全面启动，3月3日平台第一个募集项目上线，仅用27秒筹款20万元，首战告捷！同年4月继二手","coverImage":"/Public/upfile/news/15124679065855.jpg"},{"title":"爱米募集平行进口车业务再创佳绩","id":"37","content":"2017年4月13日，爱米募集平台增加平行进口车新型业务模式，当日下午3款不同车型的募集项目分别上线，共计用时12分26","coverImage":"/Public/upfile/news/15124678657185.jpg"},{"title":"爱米募集累计交易额已突破一亿大关","id":"36","content":"截止至2017年8月17日12点47分，爱米募集累计交易额已突破一亿大关！爱米募集平台经营短短4个月，在同行业中可以说是","coverImage":"/Public/upfile/news/15124677937368.png"},{"title":"爱米募集祝全体投资人节日快乐","id":"35","content":"浓情金秋，硕果飘香，在国庆中秋双节来临之际，爱米募集在全体投资人的陪伴下已上线半年有余。自三月上线以来，爱米募集投资金额","coverImage":"/Public/upfile/news/15124676175091.png"}]
         * totalNum : 13
         */

        private String totalNum;
        private List<ActivityBean> data;

        public String getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }

        public List<ActivityBean> getData() {
            return data;
        }

        public void setData(List<ActivityBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * title : 爱米募集：你实现梦想的引路者
             * id : 47
             * content : 2017年3月1日，爱米募集PC端与移动端全面启动，正式与大家见面！爱米募集是上海雷先金融信息服务有限公司创立，旨在为梦
             * coverImage : /Public/upfile/news/15124706005876.jpg
             */

            private String title;
            private String id;
            private String content;
            private String coverImage;
            private String activityName;
            private String activityUrl;
            private String activityImage;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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
        }
    }
}
