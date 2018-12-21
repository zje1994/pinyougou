package com.pinyougou.util;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by ZJE on 2018/12/17
 */

@Component
public class SolrUtil {

    @Autowired
    private SolrTemplate solrTemplate;


    @Autowired
    private TbItemMapper itemMapper;


    //将item导入到索引库中
    public void importItemData(){
        //查询上架的商品
        List<TbItem> itemList = itemMapper.findAllGrounding();

        for (TbItem item : itemList) {
            //从数据库中提取json数据 转换为map格式
            Map specMap = JSON.parseObject(item.getSpec(), Map.class);
            item.setSpecMap(specMap);
        }
        solrTemplate.saveBeans(itemList);
        solrTemplate.commit();
    }

    public static void main(String[] args) {
        //写*号导入了solr的spring配置还导入了dao的spring配置，因为要用mapper查数据库
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        SolrUtil solrUtil=  (SolrUtil) context.getBean("solrUtil");
        solrUtil.importItemData();

    }

}
