/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.handler.impl;

import com.sunw.search.handler.FieldHandler;

import java.lang.reflect.Field;

/**
 * [简要说明此类的作用]
 *
 * @ProjectName: [sunw-simple-search]
 * @Author: [Sun Wang]
 * @CreateDate: [15/9/23]
 * @Update: [说明本次修改内容] BY [Sunw] [15/9/23]
 * @Version: [v1.0]
 */
public class DefaultFieldHandlerImpl implements FieldHandler {
    @Override
    public String getDataFieldName(Field field) {
        return field.getName();
    }

    @Override
    public String getFieldName(Field field) {
        return field.getName();
    }
}
