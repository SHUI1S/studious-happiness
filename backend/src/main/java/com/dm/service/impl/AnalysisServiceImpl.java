package com.dm.service.impl;

import com.dm.dto.WrongStatDTO;
import com.dm.mapper.WrongQuestionMapper;
import com.dm.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 错题分析Service实现类
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    private WrongQuestionMapper wrongQuestionMapper;

    @Override
    public Map<String, Object> getOverview(Integer userId) {
        Map<String, Object> result = new HashMap<>();
        int total = wrongQuestionMapper.countTotal(userId);
        int mastered = wrongQuestionMapper.countMastered(userId);
        double rate = total == 0 ? 0 : Math.round((double) mastered / total * 1000) / 10.0;

        result.put("total", total);
        result.put("mastered", mastered);
        result.put("notMastered", total - mastered);
        result.put("masterRate", rate);
        return result;
    }

    @Override
    public List<WrongStatDTO> countBySubject(Integer userId) {
        List<WrongStatDTO> list = wrongQuestionMapper.countBySubject(userId);
        int total = list.stream().mapToInt(WrongStatDTO::getCount).sum();
        list.forEach(item -> {
            double percentage = total == 0 ? 0 : Math.round((double) item.getCount() / total * 1000) / 10.0;
            item.setPercentage(percentage);
        });
        return list;
    }

    @Override
    public List<WrongStatDTO> countByKnowledgePoint(Integer userId) {
        List<WrongStatDTO> list = wrongQuestionMapper.countByKnowledgePoint(userId);
        int total = list.stream().mapToInt(WrongStatDTO::getCount).sum();
        list.forEach(item -> {
            double percentage = total == 0 ? 0 : Math.round((double) item.getCount() / total * 1000) / 10.0;
            item.setPercentage(percentage);
        });
        return list;
    }

    @Override
    public List<WrongStatDTO> countByType(Integer userId) {
        List<WrongStatDTO> list = wrongQuestionMapper.countByType(userId);
        int total = list.stream().mapToInt(WrongStatDTO::getCount).sum();
        list.forEach(item -> {
            double percentage = total == 0 ? 0 : Math.round((double) item.getCount() / total * 1000) / 10.0;
            item.setPercentage(percentage);
        });
        return list;
    }

    @Override
    public Map<String, Object> generateReport(Integer userId) {
        Map<String, Object> report = new HashMap<>();

        // 概览数据
        Map<String, Object> overview = getOverview(userId);
        report.put("overview", overview);

        // 各维度统计
        List<WrongStatDTO> bySubject = countBySubject(userId);
        List<WrongStatDTO> byKnowledge = countByKnowledgePoint(userId);
        List<WrongStatDTO> byType = countByType(userId);

        report.put("bySubject", bySubject);
        report.put("byKnowledge", byKnowledge);
        report.put("byType", byType);

        // 生成学习建议
        List<String> suggestions = new ArrayList<>();

        // 根据掌握率给出建议
        double masterRate = (double) overview.get("masterRate");
        if (masterRate < 30) {
            suggestions.add("错题掌握率较低，建议优先复习近期做错的题目，重新梳理知识点。");
        } else if (masterRate < 60) {
            suggestions.add("错题掌握率中等，建议针对高频错题进行专项练习，巩固薄弱环节。");
        } else {
            suggestions.add("错题掌握率较好，建议继续保持，定期回顾已掌握的题目防止遗忘。");
        }

        // 根据薄弱学科给出建议
        if (!bySubject.isEmpty()) {
            String weakestSubject = bySubject.get(0).getName();
            suggestions.add("【" + weakestSubject + "】是当前错题最多的学科，建议增加该学科的练习量。");
        }

        // 根据薄弱知识点给出建议
        if (!byKnowledge.isEmpty()) {
            String weakestPoint = byKnowledge.get(0).getName();
            suggestions.add("【" + weakestPoint + "】知识点错误率最高，建议重点复习该知识点相关内容。");
        }

        report.put("suggestions", suggestions);
        return report;
    }
}
