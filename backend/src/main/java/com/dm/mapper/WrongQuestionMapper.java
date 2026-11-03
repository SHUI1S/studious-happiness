package com.dm.mapper;

import com.dm.dto.WrongStatDTO;
import com.dm.entity.WrongQuestion;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 错题Mapper接口
 */
public interface WrongQuestionMapper {

    /**
     * 查询用户错题列表（带题目详情）
     */
    List<WrongQuestion> selectList(@Param("userId") Integer userId,
                                   @Param("subject") String subject,
                                   @Param("mastered") Integer mastered);

    /**
     * 根据ID查询错题
     */
    WrongQuestion selectById(@Param("id") Integer id);

    /**
     * 新增错题
     */
    int insert(WrongQuestion wrongQuestion);

    /**
     * 更新错题（标记掌握/修改错误原因）
     */
    int update(WrongQuestion wrongQuestion);

    /**
     * 删除错题
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 按学科统计错题数量
     */
    List<WrongStatDTO> countBySubject(@Param("userId") Integer userId);

    /**
     * 按知识点统计错题数量
     */
    List<WrongStatDTO> countByKnowledgePoint(@Param("userId") Integer userId);

    /**
     * 按题型统计错题数量
     */
    List<WrongStatDTO> countByType(@Param("userId") Integer userId);

    /**
     * 统计用户错题总数
     */
    int countTotal(@Param("userId") Integer userId);

    /**
     * 统计已掌握错题数
     */
    int countMastered(@Param("userId") Integer userId);
}
