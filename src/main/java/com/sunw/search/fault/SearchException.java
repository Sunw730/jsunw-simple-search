/**
 * Copyright © 2015 [Sun Wang]. All Rights Reserved.
 */
package com.sunw.search.fault;

/**
 * [生成Search查询语句异常]
 *
 * @ProjectName: [sunw-web]
 * @Author: [Sun Wang]
 * @CreateDate: [15/8/23]
 * @Update: [说明本次修改内容] BY [Sunw] [15/8/23]
 * @Version: [v1.0]
 */
public class SearchException extends RuntimeException {

    //异常消息
    private CharSequence msg = "";
    //绑定一个对象，可以是实际异常对象
    private Object data = null;

    public SearchException() {
        this(null);
    }

    public SearchException(CharSequence msg) {
        this(msg, null);
    }

    public SearchException(CharSequence msg, Object data) {
        if(msg != null) this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg == null ? "" : msg.toString();
    }

    public Object getData() {
        return data;
    }

    @Override
    public String getMessage() {
        if(this.getData() != null && this.getData() instanceof SearchException) {
            return ((SearchException) this.getData()).getMessage();
        } else {
            return this.getMsg();
        }
    }

}
