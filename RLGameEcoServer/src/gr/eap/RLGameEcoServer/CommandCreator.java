package gr.eap.RLGameEcoServer;

public class CommandCreator {
	public Command create(String commandType){
		if (commandType == "LogonCommand")
			return new LogonCommand();
		else
			return null;
	}
}
