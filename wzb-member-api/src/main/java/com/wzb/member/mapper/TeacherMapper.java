package com.wzb.member.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wzb.member.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzb.member.request.TeacherREQ;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wzb
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    /**
     * 不用手动实现分页，Mbatis-plus会自动实现分页
     * 但是第一个参数必须为E extends IPage<>类型，第2个参数通过 @Param 取别名
     * 最终查询到的数据会被封装到IPage实现里面
     *
     * @param teacherPage
     * @param req
     * @return
     */
    IPage<Teacher> searchPage(IPage<Teacher> teacherPage, @Param("req") TeacherREQ req);
}
