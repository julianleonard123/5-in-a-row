package com.julianleonard.game.service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private final long gameId;

	public Game() {
		gameId = 1;
	};

	public long getGameId() {
		return gameId;
	}

	private String player1Name;

	private String player2Name;

	private String currentPlayer;

	private GameStatus gameStatus;

	private String[][] gameGrid = new String[5][9];

	public String getPlayer1Name() {
		return player1Name;
	}

	public void setPlayer1Name(String player1Name) {
		this.player1Name = player1Name;
	}

	public String getPlayer2Name() {
		return player2Name;
	}

	public void setPlayer2Name(String player2Name) {
		this.player2Name = player2Name;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public String[][] getGameGrid() {
		return gameGrid;
	}

	public void setGameGrid(String[][] gameGrid) {
		this.gameGrid = gameGrid;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public boolean isMyTurn(String playerName) {
		boolean iAmPlayer1 = playerName.equals(this.getPlayer1Name());

		return (iAmPlayer1 && this.getGameStatus().equals(GameStatus.AWAITING_PLAYER_1_MOVE))
				|| (!iAmPlayer1 && this.getGameStatus().equals(GameStatus.AWAITING_PLAYER_2_MOVE));
	}

	public boolean isPlayer1(String playerName) {
		if (playerName != null && playerName.equals(this.getPlayer1Name())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isPlayer2(String playerName) {
		if (playerName != null && playerName.equals(this.getPlayer2Name())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("\r\n");
		for (int row = 0; row < this.gameGrid.length; row++) {
			for (int column = 0; column < this.gameGrid[0].length; column++) {
				if (this.gameGrid[row][column] == null) {
					sb.append("[   ] ");
				} else {
					sb.append("[ " + this.gameGrid[row][column] + " ] ");
				}
			}
			sb.append("\r\n");
		}
		sb.append("\r\n");
		sb.append("Player 1 Name: " + this.player1Name);
		sb.append("\r\n");
		if (this.player2Name != null) {
			sb.append("Player 2 Name: " + this.player2Name);
			sb.append("\r\n");	
		}
		
		if (this.getGameStatus().equals(GameStatus.GAME_OVER_PLAYER_1_WINNER)) {
			sb.append("\r\n");
			sb.append("Game Over. Player 1 is victorious!");
			sb.append("\r\n");
			sb.append("enter 'z' to quit game:");
		} else if (this.getGameStatus().equals(GameStatus.GAME_OVER_PLAYER_2_WINNER)) {
			sb.append("\r\n");
			sb.append("Game Over. Player 2 is victorious!");
			sb.append("\r\n");
			sb.append("enter 'z' to quit game:");
		} else if (this.getGameStatus().equals(GameStatus.AWAITING_PLAYER_2_JOIN)) {
			sb.append("\r\n");
			sb.append("Waiting for Player 2 to join.");
			sb.append("\r\n");
			sb.append("Enter 's' to check game status, enter 'z' to quit game:");
		} else if (isMyTurn(this.currentPlayer)) {
			sb.append("\r\n");
			sb.append("It’s your turn " + this.currentPlayer + ", please enter column (1-9):");
		} else {
			sb.append("\r\n");
			sb.append("Waiting for other player to move.");
			sb.append("\r\n");
			sb.append("Enter 's' to check game status, enter 'z' to quit game:");
		}

		return sb.toString();
	}
}
