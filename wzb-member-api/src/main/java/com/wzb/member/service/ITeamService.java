package com.wzb.member.service;

import com.wzb.member.base.Result;
import com.wzb.member.entity.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wzb.member.request.TeamREQ;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wzb
 */
public interface ITeamService extends IService<Team> {

    /**
     * 分页条件查询
     *
     * @param page 页码
     * @param size 每页显示记录数量
     * @param req  请求参数实体
     * @return Result
     */
    Result search(long page, long size, TeamREQ req);

    /**
     * 更新记录
     *
     * @param id   记录的id值
     * @param team 记录的实例
     * @return Result
     */
    Result update(int id, Team team);

    /**
     * 删除记录，删除前需要判断是否被依赖
     * @param id
     * @return
     */
    Result deleteById(int id);

    /**
     * 新增学生记录
     *
     * @param team 记录实例
     * @return Result
     */
    Result saveTeam(Team team);

    Result searchById(int id);
}
