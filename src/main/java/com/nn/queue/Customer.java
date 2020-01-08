package com.nn.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *  消费者
 */
public class Customer {

//    private static String brokerURL= "tcp://192.168.1.107:61616"; // 在 activemq 根目录下/conf/activemq.xml 中找
    private static String brokerURL= "tcp://127.0.0.1:61616"; // 在 activemq 根目录下/conf/activemq.xml 中找
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

//        第六步：使用session对象创建一个 consumer 对象
        MessageConsumer messageConsumer = session.createConsumer(queue);

//        第七步：监听消息：可以添加参数：等待监听的时间：type：Long
        Message message = messageConsumer.receive();

        if (message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("接收到消息，消息内容为："+textMessage.getText());
        }

//        第八步：关闭资源
        messageConsumer.close();
        session.close();
        connection.close();

        System.out.println("消息 接收完成.....");
    }
}
