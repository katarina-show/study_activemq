package com.sjw;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProducer {

    //默认连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认连接地址
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    //发送的消息数量
    private static final int SENDNUM = 10;

    public static void main(String[] args) {

        //1.连接工厂：创建一个JMs连接
        ConnectionFactory connectionFactory;

        //2.JMS连接：客户端和服务器之间的一个连接。
        Connection connection = null;

        //3.JMS会话：客户和服务器会话的状态，建立在连接之上的
        Session session;

        //4.JMS目的：消息队列
        Destination destination;

        //5.消息生产者
        MessageProducer messageProducer;

        connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEURL);

        try {
            connection = connectionFactory.createConnection();
            connection.start();
            /*
            createSession参数取值
            * 1、为true表示启用事务  为true时忽略第二个参数的值
            * 2、消息的确认模式
            * AUTO_ACKNOWLEDGE  自动签收
            * CLIENT_ACKNOWLEDGE 客户端自行手动调用acknowledge方法签收
            * DUPS_OK_ACKNOWLEDGE 不是必须签收，消费可能会重复发送
            * SESSION_TRANSACTED 为true时启用该模式
            * 在第二次重新传送消息的时候，消息
               头的JmsDelivered会被置为true标示当前消息已经传送过一次，
               客户端需要进行消息的重复处理控制。
            * */
            session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("HelloWAM");
            messageProducer = session.createProducer(destination);
            for(int i=0;i<SENDNUM;i++){
                String msg = "发送消息"+i+" "+System.currentTimeMillis();
                TextMessage message = session.createTextMessage(msg);
                System.out.println("发送消息:"+msg);
                messageProducer.send(message);
            }
            session.commit();


        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}
