/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.bean;

import com.sunw.search.util.support.SearchSupportUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * [查询上下文：封装了查询语句和对应的查询参数]
 *
 * @ProjectName: [sunw-parent]
 * @Author: [SunWang]
 * @CreateDate: [2015/7/6 17:41]
 * @Update: [说明本次修改内容] BY [SunWang] [2015/7/6]
 * @Version: [v1.0]
 */
public class SearchContext {

    public static final String QUERY_AND = " and ";
    public static final String QUERY_WHERE = " where ";
    public static final String QUERY_ORDER = " order by ";
    public static final String QUERY_ORDER_SEP = ", ";
    public static final String QUERY_LIKE_SEP = "%";

    private final StringBuffer searchString = new StringBuffer();     //查询串
    private final StringBuffer orderString = new StringBuffer();     //排序语句
    private final Map<String, Object> param = new HashMap<String, Object>();                            //查询参数集

    /**
     * 添加一个条件匹配
     * @param query
     * @param paramName
     * @param paramValue
     */
    public void addSearchQuery(String query, String paramName, Object paramValue) {
        if(SearchSupportUtil.isEmpty(query)) return ;
        //查询条件
        if(!SearchSupportUtil.isEmpty(this.searchString)) this.searchString.append(QUERY_AND);
        this.searchString.append(query).append(" ");
        //添加对应参数
        if(!SearchSupportUtil.isEmpty(paramName) && !SearchSupportUtil.isEmpty(paramValue)) {
            if(query.contains(SearchType.LK.value())) {
                //如果是like匹配
                this.addParam(paramName, QUERY_LIKE_SEP + paramValue + QUERY_LIKE_SEP);
            } else {
                this.addParam(paramName, paramValue);
            }
        }
    }

    /**
     * 添加一个排序匹配
     * @param query
     */
    public void addOrderString(String query) {
        if(SearchSupportUtil.isEmpty(query)) return ;
        //order by
        if(!SearchSupportUtil.isEmpty(this.orderString)) this.orderString.append(QUERY_ORDER_SEP);
        this.orderString.append(query).append(" ");
    }

    /**
     * 获取where语句
     * @return
     */
    public String where() {
        StringBuffer sb = new StringBuffer();
        if(!SearchSupportUtil.isEmpty(this.searchString)) {
            sb.append(QUERY_WHERE).append(this.searchString);
        }
        return sb.toString();
    }

    /**
     * 获取order语句
     * @return
     */
    public String order() {
        StringBuffer sb = new StringBuffer();
        if(!SearchSupportUtil.isEmpty(this.orderString)) {
            sb.append(QUERY_ORDER).append(this.orderString);
        }
        return sb.toString();
    }

    /**
     * 获取where条件语句，不包括where关键字
     * @return
     */
    public String getSearchString() {
        return searchString.toString();
    }

    /**
     * 获取order排序语句，不包括order by关键字
     * @return
     */
    public String getOrderString() {
        return orderString.toString();
    }

    /**
     * 获取完整查询语句：where...order by...
     * @return
     */
    public String getQueryString() {
        return new StringBuffer(this.where())
                .append(this.order())
                .toString();
    }

    /**
     * 获取参数集
     * @return
     */
    public Map<String, Object> getParam() {
        return param;
    }

    /**
     * 添加一个参数
     * @param paramName
     * @param paramValue
     */
    private void addParam(String paramName, Object paramValue) {
        this.param.put(paramName, paramValue);
    }
}
