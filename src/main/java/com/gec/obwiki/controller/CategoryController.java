package com.gec.obwiki.controller;

import com.gec.obwiki.req.CategoryQueryReq;
import com.gec.obwiki.req.CategorySaveReq;
import com.gec.obwiki.resp.CategoryQueryResp;
import com.gec.obwiki.resp.CommonResp;
import com.gec.obwiki.resp.PageResp;
import com.gec.obwiki.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@Api("分类模块")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    @ApiOperation("分页查询")
    public CommonResp list(@Valid CategoryQueryReq req) {
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>(true,"查询成功",null);

        PageResp<CategoryQueryResp> pageResp = categoryService.listByname(req);
        resp.setContent(pageResp);

        return resp;
    }

    @PostMapping("/save")
    @ApiOperation("保存分类")
    public CommonResp save(@Valid @RequestBody CategorySaveReq req) {
        CommonResp resp = new CommonResp<>(true,"成功",null);
        categoryService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除分类")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>(true,"删除成功",null);
        categoryService.delete(id);
        return resp;
    }

    @GetMapping("/all")
    @ApiOperation("获取所有分类")
    public CommonResp all() {
        CommonResp<List<CategoryQueryResp>> resp = new CommonResp<>(true,"查询成功",null);
        List<CategoryQueryResp> list = categoryService.all();
        resp.setContent(list);
        return resp;
    }
}