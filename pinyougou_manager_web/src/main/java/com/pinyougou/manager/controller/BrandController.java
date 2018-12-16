package com.pinyougou.manager.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by ZJE on 2018/12/3
 */


@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    //查询所有展示所有商品
    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){

        return brandService.findAll();
    }

    //分页查询商品
    @RequestMapping("/findPage")
    public PageResult findPage(Integer pageNum,Integer pageSize){
        return brandService.findPage(pageNum,pageSize);
    }

    //新增商品
    @RequestMapping("/add")
    public Result add(@RequestBody TbBrand brand){
        try{
            brandService.add(brand);
            return new Result(true,"添加成功!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

    //修改商品
    @RequestMapping("/update")
    public Result update(@RequestBody TbBrand brand){
        try{
            brandService.update(brand);
            return new Result(true,"修改成功!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

    //根据id查询商品
    @RequestMapping("/findOne")
    public TbBrand findOne(Long id){
        return brandService.findOne(id);
    }

    //根据id删除商品
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try{
            brandService.delete(ids);
            return new Result(true,"修改成功!");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

    @RequestMapping("/selectBrandList")
    public List<Map> selectBrandList(){
        return brandService.selectBrandList();
    }






}
