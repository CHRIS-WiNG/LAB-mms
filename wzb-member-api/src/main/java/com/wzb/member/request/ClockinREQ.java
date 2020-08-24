package com.wzb.member.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description:
 */
@Data
public class ClockinREQ implements Serializable {
    /**
     * 打卡记录创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private List<Date> date;

    /**
     * 打卡者姓名
     */
    private String name;
}
