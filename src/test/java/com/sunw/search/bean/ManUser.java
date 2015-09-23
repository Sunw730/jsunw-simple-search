/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.bean;

/**
 * [简要说明此类的作用]
 *
 * @ProjectName: [sunw-simple-search]
 * @Author: [Sun Wang]
 * @CreateDate: [15/9/23]
 * @Update: [说明本次修改内容] BY [Sunw] [15/9/23]
 * @Version: [v1.0]
 */
public class ManUser extends User {

    @Search(type = SearchType.EQ)
    private int sex;
    private Long userId;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
