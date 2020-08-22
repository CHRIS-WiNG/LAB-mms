package com.wzb.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wzb.member.base.Page;
import com.wzb.member.base.Result;
import com.wzb.member.entity.Student;
import com.wzb.member.mapper.StudentMapper;
import com.wzb.member.request.StudentREQ;
import com.wzb.member.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wzb
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    /**
     * 分页条件查询
     *
     * @param page 页码
     * @param size 每页显示记录数量
     * @param req  请求参数实体
     * @return Result
     */
    @Override
    public Result search(long page, long size, StudentREQ req) {
        // 封装查询条件
        QueryWrapper<Student> query = new QueryWrapper<>();
        if (req != null) {
            if (StringUtils.isNotBlank(req.getName())) {
                query.like("name", req.getName().trim());
            }
            if (StringUtils.isNotBlank(req.getStuNum())) {
                query.like("stu_num", req.getStuNum().trim());
            }
            if (StringUtils.isNotBlank(req.getEntryYear())) {
                query.like("entry_year", req.getEntryYear().trim());
            }
            if (req.getLevel() != null) {
                query.eq("level", req.getLevel());
            }
            if (req.getIsGraduate() != null) {
                query.eq("is_graduate", req.getIsGraduate());
            }
            if (req.getOrder() != null && req.getOrder().equals("desc")) {
                query.orderByDesc("clockin_num");
            } else if (req.getOrder() != null && req.getOrder().equals("asc")) {
                query.orderByAsc("clockin_num");
            }
        }
        // 封装分页对象
        IPage<Student> data = baseMapper.selectPage(new Page<>(page, size), query);
        return Result.ok(data);
    }

    /**
     * 新增学生记录
     *
     * @param student 记录实例
     * @return Result
     */
    @Override
    public Result saveStudent(Student student) {
        if (student == null) return Result.error("新增失败");
        // 去除字段前后的空格
        trim(student);
        // 判断学号是否重复或为空格,判断email，姓名是否为空
        if (isDuplicatedOrBlank("stu_num", student.getStuNum(), null)) {
            return Result.error("学号输入有误");
        }
        if (StringUtils.isBlank(student.getEmail())) {
            return Result.error("邮箱不能为空");
        }
        if (StringUtils.isBlank(student.getName())) {
            return Result.error("姓名不能为空");
        }
        if (student.getClockinNum() == null) {
            return Result.error("打卡数不能为空");
        }

        // 写入数据库
        boolean isSucceed = this.save(student);
        if (isSucceed) {
            return Result.ok();
        }
        return Result.error("新增失败");
    }

    /**
     * 更新记录
     *
     * @param id      记录的id值
     * @param student 记录的实例
     * @return Result
     */
    @Override
    public Result update(int id, Student student) {
        if (student == null) return Result.error("修改失败");
        if (student.getId() == null) student.setId(id);

        // 去除字段前后的多余空格
        trim(student);
        // 判断学号是否重复或为空格,判断email，姓名是否为空
        if (isDuplicatedOrBlank("stu_num", student.getStuNum(), student.getId())) {
            return Result.error("学号输入有误");
        }
        if (StringUtils.isBlank(student.getEmail())) {
            return Result.error("邮箱不能为空");
        }
        if (StringUtils.isBlank(student.getName())) {
            return Result.error("姓名不能为空");
        }
        if (student.getClockinNum() == null) {
            return Result.error("打卡数不能为空");
        }

        // 写入数据库
        boolean isSucceed = this.updateById(student);
        if (isSucceed) {
            return Result.ok();
        }
        return Result.error("修改失败");
    }

    /**
     * 根据id查询记录
     *
     * @param id
     * @return
     */
    @Override
    public Result searchById(int id) {
        Student student = this.getById(id);
        if (student == null) {
            return Result.error("查询失败");
        }
        return Result.ok(student);
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
     * 根据uid查询学生记录
     * @param uid
     * @return
     */
    @Override
    public Result getbyUid(int uid) {
        QueryWrapper<Student> query = new QueryWrapper<>();
        query.eq("uid", uid);
        Student student = this.getOne(query);
        if (student == null) {
            return Result.error("查询失败");
        }
        return Result.ok(student);
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
        QueryWrapper<Student> query = new QueryWrapper<>();
        query.eq(column, val).last("LIMIT 1");
        Student data = baseMapper.selectOne(query);
        // 如果查找不到学号相同的记录，则直接返回false（不重复且不为空）
        if (data == null) return false;
        // 若根据学号查找到记录，则分情况讨论
        // 如果是更新记录调用
        if (id != null) {
            // 如果记录的id值与当前记录的id值相同，则返回false，代表更新自己这条记录，否则返回true
            return !data.getId().equals(id);
        }
        // 如果是插入记录调用，查找到记录代表一定学号重复，返回true
        return true;
    }

    /**
     * 去除 student实例中字段前后的空格
     *
     * @param student 记录实例
     */
    public void trim(Student student) {
        if (student == null) return;
        if (StringUtils.isNotBlank(student.getStuNum())) student.setStuNum(student.getStuNum().trim());
        if (StringUtils.isNotBlank(student.getName())) student.setName(student.getName().trim());
        if (StringUtils.isNotBlank(student.getEmail())) student.setEmail(student.getEmail().trim());
        if (StringUtils.isNotBlank(student.getEntryYear())) student.setEntryYear(student.getEntryYear().trim());
        if (StringUtils.isNotBlank(student.getMobile())) student.setMobile(student.getMobile().trim());
        if (StringUtils.isNotBlank(student.getCollege())) student.setCollege(student.getCollege().trim());
    }
}
