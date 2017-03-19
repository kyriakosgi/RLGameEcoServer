package gr.eap.RLGameEcoServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.java_websocket.WebSocket;

import gr.eap.RLGameEcoServer.comm.PlayersListResponse;


public class PlayersRegister {
	private static PlayersRegister __me;
	private Map<Player, WebSocket> players;

	private PlayersRegister() {
		players = new HashMap<Player, WebSocket>();
	}

	public static PlayersRegister getInstance() {
		if (__me == null)
			__me = new PlayersRegister();
		return __me;
	}

	public Map<Player, WebSocket> getPlayers() {
		return players;
	}
	
	public ArrayList<Player> getPlayersList(){
		return new ArrayList<Player>(players.keySet());
	}

	public Player registerPlayer(String userName, String password, WebSocket socket) {
		Player newPlayer = Player.getPlayer(userName, password);

		if (newPlayer != null) {
			//Add player to the connected players register along with his websocket hashcode
			//If a player was disconnected we add her with the new socketHash, so that she can continue playing
			if (players.containsKey(newPlayer)){
				//TODO Send disconnect message
				players.get(newPlayer).close();
				//players.remove(newPlayer);
			}
			players.put(newPlayer, socket);
			sendPlayersList();
		}

		return newPlayer;
	}
	
	private void sendPlayersList(){
		PlayersListResponse r = new PlayersListResponse();
		players.forEach((k,v) -> {
			r.setSocket(v);
			r.send();
		});
	}
}
