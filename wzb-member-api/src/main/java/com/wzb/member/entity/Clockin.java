package com.wzb.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("tb_clockin")
public class Clockin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 打卡记录创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date date;

    /**
     * 打卡记录创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm:ss")
    private Date time;

    /**
     * 打卡者姓名
     */
    private String name;

    /**
     * 打卡心情类型
     */
    private Integer emotionType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 打卡者的账户id
     */
    private Integer uid;


}
