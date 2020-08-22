package com.wzb.member.controller;

import com.wzb.member.base.Result;
import com.wzb.member.entity.Account;
import com.wzb.member.request.PasswordREQ;
import com.wzb.member.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    IAccountService accountService;

    /**
     * 校验旧密码
     * @param req
     * @return
     */
    @PostMapping("/pwd")
    public Result checkPwd(@RequestBody PasswordREQ req) {
        return accountService.checkPwd(req);
    }

    /**
     * 更新密码
     * @param req
     * @return
     */
    @PutMapping("/pwd")
    public Result updatePwd(@RequestBody PasswordREQ req) {
        if (req.getUserId().equals(1)) return Result.error("展示中，管理员密码暂不可修改");
        return accountService.updatePwd(req);
    }

    /**
     * 登录验证并返回token
     * @param account
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        return accountService.login(account.getUsername(), account.getPassword());
    }

    /**
     * 通过token获取用户信息
     * @param token
     * @return
     */
    @GetMapping("/info")
    public Result getUserInfo(@RequestParam("token") String token) {
        return accountService.getUserInfo(token);
    }

    @PostMapping("/logout")
    public Result logout() {
        return Result.ok();
    }
}
