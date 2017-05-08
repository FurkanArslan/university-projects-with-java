package Chess.Piece;

import java.util.ArrayList;

public class Bishop extends Piece {
	/**
	 * this is Bishop's constructor
	 * @param xPosition
	 * @param yPosition
	 * @param color
	 * @param pieceType
	 */
	public Bishop(int xPosition, int yPosition, Color color, PieceType pieceType) {
		super(xPosition, yPosition, color, pieceType);
	}
	/**
	 * @see Chess.Piece.Piece#move(int, int)
	 */
	@Override
	public boolean move(int targetX,int targetY) {
		ArrayList<Integer> moveable=new ArrayList<Integer>();
		determineMove(moveable);
		if(checkMoveable(moveable,targetX,targetY)){
			return true;
		}
		return false;
	}
	
	private void determineMove(ArrayList<Integer> moveable){
		super.determineMove(1, 1, moveable );
		super.determineMove(1, -1, moveable );
		super.determineMove(-1, 1, moveable );
		super.determineMove(-1, -1, moveable );
	}
	/**
	 * @see Chess.Piece.Piece#getMoveable()
	 */
	public ArrayList<Integer> getMoveable() {
		ArrayList<Integer> moveable=new ArrayList<Integer>();
		determineMove(moveable);
		return moveable;
	}
	
}
