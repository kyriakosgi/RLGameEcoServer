package gr.eap.RLGameEcoServer.game;

public enum GameStatus {
	WAITING_SECOND_PLAYER ("Waiting for player to join"), 
	WAITING_INVITATION_ACCEPTANCE ("Waiting for player to accept invitation"), 
	IN_PROGRESS ("In progress"), 
	INTERRUPTED ("Interrupted"), 
	FINISHED ("Finished");
	
	String description;
	
	private GameStatus(String description){
		this.description=description;
	}
	
	public String getDescription(){
		return description;
	}
}
