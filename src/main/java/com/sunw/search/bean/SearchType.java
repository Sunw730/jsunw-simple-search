/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.bean;

/**
 * [查询条件匹配方式]
 *
 * @ProjectName: [sunw-web]
 * @Author: [Sun Wang]
 * @CreateDate: [15/8/23]
 * @Update: [说明本次修改内容] BY [Sunw] [15/8/23]
 * @Version: [v1.0]
 */
public enum SearchType {

    EQ(" = "),
    GE(" >= "),
    GT(" > "),
    LE(" <= "),
    LT(" < "),
    NE(" != "),
    LK(" like "),
    NN(" is not null ", true),
    NU(" is null ", true);

    private String value;   //匹配符
    private boolean single; //是否是一元运算符，默认false
    SearchType(String value) {
        this(value, false);
    }
    SearchType(String value, boolean single) {
        this.value = value;
        this.single = single;
    }

    public String value() {
        return value;
    }

    public boolean single() {
        return single;
    }

    @Override
    public String toString() {
        return this.value;
    }
}

