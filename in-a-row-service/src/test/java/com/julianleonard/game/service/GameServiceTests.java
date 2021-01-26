package com.julianleonard.game.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GameServiceTests {

	@Mock
	private GameRepository mockedGameRepository;

	@InjectMocks
    private GameService gameServiceClassUnderTest;
	
	private final static Long GAME_ID = (long) 1;
	private final static String PLAYER1_NAME = "Player1";
	private final static String PLAYER2_NAME = "Player2";

	@Test
	public void testGetGame() {

		// Arrange
		Optional<Game> game = Optional.of(new Game());
		game.get().setPlayer1Name(PLAYER1_NAME);
		game.get().setCurrentPlayer(PLAYER1_NAME);
		game.get().setGameStatus(GameStatus.AWAITING_PLAYER_1_MOVE);

		when(mockedGameRepository.findById(GAME_ID)).thenReturn(game);

		// Act
		Game actualGame = gameServiceClassUnderTest.getGame(GAME_ID, PLAYER1_NAME);
		
		// Assert
		assertEquals(actualGame.getPlayer1Name(), PLAYER1_NAME);
		assertEquals(actualGame.getCurrentPlayer(), PLAYER1_NAME);
		assertEquals(actualGame.getGameStatus(), GameStatus.AWAITING_PLAYER_1_MOVE);

	}
	
	@Test
	public void testJoinGameAsPlayer1() {

		// Arrange
		Game game = new Game();
		game.setPlayer1Name(PLAYER1_NAME);
		game.setCurrentPlayer(PLAYER1_NAME);
		game.setGameStatus(GameStatus.AWAITING_PLAYER_2_JOIN);
		
		when(mockedGameRepository.findById(GAME_ID)).thenReturn(Optional.empty());
		when(mockedGameRepository.save(any())).thenReturn(game);
			
		// Act
		Game actualGame = gameServiceClassUnderTest.joinGame(GAME_ID, PLAYER1_NAME);
		
		// Assert
		assertEquals(actualGame.getPlayer1Name(), PLAYER1_NAME);
		assertEquals(actualGame.getCurrentPlayer(), PLAYER1_NAME);
		assertEquals(actualGame.getGameStatus(), GameStatus.AWAITING_PLAYER_2_JOIN);

	}
	
	@Test
	public void testJoinGameAsPlayer2() {

		// Arrange
		Game gameBeforeJoin = new Game();
		gameBeforeJoin.setPlayer1Name(PLAYER1_NAME);
		gameBeforeJoin.setCurrentPlayer(PLAYER1_NAME);
		gameBeforeJoin.setGameStatus(GameStatus.AWAITING_PLAYER_2_JOIN);
		
		Game gameAfterJoin = new Game();
		gameAfterJoin.setPlayer1Name(PLAYER1_NAME);
		gameAfterJoin.setPlayer2Name(PLAYER2_NAME);
		gameAfterJoin.setCurrentPlayer(PLAYER2_NAME);
		gameAfterJoin.setGameStatus(GameStatus.AWAITING_PLAYER_1_MOVE);
		
		when(mockedGameRepository.findById(GAME_ID)).thenReturn(Optional.of(gameBeforeJoin));
		when(mockedGameRepository.save(any())).thenReturn(gameAfterJoin);
			
		// Act
		Game actualGame = gameServiceClassUnderTest.joinGame(GAME_ID, PLAYER2_NAME);
		
		// Assert
		assertEquals(actualGame.getPlayer1Name(), PLAYER1_NAME);
		assertEquals(actualGame.getPlayer2Name(), PLAYER2_NAME);
		assertEquals(actualGame.getCurrentPlayer(), PLAYER2_NAME);
		assertEquals(actualGame.getGameStatus(), GameStatus.AWAITING_PLAYER_1_MOVE);
	}
}