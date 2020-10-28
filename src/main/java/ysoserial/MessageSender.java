package ysoserial;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.Serializable;

public class MessageSender {

    public void sendMessage(Object object) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://reesericke10:61616");
        factory.setExceptionListener(new ExceptionListener() {
            public void onException(JMSException e) {
                e.printStackTrace();
            }
        });
        factory.setUseAsyncSend(true);
        javax.jms.Connection connection = null;
        try {
            connection = factory.createConnection();
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        MessageProducer producer;
        Session session;
        try {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(session.createQueue("UsageTracking.Minnesota"));
            ObjectMessage obj = session.createObjectMessage((Serializable) object);
            producer.send(obj);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
