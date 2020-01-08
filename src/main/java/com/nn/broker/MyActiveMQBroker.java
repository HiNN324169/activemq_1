package com.nn.broker;

import org.apache.activemq.broker.BrokerService;

/**
 *  内嵌 broker activemq 服务器
 */
public class MyActiveMQBroker {

    public static void main(String[] args) {

        BrokerService brokerService = new BrokerService();

        // 设置 使用的消息协议
        brokerService.setUseJmx(true);

        // 设置协议形式
        try {
            brokerService.addConnector("tcp://127.0.0.1:61616");
            brokerService.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("内嵌 activemq 启动成功");
    }

}
