package org.gmdev.integration;

import static org.gmdev.utils.PropertiesLoader.*;
import static org.assertj.core.api.Assertions.*;

import org.gmdev.player.PlayerManager;
import org.gmdev.utils.PropertiesLoader;
import org.junit.jupiter.api.*;

import io.github.giansluca.jargs.Jargs;

class PlayerManagerTest {
	
	PlayerManager underTest1;
	PlayerManager underTest2;
	Jargs arguments1;
	Jargs arguments2;
	
	@Test
	void itShouldPlayerOneStartInSendFirstModeAndWin() throws Exception {
		// Given
		String winner = "Player ONE";
		
		String schema1 = "playername*, sendfirst%, initnumber#";
		String[] args1 = {"-playername", "P1", "-sendfirst", "-initnumber", "99"};
		arguments1 = new Jargs(schema1, args1);
		
		String schema2 = "playername*, sendfirst%, initnumber#";
		String[] args2 = {"-playername", "P2"};
		arguments2 = new Jargs(schema2, args2);
		
		// When
		// Player 2
		PropertiesLoader.loadProperties(player_2);
		underTest2 = new PlayerManager();
		underTest2.start(arguments2);
		
		// Player 1
		PropertiesLoader.loadProperties(player_1);
		underTest1 = new PlayerManager();
		underTest1.start(arguments1);
		
		while (!underTest1.gameover() && !underTest2.gameover())
			Thread.sleep(1000);
		
		// Then
		assertThat(underTest1.getPlayerWinnerName()).isEqualTo(winner);
		assertThat(underTest2.getPlayerWinnerName()).isEqualTo(winner);
	}
	
	@Test
	void itShouldPlayerOneStartInSendFirstModeAndLose() throws Exception {
		// Given
		String winner = "Player TWO";
		
		String schema1 = "playername*, sendfirst%, initnumber#";
		String[] args1 = {"-playername", "P1", "-sendfirst", "-initnumber", "2"};
		arguments1 = new Jargs(schema1, args1);
		
		String schema2 = "playername*, sendfirst%, initnumber#";
		String[] args2 = {"-playername", "P2"};
		arguments2 = new Jargs(schema2, args2);
		
		// When
		// Player 2
		PropertiesLoader.loadProperties(player_2);
		underTest2 = new PlayerManager();
		underTest2.start(arguments2);
		
		// Player 1
		PropertiesLoader.loadProperties(player_1);
		underTest1 = new PlayerManager();
		underTest1.start(arguments1);
				
		while (!underTest1.gameover() && !underTest2.gameover())
			Thread.sleep(1000);
			
		// Then
		assertThat(underTest1.getPlayerWinnerName()).isEqualTo(winner);
		assertThat(underTest2.getPlayerWinnerName()).isEqualTo(winner);
	}
	
	@Test
	void itShouldThrowIfPlayerIsAlreadyListening() throws Exception {
		// Given
		String schema = "playername*, sendfirst%, initnumber#";
		String[] args = {"-playername", "P2"};
		arguments1 = new Jargs(schema, args);
		arguments2 = new Jargs(schema, args);
		
		// Player 2
		PropertiesLoader.loadProperties(player_2);
		underTest1 = new PlayerManager();
		underTest1.start(arguments1);
				
		// Player 2 again
		PropertiesLoader.loadProperties(player_2);
		underTest2 = new PlayerManager();
		
		// When
		// Then
		assertThatThrownBy(() -> underTest2.start(arguments2))
			.hasMessageContaining("Player TWO Already started, port in use");
				
		// Finally
		underTest1.shutdown();
	}
	
	@Test
	void itShouldThrowIfPlayerIsAlreadyStartedInStartFirstMode() throws Exception {
		// Given
		String schema = "playername*, sendfirst%, initnumber#";
		String[] args1 = {"-playername", "P1", "-sendfirst"};
		arguments1 = new Jargs(schema, args1);
				
		String[] args2 = {"-playername", "P2", "-sendfirst"};
		arguments2 = new Jargs(schema, args2);
				
		// Player 1
		PropertiesLoader.loadProperties(player_1);
		underTest1 = new PlayerManager();
		new Thread(() -> {
			try {
				underTest1.start(arguments1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		
		Thread.sleep(1000);
					
		// Player 2
		
		PropertiesLoader.loadProperties(player_2);
		underTest2 = new PlayerManager();
		
		// When
		// Then
		assertThatThrownBy(() -> underTest2.start(arguments2))
			.hasMessageContaining("The other player is already running in 'START' mode");
		
		// Finally
		underTest1.shutdown();
	}
	

}
