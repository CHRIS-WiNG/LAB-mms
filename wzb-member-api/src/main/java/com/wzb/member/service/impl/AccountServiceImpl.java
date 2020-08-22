package com.wzb.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wzb.member.base.Page;
import com.wzb.member.base.Result;
import com.wzb.member.entity.Account;
import com.wzb.member.entity.Student;
import com.wzb.member.entity.Teacher;
import com.wzb.member.exception.CreateAccountException;
import com.wzb.member.mapper.AccountMapper;
import com.wzb.member.request.AccountREQ;
import com.wzb.member.request.PasswordREQ;
import com.wzb.member.service.IAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzb.member.service.IStudentService;
import com.wzb.member.service.ITeacherService;
import com.wzb.member.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wzb
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Override
    public Result search(long page, long size, AccountREQ req) {
        QueryWrapper<Account> query = new QueryWrapper<>();
        if (req != null) {
            if (StringUtils.isNotBlank(req.getName())) {
                query.like("name", req.getName().trim());
            }
            if (StringUtils.isNotBlank(req.getUsername())) {
                query.like("username", req.getUsername().trim());
            }
        }
        IPage<Account> data = baseMapper.selectPage(new Page<>(page, size), query);
        return Result.ok(data);
    }

    @Autowired
    IStudentService studentService;

    @Autowired
    ITeacherService teacherService;

    @Override
    // 开启事务，保证表中数据插入失败后可正确回滚
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
            timeout = 36000, rollbackFor = Exception.class)
    public Result saveAccount(Account account) throws CreateAccountException {
        if (account == null) return Result.error("新增失败");
        // 删掉实体中字段的多余空格
        trim(account);
        // 1. 校验账户信息是否合法
        if (isDuplicatedOrBlank("username", account.getUsername(), account.getId())) {
            return Result.error("用户名输入有误");
        }
        if (StringUtils.isBlank(account.getEmail())) {
            return Result.error("邮箱不能为空");
        }
        if (StringUtils.isBlank(account.getName())) {
            return Result.error("姓名不能为空");
        }
        if (StringUtils.isBlank(account.getIdentityNum())) {
            return Result.error("工号不能为空");
        }
        if (StringUtils.isBlank(account.getPassword())) {
            return Result.error("抱歉，请重试");
        }
        if (account.getIdentity() == null) {
            return Result.error("身份不能为空");
        }
        if (account.getPermission() == null) {
            return Result.error("权限不能为空");
        }
        // 2. 将提交的密码进行加密
        String encodePassword = new BCryptPasswordEncoder().encode(account.getPassword());
        account.setPassword(encodePassword);
        // 3. 保存到数据库
        boolean isSucceed = this.save(account);
        if (!isSucceed) {
            return Result.error("新增失败");
        }
        // 4. 新增成功后获取到account的主键值，根据identity构建学生或老师对象，并将主键值填入uid中。
        boolean isSaved = false;
        if (account.getIdentity() == 1) {
            // 身份为学生，构建学生对象并填入必要信息，写入数据库
            Student student = new Student();
            // 判断学号是否在学生表中存在
            if (hasRecordByStuNum(account.getIdentityNum())) {
                // 存在则抛出异常，回滚事务
                throw new CreateAccountException("学号已存在");
            }
            student.setStuNum(account.getIdentityNum());
            student.setName(account.getName());
            student.setEmail(account.getEmail());
            student.setUid(account.getId());
            student.setClockinNum(0);
            isSaved = studentService.save(student);
        } else if (account.getIdentity() == 2) {
            // 身份为教师，构建教师对象并填入必要信息，写入数据库
            Teacher teacher = new Teacher();
            if (hasRecordByTchNum(account.getIdentityNum())) {
                // 存在则抛出异常，回滚事务
                throw new CreateAccountException("教工号已存在");
            }
            teacher.setTchNum(account.getIdentityNum());
            teacher.setName(account.getName());
            teacher.setEmail(account.getEmail());
            teacher.setUid(account.getId());
            isSaved = teacherService.save(teacher);
        }
        if (isSaved) return Result.ok();
        return Result.error("新增失败");
    }

    @Override
    public Result searchById(int id) {
        Account account = this.getById(id);
        if (account == null) {
            return Result.error("查询失败");
        }
        return Result.ok(account);
    }

    /**
     * 修改账户信息
     *
     * @param id
     * @param account
     * @return
     */
    @Override
    public Result update(int id, Account account) {
        if (account == null) return Result.error("修改失败");
        if (account.getId() == null) account.setId(id);

        trim(account);
        // 1. 校验账户信息是否合法
        if (isDuplicatedOrBlank("username", account.getUsername(), account.getId())) {
            return Result.error("用户名输入有误");
        }
        if (StringUtils.isBlank(account.getEmail())) {
            return Result.error("邮箱不能为空");
        }
        if (StringUtils.isBlank(account.getName())) {
            return Result.error("姓名不能为空");
        }
        if (StringUtils.isBlank(account.getIdentityNum())) {
            return Result.error("工号不能为空");
        }
        if (StringUtils.isBlank(account.getPassword())) {
            return Result.error("抱歉，请重试");
        }
        if (account.getIdentity() == null) {
            return Result.error("身份不能为空");
        }
        if (account.getPermission() == null) {
            return Result.error("权限不能为空");
        }
        // 2. 保存至数据库
        boolean isSucceed = this.updateById(account);
        if (isSucceed) {
            return Result.ok();
        }
        return Result.error("修改失败");
    }

    @Override
    public Result deleteById(int id) {
        // 判断是否是最后一个账户
        Integer count = baseMapper.selectCount(null);
        if (count <= 1) return Result.error("账户列表不能为空！");

        boolean isSucceed = this.removeById(id);
        if (isSucceed) {
            return Result.ok();
        }
        return Result.error("删除失败");
    }

    @Override
    public Result checkPwd(PasswordREQ req) {
        if (req == null || StringUtils.isEmpty(req.getPassword())) {
            return Result.error("密码不能为空");
        }
        Account account = baseMapper.selectById(req.getUserId());
        boolean matches = new BCryptPasswordEncoder().matches(req.getPassword(), account.getPassword());
        return matches ? Result.ok() : Result.error("原密码错误");
    }

    @Override
    public Result updatePwd(PasswordREQ req) {
        if (req == null || StringUtils.isEmpty(req.getPassword())) {
            return Result.error("密码不能为空");
        }

        // 1. 加密新密码
        String encodePassword = new BCryptPasswordEncoder().encode(req.getPassword());
        // 2. 将新密码保存到数据库
        Account account = baseMapper.selectById(req.getUserId());
        account.setPassword(encodePassword);
        baseMapper.updateById(account);
        return Result.ok();
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Result login(String username, String password) {
        Result error = Result.error("用户名或密码错误");

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return error;
        }
        // 1. 通过用户名查询数据
        Account account = getByUsername(username);
        // 用户不存在
        if (account == null) return error;
        // 2. 存在，判断输入密码和用户密码是否匹配
        boolean matches = new BCryptPasswordEncoder().matches(password, account.getPassword());
        if (!matches)
            return error;
        // 3. 生成token
        String jwt = jwtUtil.createJWT(String.valueOf(account.getId()), account.getUsername(), true);
        // 4. 响应给客户端
        return Result.ok(Map.of("token", jwt));
    }

    /**
     * 通过jwt token获取用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {
        // 解析jwt
        Claims claims = jwtUtil.parseJWT(token);
        if (claims == null || StringUtils.isBlank(claims.getSubject())) {
            return Result.error("获取用户信息失败");
        }
        // 获取用户名
        String username = claims.getSubject();
        // 1. 通过用户名查询
        Account account = getByUsername(username);
        if (account == null) {
            return Result.error("用户不存在");
        }
        // 2. 将密码设置为null，不响应给前端
        account.setPassword(null);
        return Result.ok(account);
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
        QueryWrapper<Account> query = new QueryWrapper<>();
        query.eq(column, val).last("LIMIT 1");
        Account data = baseMapper.selectOne(query);
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

    private void trim(Account account) {
        if (account == null) return;
        if (StringUtils.isNotBlank(account.getEmail())) account.setEmail(account.getEmail().trim());
        if (StringUtils.isNotBlank(account.getUsername())) account.setUsername(account.getUsername().trim());
        if (StringUtils.isNotBlank(account.getName())) account.setName(account.getName().trim());
        if (StringUtils.isNotBlank(account.getIdentityNum())) account.setIdentityNum(account.getIdentityNum().trim());
    }

    /**
     * 根据 stuNum 查找学生表中是否有记录存在
     *
     * @param stuNum
     * @return
     */
    private boolean hasRecordByStuNum(String stuNum) {
        QueryWrapper<Student> query = new QueryWrapper<>();
        query.eq("stu_num", stuNum);
        return studentService.getOne(query) != null;
    }

    /**
     * 根据 tchNum 查找教师表中是否有记录存在
     *
     * @param tchNum
     * @return
     */
    private boolean hasRecordByTchNum(String tchNum) {
        QueryWrapper<Teacher> query = new QueryWrapper<>();
        query.eq("tch_num", tchNum);
        return teacherService.getOne(query) != null;
    }

    public Account getByUsername(String username) {
        QueryWrapper<Account> query = new QueryWrapper<>();
        query.eq("username", username);
        return baseMapper.selectOne(query);
    }
}
