package gr.eap.RLGameEcoServer.comm;

import java.util.ArrayList;

import gr.eap.RLGameEcoServer.Player;
import gr.eap.RLGameEcoServer.PlayersRegister;

public class PlayersListResponse extends Response {
	private ArrayList<Player> playersList = PlayersRegister.getInstance().getPlayers();

	public PlayersListResponse() {
		this.setType("gr.eap.RLGameEcoServer.comm.PlayersListResponse");
	}

	public ArrayList<Player> getPlayersList() {
		return playersList;
	}

}
