package gr.eap.RLGameEcoServer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySQLHelper {
	private static MySQLHelper __me = null;

	private MySQLHelper() {
	}

	private static String dbLocation;
	private static String userName;
	private static String password;
	private static Connection connection;

	public static MySQLHelper getInstance(String dbLocation, String userName, String password) {
		if (__me == null)
			__me = new MySQLHelper();
		MySQLHelper.dbLocation = dbLocation;
		MySQLHelper.userName = userName;
		MySQLHelper.password = password;
		return __me;
	}

	private static Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(MySQLHelper.dbLocation, MySQLHelper.userName,
						MySQLHelper.password);
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				return null;
			}
		}
		return connection;
	}

	public class parameterValue<T> {
		public T value;

		public parameterValue(T value) {
			this.value = value;
		}
	}

	public static ResultSet query(String SQLString, List<parameterValue<?>> parameters) {
		Connection conn = getConnection();
		ResultSet rs = null;
		try (PreparedStatement statement = conn.prepareStatement(SQLString, ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_READ_ONLY);) {

			for (int i = 0; i < parameters.size(); i++) {
				statement.setObject(i + 1, parameters.get(i).value);
			}

			rs = statement.executeQuery();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
		}
		return rs;
	}
}
