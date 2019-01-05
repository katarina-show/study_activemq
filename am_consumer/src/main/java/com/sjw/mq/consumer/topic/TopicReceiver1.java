package com.sjw.mq.consumer.topic;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Topic消息监听器1
 * TopicReceiver1和TopicReceiver2会同时接收到Sender发出的消息
 */
@Component
public class TopicReceiver1 implements MessageListener {

	public void onMessage(Message message) {
		try {
			String textMsg = ((TextMessage)message).getText();
			System.out.println("TopicReceiver1 accept msg : "+textMsg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}
