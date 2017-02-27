package gr.eap.RLGameEcoServer.comm;

public class CommObjectClassCreator {
	public static Class<?> create(String commObjectType){
		if (commObjectType == "LogonCommand")
			return LogonCommand.class;
		else if (commObjectType == "Response")
			return Response.class;
		else
			return null;
	}

}
