package gr.eap.RLGameEcoServer.comm;

import java.util.ArrayList;
import java.util.List;

import gr.eap.RLGameEcoServer.Player;
import gr.eap.RLGameEcoServer.PlayersRegister;

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
	public List<Response> execute() {
		Player newPlayer = PlayersRegister.getInstance().registerPlayer(userName, password, getSocketHash());

		ArrayList<Response> returnValue = new ArrayList<Response>();
		Response r1 = new Response();
		r1.setCommandID(super.getId());

		if (newPlayer != null) {
			r1.setUserId(newPlayer.getId());
			r1.setMessage("Login successfull");
			r1.setConnectionState("Logged On");
		}
		else
		{
			r1.setMessage("Login failed");
			r1.setConnectionState("Connected");
		}
		returnValue.add(r1);
		return returnValue;
	}

}
