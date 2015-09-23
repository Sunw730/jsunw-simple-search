/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.bean;


import com.sunw.search.group.DefaultSearchGroup;

import java.lang.annotation.*;

/**
 * [排序字段标识]
 *
 * @ProjectName: [sunw-web]
 * @Author: [Sun Wang]
 * @CreateDate: [15/8/23]
 * @Update: [说明本次修改内容] BY [Sunw] [15/8/23]
 * @Version: [v1.0]
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {

    OrderType type();   //排序类型

    int sort() default 0;   //排序优先级，值越小，优先级越大

    Class[] group() default DefaultSearchGroup.class; //排序分组

}
