package org.gmdev.unit.utils;

import static org.assertj.core.api.Assertions.*;

import java.util.Properties;

import org.gmdev.utils.PropertiesLoader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PropertyLoaderTest {
	
	@AfterEach
	void tearDown(TestInfo testInfo) throws Exception {
		PropertiesLoader.unloadProperties();
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"P1", "P2"})
	void itShouldLoadPlayersPropertiesFile(String playerName) {
		// Given
		// When
		PropertiesLoader.loadPlayerPropertiesFile(playerName);
		Properties prop = PropertiesLoader.getProperties();
		
		// Then
		assertThat(prop).isNotNull();
		assertThat(prop).isNotEmpty();
	}
	
	@Test
	void itShouldThrowIfPlayerNameIsWrong() {
		// Given 
		String playerName = "wrongplayer";
		
		// When
		// Then
		assertThatThrownBy(() -> PropertiesLoader.loadPlayerPropertiesFile(playerName))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(String.format("Wrong player name supplied: [ %s ]", playerName));
	}

	@Test
	void itShouldThrowIfFileIsNotFound() {
		// Given
		String filePath = "wrong-path";
		
		// When
		// Then
		assertThatThrownBy(() -> PropertiesLoader.loadProperties(filePath))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("Error loading property file.");
	}
	
	@Test
	void itShouldThrowIfGetFileBeforeLoading() {
		// Given
		// When
		// Then
		assertThatThrownBy(() -> PropertiesLoader.getProperties())
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("Property file not loaded.");
	}
	
	@Test
	void itShouldLoadAndGetThePropertiesFile() {
		// Given
		String filePath = "player_1.properties";
		
		// When
		PropertiesLoader.loadProperties(filePath);
		Properties prop = PropertiesLoader.getProperties();
		
		// Then
		assertThat(prop).isNotNull();
		assertThat(prop).isNotEmpty();
		
	}

}
