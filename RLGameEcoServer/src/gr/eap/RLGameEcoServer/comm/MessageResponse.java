package gr.eap.RLGameEcoServer.comm;

public class MessageResponse extends Response {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageResponse() {
		this.setType("gr.eap.RLGameEcoServer.comm.MessageResponse");
	}

}
