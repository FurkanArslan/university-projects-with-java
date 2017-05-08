package Chess.Piece;

import java.util.ArrayList;
/**
 * @author Furkan Arslan
 * The queen part of chess piece
 * this class determine queen's specialities
 */
public class Queen extends Piece {
	/**
	 * this is Queen's constructor
	 * @param xPosition
	 * @param yPosition
	 * @param color
	 * @param pieceType
	 */
	public Queen(int xPosition, int yPosition, Color color, PieceType pieceType) {
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

	private void determineMove(ArrayList<Integer> moveable) {
		super.determineMove(1, 1, moveable);
		super.determineMove(1, -1, moveable);
		super.determineMove(-1, 1, moveable);
		super.determineMove(-1, -1, moveable);
		super.determineMove(0, 1, moveable);
		super.determineMove(0, -1, moveable);
		super.determineMove(-1, 0, moveable);
		super.determineMove(1, 0, moveable);
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
