package com.dm.dto;

import lombok.Data;

/**
 * 错题统计DTO - 用于分析报告
 */
@Data
public class WrongStatDTO {
    /**
     * 分类名称（学科/知识点/题型）
     */
    private String name;

    /**
     * 错题数量
     */
    private Integer count;

    /**
     * 占比
     */
    private Double percentage;
}
