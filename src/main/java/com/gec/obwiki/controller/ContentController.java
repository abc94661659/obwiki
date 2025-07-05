package com.gec.obwiki.controller;

import com.gec.obwiki.resp.CommonResp;
import com.gec.obwiki.service.ContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content")
@Api
public class ContentController {
    @Autowired
    private ContentService contentService;

    @GetMapping("/findContent/{id}")
    @ApiOperation("查询内容")
    public CommonResp findContent(@PathVariable Long id) {
        CommonResp<String> resp = new CommonResp<>();
        String content = contentService.findContent(id);
        resp.setContent(content);
        return resp;
    }
}