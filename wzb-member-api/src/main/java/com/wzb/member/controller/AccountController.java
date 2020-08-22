package com.wzb.member.controller;


import com.wzb.member.base.Result;
import com.wzb.member.entity.Account;
import com.wzb.member.exception.CreateAccountException;
import com.wzb.member.request.AccountREQ;
import com.wzb.member.service.IAccountService;
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
@RequestMapping("/account")
public class AccountController {

    @Autowired
    IAccountService accountService;

    @PostMapping("list/search/{page}/{size}")
    public Result search(@PathVariable("page") long page,
                         @PathVariable("size") long size,
                         @RequestBody(required = false) AccountREQ req) {
        return accountService.search(page, size, req);
    }

    @PostMapping
    public Result add(@RequestBody Account account) {
        Result result = null;
        try {
            result = accountService.saveAccount(account);
        } catch (CreateAccountException e) {
            // 捕获事务中抛出的异常
            result = Result.error(e.getMessage());
            return result;
        }
        return result;
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable("id") int id) {
        return accountService.searchById(id);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") int id,
                         @RequestBody Account account) {
        if (id == 1 && account.getPermission() == 0) return Result.error("总管理员权限暂不可修改");
        return accountService.update(id, account);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") int id) {
        if (id == 1) return Result.error("管理员暂不可删除");
        return accountService.deleteById(id);
    }
}
