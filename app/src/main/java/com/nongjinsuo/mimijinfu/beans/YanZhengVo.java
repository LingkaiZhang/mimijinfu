package com.nongjinsuo.mimijinfu.beans;

/**
 * @author czz
 * @Description: (验证绑卡人身份)
 */
public class YanZhengVo {


    /**
     * ticket : 新浪发送验证码ticket
     * cardId : 银行卡id
     */

    private String ticket;
    private String cardId;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
