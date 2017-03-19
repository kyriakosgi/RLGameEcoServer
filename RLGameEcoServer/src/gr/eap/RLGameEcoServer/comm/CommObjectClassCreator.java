package gr.eap.RLGameEcoServer.comm;

public class CommObjectClassCreator {
	public static Class<?> create(String commObjectType){
		if (commObjectType.equals("gr.eap.RLGameEcoServer.comm.LoginCommand"))
			return LoginCommand.class;
		else if (commObjectType.equals("gr.eap.RLGameEcoServer.comm.Response"))
			return Response.class;
		else
			return null;
	}

}
