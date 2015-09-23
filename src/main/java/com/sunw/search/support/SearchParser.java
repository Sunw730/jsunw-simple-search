/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.support;

import com.sunw.search.bean.Order;
import com.sunw.search.bean.Search;
import com.sunw.search.bean.Searches;
import com.sunw.search.strategy.SearchStrategy;
import com.sunw.search.util.support.SearchSupportUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * [类解析工具]
 *
 * @ProjectName: [sunw-web]
 * @Author: [Sun Wang]
 * @CreateDate: [2015/8/26 16:58]
 * @Update: [说明本次修改内容] BY [SunWang] [2015/8/26]
 * @Version: [v1.0]
 */
public class SearchParser {

    private static final Map<Class, ClassItem> search_mapper = new ConcurrentHashMap<Class, ClassItem>();

    /**
     * 初始化clazz
     *
     * @param clazz
     * @param searchStrategy
     */
    public static void initializeSearchClass(Class clazz, SearchStrategy searchStrategy) {
        SearchSupportUtil.assertNotEmpty(clazz, "请指定Model类型");
        if (search_mapper.containsKey(clazz)) return;
        //SearchStrategy
        if (searchStrategy.shouldParse(clazz)) {
            search_mapper.put(clazz, new ClassItem(clazz));
        }
    }

    /**
     *
     * @param clazz
     * @param searchStrategy
     * @return
     */
    public static ClassItem getClassItem(Class clazz, SearchStrategy searchStrategy) {
        ClassItem classItem = newInstance(clazz, searchStrategy);
        //非空校验
        SearchSupportUtil.assertNotEmpty(classItem, "获取Search上下文失败");
        return classItem;
    }

    /**
     * 获取Model Bean所有属性，包括自身声明和父类声明的属性（static|final除外）
     *
     * @param clazz
     * @param searchStrategy
     * @return
     */
    private static ClassItem newInstance(Class clazz, SearchStrategy searchStrategy) {
        //策略验证
        if (!searchStrategy.shouldParse(clazz)) return null;
        //记录在子类中扫描到的字段，父类将不再扫描
        Set<String> fieldNames = new HashSet<String>();
        //创建实例
        ClassItem classItem = new ClassItem(clazz);
        //当前扫描的class
        Class cur = clazz;
        while (true) {
            //判断是否为顶级类
            if (cur == null || cur == Object.class) break;
            //获取当前扫描类声明的属性
            Field[] array = cur.getDeclaredFields();
            if (!SearchSupportUtil.isEmpty(array)) {
                for (Field item : array) {
                    //strategy
                    if (!searchStrategy.shouldParse(item)) continue;
                    //属性名
                    String fieldName = item.getName();
                    //判断子类中是否覆盖过此属性
                    if (fieldNames.contains(fieldName)) {
                        continue;
                    }
                    fieldNames.add(fieldName);
                    //annotation
                    Search search = item.getAnnotation(Search.class);
                    Searches searches = item.getAnnotation(Searches.class);
                    Order order = item.getAnnotation(Order.class);
                    if (SearchSupportUtil.isEmpty(search) && SearchSupportUtil.isEmpty(order) && (SearchSupportUtil.isEmpty(searches) || SearchSupportUtil.isEmpty(searches.searches()))) {
                        continue;
                    }
                    //Modifiers
                    int modifiers = item.getModifiers();
                    if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
                        continue;
                    }
                    //add field
                    if (search != null) {
                        classItem.getSearchItems().add(new SearchItem(search, item));
                    }
                    if (searches != null) {
                        Search[] sArray = searches.searches();
                        if (!SearchSupportUtil.isEmpty(sArray)) {
                            for (Search s : sArray) {
                                classItem.getSearchItems().add(new SearchItem(s, item));
                            }
                        }
                    }
                    if (order != null) {
                        classItem.getOrderItems().add(new OrderItem(order, item));
                    }
                }
            }
            cur = cur.getSuperclass();  //开始扫描父类
        }
        //sort
        Collections.sort(classItem.getOrderItems());
        return classItem;
    }


    public static void main(String[] args) {
        System.out.println(Integer.valueOf('a'));
        System.out.println(Integer.valueOf('z'));
        System.out.println(Integer.valueOf('A'));
        System.out.println(Integer.valueOf('Z'));
    }

}
