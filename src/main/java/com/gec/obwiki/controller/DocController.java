package com.gec.obwiki.controller;

import com.gec.obwiki.req.DocQueryReq;
import com.gec.obwiki.req.DocSaveReq;
import com.gec.obwiki.resp.CommonResp;
import com.gec.obwiki.resp.DocQueryResp;
import com.gec.obwiki.resp.PageResp;
import com.gec.obwiki.service.DocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gec
 * @since 2025-06-24
 */
@RestController
@RequestMapping("/api/doc")
@Api("文档管理")
@Slf4j
public class DocController {
    @Autowired
    private DocService docService;

    @GetMapping("/all")
    @ApiOperation("查询所有文章")
    public CommonResp all(){
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>(true,"查询成功",null);

        List<DocQueryResp> list = docService.all();
        resp.setContent(list);
        return resp;
    }
    @GetMapping("/list")
    @ApiOperation("查询单个文章")
    public CommonResp list(@Valid DocQueryReq req) {
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>(true,"查询成功",null);

        PageResp<DocQueryResp> pageResp = docService.listByname(req);
        resp.setContent(pageResp);

        return resp;
    }

    @PostMapping("/save")
    @ApiOperation("保存文章")
    public CommonResp save(@Valid @RequestBody DocSaveReq req) {
        log.info("接收到的 DocSaveReq 对象: {}", req); // 添加日志确认接收到的数据
        CommonResp resp = new CommonResp<>(true,"成功",null);
        docService.save(req);
        return resp;
    }

    // @DeleteMapping("/delete/{id}")
    // @ApiOperation("删除文章")
    // public CommonResp delete(@PathVariable Long id) {
    //     CommonResp resp = new CommonResp<>(true,"删除成功",null);
    //     docService.delete(id);
    //     return resp;
    // }

    @GetMapping("/remove")
    @ApiOperation("批量删除文章")
    public CommonResp delete(@RequestParam("ids") List<Long> ids) {
        CommonResp resp = new CommonResp<>(true,"删除成功",null);
        log.info("Received ids: {}", ids);
        docService.delete(ids);
        return resp;
    }

    @GetMapping("/all/{ebookId}")
    @ApiOperation("查询某个电子书下的所有文章")
    public CommonResp allByEbookId(@PathVariable Long ebookId) {
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> list = docService.allbyEbookId(ebookId);
        resp.setContent(list);
        return resp;
    }

    // 点赞
    @GetMapping("/vote/{id}")
    @ApiOperation("点赞")
    public CommonResp vote(@PathVariable Long id) {
        CommonResp commonResp = new CommonResp();
        docService.vote(id);
        return commonResp;
    }
}
