package pers.johnx;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class TopicConsumer01 {

    public static void main(String[] args) throws JMSException, IOException {

        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.188.128:61616");

        //获取连接
        Connection connection = connectionFactory.createConnection();

        //启动连接
        connection.start();

        //获取Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建主题对象
        Topic topic = session.createTopic("test-topic");

        //创建消息消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);

        //监听消息
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

                TextMessage textMessage = (TextMessage) message;

                try {
                    System.out.println("接收消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //等待键盘输入
        System.in.read();

        //关闭资源
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
