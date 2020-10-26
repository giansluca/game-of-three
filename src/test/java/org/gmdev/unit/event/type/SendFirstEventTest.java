package org.gmdev.unit.event.type;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.gmdev.event.message.SendFirstMessage;
import org.gmdev.event.type.Event;
import org.gmdev.event.type.SendFirstEvent;
import org.junit.jupiter.api.*;

class SendFirstEventTest {
	
	SendFirstEvent underTest;
	
	@Test
	void itShoulGetMessageAndType() {
		// Given
		SendFirstMessage sendFirstMessage = new SendFirstMessage(Optional.of(99));
		
		// When
		underTest = new SendFirstEvent(sendFirstMessage);
		
		// Then
		assertThat(underTest.getMessage()).isEqualTo(sendFirstMessage);
		assertThat(underTest.getType()).isEqualTo(Event.EventType.SENDFIRST);	
	}

}
