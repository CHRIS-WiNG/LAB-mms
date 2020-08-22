package com.wzb.member.controller;


import com.wzb.member.base.Result;
import com.wzb.member.entity.Clockin;
import com.wzb.member.exception.CreateClockInRecordException;
import com.wzb.member.request.ClockinREQ;
import com.wzb.member.service.IClockinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wzb
 */
@RestController
@RequestMapping("/clockin")
public class ClockinController {

    @Autowired
    private IClockinService clockinService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/list/search/{page}/{size}")
    public Result search(@PathVariable("page") long page,
                         @PathVariable("size") long size,
                         @RequestBody(required = false) ClockinREQ req) {

        return clockinService.search(page, size, req);
    }


    // post /student
    @PostMapping()
    public Result add(@RequestBody Clockin clockin) {
        Result result = null;
        try {
            // 捕获异常
            result = clockinService.saveRecord(clockin);
        } catch (CreateClockInRecordException e) {
            result = Result.error(e.getMessage());
            return result;
        }
        return result;
    }
}
