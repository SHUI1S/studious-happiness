package com.dm.entity;

import lombok.Data;
import java.util.Date;

/**
 * 错题实体类
 */
@Data
public class WrongQuestion {
    private Integer id;
    private Integer userId;
    private Integer questionId;
    private String userAnswer;
    private String wrongReason;
    private Integer mastered;
    private Date wrongTime;

    // 关联查询字段
    private Question question;
}
