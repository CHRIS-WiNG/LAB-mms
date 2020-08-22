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
@TableName("tb_team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组别主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 组名
     */
    private String name;

    /**
     * 负责人姓名
     */
    private String linkman;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 备注
     */
    private String remark;


}
