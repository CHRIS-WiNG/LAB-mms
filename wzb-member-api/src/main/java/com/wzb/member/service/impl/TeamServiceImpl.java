package com.wzb.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wzb.member.base.Page;
import com.wzb.member.base.Result;
import com.wzb.member.entity.Team;
import com.wzb.member.mapper.TeamMapper;
import com.wzb.member.request.TeamREQ;
import com.wzb.member.service.ITeacherService;
import com.wzb.member.service.ITeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wzb
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements ITeamService {

    /**
     * 分页条件查询
     *
     * @param page 页码
     * @param size 每页显示记录数量
     * @param req  请求参数实体
     * @return Result
     */
    @Override
    public Result search(long page, long size, TeamREQ req) {
        // 封装查询条件
        QueryWrapper<Team> query = new QueryWrapper<>();
        if (req != null) {
            if (StringUtils.isNotBlank(req.getName())) {
                query.like("name", req.getName().trim());
            }
            if (StringUtils.isNotBlank(req.getLinkman())) {
                query.like("linkman", req.getLinkman().trim());
            }
            if (StringUtils.isNotBlank(req.getMobile())) {
                query.like("mobile", req.getMobile().trim());
            }
        }
        // 封装分页对象
        IPage<Team> data = baseMapper.selectPage(new Page<Team>(page, size), query);
        return Result.ok(data);
    }

    /**
     * 更新记录
     *
     * @param id   记录的id值
     * @param team 记录的实例
     * @return Result
     */
    @Override
    public Result update(int id, Team team) {
        if (team == null) return Result.error("修改失败");
        if (team.getId() == null) team.setId(id);
        // 去除字段前后的多余空格
        trim(team);
        // 判断姓名、联系人、联系电话是否为空
        if (StringUtils.isBlank(team.getName())) {
            return Result.error("姓名不能为空");
        }
        if (StringUtils.isBlank(team.getLinkman())) {
            return Result.error("联系人不能为空");
        }
        if (StringUtils.isBlank(team.getMobile())) {
            return Result.error("联系电话不能为空");
        }
        // 写入数据库
        boolean isSucceed = this.updateById(team);
        if (isSucceed) {
            return Result.ok();
        }
        return Result.error("修改失败");
    }

    /**
     * 新增学生记录
     *
     * @param team 记录实例
     * @return Result
     */
    @Override
    public Result saveTeam(Team team) {
        if (team == null) return Result.error("新增失败");
        // 删除字段前后多余空格
        trim(team);
        // 判断姓名、联系人、联系电话是否为空
        if (StringUtils.isBlank(team.getName())) {
            return Result.error("姓名不能为空");
        }
        if (StringUtils.isBlank(team.getLinkman())) {
            return Result.error("联系人不能为空");
        }
        if (StringUtils.isBlank(team.getMobile())) {
            return Result.error("联系电话不能为空");
        }
        // 写入数据库
        boolean isSucceed = this.save(team);
        if (isSucceed) {
            return Result.ok();
        }
        return Result.error("新增失败");
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @Override
    public Result searchById(int id) {
        Team team = this.getById(id);
        if (team == null) {
            return Result.error("查询失败");
        }
        return Result.ok(team);
    }

    @Autowired
    ITeacherService teacherService;

    /**
     * 删除记录，删除前需要判断是否被依赖
     *
     * @param id
     * @return
     */
    @Override
    public Result deleteById(int id) {
        // 通过teacher表查询是否有记录引用该组别记录的id值
        boolean hasRecord = teacherService.hasRecordByTeamId(id);
        if (hasRecord) {
            return Result.error("该组别中依旧存在教师，无法删除");
        }
        // 从数据库中删除
        boolean isSucceed = this.removeById(id);
        if (isSucceed) {
            return Result.ok();
        }
        return Result.error("删除失败");
    }

    /**
     * 去除 student实例中字段前后的空格
     *
     * @param team 记录实例
     */
    public void trim(Team team) {
        if (team == null) return;
        if (StringUtils.isNotBlank(team.getName())) team.setName(team.getName().trim());
        if (StringUtils.isNotBlank(team.getLinkman())) team.setLinkman(team.getLinkman().trim());
        if (StringUtils.isNotBlank(team.getMobile())) team.setMobile(team.getMobile().trim());
    }
}
