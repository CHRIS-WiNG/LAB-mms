package com.wzb.member.service;

import com.wzb.member.base.Result;
import com.wzb.member.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzb.member.request.StudentREQ;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wzb
 */
public interface IStudentService extends IService<Student> {

    /**
     * 分页条件查询
     *
     * @param page 页码
     * @param size 每页显示记录数量
     * @param req  请求参数实体
     * @return Result
     */
    Result search(long page, long size, StudentREQ req);

    /**
     * 更新记录
     *
     * @param id      记录的id值
     * @param student 记录的实例
     * @return Result
     */
    Result update(int id, Student student);

    /**
     * 新增学生记录
     *
     * @param student 记录实例
     * @return Result
     */
    Result saveStudent(Student student);

    /**
     * 根据id查询记录
     * @param id
     * @return
     */
    Result searchById(int id);

    /**
     * 根据id删除记录
     * @param id
     * @return
     */
    Result deleteById(int id);

    Result getbyUid(int uid);
}
