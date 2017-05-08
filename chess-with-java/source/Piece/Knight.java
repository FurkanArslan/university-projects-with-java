package Chess.Piece;

import java.util.ArrayList;

public class Knight extends Piece {
	/**
	 * this is Knight's constructor
	 * @param xPosition
	 * @param yPosition
	 * @param color
	 * @param pieceType
	 */
	public Knight(int xPosition, int yPosition, Color color, PieceType pieceType) {
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
		determineMove(1, 2, moveable);
		determineMove(1, -2, moveable);
		determineMove(2, 1, moveable);
		determineMove(2, -1, moveable);
		determineMove(-1, -2, moveable);
		determineMove(-1, 2, moveable);
		determineMove(-2, -1, moveable);
		determineMove(-2, 1, moveable);
	}
	
	protected void determineMove(int xIncreaseAmount, int yIncreaseAmount, ArrayList<Integer> moveable) {
		int xPosition=this.getXPosition()+xIncreaseAmount;			//determine x coordinate where the program starts to search.
		int yPosition=this.getYPosition()+yIncreaseAmount;
		
		if(xPosition<=directionEnd && yPosition<=directionEnd && directionStart<=xPosition && directionStart<=yPosition)
			pieceControl(xPosition, yPosition, moveable);
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
