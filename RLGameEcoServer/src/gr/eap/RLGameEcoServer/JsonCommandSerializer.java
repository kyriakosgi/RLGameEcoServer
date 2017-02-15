package gr.eap.RLGameEcoServer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

//The general format of a serialized command is the Json serialized Command Object with the addition of the property "className" holding the Command type name
public class JsonCommandSerializer implements CommandSerializer {

	@Override
	public String serialize(Command command) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		JsonObject a = gson.toJsonTree(command).getAsJsonObject();
		//Next we add the command type name as a new property ("className")
		a.add("className", new JsonPrimitive(command.getClass().getName()));
		return a.getAsString();
	}

	@Override
	public Command deserialize(String serializedCommand, String commandType) {
		// TODO Auto-generated method stub
		return null;
	}

}
