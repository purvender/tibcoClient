package emstest;


import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.*;




public class JMSHandler {
	
	static String serverUrl = "tcp://localhost:7222"; 
    static String userName = "admin";
    static String password = "";
	
	public void sendQueueMessage(String queueName, String messageStr) {
		
		Properties props = new Properties();
		Connection connection = null;
		InitialContext jndiContext;
        Session session = null;
        MessageProducer msgProducer = null;
        Destination destination = null;
        TextMessage msg;
        ConnectionFactory factory;
        
        
        System.out.println("Publishing to queue '" + queueName+ "'");
        
       
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.tibco.tibjms.naming.TibjmsInitialContextFactory");
		props.put(Context.PROVIDER_URL, "tibjmsnaming://localhost:7222");
		
        
        try {		    
        	jndiContext = new InitialContext(props);
        	factory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory");
        	connection = factory.createConnection(userName, password);
        	session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
        	destination = (javax.jms.Queue)jndiContext.lookup(queueName);
        	msgProducer = session.createProducer(null);
		    msg = session.createTextMessage();
		    msg.setText(messageStr);
		    msgProducer.send(destination, msg);
		    System.out.println("Published message: " + messageStr);
		    connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void receiveQueueMessage(String queueName)
	{
		Properties props = new Properties();
		Connection connection = null;
		InitialContext jndiContext;
        Session session = null;
        Destination destination = null;
        MessageConsumer msgConsumer=null;
        TextMessage msg;
        ConnectionFactory factory = new com.tibco.tibjms.TibjmsConnectionFactory(serverUrl);
		
        System.out.println("Receiving from queue '" + queueName+ "'");
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.tibco.tibjms.naming.TibjmsInitialContextFactory");
		props.put(Context.PROVIDER_URL, "tibjmsnaming://localhost:7222");
        
        try {
        	jndiContext = new InitialContext(props);
        	factory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory");
        	connection = factory.createConnection(userName, password);
        	session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
        	destination = (javax.jms.Queue)jndiContext.lookup(queueName);
        	MessageConsumer consumer = session.createConsumer(destination);
        	connection.start();
        	msg = (TextMessage) consumer.receive();
        	System.out.println("Received message: " + msg.getText());
        	connection.close();
        } catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
