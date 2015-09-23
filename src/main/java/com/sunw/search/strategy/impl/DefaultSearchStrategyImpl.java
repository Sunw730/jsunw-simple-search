/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.strategy.impl;

import com.sunw.search.strategy.SearchStrategy;

import java.lang.reflect.Field;

/**
 * [默认的扫描策略]
 *
 * @ProjectName: [sunw-web]
 * @Author: [Sun Wang]
 * @CreateDate: [2015/8/26 11:34]
 * @Update: [说明本次修改内容] BY [SunWang] [2015/8/26]
 * @Version: [v1.0]
 */
public class DefaultSearchStrategyImpl implements SearchStrategy {

    @Override
    public boolean shouldParse(Class clazz) {
        return true;
    }

    @Override
    public boolean shouldParse(Field field) {
        return true;
    }

}
