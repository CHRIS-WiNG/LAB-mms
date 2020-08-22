package com.wzb.member.service;

import com.wzb.member.base.Result;
import com.wzb.member.entity.Diary;
import com.wzb.member.request.DiaryREQ;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description:
 */
public interface IDiaryService {
    Result upload(MultipartFile file);

    Result uploadDiary(Diary diary);

    Result search(long page, long size, DiaryREQ req);

    Result searchById(int id);

    Result deleteById(int id);
}
