package gr.eap.RLGameEcoServer.comm;

public class CommandCreator {
	public static Command create(String commandType){
		if (commandType == "LogonCommand")
			return new LogonCommand();
		else
			return null;
	}
}
