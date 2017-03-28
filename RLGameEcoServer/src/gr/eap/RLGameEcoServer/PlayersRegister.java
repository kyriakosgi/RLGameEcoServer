package gr.eap.RLGameEcoServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.java_websocket.WebSocket;

import gr.eap.RLGameEcoServer.comm.PlayersListResponse;


public class PlayersRegister {
	private static PlayersRegister __me;
	private Map<Integer, Player> players; //We will be using a hashmap for the players whith their id as key

	private PlayersRegister() {
		players = new HashMap<Integer, Player>();
	}

	//Singleton design pattern
	public static PlayersRegister getInstance() {
		if (__me == null)
			__me = new PlayersRegister();
		return __me;
	}

	public Map<Integer, Player> getPlayers() {
		return players;
	}
	
	public Player getPlayerById(int id){
		return players.get(id);
	}
	
	public Player getPlayerBySocket(WebSocket socket){
		Player returnPlayer = null;
		if (socket != null){
			for (Map.Entry<Integer, Player> entry : players.entrySet()){
				if (entry.getValue().getConnection().equals(socket)){
					returnPlayer = entry.getValue();
					break;
				}
			}
		}
		return returnPlayer;
	}
	
	public ArrayList<Player> getPlayersById(ArrayList<Integer> ids){
		ArrayList<Player> returnList = new ArrayList<Player>();
		// when ids is empty we should return all connected players
		Boolean returnAll = ids.isEmpty();
		players.forEach((k,v) -> {if (returnAll || ids.contains(k)) returnList.add(v);});
		return returnList;
	}
	
	public ArrayList<Player> getPlayersList(){
		return new ArrayList<Player>(players.values());
	}

	public Player registerPlayer(String userName, String password, WebSocket socket) {
		Player newPlayer = Player.getPlayer(userName, password);

		if (newPlayer != null) {
			//Add player to the connected players register along with his websocket hashcode
			//If a player was disconnected we add her with the new socketHash, so that she can continue playing
			newPlayer.setConnection(socket);
			newPlayer.setConnectionState(ConnectionState.LOGGED_IN);
			Player existingPlayer = players.get((Integer)newPlayer.getId());
			
			if (existingPlayer != null){
				//TODO Send disconnect message
				existingPlayer.getConnection().close();
				existingPlayer.setConnection(socket);
				//if logged in player is already registered, we leave his connection state intact so that he can continue playing a game that he was participating
			}
			else{
				players.put((Integer)newPlayer.getId(), newPlayer);
			}
			sendPlayersList();
		}

		return newPlayer;
	}
	
	public void deregisterPlayer(WebSocket socket){
		Player p = getPlayerBySocket(socket);
		if (p != null) players.remove(p.getId()); 
		sendPlayersList();
	}
	
	private void sendPlayersList(){
		PlayersListResponse r = new PlayersListResponse();
		players.forEach((k,v)-> {
			r.setSocket(v.getConnection());
			r.send();
		});
	}
}
