package gr.eap.RLGameEcoServer.game;

import org.rlgame.gameplay.Pawn;
import org.rlgame.gameplay.Square;

public class Move {
	private Pawn pawn;
	private Square fromSquare;
	private Square toSquare;
	
	public Move(Pawn pawn, Square fromSquare, Square toSquare){
		this.pawn = pawn;
		this.fromSquare = fromSquare;
		this.toSquare = toSquare;
	}

	public Pawn getPawn() {
		return pawn;
	}

	public Square getFromSquare() {
		return fromSquare;
	}

	public Square getToSquare() {
		return toSquare;
	}
	
	public void perform(){
		pawn.movePawn(fromSquare, toSquare);
	}
}
