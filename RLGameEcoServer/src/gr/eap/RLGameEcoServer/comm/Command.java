package gr.eap.RLGameEcoServer.comm;


public abstract class Command extends CommunicationsObject{
	private int id;
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}




	public abstract void execute();
}
