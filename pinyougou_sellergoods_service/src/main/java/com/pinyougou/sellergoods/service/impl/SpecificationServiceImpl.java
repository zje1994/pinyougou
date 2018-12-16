package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.*;
import com.pinyougou.sellergoods.service.SpecificationService;
import entity.PageResult;
import groupEntity.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by ZJE on 2018/12/3
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private TbSpecificationMapper specificationMapper;

    //注入specificationOptionMapper
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;


    //查询所有展示所有商品
    @Override
    public List<TbSpecification> findAll() {
        return specificationMapper.selectByExample(null);
    }

    //分页查询商品
    @Override
    public PageResult findPage(Integer pageNum, Integer pageSize) {
        //基于pageHelper完成分页查询
        PageHelper.startPage(pageNum, pageSize);
        Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }


    //添加商品
    @Override
    public void add(Specification specification) {
        //保存规格
        TbSpecification tbSpecification = specification.getSpecification();
        specificationMapper.insert(tbSpecification);

        //关联规范
        List<TbSpecificationOption> specificationOptions = specification.getSpecificationOptions();
        for (TbSpecificationOption specificationOption : specificationOptions) {
            specificationOption.setSpecId(tbSpecification.getId());
            specificationOptionMapper.insert(specificationOption);
        }
    }

    //修改商品
    @Override
    public void update(Specification specification) {
        TbSpecification tbSpecification = specification.getSpecification();
        //修改规格数据
        specificationMapper.updateByPrimaryKey(tbSpecification);

        //更新规格列表数据  先删除规格列表
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(tbSpecification.getId());
        specificationOptionMapper.deleteByExample(example);

        //在添加规格列表
        List<TbSpecificationOption> specificationOptions = specification.getSpecificationOptions();
        for (TbSpecificationOption specificationOption : specificationOptions) {
            specificationOption.setSpecId(tbSpecification.getId());
            specificationOptionMapper.insert(specificationOption);
        }
    }

    @Override
    public Specification findOne(Long id) {
        //根据id查询TBSpecification对象
        Specification specification = new Specification();
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        specification.setSpecification(tbSpecification);

        //根据id查询tbSpecificationOption对象
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> tbSpecificationOptions = specificationOptionMapper.selectByExample(example);
        specification.setSpecificationOptions(tbSpecificationOptions);
        return specification;
    }

    //根据id删除商品
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            specificationMapper.deleteByPrimaryKey(id);
        }
    }

    //条件查询分页
    @Override
    public PageResult search(TbSpecification specification, Integer pageNum, Integer pageSize) {
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);
        //设置查询条件
        TbSpecificationExample example = new TbSpecificationExample();
        TbSpecificationExample.Criteria criteria = example.createCriteria();

        if (specification != null) {
            //获取规格查询条件
            String specName = specification.getSpecName();
            if (specName != null && !"".equals(specName)) {
                criteria.andSpecNameLike("%" + specName + "%");
            }
        }
        Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Map> selectSpecList() {
        return specificationMapper.selectSpecList();
    }
}
