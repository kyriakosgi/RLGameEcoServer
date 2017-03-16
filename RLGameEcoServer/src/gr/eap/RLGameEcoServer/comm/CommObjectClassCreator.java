package gr.eap.RLGameEcoServer.comm;

public class CommObjectClassCreator {
	public static Class<?> create(String commObjectType){
		if (commObjectType.equals("gr.eap.RLGameEcoServer.comm.LogonCommand"))
			return LogonCommand.class;
		else if (commObjectType.equals("Response"))
			return Response.class;
		else
			return null;
	}

}
