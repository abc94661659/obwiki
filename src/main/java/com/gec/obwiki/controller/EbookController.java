package com.gec.obwiki.controller;

import com.gec.obwiki.resp.CommonResp;
import com.gec.obwiki.req.EbookQueryReq;
import com.gec.obwiki.req.EbookSaveReq;
import com.gec.obwiki.resp.EbookQueryResp;
import com.gec.obwiki.resp.PageResp;
import com.gec.obwiki.service.EbookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 电子书 前端控制器
 * </p>
 *
 * @author gec
 * @since 2025-06-24
 */
@RestController
@RequestMapping("/api/ebook")
@Api("ebook模块")
@Slf4j
public class EbookController {
    @Autowired
    private EbookService ebookService;

    /**
     * 获取数据库中的所有书
     * @param req
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询所有电子书")
    public CommonResp list(@Valid EbookQueryReq req){
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>(true,"查询成功",null);
        PageResp<EbookQueryResp> pageResp = ebookService.listByname(req);
        resp.setContent(pageResp);
        return resp;
    }

    @PostMapping("/save")
    @ApiOperation("保存电子书")
    public CommonResp save(@Valid @RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>(true,"成功",null);
        ebookService.save(req);
        return resp;
    }

    @DeleteMapping("/remove/{id}")
    @ApiOperation("删除电子书")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }

}
