package com.gec.obwiki.service;

import com.gec.obwiki.entity.Doc;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.obwiki.req.DocQueryReq;
import com.gec.obwiki.req.DocSaveReq;
import com.gec.obwiki.resp.DocQueryResp;
import com.gec.obwiki.resp.PageResp;

import java.util.List;

/**
* @author Administrator
* @description 针对表【doc】的数据库操作Service
* @createDate 2025-06-24 09:56:56
*/
public interface DocService extends IService<Doc> {

    List<DocQueryResp> all();

    PageResp<DocQueryResp> listByname(DocQueryReq req);

    void save(DocSaveReq req);

    void delete(List<Long> id);

    List<DocQueryResp> allbyEbookId(Long ebookId);

    void vote(Long id);

    void updateEbookInfo();
}
