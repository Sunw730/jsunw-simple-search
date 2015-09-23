/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.bean;

/**
 * [查询串模板]
 *
 * @ProjectName: [sunw-web]
 * @Author: [Sun Wang]
 * @CreateDate: [15/8/25]
 * @Update: [说明本次修改内容] BY [Sunw] [15/8/25]
 * @Version: [v1.0]
 */
public class QueryTemplate {

    public static final String TABLE_ALIAS = "{TABLE_ALIAS}";
    public static final String DATA_FIELD_NAME = "{DATA_FIELD_NAME}";
    public static final String PARAM_NAME = "{PARAM_NAME}";
    public static final String QUERY_TYPE = "{QUERY_TYPE}";
    public static final String DOT = ".";
    public static final String COLON = ":";
    public static final String LINE = "_";

    //a.fn = :pn
    public static final String TMPL_QUERY_PARAM = new StringBuffer(TABLE_ALIAS)
            .append(DOT)
            .append(DATA_FIELD_NAME)
            .append(" ")
            .append(QUERY_TYPE)
            .append(" ")
            .append(COLON)
            .append(PARAM_NAME)
            .toString();

    //a.fn is null  或 a.fn asc
    public static final String TMPL_QUERY_NO_PARAM = new StringBuffer(TABLE_ALIAS)
            .append(DOT)
            .append(DATA_FIELD_NAME)
            .append(" ")
            .append(QUERY_TYPE)
            .toString();

    /**
     * 根据是否需要参数获取查询模板语句
     * @param requiredParam
     * @return
     */
    public static final String getQueryStringTemplate(boolean requiredParam) {
        return requiredParam ? TMPL_QUERY_PARAM : TMPL_QUERY_NO_PARAM;
    }

}
