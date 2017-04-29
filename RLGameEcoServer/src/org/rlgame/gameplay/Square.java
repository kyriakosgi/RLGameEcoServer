package org.rlgame.gameplay;

import org.rlgame.gameplay.Settings;

public class Square {
    private int xCoord;
    private int yCoord;
    
	private boolean isFree; // no pawn is on that square
	private boolean isInWBase;
	private boolean isInBBase;

	public Square() {
		
	}
	
	public Square(int x, int y, boolean empty) {
		xCoord = x;
		yCoord = y;
		isFree = empty;
	}

	public Square(int x, int y, int dimBoard, int dimBase) {
		xCoord = x;
		yCoord = y;
		isFree = true;
		isInWBase = ((x < dimBase) && (y < dimBase));
		isInBBase = ((x > dimBoard - dimBase - 1) && (y > dimBoard - dimBase - 1));
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree() {
		isFree = true;
	}

	public void setUnFree() {
		isFree = false;
	}

	public boolean isInWhiteBase() {
		return ((xCoord < Settings.DIMBASE) && (yCoord < Settings.DIMBASE));
	}

	public boolean isInBlackBase() {
		return ((xCoord > Settings.DIMBOARD - Settings.DIMBASE - 1) && (yCoord > Settings.DIMBOARD - Settings.DIMBASE - 1));
	}

	public void setXCoord(int x) {
		xCoord = x;
	}

	public void setYCoord(int y) {
		yCoord = y;
	}

	public int getXCoord() {
		return xCoord;
	}

	public int getYCoord() {
		return yCoord;
	}



	// tag is a number I map each square with. I use it for describing the move
	// when human players are involved
	public int square2Tag() {
		return xCoord * Settings.DIMBOARD + yCoord + 1;
	}

	public Square tag2Square(int tg) {
		int tag = tg - 1;
		Square tagSq = new Square();
//		tagSq.xCoord = (int) tag / DIMBOARD;
//		tagSq.yCoord = (int) tag - DIMBOARD * (tagSq.xCoord);

		tagSq.xCoord = (int) (tag / Settings.DIMBOARD);
		tagSq.yCoord = (int) (tag - Settings.DIMBOARD * (tagSq.xCoord));
		
		return tagSq;
	}

	
	public String getPrintInfo() {
		if (this.isInWBase) return "W";
		if (this.isInBBase) return "B";
		return this.isFree ? "0" : "1"; 
	}	
	
}
