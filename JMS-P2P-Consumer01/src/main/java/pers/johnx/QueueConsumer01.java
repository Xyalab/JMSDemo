package pers.johnx;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 消费者 接收信息
 * @author Administrator
 */
public class QueueConsumer01 {

    public static void main(String[] args) throws JMSException, IOException {

        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.188.128:61616");

        //获取连接
        Connection connection = connectionFactory.createConnection();

        //启动连接
        connection.start();

        //获取session                        是否启动事务↓       消息确认模式↓
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建队列对象
        Queue queue = session.createQueue("testQueue1");

        //创建消息消费
        MessageConsumer messageConsumer = session.createConsumer(queue);

        //监听消息
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

                TextMessage textMessage = (TextMessage) message;
                try{
                    System.out.println("收到消息："+textMessage.getText());
                }catch(Exception e){
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
