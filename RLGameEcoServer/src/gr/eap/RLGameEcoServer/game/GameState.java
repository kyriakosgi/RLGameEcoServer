package gr.eap.RLGameEcoServer.game;

import gr.eap.RLGameEcoServer.player.Participant;

public class GameState {
	private int[] board;
	
	private Participant nextToPlay;

	private GameStatus status;
	
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
	public GameStatus getStatus() {
		return status;
	}
	public void setStatus(GameStatus status) {
		this.status = status;
	}
	
}
