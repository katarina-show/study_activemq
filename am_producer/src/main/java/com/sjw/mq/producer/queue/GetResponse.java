package com.sjw.mq.producer.queue;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 接收消费者应答的类
 * 实现MessageListener接口
 */
@Component
public class GetResponse implements MessageListener {

    public void onMessage(Message message) {
        try {
            String textMsg = ((TextMessage)message).getText();
            System.out.println("GetResponse accept response : "+textMsg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
