package com.gec.obwiki.req;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public  class UserSaveReq {
    // 主键
    private Long id;
    // 登录名
    private String loginName;
    // 昵称
    private String name;
    // 密码
    private String password;
    // 用户角色
    private String userRole;
}