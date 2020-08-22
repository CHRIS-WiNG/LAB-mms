package com.wzb.member.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description:
 */

@Getter
@AllArgsConstructor
public enum  ResultEnum {

    SUCCESS(2000, "成功"),
    ERROR(900, "失败");


    private final Integer code;

    private final String desc;
}
