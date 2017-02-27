package gr.eap.RLGameEcoServer.comm;

import java.util.ArrayList;
import java.util.UUID;

import gr.eap.RLGameEcoServer.GameState;

public class Response implements CommunicationsObject {
	private int userId;
	private UUID gameUid;
	private int commandID;
	private ArrayList<String> availableCommands;
	private GameState gameState;
	private String message;

	@Override
	public int getUserId() {
		return userId;
	}

	@Override
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public UUID getGameUid() {
		return gameUid;
	}

	@Override
	public void setGameUid(UUID gameUid) {
		this.gameUid = gameUid;
	}
}
