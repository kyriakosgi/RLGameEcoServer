package gr.eap.RLGameEcoServer.game;

import org.rlgame.gameplay.Pawn;
import org.rlgame.gameplay.Square;

import gr.eap.RLGameEcoServer.player.Player;

public class Move {
	private Pawn pawn;
	private Square toSquare;
	private Player player;
	
	public Move(Player player, Pawn pawn, Square toSquare){
		this.player = player;
		this.pawn = pawn;
		this.toSquare = toSquare;
	}

	public Pawn getPawn() {
		return pawn;
	}

	public Square getToSquare() {
		return toSquare;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void perform(){
		//Find gameState
		Game game = GamesRegister.getInstance().searchGameByPlayer(getPlayer());
		pawn.movePawn(pawn.getPosition(), toSquare);
		//Refresh gameState
		game.getState().refreshGameState();
		
		if (game.getState().isFinal()){
			GamesRegister.getInstance().endGame(game);
		}
		else
		{
			game.getState().setNextTurn();
		}
	}
	
	public Boolean isLegit(){
		return getPawn().isMoveLegit(getToSquare());
	}
	
	@Override
	public int hashCode() {
		int hash = 23;
		hash = 31 * hash + (pawn == null ? 0 : pawn.hashCode());
		hash = 31 * hash + (toSquare == null ? 0 : toSquare.hashCode());
		return hash;
	}
}
