package com.dm.entity;

import lombok.Data;
import java.util.Date;

/**
 * 题目实体类
 */
@Data
public class Question {
    private Integer id;
    private String subject;
    private String type;
    private String content;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String analysis;
    private String knowledgePoint;
    private Integer difficulty;
    private Date createTime;
}
