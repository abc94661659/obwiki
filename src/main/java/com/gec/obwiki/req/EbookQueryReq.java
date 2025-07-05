package com.gec.obwiki.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EbookQueryReq extends PageReq{
    // 主键
    private Long id;
    // 海洋生物电子书名
    private String name;
    // 分类2
    private Long categoryId2;
}