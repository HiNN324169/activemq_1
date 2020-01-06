package com.nn.topic;


import org.apache.activemq.ActiveMQConnectionFactory;
import sun.net.www.MeteredStream;

import javax.jms.*;

/**
 *  主题生产者
 */
public class TopicProducer {

    private static String brokerURL = "tcp://192.168.1.107:61616";
    private static String topicName = "topic-test";

    public static void main(String[] args) throws JMSException {

//        1、创建 connectionFactory 工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);

//        2、使用 工厂 创间连接
        Connection connection = factory.createConnection();

//        3、开启连接
        connection.start();

//        4、使用 connection 创建 session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(topicName);
        MessageProducer messageProducer = session.createProducer(topic);

//        5、发送失调
        for (int i = 0; i <= 10 ; i++) {

            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("topic message....."+i);

            messageProducer.send(textMessage);
        }
//        第九步：关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("消息发送成功.....");

    }
}
