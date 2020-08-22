package com.wzb.member.base;

import java.util.List;

/**
 * @author Zebin Wang
 * @Title:
 * @Package
 * @Description:
 */
public class Page<T> extends com.baomidou.mybatisplus.extension.plugins.pagination.Page {

    public Page(long page, long size) {
        super(page, size);
    }

    // 设置为空，响应后就不会有recoreds数据
    @Override
    public List<T> getRecords() {return null;}

    public List<T> getRows() {
        return super.getRecords();
    }

}
