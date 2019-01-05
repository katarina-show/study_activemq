package com.sjw.mq.consumer.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * 负责向生产者发送应答信息
 */
@Component
public class ReplyTo {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(final String consumerMsg, Message produerMessage) throws JMSException {

        //getJMSReplyTo会获取到生产者setJMSReplyTo的值，拿到临时队列
        jmsTemplate.send(produerMessage.getJMSReplyTo(), new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                Message msg = session.createTextMessage("QueueReceiver1 accept msg"
                        +consumerMsg);
                return msg;
            }
        });

    }

}
