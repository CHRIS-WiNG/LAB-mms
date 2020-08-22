package com.wzb.member.controller;


import com.wzb.member.base.Result;
import com.wzb.member.entity.Teacher;
import com.wzb.member.request.TeacherREQ;
import com.wzb.member.service.ITeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wzb
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ITeacherService teacherService;

    @PostMapping("/list/search/{page}/{size}")
    public Result search(@PathVariable("page") long page,
                         @PathVariable("size") long size,
                         @RequestBody(required = false) TeacherREQ req) {
        return teacherService.search(page, size, req);
    }

    @PostMapping()
    public Result add(@RequestBody Teacher teacher) {
        return teacherService.saveTeacher(teacher);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") int id) {
        return teacherService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable("id") int id) {
        return teacherService.searchById(id);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") int id,
                         @RequestBody Teacher teacher) {
        return teacherService.update(id, teacher);
    }

    @GetMapping("/uid/{uid}")
    public Result getByUid(@PathVariable("uid") int uid) {
        return teacherService.getbyUid(uid);
    }
}
