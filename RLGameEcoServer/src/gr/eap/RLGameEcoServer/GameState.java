package gr.eap.RLGameEcoServer;

public class GameState {
	private int[] board;
	
	private Participant nextToPlay;

	private int status;
	
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
