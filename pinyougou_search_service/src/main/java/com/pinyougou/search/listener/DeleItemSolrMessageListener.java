package com.pinyougou.search.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by ZJE on 2018/12/21
 */
public class DeleItemSolrMessageListener implements MessageListener{
    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public void onMessage(Message message) {
        //获取商品id
        TextMessage textMessage = (TextMessage) message;
        try {
            String goodsId = textMessage.getText();

            //参数：查询语言  *:*代表查询所有
            SolrDataQuery query = new SimpleQuery("item_goodsid:"+goodsId);
            solrTemplate.delete(query );
            solrTemplate.commit();//提交

            System.out.println("同步了索引库，商品id"+goodsId);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
