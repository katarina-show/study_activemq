package com.sjw.mq.producer.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * 队列消息生产者，发送消息到队列
 */
@Component("queueSender")
public class QueueSender {

	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;

	@Autowired
	private GetResponse getResponse;

	public void send(String queueName,final String message){
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message msg = session.createTextMessage(message);

				//以下属于 “应答模式” 的相关代码
				//createTemporaryQueue创建一个临时的队列，该队列用于应答模式，接收消费者回应的临时队列
				Destination tempDest = session.createTemporaryQueue();

				//创建消费者
				MessageConsumer responseConsumer = session.createConsumer(tempDest);
				//配置消费者实现类
				responseConsumer.setMessageListener(getResponse);
				//往Message对象里塞入该队列名，set操作是为了消费者得到Message时，也可以getJMSReplyTo从而知道回应到哪个队列上
				msg.setJMSReplyTo(tempDest);

				//消费者应答的id，可自由定义，也可经过加密，可自由设置
                //也可把id存入缓存、RDBMS，主要目的是为了让  生产者知道消费者回应给自己的是自己发出的哪一条信息
				String uid = System.currentTimeMillis()+"";
				msg.setJMSCorrelationID(uid);

				return msg;
			}
		});
	}
}
