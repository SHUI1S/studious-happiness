package com.dm.controller;

import com.dm.dto.Result;
import com.dm.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 错题分析Controller
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    /**
     * 获取错题概览
     */
    @GetMapping("/overview")
    public Result overview(@RequestParam Integer userId) {
        Map<String, Object> data = analysisService.getOverview(userId);
        return Result.success(data);
    }

    /**
     * 按学科统计
     */
    @GetMapping("/bySubject")
    public Result bySubject(@RequestParam Integer userId) {
        return Result.success(analysisService.countBySubject(userId));
    }

    /**
     * 按知识点统计
     */
    @GetMapping("/byKnowledge")
    public Result byKnowledge(@RequestParam Integer userId) {
        return Result.success(analysisService.countByKnowledgePoint(userId));
    }

    /**
     * 按题型统计
     */
    @GetMapping("/byType")
    public Result byType(@RequestParam Integer userId) {
        return Result.success(analysisService.countByType(userId));
    }

    /**
     * 生成完整学习报告
     */
    @GetMapping("/report")
    public Result report(@RequestParam Integer userId) {
        Map<String, Object> report = analysisService.generateReport(userId);
        return Result.success(report);
    }
}
