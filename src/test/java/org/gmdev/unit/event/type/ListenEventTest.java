package org.gmdev.unit.event.type;

import static org.assertj.core.api.Assertions.assertThat;

import org.gmdev.event.message.ListenMessage;
import org.gmdev.event.message.Message.StartMode;
import org.gmdev.event.type.Event;
import org.gmdev.event.type.ListenEvent;
import org.junit.jupiter.api.*;

class ListenEventTest {

	ListenEvent underTest;
	
	@Test
	void itShoulGetMessageAndType() {
		// Given
		ListenMessage listenMessage = new ListenMessage(StartMode.ONLY_LISTEN);
		
		// When
		underTest = new ListenEvent(listenMessage);
		
		// Then
		assertThat(underTest.getMessage()).isEqualTo(listenMessage);
		assertThat(underTest.getType()).isEqualTo(Event.EventType.LISTEN);	
	}
}
