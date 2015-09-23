/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.util;

import com.sunw.search.bean.SearchContext;
import com.sunw.search.group.DefaultSearchGroup;
import com.sunw.search.handler.FieldHandler;
import com.sunw.search.handler.impl.DefaultFieldHandlerImpl;
import com.sunw.search.strategy.SearchStrategy;
import com.sunw.search.strategy.impl.DefaultSearchStrategyImpl;
import com.sunw.search.support.ClassItem;
import com.sunw.search.support.OrderItem;
import com.sunw.search.support.SearchItem;
import com.sunw.search.support.SearchParser;
import com.sunw.search.util.support.SearchSupportUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * [Search|Order匹配工具类]
 *
 * @ProjectName: [sunw-web]
 * @Author: [Sun Wang]
 * @CreateDate: [2015/8/26 11:22]
 * @Update: [说明本次修改内容] BY [SunWang] [2015/8/26]
 * @Version: [v1.0]
 */
public class SearchUtil {

    private static FieldHandler fieldHandler = new DefaultFieldHandlerImpl();
    private static SearchStrategy searchStrategy = new DefaultSearchStrategyImpl();

    /**
     * 获取HQL查询环境
     *
     * @param clazz
     * @param paramObj
     * @param groups
     * @return
     */
    public static SearchContext getHqlSearchContext(Class clazz, Object paramObj, Class[] groups) {
        return getSearchContext(clazz, paramObj, null, groups, true);
    }

    /**
     * 获取HQL查询环境
     *
     * @param paramObj
     * @param groups
     * @return
     */
    public static SearchContext getHqlSearchContext(Object paramObj, Class[] groups) {
        return getHqlSearchContext(null, paramObj, groups);
    }

    /**
     * 获取HQL查询环境
     *
     * @param paramObj
     * @return
     */
    public static SearchContext getHqlSearchContext(Object paramObj) {
        return getHqlSearchContext(paramObj, null);
    }

    /**
     * 获取SQL查询环境
     *
     * @param clazz
     * @param paramObj
     * @param tableAlias
     * @param groups
     * @return
     */
    public static final SearchContext getSqlSearchContext(Class clazz, Object paramObj, String tableAlias, Class[] groups) {
        return getSearchContext(clazz, paramObj, tableAlias, groups, false);
    }

    /**
     * 获取SQL查询环境
     *
     * @param paramObj
     * @param tableAlias
     * @param groups
     * @return
     */
    public static final SearchContext getSqlSearchContext(Object paramObj, String tableAlias, Class[] groups) {
        return getSqlSearchContext(null, paramObj, tableAlias, groups);
    }

    /**
     * 获取SQL查询环境
     *
     * @param paramObj
     * @param groups
     * @return
     */
    public static final SearchContext getSqlSearchContext(Object paramObj, Class[] groups) {
        return getSqlSearchContext(paramObj, null, groups);
    }

    /**
     * 获取SQL查询环境
     *
     * @param paramObj
     * @return
     */
    public static final SearchContext getSqlSearchContext(Object paramObj, String tableAlias) {
        return getSqlSearchContext(paramObj, tableAlias, null);
    }

    /**
     * 获取SQL查询环境
     *
     * @param paramObj
     * @return
     */
    public static final SearchContext getSqlSearchContext(Object paramObj) {
        return getSqlSearchContext(paramObj, null, null);
    }

    /**
     * 获取SearchContext对象
     *
     * @param clazz
     * @param paramObj
     * @param tableAlias
     * @param groups
     * @return
     */
    private static final SearchContext getSearchContext(Class clazz, Object paramObj, String tableAlias, Class[] groups, boolean isHql) {
        //参数默认值处理
        Map<String, Object> param = toParamMap(paramObj);
        clazz = clazz == null ? paramObj.getClass() : clazz;
        //默认分组
        if (groups == null) groups = new Class[]{DefaultSearchGroup.class};
        //如果clazz为空，返回空环境
        if (SearchSupportUtil.isEmpty(param)) return new SearchContext();
        //获取ClassItem对象
        ClassItem classItem = SearchParser.getClassItem(clazz, searchStrategy);
        //定义一个SearchContext对象
        SearchContext searchContext = new SearchContext();
        //获取Search匹配和Order匹配
        Set<SearchItem> searchItems = classItem.getSearchItems(groups);
        List<OrderItem> orderItems = classItem.getOrderItems(groups);
        for (SearchItem item : searchItems) {
            //获取参数名
            String paramName = item.getParamName();
            //判断是否有此参数，动态的生成查询条件语句
            if (param.containsKey(paramName)) {
                if (isHql) {
                    //获取查询语句
                    String itemSearchString = item.getRealSearchString(tableAlias, fieldHandler.getFieldName(item.getField()));
                    //添加查询条件
                    searchContext.addSearchQuery(itemSearchString, paramName, param.get(paramName));
                } else {
                    //获取查询语句
                    String itemSearchString = item.getRealSearchString(tableAlias, fieldHandler.getDataFieldName(item.getField()));
                    //添加查询条件
                    searchContext.addSearchQuery(itemSearchString, paramName, param.get(paramName));
                }
            }
        }
        for (OrderItem item : orderItems) {
            if (isHql) {
                //获取排序语句
                String itemOrderString = item.getRealOrderString(tableAlias, fieldHandler.getFieldName(item.getField()));
                //添加一个排序条件
                searchContext.addOrderString(itemOrderString);
            } else {
                //获取排序语句
                String itemOrderString = item.getRealOrderString(tableAlias, fieldHandler.getDataFieldName(item.getField()));
                //添加一个排序条件
                searchContext.addOrderString(itemOrderString);
            }
        }
        return searchContext;
    }

    /**
     * 将对象转换成Map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> toParamMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) return map;
        Class clazz = obj.getClass();
        if (Map.class.isAssignableFrom(clazz)) {
            return (Map<String, Object>) obj;
        } else {
            //记录在子类中扫描到的字段，父类中被覆盖字段不重复扫描
            Set<String> fieldNames = new HashSet<String>();
            Object fieldValue;
            Class cur = clazz;
            while (true) {
                //Object顶级类不扫描
                if (cur == null || Object.class.equals(clazz)) break;
                //获取当前扫描类声明的属性
                Field[] fields = cur.getDeclaredFields();
                for (Field field : fields) {
                    int modifiers = field.getModifiers();   //修饰符
                    if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) continue;
                    String fieldName = field.getName();     //属性名
                    if (fieldNames.contains(fieldName)) continue;
                    fieldNames.add(fieldName);  //记录
                    Class fieldType = field.getType();
                    try {
                        if (Modifier.isProtected(modifiers) || Modifier.isPublic(modifiers)) {
                            fieldValue = field.get(obj);
                            if (fieldValue != null) map.put(fieldName, field.get(obj)); //可继承属性
                        } else if (fieldName.toLowerCase().startsWith("is")
                                && (boolean.class.equals(fieldType) || Boolean.class.equals(fieldType))) {
                            fieldValue = cur.getMethod(fieldName).invoke(obj);
                            if (fieldValue != null) {
                                map.put(fieldName, fieldValue);
                            }
                        } else {
                            //私有属性
                            fieldValue = new PropertyDescriptor(fieldName, cur).getReadMethod().invoke(obj);
                            if (fieldValue != null) map.put(fieldName, fieldValue);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                cur = cur.getSuperclass();
            }
        }
        return map;
    }

    /**
     * setter
     */
    public void setFieldHandler(FieldHandler fieldHandler) {
        SearchUtil.fieldHandler = fieldHandler;
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        SearchUtil.searchStrategy = searchStrategy;
    }
}
