package org.gmdev.unit.player;

import static org.gmdev.utils.PropertiesLoader.player_1;
import static org.assertj.core.api.Assertions.*;

import org.gmdev.player.Player;
import org.gmdev.utils.PropertiesLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
	
	@BeforeEach
	void setUp() throws Exception {
		PropertiesLoader.loadProperties(player_1);
	}
	
	@Test
	void itShoulCreateThePlayerObject() {
		// Given
		// When
		Player player = Player.getPlayer();
		
		// Then
		assertThat(player).isNotNull();
		assertThat(player.name).isEqualTo("Player ONE");
		assertThat(player.otherPlayerName).isEqualTo("Player TWO");
		assertThat(player.listenPort).isEqualTo(8888);
		assertThat(player.otherPlayerPort).isEqualTo(9999);
		assertThat(player.otherPlayerAddress).isEqualTo("127.0.0.1");
		assertThat(player.sendFirstFlagPort).isEqualTo(6666);
	}
	

}
