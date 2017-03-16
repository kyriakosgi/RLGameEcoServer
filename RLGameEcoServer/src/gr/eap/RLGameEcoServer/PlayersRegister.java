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
			players.put(socketHash, newPlayer);
		}

		return newPlayer;
	}
}
