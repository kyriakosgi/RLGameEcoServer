package gr.eap.RLGameEcoServer.game;

import org.rlgame.gameplay.Pawn;
import org.rlgame.gameplay.Square;

import gr.eap.RLGameEcoServer.player.Player;

public class Move {
	private Pawn pawn;
	private Square fromSquare;
	private Square toSquare;
	private Player player;
	
	public Move(Player player, Pawn pawn, Square fromSquare, Square toSquare){
		this.player = player;
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
	
	@Override
	public int hashCode() {
		int hash = 19;
		hash = 26 * hash + (pawn == null ? 0 : pawn.hashCode());
		hash = 26 * hash + (fromSquare == null ? 0 : fromSquare.hashCode());
		hash = 26 * hash + (toSquare == null ? 0 : toSquare.hashCode());
		return hash;
	}
}
