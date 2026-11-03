package com.dm.service;

import com.dm.entity.Question;
import java.util.List;

/**
 * 题目Service接口
 */
public interface QuestionService {

    /**
     * 条件查询题目列表
     */
    List<Question> list(String subject, String type, String keyword);

    /**
     * 根据ID查询题目
     */
    Question getById(Integer id);

    /**
     * 新增题目
     */
    void add(Question question);

    /**
     * 更新题目
     */
    void update(Question question);

    /**
     * 删除题目
     */
    void delete(Integer id);

    /**
     * 获取所有学科列表
     */
    List<String> getAllSubjects();
}
