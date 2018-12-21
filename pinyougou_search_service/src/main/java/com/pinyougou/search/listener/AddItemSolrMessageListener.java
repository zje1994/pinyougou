package com.pinyougou.search.listener;


import com.pinyougou.mapper.TbItemMapper;

import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

/**
 * Created by ZJE on 2018/12/21
 */
public class AddItemSolrMessageListener implements MessageListener{

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private TbItemMapper tbItemMapper;


    @Override
    public void onMessage(Message message) {

        TextMessage textMessage = (TextMessage) message;

        try {
            String text = textMessage.getText();


            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andGoodsIdEqualTo(Long.parseLong(text));
            List<TbItem> itemList = tbItemMapper.selectByExample(example);
            solrTemplate.saveBeans(itemList);
            solrTemplate.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
