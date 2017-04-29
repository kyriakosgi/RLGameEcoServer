package org.rlgame.gameplay;

import org.rlgame.gameplay.Settings;

public class Pawn {
	private int id; // pawn id
	private boolean white; // belongs to white??
	private boolean alive; // is alive??
	public Square position; // the square where this pawn resides

	// initiates the squares that make the white base	
	private Square whiteBase = new Square(0, 0, Settings.DIMBOARD, Settings.DIMBASE); 
	
	// initiates the squares that make the black base
	private Square blackBase = new Square(Settings.DIMBOARD - 1, Settings.DIMBOARD - 1, Settings.DIMBOARD, Settings.DIMBASE); 
						

	public Pawn() {
		alive = true;
		position = whiteBase;
	}

	public Pawn(int noumero, boolean whiteColor) {
		id = noumero;
		white = (whiteColor == true) ? true : false;
		position = (whiteColor == true) ? whiteBase : blackBase;
		alive = true;
	}

	public Pawn(int noumero, boolean whiteColor, Square toSquare, boolean aliveStatus) {
		id = noumero;
		white = (whiteColor == true) ? true : false;
		position = toSquare;
		
		alive = aliveStatus;
	}	
	

	// move pawn from fromSquare to toSquare
	public void movePawn(Square fromSquare, Square toSquare) {
		position = toSquare;
		fromSquare.setFree(); // set free the source square
		
		//ngav added on Apr 12
		position.setUnFree();
		toSquare.setUnFree(); // and set unfree the sink square
	}

	public boolean isPawnInOwnBase() {
		return ((this.isAlive() == true) && (((this.white) && (this.position
				.isInWhiteBase())) || ((!this.white) && (this.position
				.isInBlackBase())))) ? true : false;
	}

	public boolean isPawnInEnemyBase() {
		boolean res = false;
		if ((this.isAlive())
				&& ((this.white) && (this.position.isInBlackBase())))
			res = true;

		if ((this.isAlive())
				&& ((!this.white) && (this.position.isInWhiteBase())))
			res = true;
		return res;
		// return
		// ((this.isAlive())&&(((this.white)&&(this.position.isInBlackBase()))||(!this.white)&&(this.position.isInWhiteBase())))?true:false;
	}

	public boolean isAlive() {
		return alive;
	}

	public void killPawn() {
		position.setFree(); // release the square
		alive = false;
	}

	// finds all possible moves a pawn can make, without checking legality
	public Square[] getAllMovesForWhitePawn(Square[][] outSquare) {
		Square helpSquare[] = new Square[Settings.MAX_NUM_PAWN_MOVES];

		for (int i = 0; i < Settings.MAX_NUM_PAWN_MOVES; i++) {
			helpSquare[i] = new Square();
		}
		
		if (this.isAlive()) {
			if (this.isPawnInOwnBase()) {
				for (int i = 0; i < Settings.DIMBASE; i++) {
					helpSquare[i] = outSquare[Settings.DIMBASE][i];
				}
				for (int i = Settings.DIMBASE; i < 2 * Settings.DIMBASE; i++) {
					helpSquare[i] = outSquare[i - Settings.DIMBASE][Settings.DIMBASE];
				}
			} else if (position.getXCoord() == 0) {
				helpSquare[0] = outSquare[1][position.getYCoord()];
				helpSquare[1] = outSquare[0][position.getYCoord() - 1];
				if (position.getYCoord() < Settings.DIMBOARD - 1) {
					helpSquare[2] = outSquare[0][position.getYCoord() + 1];
				}
			} else if (position.getYCoord() == 0) {
				helpSquare[0] = outSquare[position.getXCoord()][1];
				helpSquare[1] = outSquare[position.getXCoord() - 1][0];
				if (position.getXCoord() < Settings.DIMBOARD - 1) {
					helpSquare[2] = outSquare[position.getXCoord() + 1][0];
				}
			} else if (position.getYCoord() == Settings.DIMBOARD - 1) {
				helpSquare[0] = outSquare[position.getXCoord()][Settings.DIMBOARD - 2];
				helpSquare[1] = outSquare[position.getXCoord() - 1][Settings.DIMBOARD - 1];

				//Ngav Added to correct bug 
				//null pointer exception when position.getXCoord() = 7
				if (position.getXCoord() < Settings.DIMBOARD - 1) {
					helpSquare[2] = outSquare[position.getXCoord() + 1][Settings.DIMBOARD - 1];
				}
	
			} else if (position.getXCoord() == Settings.DIMBOARD - 1) {
				helpSquare[0] = outSquare[Settings.DIMBOARD - 1][position.getYCoord() - 1];
				helpSquare[1] = outSquare[Settings.DIMBOARD - 2][position.getYCoord()];

				//to avoid null pointer exception, when position.getYCoord() = 7
 				if (position.getYCoord() < Settings.DIMBOARD - 1) {
					helpSquare[2] = outSquare[Settings.DIMBOARD - 1][position.getYCoord() + 1];
				}
 				
			} else {
				helpSquare[0] = outSquare[position.getXCoord() + 1][position.getYCoord()];
				helpSquare[1] = outSquare[position.getXCoord() - 1][position.getYCoord()];
				helpSquare[2] = outSquare[position.getXCoord()][position.getYCoord() + 1];
				helpSquare[3] = outSquare[position.getXCoord()][position.getYCoord() - 1];
			}
		}

		return helpSquare;
	}

	// finds all possible moves a pawn can make, without checking legality
	public Square[] getAllMovesForBlackPawn(Square[][] outSquare) {
		Square helpSquare[] = new Square[2 * Settings.DIMBASE];
		for (int i = 0; i < 2 * Settings.DIMBASE; i++) {
			helpSquare[i] = new Square();
		}
		if (this.isAlive()) {
			if (this.isPawnInOwnBase()) {
				for (int i = 0; i < Settings.DIMBASE; i++) {
					helpSquare[i] = outSquare[Settings.DIMBOARD - 1 - Settings.DIMBASE][Settings.DIMBOARD
							- 1 - i];
				}
				for (int i = Settings.DIMBASE; i < 2 * Settings.DIMBASE; i++) {
					helpSquare[i] = outSquare[Settings.DIMBOARD + Settings.DIMBASE - 1 - i][Settings.DIMBOARD
							- 1 - Settings.DIMBASE];
				}
			} else if (position.getXCoord() == 0) {
				helpSquare[0] = outSquare[1][position.getYCoord()];
				helpSquare[1] = outSquare[0][position.getYCoord() - 1];
				
				if (position.getYCoord() < Settings.DIMBOARD - 1) {
					helpSquare[2] = outSquare[0][position.getYCoord() + 1];
				}
			} else if (position.getYCoord() == 0) {
				helpSquare[0] = outSquare[position.getXCoord()][1];
				helpSquare[1] = outSquare[position.getXCoord() - 1][0];
				
				if (position.getXCoord() < Settings.DIMBOARD - 1) {
					helpSquare[2] = outSquare[position.getXCoord() + 1][0];
				}
			} else if (position.getYCoord() == Settings.DIMBOARD - 1) {
				helpSquare[0] = outSquare[position.getXCoord() + 1][Settings.DIMBOARD - 1];
				helpSquare[1] = outSquare[position.getXCoord()][Settings.DIMBOARD - 2];
				if (position.getXCoord() > 0) {
					helpSquare[2] = outSquare[position.getXCoord() - 1][Settings.DIMBOARD - 1];
				}
			} else if (position.getXCoord() == Settings.DIMBOARD - 1) {
				helpSquare[0] = outSquare[Settings.DIMBOARD - 1][position.getYCoord() - 1];
				helpSquare[1] = outSquare[Settings.DIMBOARD - 1][position.getYCoord() + 1];
				helpSquare[2] = outSquare[Settings.DIMBOARD - 2][position.getYCoord()];
			} else {
				helpSquare[0] = outSquare[position.getXCoord() + 1][position.getYCoord()];
				helpSquare[1] = outSquare[position.getXCoord() - 1][position.getYCoord()];
				helpSquare[2] = outSquare[position.getXCoord()][position.getYCoord() + 1];
				helpSquare[3] = outSquare[position.getXCoord()][position.getYCoord() - 1];
			}
		}

		return helpSquare;
	}

	// finds all legal moves for a pawn
	public Square[] getLegitMovesForWhitePawn(Square[][] outSquare) {
		int i = 0;
		int j = 0;
		Square legSquare[] = new Square[Settings.MAX_NUM_PAWN_MOVES];
		Square[] helpSquare = new Square[Settings.MAX_NUM_PAWN_MOVES];
		for (int ii = 0; ii < Settings.MAX_NUM_PAWN_MOVES; ii++) {
			helpSquare[ii] = new Square();
			legSquare[ii] = new Square();
		}
		legSquare = this.getAllMovesForWhitePawn(outSquare);
		while ((i < Settings.MAX_NUM_PAWN_MOVES) && ((legSquare[i].getXCoord() != 0) || (legSquare[i].getYCoord() != 0))) {
			int distanse = Math.max(position.getXCoord() + 1 - Settings.DIMBASE, position.getYCoord() + 1 - Settings.DIMBASE);
			int distanse2 = Math.max(legSquare[i].getXCoord() + 1 - Settings.DIMBASE, legSquare[i].getYCoord() + 1 - Settings.DIMBASE);
			if (legSquare[i].isFree() && (distanse2 >= distanse)) {
				helpSquare[j++] = legSquare[i];
			}
			i++;
		}
		return helpSquare;
	}

	// finds all legal moves for a pawn
	public Square[] getLegitMovesForBlackPawn(Square[][] outSquare) {
		int i = 0;
		int j = 0;
		Square[] legSquare = new Square[Settings.MAX_NUM_PAWN_MOVES];
		Square[] helpSquare = new Square[Settings.MAX_NUM_PAWN_MOVES];
		for (int ii = 0; ii < Settings.MAX_NUM_PAWN_MOVES; ii++) {
			helpSquare[ii] = new Square();
			legSquare[ii] = new Square();
		}
		legSquare = this.getAllMovesForBlackPawn(outSquare);
		while ((i < Settings.MAX_NUM_PAWN_MOVES) && ((legSquare[i].getXCoord() != 0) || (legSquare[i].getYCoord() != 0))) {
			int distanse = Math.max(Settings.DIMBOARD - Settings.DIMBASE - position.getXCoord(), Settings.DIMBOARD - Settings.DIMBASE - position.getYCoord());
			int distanse2 = Math.max(Settings.DIMBOARD - Settings.DIMBASE - legSquare[i].getXCoord(), Settings.DIMBOARD - Settings.DIMBASE - legSquare[i].getYCoord());
			if (legSquare[i].isFree() && (distanse2 >= distanse)) {
				helpSquare[j++] = legSquare[i];
			}
			i++;
		}
		return helpSquare;
	}

	// checking if a pawn can move, so that we can kill it
	public boolean isWhitePawnMovable(Square[][] outSquare) {
		Square[] helpSquare = new Square[2 * Settings.DIMBASE];
		helpSquare = this.getLegitMovesForWhitePawn(outSquare);
		
		boolean toRet = ((helpSquare[0].getXCoord() == 0) && (helpSquare[0].getYCoord() == 0)) ? false : true;
		
		return toRet;
	}

	// checking if a pawn can move, so that we can kill it
	public boolean isBlackPawnMovable(Square[][] outSquare) {
		// //nikolini
		if ((this.isPawnInEnemyBase()))// &&(this.position.getXCoord()!=0)&&(this.position.getYCoord()!=0))
			return true;
		// //nikolini
		Square[] helpSquare = new Square[2 * Settings.DIMBASE];
		helpSquare = this.getLegitMovesForBlackPawn(outSquare);
		/*
		 * System.out.println("-d-d-d-d-d-d-"); for (int
		 * ij=0;ij<helpSquare.length;ij++)
		 * System.out.print(helpSquare[ij].getXCoord
		 * ()+""+helpSquare[ij].getYCoord()+" "); System.out.println("");
		 */return ((helpSquare[0].getXCoord() == 0) && (helpSquare[0].getYCoord() == 0)) ? false
				: true;
	}

	// transform a pawn to a unique number
	public int pawn2Tag() {
		if (this.isAlive()) {
			return position.getXCoord() * Settings.DIMBOARD + position.getYCoord() + 1;
		} else
			return Settings.DIMBOARD * Settings.DIMBOARD + 1;
	}

	// the inverse of the above procedure
	public Pawn tag2Pawn(int tg) {
		int tag = tg - 1;
		Pawn tagPawn = new Pawn();
		tagPawn.position.setXCoord((int) tag / Settings.DIMBOARD);
		tagPawn.position.setYCoord((int) tag - Settings.DIMBOARD * tagPawn.position.getXCoord());
//TODO CHECK
//		tagPawn.position.setXCoord((int) (tag / Settings.DIMBOARD));
//		tagPawn.position.setYCoord((int) (tag - Settings.DIMBOARD * (tagPawn.position.getXCoord())));
		
		return tagPawn;
	}



	public Square getPosition() {
		return position;
	}

	public int getId() {
		return id;
	}

	
	
}
