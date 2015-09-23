/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.strategy;

import java.lang.reflect.Field;

/**
 * [类扫描策略，用于加快批量扫描速度]
 *
 * @ProjectName: [sunw-web]
 * @Author: [Sun Wang]
 * @CreateDate: [2015/8/26 11:23]
 * @Update: [说明本次修改内容] BY [SunWang] [2015/8/26]
 * @Version: [v1.0]
 */
public interface SearchStrategy {

    /**
     * 是否扫描clazz类
     * @param clazz
     * @return
     */
    boolean shouldParse(Class clazz);

    /**
     * 是否扫描field属性
     * @param field
     * @return
     */
    boolean shouldParse(Field field);

}
