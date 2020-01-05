package com.nn.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息 生产者
 */
public class ProducerAsynchronous {

    private static String brokerURL = "tcp://192.168.1.107:61616"; // 在 activemq 根目录下/conf/activemq.xml 中找
    private static String queueName = "queue-hello";

    public static void main(String[] args) throws JMSException {

//        第一步：创建ConnectionFactory对象，需要指定服务端口以及端口号
        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);

//        第二步：使用connectionFactory对象 创建一个connection 对象
        Connection connection = factory.createConnection();

//        第三步：开启连接，调用connection对象的start方法
        connection.start();

//        第四步：使用connection 创建一个session对象
        /**
         * 第一个参数：是否开启事务
         * 第二个参数：是 接受者签收状态
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

//        第五步：使用session对象创建一个destination对象（topic，queue），此处创建一个queue对象。
        Queue queue = session.createQueue(queueName);

//        第六步：使用session对象创建一个producer对象
        MessageProducer messageProducer = session.createProducer(queue);


        for (int i = 0; i < 10; i++) {
//            第七步：使用session对象创建一个message对象，创建一个TextMessage 对象
            System.out.println("计数器："+i);
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("第一个 activemq 应用程序......" + i);

//        第八步：使用producer 对象发送消息
            messageProducer.send(textMessage);
        }

//        第九步：关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("消息发送成功.....");
    }
}
