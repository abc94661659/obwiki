package com.gec.obwiki.service;

import com.gec.obwiki.entity.EbookSnapshot;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.obwiki.resp.StatisticResp;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ebook_snapshot】的数据库操作Service
* @createDate 2025-06-24 09:56:56
*/
public interface EbookSnapshotService extends IService<EbookSnapshot> {

    void genSnapshot();

    List<StatisticResp> getStatistic();

    List<StatisticResp> get30Statistic();
}
