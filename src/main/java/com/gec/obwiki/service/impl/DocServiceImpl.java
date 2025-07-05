package com.gec.obwiki.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.obwiki.entity.Content;
import com.gec.obwiki.entity.Doc;
import com.gec.obwiki.exception.BusinessException;
import com.gec.obwiki.exception.BusinessExceptionCode;
import com.gec.obwiki.mapper.DocMapper;
import com.gec.obwiki.mapper.EbookMapper;
import com.gec.obwiki.req.DocQueryReq;
import com.gec.obwiki.req.DocSaveReq;
import com.gec.obwiki.resp.DocQueryResp;
import com.gec.obwiki.resp.PageResp;
import com.gec.obwiki.service.ContentService;
import com.gec.obwiki.service.DocService;
import com.gec.obwiki.utils.CopyUtil;
import com.gec.obwiki.utils.RedisUtil;
import com.gec.obwiki.utils.RequestContext;
import com.gec.obwiki.utils.SnowFlake;
import com.gec.obwiki.websocket.WebSocketServer;
import com.gec.obwiki.websocket.WsServiceAsync;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author Administrator
* @description 针对表【doc】的数据库操作Service实现
* @createDate 2025-06-24 09:56:56
*/
@Service
@Slf4j
public class DocServiceImpl extends ServiceImpl<DocMapper, Doc> implements DocService{
    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    ContentService contentService;
    @Autowired
    private EbookMapper ebookMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private WsServiceAsync wsServiceAsync;

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Override
    public List<DocQueryResp> all() {
        List<Doc> categories = this.baseMapper.selectList(new QueryWrapper<Doc>().orderByAsc("sort"));
        List<DocQueryResp> list = CopyUtil.copyList(categories, DocQueryResp.class);
        return list;
    }

    @Override
    public PageResp<DocQueryResp> listByname(DocQueryReq req) {
        QueryWrapper<Doc> queryWrapper = new QueryWrapper<Doc>();
        //第一个参数：该参数是一个布尔类型，只有该参数是true时，才将like条件拼接到sql中；本例中，如果name字段不为空，则拼接name字段的like查询条件；
        queryWrapper.like(StringUtils.isNotBlank(req.getName()),"name",req.getName());
        //创建分页对象
        Page<Doc> page = new Page<>(req.getPage(),req.getSize());
        page = this.baseMapper.selectPage(page, queryWrapper);

        List<DocQueryResp> resps = CopyUtil.copyList(page.getRecords(), DocQueryResp.class);
        //创建返回对象
        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setList(resps);
        pageResp.setTotal(page.getTotal());
        return pageResp;
    }

    @Override
    public void save(DocSaveReq req) {
        Doc doc = CopyUtil.copy(req, Doc.class);
        Content content = CopyUtil.copy(req, Content.class);
        if (doc.getId() == null || doc.getId() == 0) {
            // 新增文档
            long docId = snowFlake.nextId();
            doc.setId(docId);
            doc.setEbookId(doc.getEbookId());
            doc.setViewCount(0);
            doc.setVoteCount(0);

            // 新增内容，使用相同的docId作为content的id
            content.setId(docId);  // 关键：手动设置id与文档一致
            contentService.save(content);

            this.baseMapper.insert(doc);
        } else {
            // 编辑文档
            this.baseMapper.updateById(doc);

            // 确保content的id与doc的id一致
            content.setId(doc.getId());

            // 先查询内容表是否存在该id
            Content existingContent = contentService.getById(doc.getId());
            if (existingContent != null) {
                // 存在则更新
                contentService.updateById(content);
            } else {
                // 不存在则插入（需确保id未被使用，但根据业务逻辑，这里应该总是存在）
                contentService.save(content);
            }
        }
    }

    // @Override
    // public void delete(List<Long> id) {
    //     this.baseMapper.deleteById(id);
    // }

    @Override
    public void delete(List<Long> ids) {
        this.baseMapper.deleteBatchIds(ids);
    }

    @Override
    public List<DocQueryResp> allbyEbookId(Long ebookId) {
        //该电子书阅读数+1
        ebookMapper.increaseViewCount(ebookId);
        QueryWrapper<Doc> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ebook_id",ebookId).orderByAsc("sort");
        List<Doc> doclist = this.baseMapper.selectList(queryWrapper);

        // 列表复制
        List<DocQueryResp> list = CopyUtil.copyList(doclist, DocQueryResp.class);
        return list;
    }
    @Override
    public void vote(Long id) {
        //key为  DOC_VOTE_123123123_192.168.0.1
        String key ="DOC_VOTE_"+id+"_"+RequestContext.getRemoteAddr();
        if(redisUtil.validateRepeat(key,3600*24)){
            this.baseMapper.increaseVoteCount(id);
        }else{
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }
        // sendInfo(id);//发送点赞通知
        Doc doc = this.baseMapper.selectById(id);
        webSocketServer.sendInfo("【您的文档 " + doc.getName() + "】被点赞！");
        //参数1  发送队列 参数2消息内容
        // rocketMQTemplate.convertAndSend("VOTE_TOPIC", "【" + doc.getName() + "】被点赞！");
    }

    @Override
    public void updateEbookInfo() {
        this.baseMapper.updateEbookInfo();
    }
    @Async
    public void sendInfo(Long id) {
        //查询点赞文档信息
        Doc doc = this.baseMapper.selectById(id);
        wsServiceAsync.sendInfo("【您的文档 " + doc.getName() + "】被点赞！");
    }
}




