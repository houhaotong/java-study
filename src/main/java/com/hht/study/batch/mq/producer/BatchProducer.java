package com.hht.study.batch.mq.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author hht
 * @date 2022/3/2
 */
@Component
public class BatchProducer {

    @Resource
    RabbitTemplate rabbitTemplate;

    private static final String BATCH_DOWNLOAD_EXCHANGE="batch_exchange";

    public void sendDownLoad(String sn){
        rabbitTemplate.convertAndSend(BATCH_DOWNLOAD_EXCHANGE,"",sn);
    }
}
