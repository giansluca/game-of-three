package org.gmdev.unit.event.message;

import org.gmdev.event.message.Message.InitNumberMode;
import org.gmdev.event.message.SendFirstMessage;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

class SendFirstMessageTest {
	
	SendFirstMessage underTest;
	
	@Test
	void itShoulReturnRandomNumberBetween2And1000() {
		// Given
		underTest = new SendFirstMessage(Optional.empty());
		
		// Then
		String number = underTest.getInitNumber();
		InitNumberMode mode = underTest.getInitNumberMode();
		
		// When
		assertThat(Integer.valueOf(number)).isBetween(2, 1000);
		assertThat(mode).isEqualTo(InitNumberMode.RANDOM);
	}
	
	@Test
	void itShoulReturnThePassedInitNumber() {
		// Given
		underTest = new SendFirstMessage(Optional.of(99));
		
		// Then
		String number = underTest.getInitNumber();
		InitNumberMode mode = underTest.getInitNumberMode();
		
		// When
		assertThat(Integer.valueOf(number)).isEqualTo(99);
		assertThat(mode).isEqualTo(InitNumberMode.MANUAL);
	}
	
	@ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
	void itSholdReturnFalseForTheCurrentArgumentsNumber(int number) {
		// Given
		// When
		// Then
		assertThatThrownBy(() -> new SendFirstMessage(Optional.of(number)))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(String.format("The starting number: [ %s ] is not valid", number));
	}
	

}
