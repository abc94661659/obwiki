package com.gec.obwiki.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.obwiki.entity.Category;
import com.gec.obwiki.req.CategoryQueryReq;
import com.gec.obwiki.req.CategorySaveReq;
import com.gec.obwiki.resp.CategoryQueryResp;
import com.gec.obwiki.resp.PageResp;

import java.util.List;

/**
* @author Administrator
* @description 针对表【category】的数据库操作Service
* @createDate 2025-06-24 09:56:56
*/
public interface CategoryService extends IService<Category> {

    PageResp<CategoryQueryResp> listByname(CategoryQueryReq req);
    void save(CategorySaveReq req);

    void delete(Long id);

    List<CategoryQueryResp> all();
}
