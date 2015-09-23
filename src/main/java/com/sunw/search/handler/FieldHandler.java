/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.handler;

import java.lang.reflect.Field;

/**
 * [字段处理接口]
 *
 * @ProjectName: [sunw-simple-search]
 * @Author: [Sun Wang]
 * @CreateDate: [15/9/23]
 * @Update: [说明本次修改内容] BY [Sunw] [15/9/23]
 * @Version: [v1.0]
 */
public interface FieldHandler {

    /**
     * 获取数据库字段名，用来生成sql
     * @param field
     * @return
     */
    String getDataFieldName(Field field);

    /**
     * 获取属性名，用来生成hql
     * @param field
     * @return
     */
    String getFieldName(Field field);

}
