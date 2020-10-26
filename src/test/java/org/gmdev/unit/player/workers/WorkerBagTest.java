package org.gmdev.unit.player.workers;

import static org.gmdev.utils.PropertiesLoader.player_1;

import org.gmdev.player.workers.WorkerBag;
import org.gmdev.utils.PropertiesLoader;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

class WorkerBagTest {
	
	WorkerBag underTest;

	@Test
	void itShouldPass() {
		// Given
		PropertiesLoader.loadProperties(player_1);
		
		// When
		underTest = new WorkerBag();
		
		// Then
		assertThat(underTest.getPlayer()).isNotNull();
	}
	
}
