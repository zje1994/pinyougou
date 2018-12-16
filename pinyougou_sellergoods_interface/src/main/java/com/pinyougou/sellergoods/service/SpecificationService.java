package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSpecification;
import entity.PageResult;
import groupEntity.Specification;

import java.util.List;
import java.util.Map;

/**
 * Created by ZJE on 2018/12/3
 */
public interface SpecificationService {
    List<TbSpecification> findAll();

    PageResult findPage(Integer pageNum, Integer pageSize);

    void add(Specification specification);

    void update(Specification specification);

    Specification findOne(Long id);

    void delete(Long[] ids);

    PageResult search(TbSpecification specification, Integer pageNum, Integer pageSize);

    List<Map> selectSpecList();
}
