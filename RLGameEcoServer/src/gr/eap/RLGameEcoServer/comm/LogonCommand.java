package gr.eap.RLGameEcoServer.comm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gr.eap.RLGameEcoServer.Player;
import gr.eap.RLGameEcoServer.PlayersRegister;
import gr.eap.RLGameEcoServer.db.MySQLHelper;
import gr.eap.RLGameEcoServer.db.MySQLHelper.parameterValue;

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
		ArrayList<parameterValue<?>> params = new ArrayList<MySQLHelper.parameterValue<?>>();
		params.add(new MySQLHelper.parameterValue<String>(userName));
		params.add(new MySQLHelper.parameterValue<String>(password));

		ResultSet rs = MySQLHelper.getInstance().query("Select * from players where Username = ? and Password = ?",
				params);

		ArrayList<Response> returnValue = new ArrayList<Response>();
		try {
			if (rs != null && rs.next()) {
				Response r1 = new Response();
				r1.setCommandID(super.getId());
				r1.setUserId(rs.getInt("ID"));
				r1.setMessage("Logon successfull");
				
				returnValue.add(r1);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return returnValue;
	}


}
