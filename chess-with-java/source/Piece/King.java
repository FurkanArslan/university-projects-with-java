package Chess.Piece;

import java.util.ArrayList;
/**
 * @author Furkan Arslan
 * king part of chess piece
 * this class determine king's specialities
 */
public class King extends Piece {
	private boolean moveControl;
	/**
	 * @return if king moved,returns false otherwise returns true
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
	public King(int xPosition,int yPosition,Color color,PieceType pieceType) {
		super(xPosition, yPosition, color, pieceType);
		this.moveControl=true;
	}
	/**
	 * @Override
	 * @see Chess.Piece.Piece#move(int, int)
	 */
	public boolean move(int targetX,int targetY) {
		ArrayList<Integer> moveable= new ArrayList<Integer>();
		if(moveControl){
		determineMove(2,0, targetX, targetY, moveable);
		determineMove(-2,0, targetX, targetY, moveable);
		}
		determineMove(1,1, targetX, targetY, moveable);
		determineMove(1,0, targetX, targetY, moveable);
		determineMove(1,-1, targetX, targetY, moveable);
		determineMove(0,1, targetX, targetY, moveable);
		determineMove(0,-1, targetX, targetY, moveable);
		determineMove(-1,-1, targetX, targetY, moveable);
		determineMove(-1,0, targetX, targetY, moveable);
		determineMove(-1,1, targetX, targetY, moveable);
		this.moveControl=false;
		
		if(checkMoveable(moveable, targetX, targetY))
			return true;

		return false;
	}

	private void determineMove(int xIncreaseAmount, int yIncreaseAmount,int targetX,int targetY, ArrayList<Integer> moveable) {
		int xPosition=this.getXPosition()+xIncreaseAmount;			//determine x coordinate where the program starts to search.
		int yPosition=this.getYPosition()+yIncreaseAmount;
		
		if(dangerousSituationControl(targetX,targetY) && xPosition<=directionEnd && yPosition<=directionEnd && directionStart<=xPosition && directionStart<=yPosition)
			pieceControl(xPosition, yPosition, moveable);
	}
	/**
	 * @Override
	 * @see Chess.Piece.Piece#getMoveable()
	 */
	public ArrayList<Integer> getMoveable() {
		return null;
	}
}
