/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.bean;

/**
 * [排序类型]
 *
 * @ProjectName: [sunw-web]
 * @Author: [Sun Wang]
 * @CreateDate: [15/8/23]
 * @Update: [说明本次修改内容] BY [Sunw] [15/8/23]
 * @Version: [v1.0]
 */
public enum OrderType {

    ASC(" asc "),   //升序
    DESC(" desc "); //降序

    /**
     *
     * @param value
     * @param single 是否是一元运算符
     */
    private String value;
    OrderType(String value) {
        this.value = value;
    }
    public String value() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
