package com.gec.obwiki.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.obwiki.entity.EbookSnapshot;
import com.gec.obwiki.mapper.EbookSnapshotMapper;
import com.gec.obwiki.resp.StatisticResp;
import com.gec.obwiki.service.EbookSnapshotService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【ebook_snapshot】的数据库操作Service实现
* @createDate 2025-06-24 09:56:56
*/
@Service
public class EbookSnapshotServiceImpl extends ServiceImpl<EbookSnapshotMapper, EbookSnapshot>  implements EbookSnapshotService{
    @Override
    public void genSnapshot() {
        this.baseMapper.genSnapshot();
    }
    @Override
    public List<StatisticResp> getStatistic() {
        return  this.baseMapper.getStatistic();
    }
    @Override
    public List<StatisticResp> get30Statistic() {
        return this.baseMapper.get30Statistic();
    }
}




