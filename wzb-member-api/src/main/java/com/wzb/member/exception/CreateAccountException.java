package com.wzb.member.exception;

/**
 * @author Zebin Wang
 * @Title: 创建Account事务中异常
 * @Package
 * @Description: 创建Account事务中抛出的异常，用于事务回滚
 * 事务不回滚原因分析：
 * Service方法中，把异常给try catch了，但catch里面只是打印了异常信息，没有手动抛出RuntimeException异常
 * Service方法中，抛出的异常不属于运行时异常（如IO异常或直接继承自Exception的自定义异常），因为Spring默认情况下是捕获到运行时异常就回滚
 *
 * 如何保证事务回滚:
 * 在主街上指定：rollbackFor = Exception.class
 */
public class CreateAccountException extends Exception {

    public CreateAccountException(String message) {
        super(message);
    }
}
