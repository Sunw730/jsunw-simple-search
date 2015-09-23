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
public class User {

    @Search(type = SearchType.EQ)
    @Order(type = OrderType.ASC)
    private Long userId;
    @Search(type = SearchType.LK)
    public String userName;
    @Searches(searches = {@Search(type = SearchType.GE, suffix = "ge"), @Search(type = SearchType.LE, suffix = "le")})
    private int age;
    private boolean isWorker;
    @Order(type = OrderType.DESC)
    private String _adddW;
    private String $affSSS;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isWorker() {
        return isWorker;
    }

    public void setWorker(boolean isWorker) {
        this.isWorker = isWorker;
    }

    public String get_adddW() {
        return _adddW;
    }

    public void set_adddW(String _adddW) {
        this._adddW = _adddW;
    }

    public String get$affSSS() {
        return $affSSS;
    }

    public void set$affSSS(String $affSSS) {
        this.$affSSS = $affSSS;
    }
}
