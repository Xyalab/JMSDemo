package pers.johnx;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ProduceMsgSend {

    public static void main(String[] args) throws JMSException {

        //创建连接工厂，连接到指定msg消息中间件
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.188.128:61616");

        //使用连接工厂创建连接
        Connection connection = connectionFactory.createConnection();

        //使用连接对象，创建会话对象                      ↓是否开启事务    ↓消息确认模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // (自动确认)AUTO_ACKNOWLEDGE = 1
        // (客户端手动确认)CLIENT_ACKNOWLEDGE = 2  
        // (自动批量确认)DUPS_OK_ACKNOWLEDGE = 3    
        // (事务提交并确认)SESSION_TRANSACTED = 0    

        //创建队列对象
        Queue queue = session.createQueue("testQueue1");

        //创建消息生产者对象
        MessageProducer producer = session.createProducer(queue);

        //创建消息
        TextMessage textMessage = session.createTextMessage("Hello JMS!");

        //发送消息
        producer.send(textMessage);
        System.out.println("消息发送成功！");

        //关闭资源
        producer.close();
        session.close();
        connection.close();


    }
}
