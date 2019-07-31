package com.nongjinsuo.mimijinfu.beans;

import java.io.Serializable;

public class BaseVo implements Serializable {

    /**
     * @author czz
     * @adddate 2015-9-10 上午11:18:32
     */
    public String code;
    public String text;

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
}
