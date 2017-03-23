package gr.eap.RLGameEcoServer.comm;


import gr.eap.RLGameEcoServer.ConnectionState;
import gr.eap.RLGameEcoServer.Player;
import gr.eap.RLGameEcoServer.PlayersRegister;
import gr.eap.RLGameEcoServer.comm.Message.Type;

public class LoginCommand extends Command {
	private String userName;
	private String password;

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

	public LoginCommand(){
		this.setType("gr.eap.RLGameEcoServer.comm.LoginCommand");
	}
	
	@Override
	public void execute() {
		Player newPlayer = PlayersRegister.getInstance().registerPlayer(userName, password, getSocket());

		MessageResponse r1 = new MessageResponse();
		r1.setCommandID(super.getId());
		r1.setSocket(getSocket());
		if (newPlayer != null) {
			r1.setUserId(newPlayer.getId());
			r1.setMessage(new Message("Login successfull",Type.SYSTEM_INFO));
			r1.setConnectionState(ConnectionState.LOGGED_IN);
		}
		else
		{
			r1.setMessage(new Message("Login failed", Type.SYSTEM_ERROR));
			r1.setConnectionState(ConnectionState.CONNECTED);
		}
		r1.send();
	}

}
