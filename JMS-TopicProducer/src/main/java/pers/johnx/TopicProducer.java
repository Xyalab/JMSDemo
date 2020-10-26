package pers.johnx;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicProducer {

    public static void main(String[] args) throws JMSException {

        //创建连接工厂
        ConnectionFactory connectionFactory =new ActiveMQConnectionFactory("tcp://192.168.188.128:61616");

        //获取连接
        Connection connection = connectionFactory.createConnection();

        //启动连接
        connection.start();

        //获取session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建主题对象
        Topic topic = session.createTopic("test-topic");

        //创建消息生产者
        MessageProducer producer = session.createProducer(topic);

        //创建消息
        TextMessage textMessage = session.createTextMessage("Hello JMS-Topic!");

        //发送消息
        producer.send(textMessage);
        System.out.println("消息发送成功！");

        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }

}
