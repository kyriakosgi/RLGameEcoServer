package gr.eap.RLGameEcoServer;

import org.java_websocket.WebSocket;

import gr.eap.RLGameEcoServer.comm.ConnectionState;
import gr.eap.RLGameEcoServer.comm.MessageResponse;
import gr.eap.RLGameEcoServer.player.Player;

public class Message {
	public enum Type {
		SYSTEM_INFO, SYSTEM_WARNING, SYSTEM_ALERT, USER_BROADCAST, USER_PERSONAL, USER_GAME, USER_TEAM
	}

	private String text = null;
	private Type type = null;
	private Player sender = null;
	private Player recipient = null;
	private transient WebSocket socket = null;


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public Player getSender() {
		return sender;
	}


	public void setSender(Player sender) {
		this.sender = sender;
	}


	public Player getRecipient() {
		return recipient;
	}


	public void setRecipient(Player recipient) {
		this.recipient = recipient;
	}


	public WebSocket getSocket() {
		return socket;
	}


	public void setSocket(WebSocket socket) {
		this.socket = socket;
	}


	public Message() {
	}

	
	//Standard message send by a player to another player
	public Message(String text, Type type, Player sender, Player recipient) {
		this.text = text;
		this.type = type;
		this.sender = sender;
		this.recipient = recipient;
	}

	//Message sent by the system to a player
	public Message(String text, Type type, Player recipient) {
		this.text = text;
		this.type = type;
		this.recipient = recipient;
	}
	
	//Message sent by the system to a connection (non logged in client). A player cannot send such message.
	public Message(String text, Type type, WebSocket socket) {
		this.text = text;
		this.type = type;
		this.socket = socket;
	}

	
	public void send() {
		send(-1);
	}

	public void send(int commandId) {
		if (text != null && !text.isEmpty() && type != null && (recipient != null || socket != null)) {
			
			MessageResponse response = new MessageResponse();
			if (commandId>0) response.setCommandID(commandId);
			response.setMessage(this);
			if (recipient != null){
				response.setConnectionState(recipient.getConnectionState());
				response.setSocket(recipient.getConnection());
				response.setUserId(recipient.getId());
			}
			else
			{
				response.setConnectionState(ConnectionState.CONNECTED); // if there is no recipient, means that the system sends message to a connected client
				response.setSocket(socket);
			}
			response.send();
		}

	}
}
