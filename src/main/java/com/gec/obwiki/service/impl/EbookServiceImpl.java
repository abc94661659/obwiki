package com.gec.obwiki.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.obwiki.entity.Ebook;
import com.gec.obwiki.mapper.EbookMapper;
import com.gec.obwiki.req.EbookQueryReq;
import com.gec.obwiki.req.EbookSaveReq;
import com.gec.obwiki.resp.EbookQueryResp;
import com.gec.obwiki.resp.PageResp;
import com.gec.obwiki.service.EbookService;
import com.gec.obwiki.utils.CopyUtil;
import com.gec.obwiki.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ebook(电子书)】的数据库操作Service实现
* @createDate 2025-06-24 09:56:56
*/
@Service
@Slf4j
public class EbookServiceImpl extends ServiceImpl<EbookMapper, Ebook> implements EbookService{
    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private EbookMapper ebookMapper;
    @Override
    public PageResp<EbookQueryResp> listByname(EbookQueryReq req) {

        QueryWrapper<Ebook> queryWrapper = new QueryWrapper<Ebook>();
        //第一个参数：该参数是一个布尔类型，只有该参数是true时，才将like条件拼接到sql中；本例中，如果name字段不为空，则拼接name字段的like查询条件；
        queryWrapper.like(StringUtils.isNotBlank(req.getName()),"name",req.getName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(req.getCategoryId2()),"category2_id",req.getCategoryId2());
        //创建分页对象
        Page<Ebook> page = new Page<>(req.getPage(),req.getSize());
        page = this.baseMapper.selectPage(page, queryWrapper);


        List<EbookQueryResp> resps = CopyUtil.copyList(page.getRecords(), EbookQueryResp.class);
        //创建返回对象
        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setList(resps);
        pageResp.setTotal(page.getTotal());
        log.info("总行数：{}",page.getTotal());
        log.info("总页数：{}",page.getPages());
        log.info("当前页：{}",page.getCurrent());

        return pageResp;
    }

    @Override
    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 雪花算法生成id
            long id = snowFlake.nextId();
            ebook.setId(id);
            this.baseMapper.insert(ebook);
        } else {
            // 更新
            this.baseMapper.updateById(ebook);
        }
    }

    @Override
    public void delete(Long id) {
        this.baseMapper.deleteById(id);
    }

    @Override
    public List<EbookQueryResp> all() {
        List<EbookQueryResp> list = ebookMapper.all();
        return list;
    }
}




