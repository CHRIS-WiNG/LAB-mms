package com.wzb.member.controller;

import com.wzb.member.base.Result;
import com.wzb.member.entity.Diary;
import com.wzb.member.request.DiaryREQ;
import com.wzb.member.request.StudentREQ;
import com.wzb.member.service.IDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description:
 */
@RestController
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    IDiaryService diaryService;

    @PostMapping(value = "/file")
    public Result uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return diaryService.upload(file);
    }

    @PostMapping()
    public Result uploadDiary(@RequestBody() Diary diary) {
        return diaryService.uploadDiary(diary);
    }

    @PostMapping("/list/search/{page}/{size}")
    public Result search(@PathVariable("page") long page,
                         @PathVariable("size") long size,
                         @RequestBody(required = false) DiaryREQ req) {

        return diaryService.search(page, size, req);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable("id") int id) {
        // Integer可能为null，int一定会返回一个值
        return diaryService.searchById(id);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") int id) {
        return diaryService.deleteById(id);
    }
}
