package com.nongjinsuo.mimijinfu.beans;

import java.util.List;

/**
 * description: (描述)
 * autour: czz
 * date: 2017/9/25 0025 下午 1:07
 * update: 2017/9/25 0025
 */

public class RedPacketModel {

    /**
     * code : 200
     * text : 返回成功
     * result : {"redPacket":[{"id":"34","money":"10","getTime":"2017-09-22 14:09:26","expireTime":"2017-10-02 14:09:26","descr":"单笔投资满500元可用","minUse":"500"},{"id":"35","money":"20","getTime":"2017-09-22 14:09:26","expireTime":"2017-10-02 14:09:26","descr":"单笔投资满10000元可用","minUse":"1000"},{"id":"36","money":"20","getTime":"2017-09-22 14:09:26","expireTime":"2017-10-02 14:09:26","descr":"单笔投资满10000元可用","minUse":"1000"},{"id":"37","money":"50","getTime":"2017-09-22 14:09:26","expireTime":"2017-10-02 14:09:26","descr":"单笔投资满10000元可用","minUse":"1000"}]}
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
        private List<RedPacketBean> redPacket;

        public List<RedPacketBean> getRedPacket() {
            return redPacket;
        }

        public void setRedPacket(List<RedPacketBean> redPacket) {
            this.redPacket = redPacket;
        }

    }
}
