package com.wzb.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzb.member.base.Page;
import com.wzb.member.base.Result;
import com.wzb.member.entity.Clockin;
import com.wzb.member.entity.Diary;
import com.wzb.member.entity.Student;
import com.wzb.member.mapper.DiaryMapper;
import com.wzb.member.request.DiaryREQ;
import com.wzb.member.service.IDiaryService;
import com.wzb.member.util.oss.FileState;
import com.wzb.member.util.oss.OssService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description:
 */
@Service
public class DiaryServiceImpl extends ServiceImpl<DiaryMapper, Diary> implements IDiaryService {

    @Autowired
    OssService ossService;

    private final String IMG_PREFIX = "img/";

    public Result upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        //首先判断是不是空的文件
        if (!file.isEmpty()) {
            //对文文件的全名进行截取然后在后缀名进行删选。
            int begin = originalFilename.indexOf(".");
            int last = originalFilename.length();
            //获得文件后缀名
            String suffix = originalFilename.substring(begin, last);
            //判断为图片格式，存放至oos服务器的"img/"文件夹下
            if (suffix.endsWith(".jpg") ||
                    suffix.endsWith(".jpeg") ||
                    suffix.endsWith(".png") ||
                    suffix.endsWith(".gif")) {
                return uploadImg(file, IMG_PREFIX);
            } else {
                // 错误
                return Result.error("错误格式");
            }
        }
        return Result.error("文件为空");
    }

    @Override
    public Result uploadDiary(Diary diary) {
        if (diary == null) return Result.error("提交失败");
//        if (diaryToday(diary.getUid())) return Result.error("今日已提交日报");

        if (diary.getDate() == null || diary.getTime() == null) {
            return Result.error("时间戳不能为空");
        }
        if (diary.getUid() == null) {
            return Result.error("未获取到身份信息，请重试");
        }
        boolean isSucceed = this.save(diary);
        if (isSucceed) {
            return Result.ok();
        }
        return Result.error("提交失败，请重试");

    }

    @Override
    public Result search(long page, long size, DiaryREQ req) {
        QueryWrapper<Diary> query = new QueryWrapper<>();
        if (req != null) {
            if (StringUtils.isNotBlank(req.getName())) {
                query.like("name", req.getName().trim());
            }
            if (req.getDate() != null && req.getDate()[0] != null) {
                Date startDate = req.getDate()[0];
                Date endDate = req.getDate()[1];
                query.between("date", startDate, endDate);
            }
        }
        // id值越大，日期越新
        // 需放在if外
        query.orderByDesc("id");

        IPage<Diary> data = baseMapper.selectPage(new Page<>(page, size), query);
        return Result.ok(data);
    }

    @Override
    public Result searchById(int id) {
        Diary diary = this.getById(id);
        if (diary == null) {
            return Result.error("查询失败");
        }
        return Result.ok(diary);
    }

    @Override
    public Result deleteById(int id) {
        boolean isSucceed = this.removeById(id);
        if (isSucceed) {
            return Result.ok();
        }
        return Result.error("删除失败，请重试");
    }

    public Result uploadImg(MultipartFile file, String prefix) {
        FileState fileState = ossService.addHeadImage(file, prefix);
        if (fileState.getState() <= 0) {
            return Result.error(fileState.getStateInfo());
        } else {
            return Result.ok(fileState.getUrl());
        }
    }

    /**
     * 判断账户今日有无提交日志
     *
     * @param uid
     * @return
     */
    private boolean diaryToday(int uid) {
        Date dateToday = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(dateToday);

        QueryWrapper<Diary> query = new QueryWrapper<>();
        query.eq("uid", uid);
        query.eq("date", today);

        Integer count = baseMapper.selectCount(query);
        return count == 1;
    }
}
