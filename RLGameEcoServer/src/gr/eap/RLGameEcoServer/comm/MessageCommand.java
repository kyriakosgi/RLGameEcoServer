package gr.eap.RLGameEcoServer.comm;

import java.util.ArrayList;

import gr.eap.RLGameEcoServer.Message;
import gr.eap.RLGameEcoServer.Player;
import gr.eap.RLGameEcoServer.PlayersRegister;

public class MessageCommand extends Command {
	private Message message;
	private ArrayList<Integer> recipientsIds = new ArrayList<Integer>();

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public ArrayList<Integer> getRecipientsIds() {
		return recipientsIds;
	}

	public void setRecipientsIds(ArrayList<Integer> recipientsIds) {
		this.recipientsIds = recipientsIds;
	}

	@Override
	public void execute() {
		// We will send a MessageResponse to every Player that is a recipient
		if (!(recipientsIds.isEmpty() || message == null || message.getText() == null || message.getText().isEmpty())) {

			MessageResponse response = new MessageResponse();
			response.setCommandID(getId());
			response.setMessage(message);
			ArrayList<Player> recipients = PlayersRegister.getInstance().getPlayersById(recipientsIds);

			for (Player p : recipients) {
				response.setConnectionState(p.getConnectionState());
				response.setSocket(p.getConnection());
				response.setUserId(p.getId());
				response.send();
			}
		}

	}

}
