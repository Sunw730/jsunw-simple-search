/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.support;

import com.sunw.search.bean.Order;
import com.sunw.search.bean.QueryTemplate;
import com.sunw.search.util.support.SearchSupportUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * [Order匹配]
 *
 * @ProjectName: [sunw-web]
 * @Author: [Sun Wang]
 * @CreateDate: [15/8/25]
 * @Update: [说明本次修改内容] BY [Sunw] [15/8/25]
 * @Version: [v1.0]
 */
public class OrderItem implements Comparable<OrderItem> {

    private final Order order;
    private final Field field;
    private final String orderStringTemplate;  //query string

    private final Set<Class> groups = new HashSet<Class>();

    public OrderItem(Order order, Field field) {
        this.order = order;
        this.field = field;
        this.orderStringTemplate = QueryTemplate.getQueryStringTemplate(false).replace(QueryTemplate.QUERY_TYPE, order.type().value());
        if(!SearchSupportUtil.isEmpty(order.group())) {
            this.groups.addAll(Arrays.asList(order.group()));
        }
    }

    /**
     * 是否包含有指定的分组
     * @param groups
     * @return
     */
    public boolean containsGroup(Class[] groups) {
        if(SearchSupportUtil.isEmpty(groups)) return false;
        Set<Class> set = new HashSet<Class>();
        set.addAll(this.groups);
        set.addAll(Arrays.asList(groups));
        if(set.size() < this.groups.size() + groups.length) return true;
        return false;
    }

    /**
     * 获取真实排序语句
     * @param alias
     * @param dataFieldName
     * @return
     */
    public String getRealOrderString(String alias, String dataFieldName) {
        SearchSupportUtil.assertNotEmpty(dataFieldName, "请指定数据库字段名");
        String searchStringTemplate = this.orderStringTemplate;
        String aliasTmpl = QueryTemplate.TABLE_ALIAS;
        if(SearchSupportUtil.isEmpty(alias)) {
            aliasTmpl = aliasTmpl + QueryTemplate.DOT;
            alias = "";
        }
        return searchStringTemplate.replace(aliasTmpl, alias.trim()).replace(QueryTemplate.DATA_FIELD_NAME, dataFieldName.trim());

    }

    /**
     * Getter Methods
     * @return
     */
    public Order getOrder() {
        return order;
    }

    public Field getField() {
        return field;
    }

    public String getOrderStringTemplate() {
        return orderStringTemplate;
    }

    public Set<Class> getGroups() {
        return groups;
    }

    @Override
    public int compareTo(OrderItem other) {
        return Integer.valueOf(this.order.sort()).compareTo(other.order.sort());
    }
}
