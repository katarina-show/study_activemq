package com.sjw;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsConsumer {

    //默认连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认连接地址
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {

        //1.连接工厂：创建一个JMs连接
        ConnectionFactory connectionFactory;

        //2.JMS连接：客户端和服务器之间的一个连接。
        Connection connection;

        //3.JMS会话：客户和服务器会话的状态，建立在连接之上的
        Session session;

        //4.JMS目的：消息队列
        Destination destination;

        //5.消息消费者
        MessageConsumer messageConsumer;

        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(JmsConsumer.USERNAME,
                JmsConsumer.PASSWORD, JmsConsumer.BROKEURL);

        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个连接HelloWorld的消息队列
            destination = session.createQueue("HelloWAM");

            //创建消息消费者
            messageConsumer = session.createConsumer(destination);

            //读取消息
            while(true){
                TextMessage textMessage = (TextMessage)messageConsumer.receive(10000);
                if(textMessage != null){
                    System.out.println("Accept msg : "+textMessage.getText());
                }else{
                    break;
                }

            }


        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
