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

	@Override
	public List<Response> execute() {
		Player newPlayer =  PlayersRegister.getInstance().registerPlayer(userName, password);

		ArrayList<Response> returnValue = new ArrayList<Response>();
			if (newPlayer != null) {
				Response r1 = new Response();
				r1.setCommandID(super.getId());
				r1.setUserId(newPlayer.getId());
				r1.setMessage("Logon successfull");
				
				returnValue.add(r1);
			}
		return returnValue;
	}


}
