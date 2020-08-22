package com.wzb.member.service;

import com.wzb.member.base.Result;
import com.wzb.member.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzb.member.exception.CreateAccountException;
import com.wzb.member.request.AccountREQ;
import com.wzb.member.request.PasswordREQ;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wzb
 */
public interface IAccountService extends IService<Account> {

    Result search(long page, long size, AccountREQ req);

    Result saveAccount(Account account) throws CreateAccountException;

    Result searchById(int id);

    Result update(int id, Account account);

    Result deleteById(int id);

    Result checkPwd(PasswordREQ req);

    Result updatePwd(PasswordREQ req);

    Result login(String username, String password);

    Result getUserInfo(String token);
}
