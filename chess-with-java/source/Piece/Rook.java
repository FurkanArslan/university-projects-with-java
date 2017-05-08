package Chess.Piece;

import java.util.ArrayList;
/**
 * @author Furkan Arslan
 * The rook part of chess piece
 * this class determine rook's specialities
 */
public class Rook extends Piece {
	private boolean moveControl;
	/**
	 * @return if rook moved,returns false otherwise returns true
	 */
	public boolean isMoveControl() {
		return moveControl;
	}
	/**
	 * this is King's constructor
	 * @param xPosition
	 * @param yPosition
	 * @param color
	 * @param pieceType
	 */
	public Rook(int xPosition, int yPosition, Color color, PieceType pieceType) {
		super(xPosition, yPosition, color, pieceType);
		this.moveControl=true;
	}
	/**
	 * @see Chess.Piece.Piece#move(int, int)
	 * @Override
	 */
	public boolean move(int targetX,int targetY) {
		this.moveControl=false;
		ArrayList<Integer> moveable=new ArrayList<Integer>();
		determineMove(moveable);
		if(checkMoveable(moveable,targetX,targetY))
			return true;
		
		return false;
	}
	
	private void determineMove(ArrayList<Integer> moveable){
		super.determineMove(1, 0, moveable);
		super.determineMove(0, 1, moveable);
		super.determineMove(-1, 0, moveable);
		super.determineMove(0, -1, moveable);
	}
	/**
	 * @Override
	 * @see Chess.Piece.Piece#getMoveable()
	 */
	public ArrayList<Integer> getMoveable() {
		ArrayList<Integer> moveable=new ArrayList<Integer>();
		determineMove(moveable);
		return moveable;
	}


}
