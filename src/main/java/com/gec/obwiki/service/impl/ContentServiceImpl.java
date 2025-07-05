package com.gec.obwiki.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.obwiki.entity.Content;
import com.gec.obwiki.mapper.ContentMapper;
import com.gec.obwiki.mapper.DocMapper;
import com.gec.obwiki.resp.EbookQueryResp;
import com.gec.obwiki.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【content】的数据库操作Service实现
* @createDate 2025-06-24 09:56:56
*/
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService{
    @Autowired
    DocMapper docMapper;

    @Override
    public String findContent(Long id) {
        Content content = this.baseMapper.selectById(id);
        docMapper.increaseViewCount(id);
        if(content !=null){
            return content.getContent();
        }
        return null;
    }

    @Override
    public List<EbookQueryResp> all() {
        List<EbookQueryResp> list = docMapper.all();
        return list;
    }
}




