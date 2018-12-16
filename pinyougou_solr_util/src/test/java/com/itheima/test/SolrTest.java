package com.itheima.test;

import com.pinyougou.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZJE on 2018/12/16
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-solr.xml")
public class SolrTest {

    @Autowired
    private SolrTemplate solrTemplate;

    //添加商品到索引库
    @Test
    public void addTest(){
        List<TbItem> list = new ArrayList<>();
        for (long i = 1; i <= 100; i++) {
            TbItem item = new TbItem();
            item.setId(i);
            item.setTitle(i+"飞利浦 老人手机 (X2560) 喜庆红 移动联通2G手机 双卡双待");
            item.setBrand("飞利浦");
            item.setSeller(i+"飞利浦旗舰店");
            list.add(item);
        }
        solrTemplate.saveBeans(list);
        solrTemplate.commit();
    }


    @Test
    public void queryTest(){
        TbItem item = solrTemplate.getById(1L, TbItem.class);
        System.out.println(item.getId()+" ,"+item.getBrand()+" ,"+item.getSeller());
    }

    @Test
    public void deleteTest(){
        solrTemplate.deleteById("1");
        solrTemplate.commit();
    }

    @Test
    public void deleteAllTest(){
        SolrDataQuery query = new SimpleQuery("*:*");
        solrTemplate.delete(query);
        solrTemplate.commit();
    }


    @Test
    public void queryForPageTest(){
        //先查询所有
        Query query = new SimpleQuery("*:*");
        //设置分页查询起始值 默认值:0 从第一条开始查
        query.setOffset(3);
        //每页查询条数
        query.setRows(5);
        ScoredPage<TbItem> items = solrTemplate.queryForPage(query, TbItem.class);

        System.out.println("总记录数:"+items.getTotalElements());
        System.out.println("总页数:"+items.getTotalPages());

        //当前页数据
        List<TbItem> content = items.getContent();
        for (TbItem item : content) {
            System.out.println(item.getId()+" ,"+item.getBrand()+" ,"+item.getSeller());
        }
    }

    //条件查询 item_title包含9  item_seller包含5
    @Test
    public void queryMultiTest(){
        //先查询所有
        Query query = new SimpleQuery("*:*");
        //构建查询对象
        Criteria criteria = new Criteria("item_title").contains("9").and("item_seller").contains("5");
        //将构建好的查询条件赋给查询对象
        query.addCriteria(criteria);
        ScoredPage<TbItem> items = solrTemplate.queryForPage(query, TbItem.class);
        System.out.println("总记录数:"+items.getTotalElements());
        System.out.println("总页数:"+items.getTotalPages());

        //当前页数据
        List<TbItem> content = items.getContent();
        for (TbItem item : content) {
            System.out.println(item.getId()+" ,"+item.getBrand()+" ,"+item.getSeller());
        }
    }
}
