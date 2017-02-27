package gr.eap.RLGameEcoServer;

import org.java_websocket.WebSocket;

public abstract class Player extends Participant {
	private String userName;
	private String password;
	private WebSocket connection;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public WebSocket getConnection() {
		return connection;
	}
	public void setConnection(WebSocket connection) {
		this.connection = connection;
	}
}
