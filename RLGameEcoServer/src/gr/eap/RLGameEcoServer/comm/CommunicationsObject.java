package gr.eap.RLGameEcoServer.comm;

import java.util.UUID;

public abstract class CommunicationsObject {
	private int userId;
	private UUID gameId;
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
	
}
