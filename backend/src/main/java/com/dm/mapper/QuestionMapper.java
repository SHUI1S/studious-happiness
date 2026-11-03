package com.dm.mapper;

import com.dm.entity.Question;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 题目Mapper接口
 */
public interface QuestionMapper {

    /**
     * 条件查询题目列表
     */
    List<Question> selectList(@Param("subject") String subject,
                              @Param("type") String type,
                              @Param("keyword") String keyword);

    /**
     * 根据ID查询题目
     */
    Question selectById(@Param("id") Integer id);

    /**
     * 新增题目
     */
    int insert(Question question);

    /**
     * 更新题目
     */
    int update(Question question);

    /**
     * 删除题目
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 查询所有学科列表
     */
    List<String> selectAllSubjects();
}
