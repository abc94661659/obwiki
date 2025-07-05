package com.gec.obwiki.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.obwiki.entity.User;
import com.gec.obwiki.exception.BusinessException;
import com.gec.obwiki.exception.BusinessExceptionCode;
import com.gec.obwiki.mapper.UserMapper;
import com.gec.obwiki.req.UserLoginReq;
import com.gec.obwiki.req.UserQueryReq;
import com.gec.obwiki.req.UserResetPasswordReq;
import com.gec.obwiki.req.UserSaveReq;
import com.gec.obwiki.resp.PageResp;
import com.gec.obwiki.resp.UserLoginResp;
import com.gec.obwiki.resp.UserQueryResp;
import com.gec.obwiki.service.UserService;
import com.gec.obwiki.utils.BCryptUtils;
import com.gec.obwiki.utils.CopyUtil;
import com.gec.obwiki.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2025-06-24 09:56:56
 */
@Service

public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private SnowFlake snowFlake;
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public PageResp<UserQueryResp> listByname(UserQueryReq req) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.like(StringUtils.isNotBlank(req.getName()), "name", req.getName());
        queryWrapper.or().like(StringUtils.isNotBlank(req.getLoginName()), "login_name", req.getLoginName());
        Page<User> page = new Page<>(req.getPage(), req.getSize());
        page = this.baseMapper.selectPage(page, queryWrapper);

        List<UserQueryResp> resps = CopyUtil.copyList(page.getRecords(), UserQueryResp.class);
        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setList(resps);
        pageResp.setTotal(page.getTotal());
        return pageResp;
    }

    @Override
    public void save(UserSaveReq req) {
        User user = CopyUtil.copy(req, User.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            User one = this.baseMapper.selectOne(new QueryWrapper<User>().eq("login_name", req.getLoginName()));
            if (ObjectUtils.isEmpty(one)) {
                long id = snowFlake.nextId();
                user.setId(id);
                this.baseMapper.insert(user);
            } else {
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {
            user.setLoginName(null);
            user.setPassword(null);
            this.baseMapper.updateById(user);
        }
    }

    @Override
    public void delete(Long id) {
        this.baseMapper.deleteById(id);
    }

    @Override
    public void resetPassword(UserResetPasswordReq req) {
        User user = CopyUtil.copy(req, User.class);
        this.baseMapper.updateById(user);
    }

    @Override
    public UserLoginResp login(UserLoginReq req) {
        User user = this.baseMapper.selectOne(new QueryWrapper<User>().eq("login_name", req.getLoginName()));
        if (ObjectUtils.isEmpty(user)) {
            LOG.info("用户名不存在, {}", req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (BCryptUtils.matches(req.getPassword(), user.getPassword())) {
                UserLoginResp userLoginResp = CopyUtil.copy(user, UserLoginResp.class);
                userLoginResp.setUserRole(user.getUserRole());
                return userLoginResp;
            } else {
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", req.getPassword(), user.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }
}




