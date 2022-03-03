package com.hht.study.batch.mq.listener;

import com.hht.study.batch.model.ResRecord;
import com.hht.study.batch.util.ExcelUtil;
import com.hht.study.batch.util.SaveUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author hht
 * @date 2022/3/2
 */
@Component
@Slf4j
public class BatchListener {

    private static final String BATCH_DOWNLOAD_QUEUE="batch_queue";

    @RabbitListener(queues = {BATCH_DOWNLOAD_QUEUE})
    public void executeMsg(Channel channel, Message message){
        try {
            byte[] body = message.getBody();
            String sn = new String(body);
            List<ResRecord> resRecords = SaveUtil.BatchResult.get(sn);
            if(!CollectionUtils.isEmpty(resRecords)){
                ExcelUtil.buildExcelToLocal(resRecords,ResRecord.class);
            }
        }catch (Exception e){
            log.warn("消息处理异常,queue:{},message:{},error:{}",BATCH_DOWNLOAD_QUEUE,new String(message.getBody()),e);
        }finally {
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            } catch (IOException e) {
                log.warn("消息确认异常,queue:{},message:{}",BATCH_DOWNLOAD_QUEUE,message.getBody());
            }
        }
    }
}
