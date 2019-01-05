package com.sjw.mq.consumer.queue;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 队列消息监听器
 */
@Component
public class QueueReceiver2 implements MessageListener {

	public void onMessage(Message message) {
		try {
			String textMsg = ((TextMessage)message).getText();
			System.out.println("QueueReceiver2 accept msg : "+textMsg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
