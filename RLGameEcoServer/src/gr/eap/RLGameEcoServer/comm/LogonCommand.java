package gr.eap.RLGameEcoServer.comm;

import java.util.ArrayList;
import java.util.List;

import gr.eap.RLGameEcoServer.Player;
import gr.eap.RLGameEcoServer.PlayersRegister;

public class LogonCommand extends Command {
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

	public LogonCommand(){
		super.setType("gr.eap.RLGameEcoServer.comm.LogonCommand");
	}
	
	@Override
	public List<Response> execute() {
		Player newPlayer = PlayersRegister.getInstance().registerPlayer(userName, password, getSocketHash());

		ArrayList<Response> returnValue = new ArrayList<Response>();
		Response r1 = new Response();
		r1.setCommandID(super.getId());

		if (newPlayer != null) {
			r1.setUserId(newPlayer.getId());
			r1.setMessage("Logon successfull");
		}
		else
		{
			r1.setMessage("Logon failed");
		}
		returnValue.add(r1);
		return returnValue;
	}

}
