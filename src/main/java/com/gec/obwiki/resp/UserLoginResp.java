package com.gec.obwiki.resp;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserLoginResp {
    private Long id;

    private String loginName;

    private String name;

    private String token;
    // 用户角色
    private String userRole;
}