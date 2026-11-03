package com.dm.service.impl;

import com.dm.entity.WrongQuestion;
import com.dm.mapper.WrongQuestionMapper;
import com.dm.service.WrongQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 错题Service实现类
 */
@Service
public class WrongQuestionServiceImpl implements WrongQuestionService {

    @Autowired
    private WrongQuestionMapper wrongQuestionMapper;

    @Override
    public List<WrongQuestion> list(Integer userId, String subject, Integer mastered) {
        return wrongQuestionMapper.selectList(userId, subject, mastered);
    }

    @Override
    public WrongQuestion getById(Integer id) {
        return wrongQuestionMapper.selectById(id);
    }

    @Override
    public void add(WrongQuestion wrongQuestion) {
        wrongQuestionMapper.insert(wrongQuestion);
    }

    @Override
    public void update(WrongQuestion wrongQuestion) {
        wrongQuestionMapper.update(wrongQuestion);
    }

    @Override
    public void delete(Integer id) {
        wrongQuestionMapper.deleteById(id);
    }
}
