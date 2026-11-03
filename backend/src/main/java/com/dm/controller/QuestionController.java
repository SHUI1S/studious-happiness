package com.dm.controller;

import com.dm.dto.Result;
import com.dm.entity.Question;
import com.dm.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 题目Controller
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 查询题目列表
     */
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) String subject,
                       @RequestParam(required = false) String type,
                       @RequestParam(required = false) String keyword) {
        List<Question> list = questionService.list(subject, type, keyword);
        return Result.success(list);
    }

    /**
     * 根据ID查询题目详情
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Question question = questionService.getById(id);
        return Result.success(question);
    }

    /**
     * 新增题目
     */
    @PostMapping
    public Result add(@RequestBody Question question) {
        questionService.add(question);
        return Result.success("添加成功", null);
    }

    /**
     * 更新题目
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody Question question) {
        question.setId(id);
        questionService.update(question);
        return Result.success("更新成功", null);
    }

    /**
     * 删除题目
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        questionService.delete(id);
        return Result.success("删除成功", null);
    }

    /**
     * 获取所有学科
     */
    @GetMapping("/subjects")
    public Result subjects() {
        List<String> subjects = questionService.getAllSubjects();
        return Result.success(subjects);
    }
}
