package gr.eap.RLGameEcoServer.comm;

public class Message {
	public enum Type{SYSTEM_INFO, SYSTEM_WARNING, SYSTEM_ERROR, USER_BROADCAST, USER_PERSONAL, USER_GAME, USER_TEAM}
	
	public String text;
	public Type type;
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
	
	
}
