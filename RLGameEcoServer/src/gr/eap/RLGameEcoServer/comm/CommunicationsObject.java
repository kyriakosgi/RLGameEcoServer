package gr.eap.RLGameEcoServer.comm;

import java.util.UUID;

public interface CommunicationsObject {
	public int getUserId();
	public void setUserId(int userId);
	public UUID getGameUid();
	public void setGameUid(UUID gameUid);
	
}
