package com.dm.controller;

import com.dm.dto.Result;
import com.dm.entity.WrongQuestion;
import com.dm.service.WrongQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 错题Controller
 */
@RestController
@RequestMapping("/wrong")
public class WrongQuestionController {

    @Autowired
    private WrongQuestionService wrongQuestionService;

    /**
     * 查询错题列表
     */
    @GetMapping("/list")
    public Result list(@RequestParam Integer userId,
                       @RequestParam(required = false) String subject,
                       @RequestParam(required = false) Integer mastered) {
        List<WrongQuestion> list = wrongQuestionService.list(userId, subject, mastered);
        return Result.success(list);
    }

    /**
     * 根据ID查询错题详情
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        WrongQuestion wrongQuestion = wrongQuestionService.getById(id);
        return Result.success(wrongQuestion);
    }

    /**
     * 添加错题
     */
    @PostMapping
    public Result add(@RequestBody WrongQuestion wrongQuestion) {
        wrongQuestionService.add(wrongQuestion);
        return Result.success("添加成功", null);
    }

    /**
     * 更新错题（标记掌握）
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody WrongQuestion wrongQuestion) {
        wrongQuestion.setId(id);
        wrongQuestionService.update(wrongQuestion);
        return Result.success("更新成功", null);
    }

    /**
     * 删除错题
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        wrongQuestionService.delete(id);
        return Result.success("删除成功", null);
    }
}
