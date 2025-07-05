package com.gec.obwiki.controller;

import com.gec.obwiki.resp.CommonResp;
import com.gec.obwiki.resp.StatisticResp;
import com.gec.obwiki.service.EbookSnapshotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/ebookSnapshot")
public class EbookSnapshotController {
    private static final Logger log = LoggerFactory.getLogger(EbookSnapshotController.class);
    @Resource
    private EbookSnapshotService ebookSnapshotService;


    @GetMapping("/getStatistic")
    public CommonResp getStatistic() {
        List<StatisticResp> statisticResp = ebookSnapshotService.getStatistic();
        CommonResp<List<StatisticResp>> commonResp = new CommonResp<>();
        commonResp.setContent(statisticResp);
        return commonResp;
    }
    @GetMapping("/get30Statistic")
    public CommonResp get30Statistic() {
        List<StatisticResp> statisticResp = ebookSnapshotService.get30Statistic();
        CommonResp<List<StatisticResp>> commonResp = new CommonResp<>();
        commonResp.setContent(statisticResp);
        log.info("获取成功{}",statisticResp);
        return commonResp;
    }
}
