package com.gec.obwiki.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class DocSaveReq {
    private Long id;
    // 电子书id
    @NotNull(message = "【电子书】不能为空")
    private Long ebookId;
    // 父id
    @NotNull(message = "【父文档】不能为空")
    private Long parent;
    // 名称
    @NotNull(message = "【名称】不能为空")
    private String name;
    // 顺序
    @NotNull(message = "【顺序】不能为空")
    private Integer sort;
    // 阅读数
    private Integer viewCount;
    // 点赞数
    private Integer voteCount;

    @NotNull(message = "【内容】不能为空")
    private String content;
}