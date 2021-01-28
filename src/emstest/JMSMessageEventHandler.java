package emstest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class JMSMessageEventHandler implements MessageListener {

	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		
		try {
			System.out.println("Received event"+ textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
