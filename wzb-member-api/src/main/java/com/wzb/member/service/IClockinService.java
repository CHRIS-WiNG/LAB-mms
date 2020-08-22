package com.wzb.member.service;

import com.wzb.member.base.Result;
import com.wzb.member.entity.Clockin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzb.member.exception.CreateClockInRecordException;
import com.wzb.member.request.ClockinREQ;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wzb
 */
public interface IClockinService extends IService<Clockin> {

    Result search(long page, long size, ClockinREQ req);

    Result saveRecord(Clockin clockin) throws CreateClockInRecordException;
}
