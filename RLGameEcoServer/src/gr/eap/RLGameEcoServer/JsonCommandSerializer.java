package gr.eap.RLGameEcoServer;

//The general format of a serialized command is the Json serialized Command Object with the addition of the property "className" holding the Command type name
public class JsonCommandSerializer implements CommandSerializer {

	@Override
	public String serialize(Command command) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command deserialize(String serializedCommand, String commandType) {
		// TODO Auto-generated method stub
		return null;
	}

}
