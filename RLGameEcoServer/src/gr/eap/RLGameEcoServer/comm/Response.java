package gr.eap.RLGameEcoServer.comm;

import java.util.ArrayList;

import gr.eap.RLGameEcoServer.GameState;

public class Response extends CommunicationsObject {
	private int commandID;
	private ArrayList<String> availableCommands;
	private GameState gameState;
	private String message;
	private String connectionState;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Response(){
		this.setType("gr.eap.RLGameEcoServer.comm.Response");
	}

	public String getConnectionState() {
		return connectionState;
	}

	public void setConnectionState(String connectionState) {
		this.connectionState = connectionState;
	}
}
