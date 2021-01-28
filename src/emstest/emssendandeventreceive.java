package emstest;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class emssendandeventreceive {

	public static void main(String[] args) {
		
		String serverUrl = "tcp://localhost:7222"; 
	    String userName = "admin";
	    String password = "";
		
		JMSHandler myJMSHandler = new JMSHandler();
		JMSMessageEventHandler messageEventHandler; 
		
		myJMSHandler.sendQueueMessage("javaTestQueue", "<tag>some xml</tag>");
		
		
		Properties props = new Properties();
		Connection connection = null;
		InitialContext jndiContext;
        Session session = null;
        Destination destination = null;
        MessageConsumer msgConsumer=null;
        TextMessage msg;
        ConnectionFactory factory = new com.tibco.tibjms.TibjmsConnectionFactory(serverUrl);
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.tibco.tibjms.naming.TibjmsInitialContextFactory");
		props.put(Context.PROVIDER_URL, "tibjmsnaming://localhost:7222");
        
        try {
        	jndiContext = new InitialContext(props);
        	factory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory");
        	connection = factory.createConnection(userName, password);
        	session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
        	destination = (javax.jms.Queue)jndiContext.lookup("javaTestQueue");
        	
        	msgConsumer = session.createConsumer(destination);
        	msgConsumer.setMessageListener(new JMSMessageEventHandler());
        
        	connection.start();
        	Thread.sleep(5000);
    		session.close();
        	connection.close();
        	System.out.println("Closed connection");
        } catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
