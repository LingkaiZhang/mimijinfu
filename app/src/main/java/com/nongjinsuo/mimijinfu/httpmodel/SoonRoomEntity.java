package com.nongjinsuo.mimijinfu.httpmodel;

import com.nongjinsuo.mimijinfu.beans.BackMoneyBean;

import java.util.List;

/**
 * description: (描述)
 * autour: czz
 * date: 2017/10/26 0026 下午 2:10
 * update: 2017/10/26 0026
 */

public class SoonRoomEntity  {


    /**
     * code : 200
     * text : 操作成功
     * result : {"back":[{"date":"2018-01","data":[{"id":"978","backMoney":"10.42","backTime":"2018-01-05","dateMonth":"2018-01","duraNum":"3","projectPayNo":"815121288734446-1","name":"定期 3月 D20171201-0011","backMoneyMoney":10.42,"backMoneyUnit":"元","sn":"1"},{"id":"1088","backMoney":"1.04","backTime":"2018-01-07","dateMonth":"2018-01","duraNum":"3","projectPayNo":"815125612355193-1","name":"定期 3月 D20171206-0005","backMoneyMoney":1.04,"backMoneyUnit":"元","sn":"1"},{"id":"1109","backMoney":"80.17","backTime":"2018-01-08","dateMonth":"2018-01","duraNum":"12","projectPayNo":"815125530161004-1","name":"定期 12月 D20171206-0004","backMoneyMoney":80.17,"backMoneyUnit":"元","sn":"1"},{"id":"1136","backMoney":"1.04","backTime":"2018-01-08","dateMonth":"2018-01","duraNum":"3","projectPayNo":"815126306382094-1","name":"定期 3月 D20171207-0004","backMoneyMoney":1.04,"backMoneyUnit":"元","sn":"1"},{"id":"1188","backMoney":"4.17","backTime":"2018-01-10","dateMonth":"2018-01","duraNum":"3","projectPayNo":"815127232534618-1","name":"定期 3月 D20171208-0004","backMoneyMoney":4.17,"backMoneyUnit":"元","sn":"1"},{"id":"1290","backMoney":"0.92","backTime":"2018-01-12","dateMonth":"2018-01","duraNum":"6","projectPayNo":"815125455084320-1","name":"新手标 6月 X20171206-0001","backMoneyMoney":0.92,"backMoneyUnit":"元","sn":"1"}]},{"date":"2018-02","data":[{"id":"979","backMoney":"10.42","backTime":"2018-02-05","dateMonth":"2018-02","duraNum":"3","projectPayNo":"815121288734446-2","name":"定期 3月 D20171201-0011","backMoneyMoney":10.42,"backMoneyUnit":"元","sn":"2"},{"id":"1056","backMoney":"5.21","backTime":"2018-02-05","dateMonth":"2018-02","duraNum":"3","projectPayNo":"815123847058677-2","name":"定期 3月 D20171204-0007","backMoneyMoney":5.21,"backMoneyUnit":"元","sn":"2"},{"id":"1089","backMoney":"1.04","backTime":"2018-02-07","dateMonth":"2018-02","duraNum":"3","projectPayNo":"815125612355193-2","name":"定期 3月 D20171206-0005","backMoneyMoney":1.04,"backMoneyUnit":"元","sn":"2"},{"id":"1137","backMoney":"1.04","backTime":"2018-02-08","dateMonth":"2018-02","duraNum":"3","projectPayNo":"815126306382094-2","name":"定期 3月 D20171207-0004","backMoneyMoney":1.04,"backMoneyUnit":"元","sn":"2"},{"id":"1110","backMoney":"80.17","backTime":"2018-02-08","dateMonth":"2018-02","duraNum":"12","projectPayNo":"815125530161004-2","name":"定期 12月 D20171206-0004","backMoneyMoney":80.17,"backMoneyUnit":"元","sn":"2"},{"id":"1189","backMoney":"4.17","backTime":"2018-02-10","dateMonth":"2018-02","duraNum":"3","projectPayNo":"815127232534618-2","name":"定期 3月 D20171208-0004","backMoneyMoney":4.17,"backMoneyUnit":"元","sn":"2"},{"id":"1291","backMoney":"0.92","backTime":"2018-02-12","dateMonth":"2018-02","duraNum":"6","projectPayNo":"815125455084320-2","name":"新手标 6月 X20171206-0001","backMoneyMoney":0.92,"backMoneyUnit":"元","sn":"2"}]},{"date":"2018-03","data":[{"id":"980","backMoney":"1010.41","backTime":"2018-03-05","dateMonth":"2018-03","duraNum":"3","projectPayNo":"815121288734446-3","name":"定期 3月 D20171201-0011","backMoneyMoney":1010.41,"backMoneyUnit":"元","sn":"3"},{"id":"1057","backMoney":"505.21","backTime":"2018-03-05","dateMonth":"2018-03","duraNum":"3","projectPayNo":"815123847058677-3","name":"定期 3月 D20171204-0007","backMoneyMoney":505.21,"backMoneyUnit":"元","sn":"3"},{"id":"1090","backMoney":"101.05","backTime":"2018-03-07","dateMonth":"2018-03","duraNum":"3","projectPayNo":"815125612355193-3","name":"定期 3月 D20171206-0005","backMoneyMoney":101.05,"backMoneyUnit":"元","sn":"3"},{"id":"1111","backMoney":"80.17","backTime":"2018-03-08","dateMonth":"2018-03","duraNum":"12","projectPayNo":"815125530161004-3","name":"定期 12月 D20171206-0004","backMoneyMoney":80.17,"backMoneyUnit":"元","sn":"3"},{"id":"1138","backMoney":"101.05","backTime":"2018-03-08","dateMonth":"2018-03","duraNum":"3","projectPayNo":"815126306382094-3","name":"定期 3月 D20171207-0004","backMoneyMoney":101.05,"backMoneyUnit":"元","sn":"3"},{"id":"1190","backMoney":"404.16","backTime":"2018-03-10","dateMonth":"2018-03","duraNum":"3","projectPayNo":"815127232534618-3","name":"定期 3月 D20171208-0004","backMoneyMoney":404.16,"backMoneyUnit":"元","sn":"3"},{"id":"1292","backMoney":"0.92","backTime":"2018-03-12","dateMonth":"2018-03","duraNum":"6","projectPayNo":"815125455084320-3","name":"新手标 6月 X20171206-0001","backMoneyMoney":0.92,"backMoneyUnit":"元","sn":"3"}]},{"date":"2018-04","data":[{"id":"1112","backMoney":"80.17","backTime":"2018-04-08","dateMonth":"2018-04","duraNum":"12","projectPayNo":"815125530161004-4","name":"定期 12月 D20171206-0004","backMoneyMoney":80.17,"backMoneyUnit":"元","sn":"4"},{"id":"1293","backMoney":"0.92","backTime":"2018-04-12","dateMonth":"2018-04","duraNum":"6","projectPayNo":"815125455084320-4","name":"新手标 6月 X20171206-0001","backMoneyMoney":0.92,"backMoneyUnit":"元","sn":"4"}]},{"date":"2018-05","data":[{"id":"1113","backMoney":"80.17","backTime":"2018-05-08","dateMonth":"2018-05","duraNum":"12","projectPayNo":"815125530161004-5","name":"定期 12月 D20171206-0004","backMoneyMoney":80.17,"backMoneyUnit":"元","sn":"5"},{"id":"1294","backMoney":"0.92","backTime":"2018-05-12","dateMonth":"2018-05","duraNum":"6","projectPayNo":"815125455084320-5","name":"新手标 6月 X20171206-0001","backMoneyMoney":0.92,"backMoneyUnit":"元","sn":"5"}]},{"date":"2018-06","data":[{"id":"1114","backMoney":"80.17","backTime":"2018-06-08","dateMonth":"2018-06","duraNum":"12","projectPayNo":"815125530161004-6","name":"定期 12月 D20171206-0004","backMoneyMoney":80.17,"backMoneyUnit":"元","sn":"6"},{"id":"1295","backMoney":"100.90","backTime":"2018-06-12","dateMonth":"2018-06","duraNum":"6","projectPayNo":"815125455084320-6","name":"新手标 6月 X20171206-0001","backMoneyMoney":100.9,"backMoneyUnit":"元","sn":"6"}]}]}
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
        private List<BackBean> back;

        public List<BackBean> getBack() {
            return back;
        }

        public void setBack(List<BackBean> back) {
            this.back = back;
        }

        public static class BackBean {
            /**
             * date : 2018-01
             * data : [{"id":"978","backMoney":"10.42","backTime":"2018-01-05","dateMonth":"2018-01","duraNum":"3","projectPayNo":"815121288734446-1","name":"定期 3月 D20171201-0011","backMoneyMoney":10.42,"backMoneyUnit":"元","sn":"1"},{"id":"1088","backMoney":"1.04","backTime":"2018-01-07","dateMonth":"2018-01","duraNum":"3","projectPayNo":"815125612355193-1","name":"定期 3月 D20171206-0005","backMoneyMoney":1.04,"backMoneyUnit":"元","sn":"1"},{"id":"1109","backMoney":"80.17","backTime":"2018-01-08","dateMonth":"2018-01","duraNum":"12","projectPayNo":"815125530161004-1","name":"定期 12月 D20171206-0004","backMoneyMoney":80.17,"backMoneyUnit":"元","sn":"1"},{"id":"1136","backMoney":"1.04","backTime":"2018-01-08","dateMonth":"2018-01","duraNum":"3","projectPayNo":"815126306382094-1","name":"定期 3月 D20171207-0004","backMoneyMoney":1.04,"backMoneyUnit":"元","sn":"1"},{"id":"1188","backMoney":"4.17","backTime":"2018-01-10","dateMonth":"2018-01","duraNum":"3","projectPayNo":"815127232534618-1","name":"定期 3月 D20171208-0004","backMoneyMoney":4.17,"backMoneyUnit":"元","sn":"1"},{"id":"1290","backMoney":"0.92","backTime":"2018-01-12","dateMonth":"2018-01","duraNum":"6","projectPayNo":"815125455084320-1","name":"新手标 6月 X20171206-0001","backMoneyMoney":0.92,"backMoneyUnit":"元","sn":"1"}]
             */

            private String date;
            private List<BackMoneyBean> data;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public List<BackMoneyBean> getData() {
                return data;
            }

            public void setData(List<BackMoneyBean> data) {
                this.data = data;
            }

        }
    }
}
