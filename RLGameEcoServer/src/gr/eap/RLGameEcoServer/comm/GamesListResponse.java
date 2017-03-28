package gr.eap.RLGameEcoServer.comm;

import java.util.ArrayList;

import gr.eap.RLGameEcoServer.game.Game;
import gr.eap.RLGameEcoServer.game.GamesRegister;

public class GamesListResponse extends Response {
	private ArrayList<Game> gamesList = GamesRegister.getInstance().getGamesList();

	public GamesListResponse() {
		this.setType("gr.eap.RLGameEcoServer.comm.GamesListResponse");
	}

	public ArrayList<Game> getGamesList() {
		return gamesList;
	}

}
