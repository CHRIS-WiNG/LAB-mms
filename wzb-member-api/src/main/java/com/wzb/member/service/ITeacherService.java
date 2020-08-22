package com.wzb.member.service;

import com.wzb.member.base.Result;
import com.wzb.member.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzb.member.request.TeacherREQ;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wzb
 */
public interface ITeacherService extends IService<Teacher> {

    /**
     * 条件查询，外连接Team表，查询方法在TeacherMapper中实现
     *
     * @param page
     * @param size
     * @param req
     * @return
     */
    Result search(long page, long size, TeacherREQ req);

    /**
     * 保存teacher记录到数据库
     *
     * @param teacher
     * @return
     */
    Result saveTeacher(Teacher teacher);

    /**
     * 根据id删除记录
     *
     * @param id
     * @return
     */
    Result deleteById(int id);

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     */
    Result searchById(int id);

    /**
     * 更新记录到数据库
     *
     * @param id
     * @param teacher
     * @return
     */
    Result update(int id, Teacher teacher);

    /**
     * 判断是否有teacher表记录中的team_id字段中的值为teamId
     *
     * @param teamId
     * @return
     */
    boolean hasRecordByTeamId(int teamId);

    Result getbyUid(int uid);
}
