package com.julianleonard.game.client;

import com.julianleonard.game.service.Game;
import java.util.Scanner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ClientApplication {

	private static String SERVER_URL = "http://localhost:8080/";

	public static void main(String[] args) {
		System.out.println("Welcome!");
		System.out.print("Enter your name:");

		Scanner scanner = new Scanner(System.in);
		String playerName = scanner.next();

		Game game = joinGame(playerName);

		System.out.println(game.toString());
		
		int column = 0;

		while (scanner.hasNext() == true) {
			String input = scanner.next();
			game = game(playerName);
			if ("z".equals(input)) {
				System.out.println("Exiting game, bye!");
				scanner.close();
				endGame();
				break;
			}
			if (game.isMyTurn(playerName)) {
				game.setCurrentPlayer(playerName);
				try {
					column = Integer.parseInt(input);
				} catch (NumberFormatException e) {
					column = 0;
				}
				if (column > 0 && column < 10){
					game = move(playerName, column);
				}
			}
			System.out.print(game.toString());
		}

	}

	private static HttpEntity<MultiValueMap<String, String>> prepareRequest(String playerName) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("playerName", playerName);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
		return request;
	}

	private static Game game(String playerName) {
		String url = SERVER_URL + "game";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Game> game = restTemplate.exchange(url, HttpMethod.GET, prepareRequest(playerName), Game.class);
		return game.getBody();
	}

	private static Game joinGame(String playerName) {
		String url = SERVER_URL + "join-game";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Game> game = restTemplate.exchange(url, HttpMethod.PUT, prepareRequest(playerName), Game.class);
		return game.getBody();
	}

	private static Game move(String playerName, int column) {
		String url = SERVER_URL + "move?column=" + column;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Game> game = restTemplate.exchange(url, HttpMethod.PUT, prepareRequest(playerName), Game.class);
		return game.getBody();
	}
	
	private static void endGame() {
		String url = SERVER_URL + "game";;
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(url);
	}
}