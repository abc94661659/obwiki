package com.gec.obwiki.resp;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PageResp<T> {
    // 总条数
    private long total;
    // 分页数据
    private List<T> list;
}