package gr.eap.RLGameEcoServer.comm;

public class CommObjectClassCreator {
	public static Class<?> create(String commObjectType){
		if (commObjectType.equals("gr.eap.RLGameEcoServer.comm.LoginCommand"))
			return LoginCommand.class;
		if (commObjectType.equals("gr.eap.RLGameEcoServer.comm.MessageCommand"))
			return MessageCommand.class;
		else if (commObjectType.equals("gr.eap.RLGameEcoServer.comm.MessageResponse"))
			return Response.class;
		else if (commObjectType.equals("gr.eap.RLGameEcoServer.comm.PlayersListResponse"))
			return PlayersListResponse.class;
		else
			return null;
	}

}
