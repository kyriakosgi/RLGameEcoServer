package gr.eap.RLGameEcoServer.game;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import gr.eap.RLGameEcoServer.player.Participant;

public class Game {
	private UUID uid;
	private Date startDateTime;
	private Duration duration;
	private GameState state;
	private ArrayList<Participant> participants;
	
	
	public ArrayList<Participant> getParticipants() {
		return participants;
	}
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
	
	public boolean addPlayer1(Participant player1){
		boolean returnValue = true;
		for (Participant participant : participants){
			if (player1.equals(participant)){
				returnValue = false;
				break;
			}
		}
		if (returnValue){
			player1.setRole(Participant.Role.PLAYER1);
			participants.add(player1);
		}
		return returnValue;
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
