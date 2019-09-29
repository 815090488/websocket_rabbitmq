package com.websocket.springboot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {


    //不需要匹配
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("exchange2");
    }

    /**
     * 创建一个队列，合规队列
     * @return
     */
    @Bean
    public Queue queue2() {
        return new Queue("queue2", true);
    }

    /**
     * 创建一个队列，合规队列
     * @return
     */
    @Bean
    public Queue queue3() {
        return new Queue("queue3", true);
    }

    @Bean
    public Binding bindingFanoutExchange1(Queue queue2, FanoutExchange fanoutExchange) {
        return  BindingBuilder.bind(queue2).to(fanoutExchange);
    }

    @Bean
    public Binding bindingFanoutExchange2(Queue queue3, FanoutExchange fanoutExchange) {
        return  BindingBuilder.bind(queue3).to(fanoutExchange);
    }


}
