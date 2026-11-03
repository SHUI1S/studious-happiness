package com.dm.service.impl;

import com.dm.entity.Question;
import com.dm.mapper.QuestionMapper;
import com.dm.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 题目Service实现类
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<Question> list(String subject, String type, String keyword) {
        return questionMapper.selectList(subject, type, keyword);
    }

    @Override
    public Question getById(Integer id) {
        return questionMapper.selectById(id);
    }

    @Override
    public void add(Question question) {
        questionMapper.insert(question);
    }

    @Override
    public void update(Question question) {
        questionMapper.update(question);
    }

    @Override
    public void delete(Integer id) {
        questionMapper.deleteById(id);
    }

    @Override
    public List<String> getAllSubjects() {
        return questionMapper.selectAllSubjects();
    }
}
