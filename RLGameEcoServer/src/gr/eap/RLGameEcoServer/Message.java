package gr.eap.RLGameEcoServer;

import gr.eap.RLGameEcoServer.comm.MessageResponse;

public class Message {
	public enum Type {
		SYSTEM_INFO, SYSTEM_WARNING, SYSTEM_ALERT, USER_BROADCAST, USER_PERSONAL, USER_GAME, USER_TEAM
	}

	private String text;
	private Type type;
	private Player sender;
	private Player recipient;

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

	public Message() {

	}

	public Message(String text, Type type, Player sender, Player recipient) {
		this.text = text;
		this.type = type;
		this.sender = sender;
		this.recipient = recipient;
	}

	public void send() {
		send(-1);
	}

	public void send(int commandId) {
		MessageResponse response = new MessageResponse();
		if (commandId>0) response.setCommandID(commandId);
		response.setMessage(this);
		response.setConnectionState(recipient.getConnectionState());
		response.setSocket(recipient.getConnection());
		response.setUserId(recipient.getId());
		response.send();

	}
}
