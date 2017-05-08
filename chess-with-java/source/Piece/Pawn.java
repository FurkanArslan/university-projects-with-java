package Chess.Piece;

import java.util.ArrayList;

import Chess.Game.ChessGame;

import chessGUI.cguiInterfaces.ChessPiece;

public class Pawn extends Piece {
	/**
	 * this is Pawn's constructor
	 * @param xPosition
	 * @param yPosition
	 * @param color
	 * @param pieceType
	 */
	public Pawn(int xPosition, int yPosition, Color color, PieceType pieceType) {
		super(xPosition, yPosition, color, pieceType);
	}
	/**
	 * @see Chess.Piece.Piece#move(int, int)
	 */
	@Override
	public boolean move(int targetX,int targetY) {
	ArrayList<Integer> moveable= new ArrayList<Integer>();
		determineMove(moveable);
		if(checkMoveable(moveable, targetX, targetY)){
			return true;
		}
		return false;
	}
	
	private void crossMove(int xIncreaseAmount,int yIncreaseAmount, ArrayList<Integer> moveable){
		int xPosition=this.getXPosition()+xIncreaseAmount;
		int yPosition=this.getYPosition()+yIncreaseAmount;
		if(crossMoveControl(xPosition,yPosition)){
			moveable.add(xPosition);
			moveable.add(yPosition);
		}
	}
	
	private boolean crossMoveControl(int xPosition, int yPosition){
		ArrayList<ChessPiece> piece=ChessGame.getPiece();
		for (int pieceNumber = 0; pieceNumber < piece.size(); pieceNumber++) {
			if(xPosition == piece.get(pieceNumber).getXPosition() && yPosition == piece.get(pieceNumber).getYPosition()){ //to control Is any piece exist that position
				if(this.getColor() != piece.get(pieceNumber).getColor()){	//if one piece exist, color is controlled. 
					return true;											//if the piece has different color to moved piece,returns true
				}
			}
		}
		return false;														//if the piece has same color to moved piece,returns false
	}

	private void firstMove(ArrayList<Integer> moveable, int yIncreaseAmount ) {
		determineMove(0, yIncreaseAmount, moveable);
	}

	private void determineMove(int yIncreaseAmount, ArrayList<Integer> moveable){
		determineMove(0, yIncreaseAmount, moveable);
		crossMove(1, yIncreaseAmount, moveable);
		crossMove(-1, yIncreaseAmount , moveable);
	}
	
	protected void determineMove(int xIncreaseAmount,int yIncreaseAmount, ArrayList<Integer> moveable){
		int xPosition=this.getXPosition()+xIncreaseAmount;
		int yPosition=this.getYPosition()+yIncreaseAmount;
		if(pieceControl(xPosition, yPosition)){
			moveable.add(xPosition);
			moveable.add(yPosition);
		}
	}

	private boolean pieceControl(int xPosition, int yPosition) {
		ArrayList<ChessPiece> piece=ChessGame.getPiece();
		for (int pieceNumber = 0; pieceNumber < piece.size(); pieceNumber++) {
			if(piece.get(pieceNumber).getXPosition()== xPosition && piece.get(pieceNumber).getYPosition() == yPosition){
				return false;
			}
		}
		return true;
	}
	/**
	 * @see Chess.Piece.Piece#getMoveable()
	 */
	public ArrayList<Integer> getMoveable() {
		ArrayList<Integer> moveable=new ArrayList<Integer>();
		determineMove(moveable);
		return moveable;
	}

	private void determineMove(ArrayList<Integer> moveable) {
		switch (this.getColor()) {
		case WHITE:{
			if(this.getYPosition() == 2){
				firstMove(moveable,2);
			}
				determineMove(1, moveable);
				break;
			}
		case BLACK:{
			if(this.getYPosition() == 7){
				firstMove(moveable,-2);
			}
				determineMove(-1, moveable);
				break;
			}
		}
	}
	/**
	 * 
	 * @return if promotion process happens right,returns true otherwise returns false
	 */
	public boolean promotion(){
		switch (this.getColor()) {
		case WHITE:{
			if(this.getYPosition() == 8){
				return true;
			}
		}
		case BLACK:{
			if(this.getYPosition() == 1){
				return true;
			}
		}
	}
		return false;		//if pawn does not reach whether table's top or table's bottom, returns false
	}
}
