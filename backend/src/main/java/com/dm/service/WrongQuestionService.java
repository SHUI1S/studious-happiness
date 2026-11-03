package com.dm.service;

import com.dm.entity.WrongQuestion;
import java.util.List;

/**
 * 错题Service接口
 */
public interface WrongQuestionService {

    /**
     * 查询用户错题列表
     */
    List<WrongQuestion> list(Integer userId, String subject, Integer mastered);

    /**
     * 根据ID查询错题
     */
    WrongQuestion getById(Integer id);

    /**
     * 添加错题
     */
    void add(WrongQuestion wrongQuestion);

    /**
     * 更新错题（标记掌握/修改错误原因）
     */
    void update(WrongQuestion wrongQuestion);

    /**
     * 删除错题
     */
    void delete(Integer id);
}
