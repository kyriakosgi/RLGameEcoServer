package gr.eap.RLGameEcoServer.comm;

public class CommandClassCreator {
	public static Class<?> create(String commandType){
		if (commandType == "LogonCommand")
			return LogonCommand.class;
		else
			return null;
	}

}
