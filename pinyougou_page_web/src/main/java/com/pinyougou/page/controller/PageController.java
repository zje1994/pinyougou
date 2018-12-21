package com.pinyougou.page.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.page.service.PageService;
import com.pinyougou.pojo.TbItem;
import freemarker.template.Configuration;
import freemarker.template.Template;
import groupEntity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by ZJE on 2018/12/19
 */

@RestController
@RequestMapping("/page")
public class PageController {

    @Reference
    private PageService pageService;


    @Autowired
    private FreeMarkerConfig freeMarkerConfig;


    //生成页面详情页
    @RequestMapping("/genHtml")
    public String genHtml(Long goodId){
        try {
            //  创建模版步骤
            // 1.创建配置类Configuration
            Configuration configuration =freeMarkerConfig.getConfiguration();
            // 4.加载模板
            Template template = configuration.getTemplate("item.ftl");
            // 5.准备导出数据
            //创建一个模板使用的数据集，可以是 pojo 也可以是 map。一般是 Map。
            Goods goods = pageService.findOne(goodId);
            List<TbItem> itemList = goods.getItemList();
            for (TbItem item : itemList) {
                Map map = new HashMap();
                map.put("goods",goods);
                map.put("item",item);
                // 6.创建Writer对象
                FileWriter fileWriter = new FileWriter("G:\\java_project\\pinyougou\\item\\"+item.getId()+".html");
                // 7.通过template.process进行文件的生成
                template.process(map,fileWriter);
                // 8.关闭Writer对象
                fileWriter.close();
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

    }


}
