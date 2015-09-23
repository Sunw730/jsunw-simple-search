/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.support;

import com.sunw.search.bean.QueryTemplate;
import com.sunw.search.bean.Search;
import com.sunw.search.util.support.SearchSupportUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * [Search匹配]
 *
 * @ProjectName: [sunw-web]
 * @Author: [Sun Wang]
 * @CreateDate: [15/8/25]
 * @Update: [说明本次修改内容] BY [Sunw] [15/8/25]
 * @Version: [v1.0]
 */
public class SearchItem {

    private final Search search;    //Search标识
    private final Field field;      //Field
    private final boolean requireParam; //是否需要参数
    private final String paramName; //命名参数名称
    private final String searchStringTemplate;  //查询语句模板
    private final Set<Class> groups = new HashSet<Class>(); //包含的分组信息

    public SearchItem(Search search, Field field) {
        this.search = search;
        this.field = field;
        //是否需要参数
        this.requireParam = !search.type().single();
        //初始化参数名
        String paramName = null;
        if(requireParam) {
            paramName = field.getName();
            //suffix
            if(!SearchSupportUtil.isEmpty(search.suffix())) {
                paramName += QueryTemplate.LINE + search.suffix();
            }
            SearchSupportUtil.assertNotEmpty(paramName, "获取参数名称失败");
        }
        this.paramName = paramName;
        //初始化查询语句模板
        String template = QueryTemplate.getQueryStringTemplate(requireParam);
        this.searchStringTemplate = template.replace(QueryTemplate.QUERY_TYPE, search.type().value())
                .replace(QueryTemplate.PARAM_NAME, paramName);
        //初始化分组信息
        if(!SearchSupportUtil.isEmpty(search.group())) groups.addAll(Arrays.asList(search.group()));
    }

    /**
     * 是否包含有指定的分组
     * @param groups
     * @return
     */
    public boolean containsGroup(Class[] groups) {
        if(SearchSupportUtil.isEmpty(groups)) return false;
        Set<Class> set = new HashSet<Class>();
        set.addAll(this.groups);
        set.addAll(Arrays.asList(groups));
        if(set.size() < this.groups.size() + groups.length) return true;
        return false;
    }

    /**
     * 获取真实查询语句
     * @param alias
     * @param dataFieldName
     * @return
     */
    public String getRealSearchString(String alias, String dataFieldName) {
        SearchSupportUtil.assertNotEmpty(dataFieldName, "请指定数据库字段名");
        String searchStringTemplate = this.searchStringTemplate;
        String aliasTmpl = QueryTemplate.TABLE_ALIAS;
        if(SearchSupportUtil.isEmpty(alias)) {
            aliasTmpl = aliasTmpl + QueryTemplate.DOT;
            alias = "";
        }
        return searchStringTemplate.replace(aliasTmpl, alias.trim()).replace(QueryTemplate.DATA_FIELD_NAME, dataFieldName.trim());
    }

    /**
     * Getter Methods
     * @return
     */
    public Search getSearch() {
        return search;
    }

    public Field getField() {
        return field;
    }

    public boolean isRequireParam() {
        return requireParam;
    }

    public String getParamName() {
        return paramName;
    }

    public String getSearchStringTemplate() {
        return searchStringTemplate;
    }
}
