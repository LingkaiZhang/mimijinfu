package com.nongjinsuo.mimijinfu.httpmodel;

import com.nongjinsuo.mimijinfu.beans.BaseVo;
import com.nongjinsuo.mimijinfu.beans.LoginPromptVo;
import com.nongjinsuo.mimijinfu.beans.TrailerVo;

import java.util.List;

/**
 * @author czz
 * @Description: (用一句话描述)
 */
public class ProjectListModel extends BaseVo{
    public List<TrailerVo> allproject;
    public int loginState;
    public String loginDescr;

}
