package org.gmdev.unit.event.type;

import static org.assertj.core.api.Assertions.assertThat;

import org.gmdev.event.message.SendMessage;
import org.gmdev.event.type.Event;
import org.gmdev.event.type.SendEvent;
import org.junit.jupiter.api.*;


class SendEventTest {
	
	SendEvent underTest;
	
	@Test
	void itShoulGetMessageAndType() {
		// Given
		SendMessage sendMessage = new SendMessage("99");
		
		// When
		underTest = new SendEvent(sendMessage);
		
		// Then
		assertThat(underTest.getType()).isEqualTo(Event.EventType.SEND);
		assertThat(underTest.getMessage()).isEqualTo(sendMessage);
	}

}
