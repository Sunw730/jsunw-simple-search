/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.bean;

import com.sunw.search.group.DefaultSearchGroup;

import java.lang.annotation.*;

/**
 * [查询条件字段标识]
 *
 * @ProjectName: [sunw-web]
 * @Author: [Sun Wang]
 * @CreateDate: [15/8/23]
 * @Update: [说明本次修改内容] BY [Sunw] [15/8/23]
 * @Version: [v1.0]
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Search {

    SearchType type();  //匹配类型

    /**
     * 一个字段多个二元匹配条件时必须为每一个二元匹配条件指定参数别名
     * 别名会以[ fieldname + "_" + alias ]的格式加入到命名参数名中
     * @return
     */
    String suffix() default "";  //

    Class[] group() default DefaultSearchGroup.class; //匹配分组

}
