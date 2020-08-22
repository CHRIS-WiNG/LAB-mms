package com.wzb.member.controller;


import com.wzb.member.base.Result;
import com.wzb.member.entity.Team;
import com.wzb.member.request.TeamREQ;
import com.wzb.member.service.ITeamService;
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
@RequestMapping("/team")
public class TeamController {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ITeamService teamService;

    @PostMapping("/list/search/{page}/{size}")
    public Result search(@PathVariable("page") long page,
                         @PathVariable("size") long size,
                         @RequestBody(required = false) TeamREQ req) {
        return teamService.search(page, size, req);
    }

    @PostMapping("") // /team
    public Result add(@RequestBody Team team) {
        return teamService.saveTeam(team);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable("id") int id) {
        return teamService.searchById(id);

    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") int id,
                         @RequestBody Team team) {
        return teamService.update(id, team);
    }

    /**
     * 通过供应商id删除数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") int id) {
        return teamService.deleteById(id);
    }
}
