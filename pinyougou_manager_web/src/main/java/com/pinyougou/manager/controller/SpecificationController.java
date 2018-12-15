package com.pinyougou.manager.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.sellergoods.service.SpecificationService;
import entity.PageResult;
import entity.Result;
import groupEntity.Specification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by ZJE on 2018/12/3
 */


@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;

    //查询所有展示所有规格
    @RequestMapping("/findAll")
    public List<TbSpecification> findAll(){

        return specificationService.findAll();
    }

    //分页查询规格
    @RequestMapping("/findPage")
    public PageResult findPage(Integer pageNum,Integer pageSize){
        return specificationService.findPage(pageNum,pageSize);
    }

    //条件分页查询
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbSpecification specification,Integer pageNum,Integer pageSize){
        return specificationService.search(specification,pageNum,pageSize);

    }




    //新增规格
    @RequestMapping("/add")
    public Result add(@RequestBody Specification specification){
        try{
            specificationService.add(specification);
            return new Result(true,"添加成功!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

    //修改规格
    @RequestMapping("/update")
    public Result update(@RequestBody Specification specification){
        try{
            specificationService.update(specification);
            return new Result(true,"修改成功!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

    //根据id查询规格
    @RequestMapping("/findOne")
    public Specification findOne(Long id){
        return specificationService.findOne(id);
    }

    //根据id删除规格
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try{
            specificationService.delete(ids);
            return new Result(true,"修改成功!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }


    @RequestMapping("/selectSpecList")
    public List<Map> selectSpecList(){
        return specificationService.selectSpecList();
    }
}
