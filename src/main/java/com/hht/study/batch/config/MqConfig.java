package com.hht.study.batch.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hht
 * @date 2022/3/2
 */
@Configuration
public class MqConfig {

    private static final String BATCH_EXCHANGE="batch_exchange";

    private static final String BATCH_QUEUE="batch_queue";

    @Bean
    public AmqpAdmin batchAmqpAdmin(ConnectionFactory connectionFactory){

        FanoutExchange fanoutExchange = new FanoutExchange(BATCH_EXCHANGE);
        Queue queue=new Queue(BATCH_QUEUE);
        Binding binding=BindingBuilder.bind(queue).to(fanoutExchange);

        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);

        rabbitAdmin.declareExchange(fanoutExchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
        return rabbitAdmin;
    }
}
