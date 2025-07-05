package com.gec.obwiki.exception;

public enum BusinessExceptionCode {
    USER_LOGIN_NAME_EXIST("登录名已存在"),
    LOGIN_USER_ERROR("用户名不存在或密码错误"),
    VOTE_REPEAT("您已点赞过") ;
    //定义私有变量 desc 描述
    private String desc;
    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    //get set 获取和设置异常代码的描述字符串
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
