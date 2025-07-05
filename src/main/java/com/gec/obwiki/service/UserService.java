package com.gec.obwiki.service;

import com.gec.obwiki.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.obwiki.req.UserLoginReq;
import com.gec.obwiki.req.UserQueryReq;
import com.gec.obwiki.req.UserResetPasswordReq;
import com.gec.obwiki.req.UserSaveReq;
import com.gec.obwiki.resp.PageResp;
import com.gec.obwiki.resp.UserLoginResp;
import com.gec.obwiki.resp.UserQueryResp;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service
* @createDate 2025-06-24 09:56:56
*/
public interface UserService extends IService<User> {

    PageResp<UserQueryResp> listByname(UserQueryReq req);

    void save(UserSaveReq req);

    void delete(Long id);

    void resetPassword(UserResetPasswordReq req);

    UserLoginResp login(UserLoginReq req);
}
