package com.wzb.member.exception;

/**
 * @author Zebin Wang
 * @Title: 添加打卡事务异常
 * @Package
 * @Description: 添加打卡事务中抛出的异常，若在修改用户记录的打卡数中出现错误，则抛出异常
 */
public class CreateClockInRecordException extends Exception {

    public CreateClockInRecordException(String message) {
        super(message);
    }
}
