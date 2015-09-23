/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.bean;

import java.lang.annotation.*;

/**
 * [多条件匹配]
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
public @interface Searches {

    Search[] searches();

}
