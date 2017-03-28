package gr.eap.RLGameEcoServer.game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import gr.eap.RLGameEcoServer.player.Participant;
import gr.eap.RLGameEcoServer.player.Player;

public class GamesRegister {
	private static GamesRegister __me;
	private Map<UUID, Game> games; //We will be using a hashmap for the players whith their id as key

	private GamesRegister() {
		games = new HashMap<UUID, Game>();
	}

	//Singleton design pattern
	public static GamesRegister getInstance() {
		if (__me == null)
			__me = new GamesRegister();
		return __me;
	}

	public Map<UUID, Game> getGames() {
		System.out.println("getGames");
		return games;
	}

	public void createGame(Player player1, int boardSize, int baseSize, int numberOfPawns){
		Game newGame = new Game(boardSize, baseSize, numberOfPawns);
		Participant p1 = new Participant(player1);
		p1.setRole(Participant.Role.PLAYER1);
		newGame.addPlayer1(p1);
		newGame.getState().setStatus(GameStatus.WAITING_SECOND_PLAYER);
		games.put(newGame.getUid(), newGame);
	}
	
}
