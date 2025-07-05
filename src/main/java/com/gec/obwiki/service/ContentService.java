package com.gec.obwiki.service;

import com.gec.obwiki.entity.Content;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.obwiki.resp.EbookQueryResp;

import java.util.List;

/**
* @author Administrator
* @description 针对表【content】的数据库操作Service
* @createDate 2025-06-24 09:56:56
*/
public interface ContentService extends IService<Content> {

    String findContent(Long id);


    List<EbookQueryResp> all();
}
