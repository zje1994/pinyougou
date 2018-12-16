package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Created by ZJE on 2018/12/3
 */
public interface BrandService {
    List<TbBrand> findAll();

    PageResult findPage(Integer pageNum, Integer pageSize);

    void add(TbBrand brand);

    void update(TbBrand brand);

    TbBrand findOne(Long id);

    void delete(Long[] ids);

    List<Map> selectBrandList();
}
