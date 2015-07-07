package annotator;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import message.Message;

public class MessageViewer extends JTextPane{
	Message currentMessage;
	public MessageViewer(){
		super();
		this.setContentType("text/html");			
	}
	
	public MessageViewer(JFrame frame){
		super();
		this.setContentType("text/html");				
		frame.add(this);
	}

	public void display(Message message) {
		currentMessage=message;
		setText(message.getHTMLView());		
		
	}

	public Message getCurrentMessage() {		
		return currentMessage;
	}
}
