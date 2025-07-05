package com.gec.obwiki.mapper;

import com.gec.obwiki.entity.Doc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.obwiki.resp.EbookQueryResp;

import java.util.List;

/**
* @author Administrator
* @description 针对表【doc】的数据库操作Mapper
* @createDate 2025-06-24 09:56:56
* @Entity com.gec.obwiki.entity.Doc
*/
public interface DocMapper extends BaseMapper<Doc> {

    void increaseViewCount(Long id);

    void increaseVoteCount(Long id);

    void updateEbookInfo();

    List<EbookQueryResp> all();
}




