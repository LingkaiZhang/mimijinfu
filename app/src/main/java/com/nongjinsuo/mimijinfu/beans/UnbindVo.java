package com.nongjinsuo.mimijinfu.beans;

/**
 * @author czz
 * @Description: (解绑)
 */
public class UnbindVo extends BaseVo{
    private UnbindBean result;

    public UnbindBean getResult() {
        return result;
    }

    public void setResult(UnbindBean result) {
        this.result = result;
    }

    public class UnbindBean{
        private String ticket;
        private String cardMobile;

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public String getCardMobile() {
            return cardMobile;
        }

        public void setCardMobile(String cardMobile) {
            this.cardMobile = cardMobile;
        }
    }
}
