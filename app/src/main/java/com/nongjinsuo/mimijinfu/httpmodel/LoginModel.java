package com.nongjinsuo.mimijinfu.httpmodel;

import com.nongjinsuo.mimijinfu.beans.BaseVo;

/**
 * @author czz
 * @Description: (登录)
 */
public class LoginModel extends BaseVo{

    public User result;
    public class User{
        public String userId;
        public String token;
    }
}
