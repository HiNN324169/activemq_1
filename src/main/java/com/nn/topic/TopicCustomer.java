package com.nn.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 *  订阅模式 消费者
 */
public class TopicCustomer {

    private static final String TOCIP_USER_ID = "zhangsan";
    private static final String TOCIP_USER_ID2 = "lisi";
    private static String brokerURL = "tcp://192.168.1.107:61616";
    private static String topicName = "topic-test";

    public static void main(String[] args) throws JMSException, IOException {

//        1. 创建 connectionFactory 工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);

//        2、创建 connection 连接对象
        Connection connection = factory.createConnection();

//         3、设置订阅者ID
        connection.setClientID(TOCIP_USER_ID2);

//        4、开启连接
        connection.start();

//        5、使用 connection 创建 session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

//        6、使用 session 先创建 Topic 主题，再创建主题 持久订阅者
        Topic topic = session.createTopic(topicName);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark");

        System.out.println("消费者：" + TOCIP_USER_ID2);

//        7、异步监听消息
        topicSubscriber.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if(message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("接收到的消息为："+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.in.read(); // 测试的时候使用，目的 不让程序关闭

        topicSubscriber.close();
        session.close();
        connection.close();
        System.out.println("接收消息 完成......");
    }
}
