package com.wzb.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wzb.member.base.Page;
import com.wzb.member.base.Result;
import com.wzb.member.entity.Student;
import com.wzb.member.entity.Teacher;
import com.wzb.member.entity.Team;
import com.wzb.member.mapper.TeacherMapper;
import com.wzb.member.request.TeacherREQ;
import com.wzb.member.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzb.member.service.ITeamService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wzb
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    /**
     * 条件查询，外连接Team表，查询方法在TeacherMapper中实现
     *
     * @param page
     * @param size
     * @param req
     * @return
     */
    @Override
    public Result search(long page, long size, TeacherREQ req) {

        if (req == null) {
            req = new TeacherREQ();
        }
        // 去除搜索内容的前后多余空格
        if (StringUtils.isNotBlank(req.getName())) req.setName(req.getName().trim());
        if (StringUtils.isNotBlank(req.getTchNum())) req.setTchNum(req.getTchNum().trim());
        // 在 TeacherMapper 中已经实现了 searchPage 分页条件查询
        IPage<Teacher> data = baseMapper.searchPage(new Page<Teacher>(page, size), req);
        return Result.ok(data);
    }

    /**
     * 保存teacher记录到数据库
     *
     * @param teacher
     * @return
     */
    @Override
    public Result saveTeacher(Teacher teacher) {
        if (teacher == null) return Result.error("新增失败");
        // 去除字段前后的空格
        trim(teacher);
        // 判断教工号是否重复或为空格,判断email，姓名是否为空
        if (isDuplicatedOrBlank("tch_num", teacher.getTchNum(), null)) {
            return Result.error("教工号号输入有误");
        }
        if (StringUtils.isBlank(teacher.getEmail())) {
            return Result.error("邮箱不能为空");
        }
        if (StringUtils.isBlank(teacher.getName())) {
            return Result.error("姓名不能为空");
        }

        // 写入数据库
        boolean isSucceed = this.save(teacher);
        if (isSucceed) {
            return Result.ok();
        }
        return Result.error("新增失败");
    }

    /**
     * 更新记录到数据库
     *
     * @param id
     * @param teacher
     * @return
     */
    @Override
    public Result update(int id, Teacher teacher) {
        if (teacher == null) return Result.error("修改失败");
        if (teacher.getId() == null) teacher.setId(id);
        // 去除字段前后的多余空格
        trim(teacher);
        // 判断教工号是否重复或为空格,判断email，姓名是否为空
        if (isDuplicatedOrBlank("tch_num", teacher.getTchNum(), teacher.getId())) {
            return Result.error("教工号输入有误");
        }
        if (StringUtils.isBlank(teacher.getEmail())) {
            return Result.error("邮箱不能为空");
        }
        if (StringUtils.isBlank(teacher.getName())) {
            return Result.error("姓名不能为空");
        }

        // 写入数据库
        boolean isSucceed = this.updateById(teacher);
        if (isSucceed) {
            return Result.ok();
        }
        return Result.error("修改失败");
    }

    @Autowired
    ITeamService teamService;

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     */
    @Override
    public Result searchById(int id) {
        Teacher teacher = this.getById(id);
        if (teacher == null) {
            return Result.error("查询失败");
        }
        // 查询到teacher数据，接下来向里写入组名teamName
        Integer team_id = teacher.getTeamId();
        if (team_id != null) {
            // 组id不为空则通过 teamService 获取组的信息
            Team team = teamService.getById(team_id);
            if (team != null) {
                // 若存在该组则将名称写入teacher
                String team_name = team.getName();
                teacher.setTeamName(team_name);
            }
        }
        return Result.ok(teacher);
    }

    /**
     * 根据id删除记录
     *
     * @param id
     * @return
     */
    @Override
    public Result deleteById(int id) {
        boolean isSucceed = this.removeById(id);
        if (isSucceed) {
            return Result.ok();
        }
        return Result.error("删除失败");
    }

    /**
     * 判断是否有teacher表记录中的team_id字段中的值为teamId
     *
     * @param teamId
     * @return
     */
    @Override
    public boolean hasRecordByTeamId(int teamId) {
        QueryWrapper<Teacher> query = new QueryWrapper<>();
        query.eq("team_id", teamId).last("LIMIT 1");
        int count = baseMapper.selectCount(query);
        return count == 1;
    }

    @Override
    public Result getbyUid(int uid) {
        QueryWrapper<Teacher> query = new QueryWrapper<>();
        query.eq("uid", uid);
        Teacher teacher = this.getOne(query);
        if (teacher == null) {
            return Result.error("查询失败");
        }
        return Result.ok(teacher);
    }

    /**
     * 判断 唯一索引值val 是否为空 或 是否重复
     *
     * @param column 唯一索引值val在数据库中对应的字段名
     * @param val    唯一索引值val
     * @param id     该记录的id值，更新记录中使用，新增记录中直接设置为null
     * @return true：重复或为空；false：不重复且不为空
     */
    public boolean isDuplicatedOrBlank(String column, String val, Integer id) {
        if (StringUtils.isBlank(val)) return true;

        // 根据val值查询
        QueryWrapper<Teacher> query = new QueryWrapper<>();
        query.eq(column, val).last("LIMIT 1");
        Teacher data = baseMapper.selectOne(query);
        // 如果查找不到教工号相同的记录，则直接返回false（不重复且不为空）
        if (data == null) return false;
        // 若根据教工号查找到记录，则分情况讨论
        // 如果是更新记录调用
        if (id != null) {
            // 如果记录的id值与当前记录的id值相同，则返回false，代表更新自己这条记录，否则返回true
            return !data.getId().equals(id);
        }
        // 如果是插入记录调用，查找到记录代表一定教工号重复，返回true
        return true;
    }

    /**
     * 去除 teacher实例中字段前后的多余空格
     *
     * @param teacher 记录实例
     */
    public void trim(Teacher teacher) {
        if (teacher == null) return;
        if (StringUtils.isNotBlank(teacher.getName())) teacher.setName(teacher.getName().trim());
        if (StringUtils.isNotBlank(teacher.getTchNum())) teacher.setTchNum(teacher.getTchNum().trim());
        if (StringUtils.isNotBlank(teacher.getEmail())) teacher.setEmail(teacher.getEmail().trim());
        if (StringUtils.isNotBlank(teacher.getMobile())) teacher.setMobile(teacher.getMobile().trim());
        if (StringUtils.isNotBlank(teacher.getOffice())) teacher.setOffice(teacher.getOffice().trim());
    }
}
