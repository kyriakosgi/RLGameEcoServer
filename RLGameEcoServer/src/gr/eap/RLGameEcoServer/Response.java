package gr.eap.RLGameEcoServer;

import java.util.ArrayList;

public class Response {
	private int userId;
	private int commandID;
	private ArrayList<String> availableCommands;
	private GameState gameState;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCommandID() {
		return commandID;
	}
	public void setCommandID(int commandID) {
		this.commandID = commandID;
	}
	public ArrayList<String> getAvailableCommands() {
		return availableCommands;
	}
	public void setAvailableCommands(ArrayList<String> availableCommands) {
		this.availableCommands = availableCommands;
	}
	public GameState getGameState() {
		return gameState;
	}
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
}
