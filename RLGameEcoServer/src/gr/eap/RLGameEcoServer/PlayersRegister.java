package gr.eap.RLGameEcoServer;

import java.util.ArrayList;

import org.java_websocket.WebSocket;

import gr.eap.RLGameEcoServer.comm.PlayersListResponse;


public class PlayersRegister {
	private static PlayersRegister __me;
	private ArrayList<Player> players;

	private PlayersRegister() {
		players = new ArrayList<Player>();
	}

	public static PlayersRegister getInstance() {
		if (__me == null)
			__me = new PlayersRegister();
		return __me;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	
//	public ArrayList<Player> getPlayersList(){
//		return new ArrayList<Player>(players.keySet());
//	}

	public Player registerPlayer(String userName, String password, WebSocket socket) {
		Player newPlayer = Player.getPlayer(userName, password);

		if (newPlayer != null) {
			//Add player to the connected players register along with his websocket hashcode
			//If a player was disconnected we add her with the new socketHash, so that she can continue playing
			newPlayer.setConnection(socket);
			newPlayer.setConnectionState(ConnectionState.LOGGED_IN);
			if (players.contains(newPlayer)){
				//TODO Send disconnect message
				int i = players.indexOf(newPlayer);
				players.get(i).getConnection().close();
				players.get(i).setConnection(socket);
				//if logged in player is already registered, we leave his connection state intact so that he can continue playing a game that he was participating
			}
			else{
				players.add(newPlayer);
			}
			sendPlayersList();
		}

		return newPlayer;
	}
	
	private void sendPlayersList(){
		PlayersListResponse r = new PlayersListResponse();
		players.forEach(p-> {
			r.setSocket(p.getConnection());
			r.send();
		});
	}
}
