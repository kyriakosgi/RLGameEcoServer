package gr.eap.RLGameEcoServer;

// The general format of a serialized command is the Json serialized Command Object with the addition of the property "className" holding the Command type name
public interface CommandSerializer {
	public String serialize(Command command);
	public Command deserialize(String serializedCommand, String commandType);
}
