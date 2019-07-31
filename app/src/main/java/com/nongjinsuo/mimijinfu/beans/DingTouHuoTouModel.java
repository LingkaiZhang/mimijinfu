package com.nongjinsuo.mimijinfu.beans;

import java.util.List;

/**
 * description: (描述)
 * autour: czz
 * date: 2018/1/16 0016 下午 2:39
 * update: 2018/1/16 0016
 */

public class DingTouHuoTouModel {


    /**
     * code : 200
     * text : 操作成功
     * result : {"list":[{"title":"定投宝","data":[{"id":"664","name":"6月 D18010014","bm":"815156547146501","needMoney":"30000.00","projectDay":"6","projectUnit":"2","investMoney":"0.00","investPeopleNum":"0","status":"1","projectRate":"9","pubTime":"2018-01-11 15:11:54","actualInvestOverTime":"0000-00-00 00:00:00","backMoneyClass":"1","basicRate":"9","plusRate":"","projectClass":"2","backMoneyTime":"0000-00-00 00:00:00","interestDate":"0000-00-00","residueMoney":"3","residueUnit":"万","residueMoneyDescr":"剩余3万","nameSimplify":"定期 6月","statusDescr":"去投资","backMoneyClassName":"先息后本"}]},{"title":"活投宝","data":[{"bm":"15142828278323","period":"0003","name":"H18010008","beginTime":"2017-12-28 15:26:59","projectClass":"2","buyBackMoney":"0","carImageCover":"/Public/upfile/project/15142828355730.jpg","needMoney":"1300","needUnit":"元","investMoney":"100","investUnit":"元","residueMoney":"1200","residueUnit":"元","investprogress":7,"status":1,"statusname":"募集中","surplustime":0,"rate":"%","duetime":"1-90天","raterangemin":"8%","raterangemax":"11%","descr":"剩余可投","basicRate":"11","plusRate":"","deadline":"90天内","dueDays":null,"investPeopleNum":"1","actualRate":"%","investUsedTime":"17528天7时"}]}],"loginState":0,"loginDescr":""}
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
         * list : [{"title":"定投宝","data":[{"id":"664","name":"6月 D18010014","bm":"815156547146501","needMoney":"30000.00","projectDay":"6","projectUnit":"2","investMoney":"0.00","investPeopleNum":"0","status":"1","projectRate":"9","pubTime":"2018-01-11 15:11:54","actualInvestOverTime":"0000-00-00 00:00:00","backMoneyClass":"1","basicRate":"9","plusRate":"","projectClass":"2","backMoneyTime":"0000-00-00 00:00:00","interestDate":"0000-00-00","residueMoney":"3","residueUnit":"万","residueMoneyDescr":"剩余3万","nameSimplify":"定期 6月","statusDescr":"去投资","backMoneyClassName":"先息后本"}]},{"title":"活投宝","data":[{"bm":"15142828278323","period":"0003","name":"H18010008","beginTime":"2017-12-28 15:26:59","projectClass":"2","buyBackMoney":"0","carImageCover":"/Public/upfile/project/15142828355730.jpg","needMoney":"1300","needUnit":"元","investMoney":"100","investUnit":"元","residueMoney":"1200","residueUnit":"元","investprogress":7,"status":1,"statusname":"募集中","surplustime":0,"rate":"%","duetime":"1-90天","raterangemin":"8%","raterangemax":"11%","descr":"剩余可投","basicRate":"11","plusRate":"","deadline":"90天内","dueDays":null,"investPeopleNum":"1","actualRate":"%","investUsedTime":"17528天7时"}]}]
         * loginState : 0
         * loginDescr :
         */

        private int loginState;
        private String loginDescr;
        private List<ListBean> list;

        public int getLoginState() {
            return loginState;
        }

        public void setLoginState(int loginState) {
            this.loginState = loginState;
        }

        public String getLoginDescr() {
            return loginDescr;
        }

        public void setLoginDescr(String loginDescr) {
            this.loginDescr = loginDescr;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * title : 定投宝
             * data : [{"id":"664","name":"6月 D18010014","bm":"815156547146501","needMoney":"30000.00","projectDay":"6","projectUnit":"2","investMoney":"0.00","investPeopleNum":"0","status":"1","projectRate":"9","pubTime":"2018-01-11 15:11:54","actualInvestOverTime":"0000-00-00 00:00:00","backMoneyClass":"1","basicRate":"9","plusRate":"","projectClass":"2","backMoneyTime":"0000-00-00 00:00:00","interestDate":"0000-00-00","residueMoney":"3","residueUnit":"万","residueMoneyDescr":"剩余3万","nameSimplify":"定期 6月","statusDescr":"去投资","backMoneyClassName":"先息后本"}]
             */

            private String title;
            private List<DataBean> data;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }


        }
    }
}
