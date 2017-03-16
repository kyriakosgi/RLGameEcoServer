package gr.eap.RLGameEcoServer.comm;

import java.util.UUID;

public abstract class CommunicationsObject {
	private int userId;
	private UUID gameId;
	private String type;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public UUID getGameId() {
		return gameId;
	}
	public void setGameId(UUID gameId) {
		this.gameId = gameId;
	}
	public String getType() {
		return type;
	}
	//The type property should only be set by concrete subclasses
	protected void setType(String type) {
		this.type = type;
	}
	
}
