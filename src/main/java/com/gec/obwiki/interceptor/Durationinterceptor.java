package com.gec.obwiki.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
  * 拦截器：Spring框架特有的，常用于登录校验，权限校验，请求日志打印 /login
  */
 @Component
 public class Durationinterceptor implements HandlerInterceptor {

     private static final Logger LOG = LoggerFactory.getLogger(Durationinterceptor.class);

     // 执行控制层方法之前执行
     @Override
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         // 打印请求信息
         LOG.info("------------- LogInterceptor 开始 -------------");
         LOG.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
         LOG.info("远程地址: {}", request.getRemoteAddr());

         long startTime = System.currentTimeMillis();
         request.setAttribute("requestStartTime", startTime);
         return true;
     }

     // 执行控制层方法之后执行
     @Override
     public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
         long startTime = (Long) request.getAttribute("requestStartTime");
         LOG.info("------------- Durationinterceptor 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
     }
 }