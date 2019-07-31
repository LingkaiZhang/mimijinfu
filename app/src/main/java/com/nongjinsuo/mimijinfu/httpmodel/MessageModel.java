package com.nongjinsuo.mimijinfu.httpmodel;

import com.nongjinsuo.mimijinfu.beans.BaseVo;
import com.nongjinsuo.mimijinfu.beans.MessageVo;

import java.util.List;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class MessageModel extends BaseVo{
    public MessageBean result;
   public class MessageBean{
        public List<MessageVo> message;
        public String messageNum;
    }
}
