package org.gmdev.unit.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.gmdev.event.subscriber.EventSubscriber;
import org.gmdev.event.subscriber.EventSubscriber.Subscriber;
import org.gmdev.player.workers.AbstractWorker;
import org.gmdev.utils.Factory;
import org.gmdev.utils.PropertiesLoader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class FactoryTest {
	
	Factory factory = Factory.getFactory();
	
	@BeforeEach
	void setUp() throws Exception {
		PropertiesLoader.loadPlayerPropertiesFile("P1");
		AbstractWorker.createWorkerBag();
	}
	
	@AfterEach
	void tearDown(TestInfo testInfo) throws Exception {
		PropertiesLoader.unloadProperties();
	}
	
	@ParameterizedTest
	@EnumSource(value = Subscriber.class)
	void itShouldLoadPlayersPropertiesFile(Subscriber subscriber) {
		// Given
		// When
		EventSubscriber eventSubscriber = factory.createEventSubscriber(subscriber);
		
		// Then
		assertThat(eventSubscriber).isNotNull();
	}

}
