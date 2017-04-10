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
	private transient ArrayList<Participant> participants = new ArrayList<Participant>();
	private int boardSize;
	private int baseSize;
	private int numberOfPawns;

	//player1 and player2 properties will be read-only and will get updated when needed, so that we can correctly serialize those properties
	private Participant player1;
	private Participant player2;
	
	
	public Participant getPlayer1() {
		return player1;
	}
	public Participant getPlayer2() {
		return player2;
	}
	public int getBoardSize() {
		return boardSize;
	}
	public int getBaseSize() {
		return baseSize;
	}
	public int getNumberOfPawns() {
		return numberOfPawns;
	}
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
	
	public Game(int boardSize, int baseSize, int numberOfPawns){
		uid = UUID.randomUUID();
		this.boardSize = boardSize;
		this.baseSize = baseSize;
		this.numberOfPawns = numberOfPawns;
		state = new GameState();
		state.setBoard(new int[boardSize * boardSize]);
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
			this.player1 = player1;
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
