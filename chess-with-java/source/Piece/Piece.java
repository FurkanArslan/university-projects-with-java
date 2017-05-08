package Chess.Piece;

import java.util.ArrayList;
import Chess.Game.ChessGame;

import chessGUI.cguiInterfaces.ChessPiece;
/**
 * @author Furkan Arslan
 * Piece class is abstract class
 * Piece class is main class of other piece type class
 */
public abstract class Piece implements ChessPiece {
	private int xPosition;
	private int yPosition;
	private Color color;
	private PieceType pieceType;
	protected final int directionEnd=8;
	protected final int directionStart=1;
	/**
	 * this is piece's constructor
	 * @param xPosition
	 * @param yPosition
	 * @param color
	 * @param pieceType
	 */
	public Piece(int xPosition,int yPosition,Color color,PieceType pieceType) {
		this.xPosition=xPosition;
		this.yPosition=yPosition;
		this.color=color;
		this.pieceType=pieceType;
	}
	/**
	 * @param xPosition
	 */
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	/**
	 * @param yPosition
	 */
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	/**
	 * @Override
	 * @return yPosition
	 */
	public int getYPosition() {
		return yPosition;
	}
	/**
	 * @Override
	 * @return xPosition
	 */
	public int getXPosition() {
		return xPosition;
	}
	/**
	 * @Override
	 * @return color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param pieceType
	 */
	public void setPieceType(PieceType pieceType) {
		this.pieceType = pieceType;
	}
	/**
	 * @Override
	 * @return xPosition
	 */
	public PieceType getPieceType() {
		return pieceType;
	}
	/**
	 * @param targetX user wants to move that x position
	 * @param targetY user wants to move that y position
	 * @return if piece can move that position returns true, otherwise returns false
	 */
	public abstract boolean move(int targetX,int targetY);
	/**
	 * @return an array list which include positions where piece can move
	 */
	public abstract ArrayList<Integer> getMoveable();

	protected void determineMove(int xIncreaseAmount,int yIncreaseAmount, ArrayList<Integer> moveable) {
		int xPosition=this.getXPosition()+xIncreaseAmount;			//determine x coordinate where the program starts to search.
		int yPosition=this.getYPosition()+yIncreaseAmount;
		for (;xPosition<=directionEnd && yPosition<=directionEnd && directionStart<=xPosition && directionStart<=yPosition ; xPosition+=xIncreaseAmount,yPosition+=yIncreaseAmount) {
			if (pieceControl(xPosition, yPosition , moveable)){
				System.out.println(xPosition+ " "+ yPosition);
				break;
			}
		}
	}

	protected boolean pieceControl(int xPosition, int yPosition,ArrayList<Integer> moveable) {
		ArrayList<ChessPiece> piece=ChessGame.getPiece();
		for (int pieceNumber = 0; pieceNumber < piece.size(); pieceNumber++) {
			if(xPosition == piece.get(pieceNumber).getXPosition() && yPosition == piece.get(pieceNumber).getYPosition()){ //to control Is any piece exist that position
				if(this.getColor() == piece.get(pieceNumber).getColor()){	//if one piece exist, color is controlled. 
					return true;											//if the piece has same color to moved piece,returns true
				}
				else{									//if the piece has different color to moved piece,firstly the position adds array then returns true
					System.out.println("as");
					moveable.add(xPosition);
					moveable.add(yPosition);
					return true;											
				}
			}
		}
		moveable.add(xPosition);											//if that position is empty,the position adds to array
		moveable.add(yPosition);											
		return false;
	}
	/**
	 * @param moveable which include positions where piece can move
	 * @param targetX  where user wants to move
	 * @param targetY  where user wants to move
	 * @return	if piece can move the target position,returns true  otherwise returns false
	 */
	public boolean checkMoveable(ArrayList<Integer> moveable, int targetX,int targetY) {
		/*if(moveable==null)
			return false;*/
		for (int position = 0; position < moveable.size(); position+=2) {
			if(moveable.get(position) == targetX && moveable.get(position+1) == targetY)
				return true;
		}
		return false;
	}
	/**
	 * this method check opposite color's pieces for determine come into existence any problem after piece move
	 * @param positionX 
	 * @param positionY
	 * @return if come into existence problem about to king returns false,otherwise true
	 */
	public boolean dangerousSituationControl(int positionX, int positionY) {
		for (ChessPiece pieces :ChessGame.getPiece() ) {
			if(pieces.getColor()!=this.getColor()){
				if (pieces instanceof Queen) {
				Queen queen = (Queen) pieces;
					if(checkMoveable(queen.getMoveable(),positionX, positionY))
						return false;
				}
				else if (pieces instanceof Bishop) {
					Bishop bishop = (Bishop) pieces;
					if(checkMoveable(bishop.getMoveable(),positionX, positionY))
						return false;
				}
				else if (pieces instanceof Knight) {
					Knight knight = (Knight) pieces;
					if(checkMoveable(knight.getMoveable(),positionX, positionY))
						return false;
				}
				else if (pieces instanceof Rook) {
					Rook rook = (Rook) pieces;
					if(checkMoveable(rook.getMoveable(),positionX, positionY))
						return false;
				}
				else if (pieces instanceof Pawn) {
					Pawn pawn = (Pawn) pieces;
					if(checkMoveable(pawn.getMoveable(),positionX, positionY))
						return false;
						
				}
			}
		}
		return true;
	}
}
