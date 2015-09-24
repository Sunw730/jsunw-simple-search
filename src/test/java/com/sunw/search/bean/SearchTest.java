/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.bean;

import com.sunw.search.util.SearchUtil;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

/**
 * [简要说明此类的作用]
 *
 * @ProjectName: [sunw-simple-search]
 * @Author: [Sun Wang]
 * @CreateDate: [15/9/23]
 * @Update: [说明本次修改内容] BY [Sunw] [15/9/23]
 * @Version: [v1.0]
 */
public class SearchTest {

    @Test
    public void test() {
        ManUser man = new ManUser();
        man.setSex(1);
        man.setUserId(12345l);
        man.setUserName("Sunw");
        man.set$affSSS("$$$$$");
        man.set_adddW("______");
        man.setAge(44);
        man.setWorker(true);
//        Map<String, Object> map = SearchSupportUtil.toParamMap(man);
//        Set<String> keys = map.keySet();
//        for(String key : keys) {
//            System.out.println(key + ":  " +map.get(key));
//        }

        SearchContext context = SearchUtil.getSqlSearchContext(man);
        System.out.println(context.getQueryString());
        Map<String, Object> param = context.getParam();
        Set<String> keys = param.keySet();
        for(String key : keys) {
            System.out.println(key + ":  " + param.get(key));
        }

    }

}
