package org.rlgame.gameplay;

import java.util.Vector;

import org.rlgame.common.*;
import org.rlgame.gameplay.Settings;

public class GameState  {

	private Square[][] gameBoard;	
	private Pawn[] whitePawns;
	private Pawn[] blackPawns;
	
	private Pawn[] cloneWhite = new Pawn[Settings.NUMOFPAWNS];
	private Pawn[] cloneBlack = new Pawn[Settings.NUMOFPAWNS];
	private Square[][] cloneGameBoard;
	
	// Added by Dockos for saving the coordinates of deleted pawns
	private String positionOfDeletedPawns = "";
	private double[] networkInput;

	public GameState() {
		//initialize gameBoard Squares array
		initGameBoard();
		
		whitePawns = new Pawn[Settings.NUMOFPAWNS];
		blackPawns = new Pawn[Settings.NUMOFPAWNS];
	}
	
	
//	

	public GameState(Pawn[] wh_Pawns, Pawn[] bl_Pawns) {
		init(wh_Pawns, bl_Pawns);
	}

	public void init(Pawn[] wh_Pawns, Pawn[] bl_Pawns) {
		whitePawns = wh_Pawns;
		blackPawns = bl_Pawns;
		synchronizeGameBoard();
	}	

	private void initGameBoard() {
		gameBoard = new Square[Settings.DIMBOARD][Settings.DIMBOARD];
		for (int i = 0; i < Settings.DIMBOARD; i++) {
			for (int j = 0; j < Settings.DIMBOARD; j++) {
				gameBoard[i][j] = new Square(i, j, Settings.DIMBOARD, Settings.DIMBASE);
			}
		}
	}
	
	public void synchronizeGameBoard() {
		if (gameBoard == null || gameBoard[0][0] == null) {
			initGameBoard();
		}
		
		for(int i=0; i< Settings.NUMOFPAWNS; i++) {
			if (!whitePawns[i].isAlive()){
	            gameBoard[whitePawns[i].position.getXCoord()][whitePawns[i].position.getYCoord()].setFree();
	        }
			
	        if (!blackPawns[i].isAlive()){
	        	gameBoard[blackPawns[i].position.getXCoord()][blackPawns[i].position.getYCoord()].setFree();
	        }
	        
	        if ((whitePawns[i].isAlive()) && (!whitePawns[i].position.isInWhiteBase())){
	        	gameBoard[whitePawns[i].position.getXCoord()][whitePawns[i].position.getYCoord()].setUnFree();
	        }
	        
	        if ((blackPawns[i].isAlive()) && (!blackPawns[i].position.isInBlackBase())){
	        	gameBoard[blackPawns[i].position.getXCoord()][blackPawns[i].position.getYCoord()].setUnFree();
	        }
		}
	}
	
	// check how many white pawns are still alive
	public int getWhiteRemaining(Pawn[] helpPawns) {
		int whiteRemaining = 0;
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			if (helpPawns[i].isAlive())
				whiteRemaining++;
		}
		return whiteRemaining;
	}

	// check how many black pawns are still alive
	public int getBlackRemaining(Pawn[] helpPawns) {
		int blackRemaining = 0;
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			if (helpPawns[i].isAlive())
				blackRemaining++;
		}
		return blackRemaining;
	}


	public Vector <ObservationCandidateMove> getAllPossibleMovesForPlayer(int turn, Square[][] outSquare) {
		Vector<ObservationCandidateMove> toRet = new Vector<ObservationCandidateMove>(); 
		Square[][] moves = this.getAllMovesForPlayer(turn, outSquare);
		
		//Blank moves - 0,0 target aren't added to the moves vector
		//
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			for (int j = 0; j < Settings.MAX_NUM_PAWN_MOVES; j++) {
				if ((moves[i][j].getXCoord() + moves[i][j].getYCoord()) != 0) {
					toRet.add(this.getMoveTargetGameState(turn, i, moves[i][j]));
				}
			}
		}

		return toRet;
	}
	
	private Square[][] getAllMovesForPlayer(int turn, Square[][] outSquare) {
		Square[][] helpSquare = new Square[Settings.NUMOFPAWNS][1];

		Square [] blankMoves = new Square[Settings.MAX_NUM_PAWN_MOVES];
		
		
		for (int i = 0; i < Settings.MAX_NUM_PAWN_MOVES; i++) {
			blankMoves[i] = new Square();
		}
		boolean baseFound = false;
		//addition if more than one pawns in in base there is no need to communicate 
		//more than one base located pawn possible moves
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			if (turn == Settings.WHITE_PLAYER) {
				if (baseFound && whitePawns[i].isPawnInOwnBase()) {
					helpSquare[i] = blankMoves;
				} else {
					helpSquare[i] = whitePawns[i].getLegitMovesForWhitePawn(outSquare);
				}
				
				if (! baseFound && whitePawns[i].isPawnInOwnBase()) {
					baseFound = true;
				}
				
			} else {
				if (baseFound && blackPawns[i].isPawnInOwnBase()) {
					helpSquare[i] = blankMoves;
				} else {
					helpSquare[i] = blackPawns[i].getLegitMovesForBlackPawn(outSquare);
				}

				if (! baseFound && blackPawns[i].isPawnInOwnBase()) {
					baseFound = true;
				}
			}
		}
		return helpSquare;
		
	}	

	// check for winner
	public boolean isFinal() {
		return isFinal(whitePawns, blackPawns);
	}
	
	
	// check for winner
	public boolean isFinal(Pawn[] wPawns, Pawn[] bPawns) {
		boolean answer = false;
		int whiteLeft = getWhiteRemaining(wPawns);
		int blackLeft = getBlackRemaining(bPawns);
		if ((whiteLeft == 0) || (blackLeft == 0))
			answer = true;
		else {
			for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
				if (wPawns[i].isPawnInEnemyBase()) {
					answer = true;
				}
				if (bPawns[i].isPawnInEnemyBase()) {
					answer = true;
				}
			}
		}
		return answer;
	}


	

	
	/*
	 * Get the occuring cloned Gamestate after playing the proposed move in a cloned board
	 */
	public ObservationCandidateMove getMoveTargetGameState(int turn, int pioni, Square tetr) {
		int[] deadWhite;
		int[] deadBlack;
		deadWhite = new int[Settings.NUMOFPAWNS];
		deadBlack = new int[Settings.NUMOFPAWNS];
		

		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			deadWhite[i] = 0;
			deadBlack[i] = 0;
		}
		
		// clone the board
		cloneGameBoard = new Square[Settings.DIMBOARD][Settings.DIMBOARD];
		// clone the pawns
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			if (whitePawns[i].position.isInWhiteBase() && whitePawns[i].isAlive()) {
				cloneWhite[i] = new Pawn(i, true);
			} else {
				Square whitePawnPosition = new Square(whitePawns[i].position.getXCoord(), whitePawns[i].position.getYCoord(), whitePawns[i].position.isFree());  
				cloneWhite[i] = new Pawn(i, true, whitePawnPosition, whitePawns[i].isAlive());
			}

			if (blackPawns[i].position.isInBlackBase() && blackPawns[i].isAlive()) {
				cloneBlack[i] = new Pawn(i, false);
			} else {
				Square blackPawnPosition = new Square(blackPawns[i].position.getXCoord(), blackPawns[i].position.getYCoord(), blackPawns[i].position.isFree());  
				cloneBlack[i] = new Pawn(i, false, blackPawnPosition, blackPawns[i].isAlive());
			}
		}
		
		for (int i = 0; i < Settings.DIMBOARD; i++) {
			for (int j = 0; j < Settings.DIMBOARD; j++) {
				cloneGameBoard[i][j] = new Square(gameBoard[i][j].getXCoord(), gameBoard[i][j].getYCoord(), gameBoard[i][j].isFree());
			}
		}

		// move the cloned pawn
		if (turn == Settings.WHITE_PLAYER) {
			cloneWhite[pioni].movePawn(cloneWhite[pioni].position, cloneGameBoard[tetr.getXCoord()][tetr.getYCoord()]);
		} else {
			cloneBlack[pioni].movePawn(cloneBlack[pioni].position, cloneGameBoard[tetr.getXCoord()][tetr.getYCoord()]);
		}

		// check for dead
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			if ((cloneBlack[i].isAlive()) && (cloneBlack[i].isBlackPawnMovable(cloneGameBoard) == false)) {
				deadBlack[i] = 1;
				// kill dead pawns
				cloneBlack[i].killPawn();
			}
			if ((cloneWhite[i].isAlive()) && (cloneWhite[i].isWhitePawnMovable(cloneGameBoard) == false)) {
				deadWhite[i] = 1;
				// kill dead pawns
				cloneWhite[i].killPawn();
			}
		}
		
		//new object is returned to check
		
		GameState temp = new GameState(cloneWhite, cloneBlack);
		temp.pawnsToBinaryArray(cloneWhite, cloneBlack);

		ObservationCandidateMove moveItem = new ObservationCandidateMove();
		moveItem.setPawnId(pioni);
		moveItem.setTargetCoordX(tetr.getXCoord());
		moveItem.setTargetCoordY(tetr.getYCoord());
		moveItem.setInputNode(temp.networkInput);
		moveItem.setEnvReward(temp.getReward(turn));
		
		return moveItem;
	}

	public void refreshGameState() {
		int[] deadWhite;
		int[] deadBlack;
		deadWhite = new int[Settings.NUMOFPAWNS];
		deadBlack = new int[Settings.NUMOFPAWNS];

		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			deadWhite[i] = 0;
			deadBlack[i] = 0;
		}
		
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			if ((blackPawns[i].isAlive()) && (blackPawns[i].isBlackPawnMovable(gameBoard) == false)) {
				deadBlack[i] = 1;

				if (positionOfDeletedPawns.equals("")) {
					positionOfDeletedPawns = "->d:";
				}
				
				positionOfDeletedPawns += "#0*" + String.valueOf(blackPawns[i].position.getXCoord()) + "*" + String.valueOf(blackPawns[i].position.getYCoord());
				// ************************************

				blackPawns[i].killPawn();
				// System.out.println("DeaD BlacK = "+blackPawns[i].position.getXCoord()+""+blackPawns[i].position.getYCoord());
			}
			if ((whitePawns[i].isAlive()) && (whitePawns[i].isWhitePawnMovable(gameBoard) == false)) {
				deadWhite[i] = 1;
				// System.out.println("DeaD WhitE = "+whitePawns[i].position.getXCoord()+""+whitePawns[i].position.getYCoord());

				if (positionOfDeletedPawns.equals("")) {
					positionOfDeletedPawns = "->d:";
				}
				
				positionOfDeletedPawns = positionOfDeletedPawns + "#1*" + String.valueOf(whitePawns[i].position.getXCoord()) + "*" + String.valueOf(whitePawns[i].position.getYCoord());

				whitePawns[i].killPawn();
			}
		}
	}

	// check if player can win after opponent's last move
	public boolean canWin(int turn, Square[][] helpSquare) {
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			for (int j = 0; j < Settings.MAX_NUM_PAWN_MOVES; j++) {
				if (turn == Settings.WHITE_PLAYER) {
					if (helpSquare[i][j].isInBlackBase()) {
						return true;
					}
				} else if (turn == Settings.BLACK_PLAYER) {
					if (helpSquare[i][j].isInWhiteBase()) {
						return true;
					}
				}
			}
	}
		return false;
	}
	
	// check if player can win after opponent's last move
	public boolean canWin(int turn) {
		Square[][] helpSquare = this.getAllMovesForPlayer(turn, this.getGameBoard());
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			for (int j = 0; j < Settings.MAX_NUM_PAWN_MOVES; j++) {
				if (turn == Settings.WHITE_PLAYER) {
					if (helpSquare[i][j].isInBlackBase()) {
						return true;
					}
				} else if (turn == Settings.BLACK_PLAYER) {
					if (helpSquare[i][j].isInWhiteBase()) {
						return true;
					}
				}
			}
	}
		return false;
	}
	
	
	public boolean nextToFinal(Pawn[] wPawns, Pawn[] bPawns) {
		boolean answer = false;
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			if (wPawns[i].isPawnInEnemyBase()) {
				answer = true;
			}

			if (bPawns[i].isPawnInEnemyBase()) {
				answer = true;
			}
		}

		return answer;
	}

	
	// Statistics 
	public String getFinalStateStats() {
		String toRet = "";
		int whiteLeft = getWhiteRemaining(this.whitePawns);
		int blackLeft = getBlackRemaining(this.blackPawns);
		int whiteBase = 0;
		int blackBase = 0;
//		if (whiteLeft == 0) {
//			toRet = "aspros" + "|";
//		} else if (blackLeft == 0) {
//			toRet = "mavros" + "|";
//		} else {
//			for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
//				if (this.whitePawns[i].isPawnInEnemyBase()) {
//					toRet = "aspros" + "|";
//					i = Settings.NUMOFPAWNS;
//				} else if (this.blackPawns[i].isPawnInEnemyBase()) {
//					toRet = "mavros" + "|";
//					i = Settings.NUMOFPAWNS;
//				}
//			}
//		}

		toRet += "" + whiteLeft + "|" + blackLeft + "|";
		
		//Count the base pawns
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			if (this.whitePawns[i].isAlive() && this.whitePawns[i].isPawnInOwnBase()) {
				whiteBase++;
			} 
			
			if (this.blackPawns[i].isAlive() && this.blackPawns[i].isPawnInOwnBase()) {
				blackBase++;
			}
		}
		toRet += "" + whiteBase + "|" + blackBase + "|";

		pawnsToBinaryArray();
		
		for (int i = 0; i < networkInput.length; i++) {
			toRet += String.valueOf((int) networkInput[i]);
		}
		
		return toRet;
	}

	
	// Added by Dockos
	public String getPositionOfDeletedPawns() {
		return positionOfDeletedPawns;
	}

	// Added by Dockos
	public void setPositionOfDeletedPawns(String start) {
		positionOfDeletedPawns = start;
	}

	public Pawn[] getPlayerPawns(int turn) {
		if (turn == Settings.WHITE_PLAYER) {
			return whitePawns;
		} else  {
			return blackPawns;
		}
		
	}
	
	public Pawn[] getWhitePawns() {
		return whitePawns;
	}

	public Pawn[] getBlackPawns() {
		return blackPawns;
	}

	public Square[][] getGameBoard() {
		return gameBoard;
	}


	
	void pawnsToBinaryArray() {
		pawnsToBinaryArray(whitePawns, blackPawns);
	}
	
	// transforms the pawns configuration to a binary array, that we use for input to the net for each square we have 2 values,
	// one for each player, the appropriate value is set to 1 or both are set to zero we also measure how many pawns are still
	// inside the base and we use 4 values for each player to state the percentage of the pawns that are still inside the base
	// finally there are two more values, 1 for each, triggered in case someone has already won
	private void pawnsToBinaryArray(Pawn[] white, Pawn[] black) {
		int pos = 0;
		int aspros = 0;
		int mavros = 0;
		double [] inputNode = new double[Settings.NEURAL_INPUT_SIZE + 1]; 

		//BIAS will be filled in the Neural network method
		//inputNode[AIConstants.NEURAL_INPUT_SIZE] = BIAS;
		
		int shortcut = 2 * Settings.DIMBOARD * Settings.DIMBOARD - 4 * Settings.DIMBASE * Settings.DIMBASE;
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			if ((white[i].isAlive()) && (!white[i].isPawnInEnemyBase()) && (!white[i].isPawnInOwnBase())) {
				if (white[i].getPosition().getXCoord() < Settings.DIMBASE) {
					pos = white[i].getPosition().getXCoord() * Settings.DIMBOARD + white[i].getPosition().getYCoord() - (white[i].getPosition().getXCoord() + 1) * Settings.DIMBASE;
				} else if ((white[i].getPosition().getXCoord() >= Settings.DIMBASE) && (white[i].getPosition().getXCoord() < Settings.DIMBOARD - Settings.DIMBASE)) {
					pos = white[i].getPosition().getXCoord() * Settings.DIMBOARD + white[i].getPosition().getYCoord() - Settings.DIMBASE * Settings.DIMBASE;
				} else if (white[i].getPosition().getXCoord() >= Settings.DIMBOARD - Settings.DIMBASE) {
					pos = white[i].getPosition().getXCoord() * Settings.DIMBOARD + white[i].getPosition().getYCoord() - (white[i].getPosition().getXCoord() - Settings.DIMBOARD + 2 * Settings.DIMBASE) * Settings.DIMBASE;
				}	
				inputNode[2 * pos] = 1;
			}
			if (white[i].isPawnInOwnBase()) {
				aspros++; // counts white pawns in base
			}
			
			if (white[i].isPawnInEnemyBase()) {
				inputNode[shortcut + 8] = 1; // white has already won
			}
			
			if ((black[i].isAlive()) && (!black[i].isPawnInEnemyBase()) && (!black[i].isPawnInOwnBase())) {
				if (black[i].getPosition().getXCoord() < Settings.DIMBASE) {
					pos = black[i].getPosition().getXCoord() * Settings.DIMBOARD + black[i].getPosition().getYCoord() - (black[i].getPosition().getXCoord() + 1) * Settings.DIMBASE;
				} else if ((black[i].getPosition().getXCoord() >= Settings.DIMBASE) && (black[i].getPosition().getXCoord() < Settings.DIMBOARD - Settings.DIMBASE)) {
					pos = black[i].getPosition().getXCoord() * Settings.DIMBOARD + black[i].getPosition().getYCoord() - Settings.DIMBASE * Settings.DIMBASE;
				} else if (black[i].getPosition().getXCoord() >= Settings.DIMBOARD - Settings.DIMBASE) {
					pos = black[i].getPosition().getXCoord() * Settings.DIMBOARD + black[i].getPosition().getYCoord() - (black[i].getPosition().getXCoord() - Settings.DIMBOARD + 2 * Settings.DIMBASE) * Settings.DIMBASE;
				}	
				inputNode[2 * pos + 1] = 1;
			}
			
			if (black[i].isPawnInOwnBase()) {
				mavros++; // counts black pawns in base
			}
			if (black[i].isPawnInEnemyBase()) {
				inputNode[shortcut + 9] = 1; // black has already won
			}
		}

		// triggers the appropriate percentage
		
		//bug correction int type casting was converting small decimal to 0 and then the multiplication was taking place
		if (aspros <= (int) (.25 * Settings.NUMOFPAWNS) ) {
			inputNode[shortcut] = 1;
		} else if (aspros <= (int) (0.5 * Settings.NUMOFPAWNS)) {
			inputNode[shortcut + 1] = 1;
		} else if (aspros <= (int) (0.75 * Settings.NUMOFPAWNS)) {
			inputNode[shortcut + 2] = 1;
		} else {
			inputNode[shortcut + 3] = 1;
		}
		
		if (mavros <= (int) (.25 * Settings.NUMOFPAWNS)) {
			inputNode[shortcut + 4] = 1;
		} else if (mavros <= (int) (0.5 * Settings.NUMOFPAWNS)) {
			inputNode[shortcut + 5] = 1;
		} else if (mavros <= (int) (0.75 * Settings.NUMOFPAWNS)) {
			inputNode[shortcut + 6] = 1;
		} else {
			inputNode[shortcut + 7] = 1;
		}
		
		
		this.networkInput = inputNode;
	}

	
	
	public  double [] getNetworkInput() {
		return networkInput;
	}


	public double getReward(int turn) {
		int whiteLeft = this.getWhiteRemaining(whitePawns);
		int blackLeft = this.getBlackRemaining(blackPawns);
		boolean finalState = this.isFinal();
		
		return getReward(turn, whiteLeft, blackLeft, finalState);
	}

	
	private double getReward(int turn, int whiteLeft, int blackLeft, boolean finalState) {
		
		if (finalState) {
			//In case white plays and white is out of Pawns assign the negative reward value
			if ((turn == Settings.WHITE_PLAYER) && (whiteLeft == 0))
				return -Settings.whiteReward;
			
			//In case black plays and black is out of Pawns assign the negative reward value
			if ((turn == Settings.BLACK_PLAYER) && (blackLeft == 0))
				return -Settings.blackReward;
			
			//Otherwise the possible final move gets 100 points
			return  100;
		} 
		// an xasei kapoio pioni
		double portion = 1.0 / Settings.NUMOFPAWNS;
		// gia tis ypoloipew periptoseiw epistrefoume to reward os eksis:
		// briskoyme ti diafora tvn pionion ton dyo antipalon (mporei na exasan
		// kapoia piona)
		// kai tin antistoixoume sto diastima (-100,100)
		int differenceOfPlayersPawns = whiteLeft - blackLeft;
		double res;
		if (turn == Settings.WHITE_PLAYER) {
			res = differenceOfPlayersPawns * portion;
			return res * Settings.whiteReward;
		} else if (turn == Settings.BLACK_PLAYER) {
			res = -differenceOfPlayersPawns * portion;
			return res * Settings.blackReward;
		} else {
			return  0;
		}
	}
	
	
	
	public GameState deepCopy() {
		GameState toRet = new GameState();

		
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			
			if (this.whitePawns[i].position.isInWhiteBase() && this.whitePawns[i].isAlive()) {
				toRet.whitePawns[i] = new Pawn(i, true);
			} else {
				Square whitePawnPosition = new Square(this.whitePawns[i].position.getXCoord(), this.whitePawns[i].position.getYCoord(), this.whitePawns[i].position.isFree());  
				toRet.whitePawns[i] = new Pawn(i, true, whitePawnPosition, this.whitePawns[i].isAlive());
			}
			
			if (this.blackPawns[i].position.isInBlackBase() && this.blackPawns[i].isAlive()) {
				toRet.blackPawns[i] = new Pawn(i, false);
			} else {
				Square blackPawnPosition = new Square(this.blackPawns[i].position.getXCoord(), this.blackPawns[i].position.getYCoord(), this.blackPawns[i].position.isFree());  
				toRet.blackPawns[i] = new Pawn(i, false, blackPawnPosition, this.blackPawns[i].isAlive());
			}
			
		}

		for (int i = 0; i < Settings.DIMBOARD; i++) {
			for (int j = 0; j < Settings.DIMBOARD; j++) {
				toRet.gameBoard[i][j] = new Square(gameBoard[i][j].getXCoord(), gameBoard[i][j].getYCoord(), gameBoard[i][j].isFree());
			}
		}
		
		////
		toRet.synchronizeGameBoard();
		
		return toRet;
	}	
	
	
	public Square getSquareByCoordinates(int coordX, int coordY) {
		return gameBoard[coordX][coordY];
	}
	
	public void printGameBoard() {
		System.out.println(" ");
		for (int i = 0; i < Settings.DIMBOARD; i++) {
			for (int j = 0; j < Settings.DIMBOARD; j++) {
				
				System.out.print(gameBoard[i][j].getPrintInfo());
				
			}
			System.out.println(" ");
		}
	}

	public void printPawns(int turn) {
		System.out.println(" ");
		for (int i = 0; i < Settings.NUMOFPAWNS; i++) {
			if (turn == Settings.WHITE_PLAYER) {
				if ((whitePawns[i].position.getXCoord() + whitePawns[i].position.getYCoord()) > 0 ) {
					System.out.println("Pawn " + i + " : " + whitePawns[i].position.getXCoord() + "-" + whitePawns[i].position.getYCoord());
				}
			} else {
				if ((blackPawns[i].position.getXCoord() + blackPawns[i].position.getYCoord()) > 0 ) {
					System.out.println("Pawn " + i + " : " + blackPawns[i].position.getXCoord() + "-" + blackPawns[i].position.getYCoord());
				}	
			}
		}
	}
	
	
	
}
