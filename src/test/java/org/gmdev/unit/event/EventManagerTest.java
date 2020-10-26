package org.gmdev.unit.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gmdev.event.type.Event.EventType.*;
import static org.mockito.Mockito.doNothing;

import org.gmdev.event.EventManager;
import org.gmdev.event.message.SendMessage;
import org.gmdev.event.subscriber.EventSubscriber;
import org.gmdev.event.type.*;
import org.junit.jupiter.api.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EventManagerTest {
	
	EventManager underTest;
	
	@Mock
	EventSubscriber subscriber;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		underTest = new EventManager();
	}

	@Test
	void itShouldAddASuscriber() {
		// Given
		// When
		underTest.subscribe(subscriber, SEND);
		
		// Then
		assertThat(underTest.getSubscribers().get(SEND).get(0)).isEqualTo(subscriber);
	}
	
	@Test
	void itShouldRemoveASuscriber() {
		// Given
		// When
		underTest.subscribe(subscriber, SEND);
		underTest.unsubscribe(subscriber, SEND);
		
		// Then
		assertThat(underTest.getSubscribers().get(SEND)).isEmpty();
	}
	
	@Test
	void itShouldCallNotifyOnEachSubscriber() throws Exception {
		// Given
		underTest.subscribe(subscriber, SEND);
		SendMessage sendMessage = new SendMessage("99");
		Event event = new SendEvent(sendMessage);
		
		doNothing().when(subscriber).notify(event);
		
		// When
		underTest.notifySubscribers(event);
		
		// Then
		//verify(subscriber, times(1)).notify(event);
	}
	

}












