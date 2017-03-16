package gr.eap.RLGameEcoServer.comm;

import java.util.List;

import org.java_websocket.WebSocket;

public abstract class Command extends CommunicationsObject{
	private int id;
	private int socketHash;
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getSocketHash() {
		return socketHash;
	}


	public void setSocketHash(int socketHash) {
		this.socketHash = socketHash;
	}


	public abstract List<Response> execute();
}
