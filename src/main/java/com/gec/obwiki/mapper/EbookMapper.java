package com.gec.obwiki.mapper;

import com.gec.obwiki.entity.Ebook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.obwiki.resp.EbookQueryResp;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ebook(电子书)】的数据库操作Mapper
* @createDate 2025-06-24 09:56:56
* @Entity com.gec.obwiki.entity.Ebook
*/
public interface EbookMapper extends BaseMapper<Ebook> {

    void increaseViewCount(Long ebookId);

    List<EbookQueryResp> all();
}




