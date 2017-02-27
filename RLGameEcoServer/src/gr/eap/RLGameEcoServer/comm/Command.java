package gr.eap.RLGameEcoServer.comm;

import java.util.List;

import org.java_websocket.WebSocket;

public abstract class Command extends CommunicationsObject{
	private int id;
	private WebSocket connection;
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public WebSocket getConnection() {
		return connection;
	}


	public void setConnection(WebSocket connection) {
		this.connection = connection;
	}


	public abstract List<Response> execute();
}
