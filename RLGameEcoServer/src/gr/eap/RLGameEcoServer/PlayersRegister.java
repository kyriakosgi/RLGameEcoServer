package gr.eap.RLGameEcoServer;

import java.util.HashMap;
import java.util.Map;


public class PlayersRegister {
	private static PlayersRegister __me;
	private Map<Integer, Player> players;

	private PlayersRegister() {
		players = new HashMap<Integer, Player>();
	}

	public static PlayersRegister getInstance() {
		if (__me == null)
			__me = new PlayersRegister();
		return __me;
	}

	public Map<Integer, Player> getPlayers() {
		return players;
	}

	public Player registerPlayer(String userName, String password, int socketHash) {
		Player newPlayer = Player.getPlayer(userName, password);

		if (newPlayer != null) {
			//Add player to the connected players register along with his websocket hashcode
			//If a player was disconnected we add her with the new socketHash, so that she can continue playing
			if (players.containsKey(socketHash)){
				players.remove(socketHash);
			}
			players.put(socketHash, newPlayer);
		}

		return newPlayer;
	}
}
