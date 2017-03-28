package gr.eap.RLGameEcoServer.game;

import gr.eap.RLGameEcoServer.Participant;

public class GameState {
	private int[] board;
	
	private Participant nextToPlay;

	private Status status;
	
	public int[] getBoard() {
		return board;
	}
	public void setBoard(int[] board) {
		this.board = board;
	}
	public Participant getNextToPlay() {
		return nextToPlay;
	}
	public void setNextToPlay(Participant nextToPlay) {
		this.nextToPlay = nextToPlay;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
