package com.julianleonard.game.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	public Game getGame(Long gameId, String playerName) {
		Optional<Game> game = gameRepository.findById(gameId);
		if (game.isEmpty()) {
			game = createNewGame(playerName);
			return gameRepository.save(game.get());
		} else {
			return game.get();
		}
	}

	public Game joinGame(Long gameId, String playerName) {
		Optional<Game> game = gameRepository.findById(gameId);
		if (game.isEmpty()) {
			game = createNewGame(playerName);
			return gameRepository.save(game.get());
		} else {
			game.get().setGameStatus(GameStatus.AWAITING_PLAYER_1_MOVE);
			game.get().setPlayer2Name(playerName);
			game.get().setCurrentPlayer(playerName);
			return gameRepository.save(game.get());
		}
	}

	public Game move(Long gameId, String playerName, int column) {

		Optional<Game> game = gameRepository.findById(gameId);

		String playerToken = getPlayerToken(playerName, game.get());

		if (game.isEmpty()) {
			return joinGame(gameId, playerName);
		} else {
			game.get().setCurrentPlayer(playerName);
			if (game.get().isPlayer1(playerName)
					&& GameStatus.AWAITING_PLAYER_1_MOVE.equals(game.get().getGameStatus())) {
				if (move(column, playerToken, game.get().getGameGrid())) {
					game.get().setGameStatus(GameStatus.AWAITING_PLAYER_2_MOVE);
				}
			}

			if (game.get().isPlayer2(playerName)
					&& GameStatus.AWAITING_PLAYER_2_MOVE.equals(game.get().getGameStatus())) {
				if (move(column, playerToken, game.get().getGameGrid())) {
					game.get().setGameStatus(GameStatus.AWAITING_PLAYER_1_MOVE);
				}
			}
			
			
			if(doWeHaveAWinner(game.get().getGameGrid())) {
				if (game.get().isPlayer1(playerName)) {
					game.get().setGameStatus(GameStatus.GAME_OVER_PLAYER_1_WINNER);
				} else {
					game.get().setGameStatus(GameStatus.GAME_OVER_PLAYER_2_WINNER);
				}
			}
			gameRepository.save(game.get());
			
		}
		return game.get();
	}

	public void endGame(Long gameId) {
		Optional<Game> game = gameRepository.findById(gameId);
		if (!game.isEmpty()) {
			gameRepository.deleteById(gameId);
		}
	}

	private String getPlayerToken(String playerName, Game game) {
		if (game.isPlayer1(playerName)) {
			return "x";
		} else {
			return "o";
		}
	}

	private Optional<Game> createNewGame(String playerName) {
		Optional<Game> game = Optional.of(new Game());
		game.get().setPlayer1Name(playerName);
		game.get().setCurrentPlayer(playerName);
		game.get().setGameStatus(GameStatus.AWAITING_PLAYER_2_JOIN);
		return game;
	}

	private boolean move(int column, String playerToken, String[][] gameGrid) {
		if (!(column >= 1 && column <= 9)) {
			return false;
		}
		column = column - 1;

		for (int row = gameGrid.length - 1; row >= 0; row--) {
			if (gameGrid[row][column] == null) {
				gameGrid[row][column] = playerToken;
				return true;
			}
		}
		return false;
	}

	private boolean doWeHaveAWinner(String[][] gameGrid) {
		
		int countXTokens = 0;
		int countOTokens = 0;
		
		for (int row = 0; row < gameGrid.length; row++) {
			for (int column = 0; column < gameGrid[0].length; column++) {
				if ("x".equals(gameGrid[row][column])) {
					countXTokens++;
				}
				if ("o".equals(gameGrid[row][column])) {
					countOTokens++;
				}
			}
		}
		
		if (countXTokens >=5 || countOTokens >=5) {
			return true;
		} else {
			return false;
		}
	}

}