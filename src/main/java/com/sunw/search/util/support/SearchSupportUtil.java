/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.util.support;

import com.sunw.search.fault.SearchException;

import java.util.Collection;
import java.util.Map;

/**
 * [辅助工具类]
 *
 * @ProjectName: [sunw-simple-search]
 * @Author: [Sun Wang]
 * @CreateDate: [15/9/23]
 * @Update: [说明本次修改内容] BY [Sunw] [15/9/23]
 * @Version: [v1.0]
 */
public class SearchSupportUtil {


    /**
     * 判断是否为空对象
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(Object value) {
        if (value == null) return true;
        if (value instanceof CharSequence) {
            //字符类型
            return "".equals(value.toString().trim());
        } else if (value instanceof Collection) {
            //集合类型
            return ((Collection) value).isEmpty();
        } else if (value instanceof Object[]) {
            //数组类型
            return ((Object[]) value).length == 0;
        } else if (value instanceof Map) {
            //Map类型
            return ((Map) value).isEmpty();
        }
        return false;
    }


    /**
     * 非空断言
     *
     * @param value
     * @param msg
     */
    public static void assertNotEmpty(Object value, CharSequence msg) {
        if (isEmpty(value)) {
            throw new SearchException(isEmpty(msg) ? "对象不能为空" : msg);
        }
    }

}
