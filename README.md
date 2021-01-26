# 5-in-a-row
Coding Challenge: 5 in a row game

# Approach:

Build a SpringBoot REST service, using JPA and H2 database to store the state of the game.
Build a simple Java Command Line program to call the SpringBoot REST service using Spring RestTemplate

(See Architecture Diagram.png for an overview)

The Springboot service has a simple RestController class which calls the GameService which has the logic for the game, allowing players to:

1. Join Game
2. Move (make a move in game)
3. Check on status of game
4. Quit Game

# Current State:

The service is up and running with 4 endpoints visible in GameController.java.  Postman collection available here for reference: https://www.postman.com/collections/2a551b87dd41215ce822 

The clients can connect to the game and make moves and the game tracks the gameGrid and who's turn is it and displays that to the players.

# Major Gaps: (not done due to time constraints):
Basic JUnit 5 testing of the GameService class is implemented but more scenarios are needed.
Would also consider a Cucumber approach to test scenarios with more time.

doWeHaveAWinner?  The logic to determine that a player has 5 tokens in a row is not complete. The current logic is a simplified version of the game, where the first player to have 5 tokens in the grid wins.

# Future Considerations:

Externalize configuration for client - to allow clients to specify endpoint of service.

Externalize database outside the service for persistence beyond lifetime of service.

Consider using Websockets instead of plain HTTP to allow for more seamless experience for players - current setup is a bit manual to poll for status of other players moves.

Gracefully handle errors - e.g. call to service fails.






