package Entities;
import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable{
	private static final long serialVersionUID = -4880706272477371235L;
	
	private MessageType messageType;
	private Object messageData;
	
	public Message(MessageType messageType, Object messageData) {
		this.messageData=messageData;
		this.messageType=messageType;
	}
	
	public MessageType getMessageType() {
		return messageType;
	}
	
	public Object getMessageData() {
		return messageData;
	}
	
	public String toString() {
		return "MESSAGE";
	}
}
