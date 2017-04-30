package gr.eap.RLGameEcoServer.comm;

import org.rlgame.gameplay.GameState;

public class GameStateResponse extends Response {
	private GameState state;

	public GameState getState() {
		return state;
	}
	
	public GameStateResponse(GameState state) {
		this.state = state;
	}

	public GameStateResponse() {
		this.setType("gr.eap.RLGameEcoServer.comm.GameStateResponse");
	}

}
