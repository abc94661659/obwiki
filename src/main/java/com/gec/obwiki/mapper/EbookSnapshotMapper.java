package com.gec.obwiki.mapper;

import com.gec.obwiki.entity.EbookSnapshot;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.obwiki.resp.StatisticResp;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ebook_snapshot】的数据库操作Mapper
* @createDate 2025-06-24 09:56:56
* @Entity com.gec.obwiki.entity.EbookSnapshot
*/
public interface EbookSnapshotMapper extends BaseMapper<EbookSnapshot> {

    void genSnapshot();

    List<StatisticResp> getStatistic();

    List<StatisticResp> get30Statistic();
}




