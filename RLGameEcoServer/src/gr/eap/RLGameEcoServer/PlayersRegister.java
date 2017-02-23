package gr.eap.RLGameEcoServer;

import java.util.ArrayList;

public class PlayersRegister {
	private static PlayersRegister __me;
	private PlayersRegister(){}
	private ArrayList<Player> players;
	
	public static PlayersRegister getInstance(){
		if (__me == null) __me = new PlayersRegister();
		return __me;
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public Player registerPlayer(String userName, String password){
		//TODO not yet implemented
		return null;
	}
}
