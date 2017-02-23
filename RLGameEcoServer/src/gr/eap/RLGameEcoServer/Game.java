package gr.eap.RLGameEcoServer;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

public class Game {
	private UUID uid;
	private Date startDateTime;
	private Duration duration;
	private GameState state;
	public UUID getUid() {
		return uid;
	}
	public Date getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	public GameState getState() {
		return state;
	}
	public void setState(GameState state) {
		this.state = state;
	}
	
	public Game(){
		uid = UUID.randomUUID();
	}
	public void join(Participant player2){
		//TODO Not yet implemented
	}
	public void shareState(){
		//TODO Not yet implemented
	}
	public void endGame(){
		//TODO Not yet implemented
	}
	public void performMove(Participant player, int from, int to){
		//TODO Not yet implemented
	}

}
