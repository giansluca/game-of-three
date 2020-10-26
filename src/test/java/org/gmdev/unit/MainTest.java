package org.gmdev.unit;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import org.gmdev.Main;
import org.gmdev.player.PlayerManager;
import org.gmdev.utils.Factory;
import org.junit.jupiter.api.*;
import org.mockito.*;


class MainTest {
	
	@Mock
	PlayerManager playerManager;
	
	@Mock
	Factory factory;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void itShouldStartSetuPAllArgumentsAndPlayerProperties() throws Exception {
		// Given
		String[] args = {"-playername", "P1", "-sendfirst", "-initnumber", "99"};
		
		try (MockedStatic<Factory> theMock = Mockito.mockStatic(Factory.class)) {
		    theMock.when(Factory::getFactory).thenReturn(factory);
		    
			given(factory.createPlayerManager()).willReturn(playerManager);
			doNothing().when(playerManager).start(any());
		    
			// When
			Main.main(args);
			
			verify(playerManager, times(1)).start(any());
		}
		
	}
	
	@Test
	void itShouldThrowIfPlayerNameIsWrong() throws Exception {
		// Given
		String[] args = {"-sendfirst", "-initnumber", "99"};
		
		try (MockedStatic<Factory> theMock = Mockito.mockStatic(Factory.class)) {
		    theMock.when(Factory::getFactory).thenReturn(factory);
		
		
		    given(factory.createPlayerManager()).willReturn(playerManager);
		    doNothing().when(playerManager).start(any());
				    
		    // When
		    assertThatThrownBy(() -> Main.main(args))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("No player name supplied");
			
					
		    then(playerManager).shouldHaveNoInteractions();
		}
	}

}
