package com.wzb.member.entity;

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
@TableName("tb_account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账户主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账户email
     */
    private String email;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份类别
     */
    private Integer identity;

    /**
     * 权限类别
     */
    private Integer permission;

    /**
     * 号 根据身份类别判断是学号还是教工号
     */
    private String identityNum;


}
