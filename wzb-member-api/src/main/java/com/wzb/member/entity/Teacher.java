package com.wzb.member.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author wzb
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 教工号
     */
    private String tchNum;

    /**
     * 姓名
     */
    private String name;

    /**
     * email
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 办公室
     */
    private String office;

    /**
     * 职级
     */
    private Integer level;

    /**
     * 所在组号
     */
    private Integer teamId;

    /**
     * 账户id
     */
    private Integer uid;

    /**
     * 所在组名
     *
     * @TableField(exist = false): 标识它不是tb_teacher表中字段，不然会报错
     */
    @TableField(exist = false)
    private String teamName;

}
