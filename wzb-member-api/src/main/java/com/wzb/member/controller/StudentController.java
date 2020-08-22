package com.wzb.member.controller;


import com.wzb.member.base.Result;
import com.wzb.member.entity.Student;
import com.wzb.member.request.StudentREQ;
import com.wzb.member.service.IStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wzb
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/list/search/{page}/{size}")
    public Result search(@PathVariable("page") long page,
                         @PathVariable("size") long size,
                         @RequestBody(required = false) StudentREQ req) {

        logger.info("查询会员列表：page={}，size={}", page, size);
        return studentService.search(page, size, req);
    }


    // post /student
    @PostMapping()
    public Result add(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable("id") int id) {
        // Integer可能为null，int一定会返回一个值
        return studentService.searchById(id);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") int id,
                         @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") int id) {
        return studentService.deleteById(id);
    }

    @GetMapping("/uid/{uid}")
    public Result getByUid(@PathVariable("uid") int uid) {
        return studentService.getbyUid(uid);
    }
}
