package com.dm.service;

import com.dm.dto.WrongStatDTO;
import java.util.List;
import java.util.Map;

/**
 * 错题分析Service接口
 */
public interface AnalysisService {

    /**
     * 获取错题概览（总数、已掌握数、掌握率）
     */
    Map<String, Object> getOverview(Integer userId);

    /**
     * 按学科统计
     */
    List<WrongStatDTO> countBySubject(Integer userId);

    /**
     * 按知识点统计
     */
    List<WrongStatDTO> countByKnowledgePoint(Integer userId);

    /**
     * 按题型统计
     */
    List<WrongStatDTO> countByType(Integer userId);

    /**
     * 生成学习报告
     */
    Map<String, Object> generateReport(Integer userId);
}
