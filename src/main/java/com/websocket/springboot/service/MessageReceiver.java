package com.websocket.springboot.service;

import com.rabbitmq.client.Channel;
import com.websocket.springboot.controller.WebSocketController;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;


@Component
//@RabbitListener(queues = "queue1")
@RabbitListener(queues = "queue2")
//@RabbitListener(queues = "queue5")
public class MessageReceiver {

    private static CopyOnWriteArraySet<MessageReceiver> webSocketSet = new CopyOnWriteArraySet<MessageReceiver>();

    private Session session;

    @RabbitHandler
    public void process(Object object, Channel channel, Message message) throws IOException {

        try {
            Thread.sleep(3000);
            System.out.println("睡眠3s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            //告诉服务器收到消息，已经被我消费了，可以在队列删掉，否则消息服务器没处理掉 后续还会再发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("接受消息1"+object);
        }catch (Exception e){
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
            System.out.println("丢弃这条消息1");
        }

    }
}
