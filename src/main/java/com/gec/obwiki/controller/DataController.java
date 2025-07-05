package com.gec.obwiki.controller;

import com.gec.obwiki.resp.CategoryQueryResp;
import com.gec.obwiki.resp.CommonResp;
import com.gec.obwiki.resp.DocQueryResp;
import com.gec.obwiki.resp.EbookQueryResp;
import com.gec.obwiki.service.CategoryService;
import com.gec.obwiki.service.ContentService;
import com.gec.obwiki.service.DocService;
import com.gec.obwiki.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private EbookService ebookService;
    @Autowired
    private ContentService contentService;
    @Autowired
    private DocService docService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getBackendData")
    public CommonResp getBackendData() {
        CommonResp<Map<String, Object>> resp = new CommonResp<>();
        Map<String, Object> dataMap = new HashMap<>();

        // 获取电子书数据
        List<EbookQueryResp> ebooks = ebookService.all();
        dataMap.put("ebooks", ebooks);

        List<EbookQueryResp> contents = contentService.all();
        dataMap.put("contents", contents);
        // 获取文档数据
        List<DocQueryResp> docs = docService.all();
        dataMap.put("docs", docs);

        // 获取分类数据
        List<CategoryQueryResp> categories = categoryService.all();
        dataMap.put("categories", categories);

        resp.setContent(dataMap);
        return resp;
    }
}