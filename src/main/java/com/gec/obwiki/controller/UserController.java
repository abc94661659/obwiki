package com.gec.obwiki.controller;

import com.alibaba.fastjson.JSONObject;
import com.gec.obwiki.req.UserLoginReq;
import com.gec.obwiki.req.UserQueryReq;
import com.gec.obwiki.req.UserResetPasswordReq;
import com.gec.obwiki.req.UserSaveReq;
import com.gec.obwiki.resp.CommonResp;
import com.gec.obwiki.resp.PageResp;
import com.gec.obwiki.resp.UserLoginResp;
import com.gec.obwiki.resp.UserQueryResp;
import com.gec.obwiki.service.UserService;
import com.gec.obwiki.utils.BCryptUtils;
import com.gec.obwiki.utils.SnowFlake;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/api/user")
@ApiOperation("用户管理接口")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/list")
    @ApiOperation("分页查询")
    public CommonResp list(@Valid UserQueryReq req) {
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp<>(true, "查询成功", null);
        PageResp<UserQueryResp> pageResp = userService.listByname(req);
        resp.setContent(pageResp);
        return resp;
    }

    @ApiOperation("保存用户")
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody UserSaveReq req) {
        req.setPassword(BCryptUtils.encode(req.getPassword()));
        CommonResp resp = new CommonResp<>(true, "成功", null);
        userService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除用户")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>(true, "删除成功", null);
        userService.delete(id);
        return resp;
    }

    @PostMapping("resetPassword")
    @ApiOperation("修改密码")
    public CommonResp resetPassword(@Valid @RequestBody UserResetPasswordReq req) {
        req.setPassword(BCryptUtils.encode(req.getPassword()));
        CommonResp resp = new CommonResp<>(true, "成功", null);
        userService.resetPassword(req);
        return resp;
    }

    @PostMapping("/login")
    public CommonResp login(@Valid @RequestBody UserLoginReq req) {
        CommonResp<UserLoginResp> resp = new CommonResp<>();
        UserLoginResp userLoginResp = userService.login(req);

        Long token = snowFlake.nextId();
        log.info("生成单点登录token：{}，并放入redis中", token);
        userLoginResp.setToken(token.toString());
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(userLoginResp), 3600 * 24, TimeUnit.SECONDS);

        resp.setContent(userLoginResp);
        return resp;
    }

    @GetMapping("/logout/{token}")
    public CommonResp logout(@PathVariable String token) {
        CommonResp resp = new CommonResp<>();
        redisTemplate.delete(token);
        log.info("从redis中删除token: {}", token);
        return resp;
    }
}
