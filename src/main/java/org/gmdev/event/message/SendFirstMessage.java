package org.gmdev.event.message;

import static org.gmdev.event.message.Message.InitNumberMode.*;

import java.util.*;


public class SendFirstMessage implements Message {
	
	private String initNumber;
	private InitNumberMode initNumberMode;
	
	public SendFirstMessage(Optional<Integer> initNumber) {
		setUpInitNumberAndMode(initNumber);
	}
	
	public String getInitNumber() {
		return initNumber;
	}

	public InitNumberMode getInitNumberMode() {
		return initNumberMode;
	}
	
	private void setUpInitNumberAndMode(Optional<Integer> initNumber) {
		if (initNumber.isPresent()) {
			int number = initNumber.get();
			if (!isValidNumber(number))
				throw new IllegalArgumentException(
						String.format("The starting number: [ %s ] is not valid", number));
			
			this.initNumber = String.valueOf(number);
			this.initNumberMode = MANUAL;
		} 
		else {
			this.initNumber = getInitRandomNumber();
			this.initNumberMode = RANDOM;
		}
	}
	
	private String getInitRandomNumber() {
		int min = 2;
		int max = 1000;
		Random random = new Random();
		
		return String.valueOf((random.nextInt((max - min) + 1) + min));
	}
	
	private boolean isValidNumber(int number) { 
		if (Integer.signum(number) == -1 || number == 0 || number == 1)
			return false; 
	
		return true;
	}
	
}
