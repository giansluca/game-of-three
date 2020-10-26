package org.gmdev.event.message;

public class SendMessage implements Message {
	
	private String number;
	
	public SendMessage(String number) {
		this.number = number;
	}
	
	public String getNumber() {
		return number;
	}
	
}
