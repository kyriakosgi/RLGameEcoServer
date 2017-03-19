package gr.eap.RLGameEcoServer.comm;

import java.util.List;

import org.java_websocket.WebSocket;

public abstract class Command extends CommunicationsObject{
	private int id;
	private WebSocket socket;
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public WebSocket getSocket() {
		return socket;
	}


	public void setSocket(WebSocket socket) {
		this.socket = socket;
	}


	public abstract List<Response> execute();
}
