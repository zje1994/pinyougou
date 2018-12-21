package com.pinyougou.page.service;

import groupEntity.Goods;

/**
 * Created by ZJE on 2018/12/19
 */
public interface PageService {
    //查询商品详情
    public Goods findOne(Long goodsId);
}
