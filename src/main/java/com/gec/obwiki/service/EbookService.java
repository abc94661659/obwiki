package com.gec.obwiki.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.obwiki.entity.Ebook;
import com.gec.obwiki.req.EbookQueryReq;
import com.gec.obwiki.req.EbookSaveReq;
import com.gec.obwiki.resp.EbookQueryResp;
import com.gec.obwiki.resp.PageResp;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ebook(电子书)】的数据库操作Service
* @createDate 2025-06-24 09:56:56
*/
public interface EbookService extends IService<Ebook> {


    PageResp<EbookQueryResp> listByname(EbookQueryReq req);

    void save(EbookSaveReq req);

    void delete(Long id);

    List<EbookQueryResp> all();
}
