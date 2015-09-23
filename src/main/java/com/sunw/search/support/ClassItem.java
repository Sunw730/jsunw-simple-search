/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.support;

import com.sunw.search.fault.SearchException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * [Class匹配信息]
 *
 * @ProjectName: [sunw-web]
 * @Author: [Sun Wang]
 * @CreateDate: [2015/8/26 10:40]
 * @Update: [说明本次修改内容] BY [SunWang] [2015/8/26]
 * @Version: [v1.0]
 */
public class ClassItem {

    //class
    private final Class clazz;
    //Search|Order字段集合
    private final Set<SearchItem> searchItems = new HashSet<SearchItem>();
    private final List<OrderItem> orderItems = new ArrayList<OrderItem>();

    public ClassItem(Class clazz) {
        if(clazz == null) throw new SearchException("请指定Model类型");
        this.clazz = clazz;
    }

    /**
     * 获取所有Search匹配
     * @return
     */
    public Set<SearchItem> getSearchItems() {
        return this.searchItems;
    }

    /**
     * 获取包含指定组的Search匹配
     * @param groups
     * @return
     */
    public Set<SearchItem> getSearchItems(Class[] groups) {
        Set<SearchItem> result = new HashSet<SearchItem>();
        for(SearchItem item : searchItems) {
            if(item.containsGroup(groups)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * 获取所有Order匹配
     * @return
     */
    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    /**
     * 获取包含指定组的Order匹配
     * @param groups
     * @return
     */
    public List<OrderItem> getOrderItems(Class[] groups) {
        List<OrderItem> result = new ArrayList<OrderItem>();
        for(OrderItem item : orderItems) {
            if(item.containsGroup(groups)){
                result.add(item);
            }
        }
        return result;
    }

}
