package com.nn.queue;

import com.nn.pojo.User;
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
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);

        /**
         *  放行 所有包的安全检查
         */
        factory.setTrustAllPackages(true);

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

        /**
         *  设置 生产者 的消息的持久性
         *
         *  持久性：发送一条消息到 mq 服务器，当服务器关闭时消息不丢失
         *  persistent 持久，non_persistent 非持久
         */
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

//        TextMessage 的使用
//        for (int i = 0; i < 10; i++) {
////            第七步：使用session对象创建一个message对象，创建一个TextMessage 对象
//            System.out.println("计数器：" + i);
//            TextMessage textMessage = session.createTextMessage("第一个 activemq 应用程序......" + i);
//
//            /**
//             *  设置 消息的 目的地
//             *  1：TOPIC 主题
//             *  2、QUEUE 队列
//             *  textMessage.setJMSDestination();
//             */
//
//            /**
//             *  设置 单个消息的持久性
//             *
//             *  持久性：发送一条消息到 mq 服务器，当服务器关闭时消息不丢失
//             *  persistent 持久，non_persistent 非持久
//             */
////            textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
//
//            /**
//             *  设置 消息的 有效期: 单位 秒。
//             *  0：永久有效
//             */
////            textMessage.setJMSExpiration(10L);
//
//            /**
//             *  设置 消息的优先级别
//             *  0-4：普通消息
//             *  5-9：优先消息
//             *
//             *  默认：4
//             */
////            textMessage.setJMSPriority(4);
//
//            /**
//             *  设置 messageID
//             */
//            textMessage.setJMSMessageID("14153415");
//
//
////        第八步：使用producer 对象发送消息
//            messageProducer.send(textMessage);
//        }

        /**
         *  MapMessage
         */
//        for (int i = 0; i <= 5; i++) {
//            MapMessage mapMessage = session.createMapMessage();
//            mapMessage.setInt("id",11111);
//            mapMessage.setString("name","张三");
//            messageProducer.send(mapMessage);
//        }

        /**
        *  ObjectMassage
        */
        for (int i = 0; i <= 5; i++) {
            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(new User(1,"小明"));
            objectMessage.setStringProperty("property","这是 User 实体类对象");
            messageProducer.send(objectMessage);
        }

        /**
         * 第 九步：关闭资源
         */
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("消息发送成功.....");
    }
}
