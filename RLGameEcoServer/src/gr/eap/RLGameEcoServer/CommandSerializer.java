package gr.eap.RLGameEcoServer;

// The general format of a serialized command is the serialized Command Object with the addition of the Command type name info
public interface CommandSerializer {
	public String serialize(Command command);
	public Command deserialize(String serializedCommand, String commandType);
}
