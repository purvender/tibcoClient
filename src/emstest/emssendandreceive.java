package emstest;

public class emssendandreceive {

	public static void main(String[] args) {
		
		JMSHandler myJMSHandler = new JMSHandler();
		
		myJMSHandler.sendQueueMessage("javaTestQueue", "<tag>some xml</tag>");
		myJMSHandler.receiveQueueMessage("javaTestQueue");
	}

}


