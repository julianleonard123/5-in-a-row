package com.julianleonard.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

	@Autowired
	private GameService gameService;

	@GetMapping("/game")
	public Game game(@RequestParam(value = "gameId", defaultValue = "1") Long gameId,
			@RequestHeader("playerName") String playerName) {

		return gameService.getGame(gameId, playerName);
	}

	@PutMapping("/join-game")
	public Game joinGame(@RequestParam(value = "gameId", defaultValue = "1") Long gameId,
			@RequestHeader("playerName") String playerName) {

		return gameService.joinGame(gameId, playerName);
	}

	@PutMapping("/move")
	public Game move(@RequestParam(value = "gameId", defaultValue = "1") Long gameId,
			@RequestHeader("playerName") String playerName,
			@RequestParam(value = "column", defaultValue = "1") Integer column) {

		return gameService.move(gameId, playerName, column);
	}
	
	@DeleteMapping("/game")
	public void endGame(@RequestParam(value = "gameId", defaultValue = "1") Long gameId) {

		gameService.endGame(gameId);
	}
}