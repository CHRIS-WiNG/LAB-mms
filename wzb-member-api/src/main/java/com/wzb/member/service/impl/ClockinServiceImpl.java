package com.wzb.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wzb.member.base.Page;
import com.wzb.member.base.Result;
import com.wzb.member.entity.Clockin;
import com.wzb.member.entity.Student;
import com.wzb.member.exception.CreateClockInRecordException;
import com.wzb.member.mapper.ClockinMapper;
import com.wzb.member.request.ClockinREQ;
import com.wzb.member.service.IClockinService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzb.member.service.IStudentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wzb
 */
@Service
public class ClockinServiceImpl extends ServiceImpl<ClockinMapper, Clockin> implements IClockinService {

    @Override
    public Result search(long page, long size, ClockinREQ req) {
        QueryWrapper<Clockin> query = new QueryWrapper<>();
        if (req != null) {
            if (StringUtils.isNotBlank(req.getName())) {
                query.like("name", req.getName().trim());
            }
            if (req.getDate() != null && req.getDate().size() != 0) {
                Date startDate = req.getDate().get(0);
                Date endDate = req.getDate().get(1);
                query.between("date", startDate, endDate);
            }
        }
        // id值越大，日期越新
        // 需放在if外
        query.orderByDesc("id");

        IPage<Clockin> data = baseMapper.selectPage(new Page<>(page, size), query);
        return Result.ok(data);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
            timeout = 36000, rollbackFor = Exception.class)
    public Result saveRecord(Clockin clockin) throws CreateClockInRecordException {
        if (clockin == null) return Result.error("打卡失败，请重试");
//        if (clockInToday(clockin.getUid())) return Result.error("今日已打卡");

        if (clockin.getDate() == null || clockin.getTime() == null) {
            return Result.error("时间戳不能为空");
        }
        if (StringUtils.isBlank(clockin.getName())) {
            return Result.error("未获取到姓名信息，请重试");
        }
        if (clockin.getEmotionType() == null) {
            return Result.error("心情不能为空");
        }
        if (clockin.getUid() == null) {
            return Result.error("未获取到身份信息，请重试");
        }

        boolean isSucceed = this.save(clockin);
        if (isSucceed) {
            boolean addCount = addClockInNumInStudent(clockin.getUid());
            if (!addCount) {
                // 抛出异常，回滚事务
                throw new CreateClockInRecordException("修改学生打卡记录异常，请重试");
            }
            return Result.ok();
        }
        return Result.error("打卡失败，请重试");
    }

    /**
     * 判断账户今日有无打卡
     *
     * @param uid
     * @return
     */
    private boolean clockInToday(int uid) {
        Date dateToday = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(dateToday);

        QueryWrapper<Clockin> query = new QueryWrapper<>();
        query.eq("uid", uid);
        query.eq("date", today);

        Integer count = baseMapper.selectCount(query);
        return count == 1;
    }

    @Autowired
    IStudentService studentService;

    /**
     * 将学生表的某条记录的本月打卡数+1
     *
     * @param uid
     * @return
     */
    private boolean addClockInNumInStudent(int uid) {
        QueryWrapper<Student> query = new QueryWrapper<>();
        query.eq("uid", uid);
        Student student = studentService.getOne(query);
        if (student != null) {
            // 在学生表中查询到了账户记录
            student.setClockinNum(student.getClockinNum() + 1);
            return studentService.updateById(student);
        }
        return false;
    }
}
