package gr.eap.RLGameEcoServer.comm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gr.eap.RLGameEcoServer.db.MySQLHelper;
import gr.eap.RLGameEcoServer.db.MySQLHelper.parameterValue;

public class LogonCommand implements Command {
	private String userName;
	private String password;
	private int id;
	private int userId;
	private UUID gameUid;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

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
		ArrayList<parameterValue<?>> params = new ArrayList<MySQLHelper.parameterValue<?>>();
		params.add(new MySQLHelper.parameterValue<String>(userName));
		params.add(new MySQLHelper.parameterValue<String>(password));

		ResultSet rs = MySQLHelper.getInstance().query("Select * from players where Username = ? and Password = ?",
				params);

		ArrayList<Response> returnValue = new ArrayList<Response>();
		try {
			if (rs != null && rs.next()) {
				Response r1 = new Response();
				r1.setCommandID(id);
				r1.setUserId(rs.getInt("ID"));
				r1.setMessage("Logon successfull");
				
				returnValue.add(r1);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return returnValue;
	}

	@Override
	public int getUserId() {
		return userId;
	}

	@Override
	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public UUID getGameUid() {
		return gameUid;
	}

	@Override
	public void setGameUid(UUID gameUid) {
		this.gameUid = gameUid;
	}

}
