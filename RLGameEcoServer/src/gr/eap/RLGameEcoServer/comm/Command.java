package gr.eap.RLGameEcoServer.comm;

import java.util.List;


public abstract class Command extends CommunicationsObject{
	private int id;
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}




	public abstract List<Response> execute();
}
