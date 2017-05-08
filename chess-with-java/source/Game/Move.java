package Chess.Game;

import java.io.Serializable;
import java.util.ArrayList;
import chessGUI.ChessGUI;
import chessGUI.cguiExceptions.InitializeException;
import chessGUI.cguiInterfaces.ChessPiece;
import chessGUI.cguiInterfaces.ChessPiece.Color;
import chessGUI.cguiInterfaces.ChessPiece.PieceType;
import Chess.Piece.Bishop;
import Chess.Piece.King;
import Chess.Piece.Knight;
import Chess.Piece.Pawn;
import Chess.Piece.Piece;
import Chess.Piece.Queen;
import Chess.Piece.Rook;
/**
 * 
 * @author Furkan Arslan
 * this class include chess's move.
 */
public class Move implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7440752921597062739L;
	private int turn;
	private ArrayList<ChessPiece> pieces;
	transient private ChessGUI chessGUI;
	/**
	 * this is the constructor
	 * @param chessGame
	 */
	public Move(ChessGame chessGame) {
		turn=chessGame.getTurn();
		pieces=ChessGame.getPiece();
		chessGUI=chessGame.getChessGUI();
	}
	/**
	 * 
	 * @return turn
	 */
	public int getTurn() {
		return turn;
	}
	/**
	 * 
	 * @return pieces array list
	 */
	public ArrayList<ChessPiece> getPieces() {
		return pieces;
	}
	/**
	 * this method check wanted piece whether can move or not.
	 * @param sourceX	where moving piece be
	 * @param sourceY	where moving piece be
	 * @param targetX	where user wants to move to any piece
	 * @param targetY	where user wants to move to any piece
	 * @return turn
	 */
	public int moveProcess(int sourceX, int sourceY, int targetX, int targetY){
		try {
			Piece movingPiece=(Piece)findPiece(sourceX,sourceY);
			int turn= turnControl(movingPiece);
			King oppositeKing=determineKingPosition(movingPiece.getColor());
			
			if(this.turn == turn && movingPiece.move(targetX,targetY) ){controlCheck(movingPiece, oppositeKing);
				if(castling(movingPiece,targetX,targetY))
					return this.turn;

				setPiecePosition(targetX,targetY,movingPiece);
				determineTurn();
				promotion(movingPiece);
			}
		}catch (InitializeException e) {
			e.printStackTrace();
		}
		return this.turn;
	}
	
	private boolean castling(Piece king, int targetX, int targetY) {
		if(king.getPieceType() != PieceType.KING)
			return false;
		boolean firstPositionDangerousControl=false;
		boolean secondPositionDangerousControl = false;
		boolean emptyControl=false;
		boolean rookMoveControl=false;
		int rookPositionX = 1;
		int rookNewPositionX=1;
		if(targetX == 7){			//it's mean castling is being done straight right side 
			firstPositionDangerousControl=king.dangerousSituationControl(6, targetY);
			secondPositionDangerousControl=king.dangerousSituationControl(7, targetY);
			rookPositionX=8;
			rookNewPositionX=6;
			emptyControl=emptyControl(king.getXPosition()+1, targetY, rookPositionX);
		}
		else if(targetX == 3){
			firstPositionDangerousControl=king.dangerousSituationControl(4, targetY);
			secondPositionDangerousControl=king.dangerousSituationControl(3, targetY);
			rookPositionX=1;
			rookNewPositionX=4;
			emptyControl=emptyControl(rookPositionX+1, targetY, king.getXPosition());
		}
		Piece rook=(Piece) findPiece(rookPositionX,targetY);
		if (rook instanceof Rook) {
			Rook fixrook = (Rook) rook;
			rookMoveControl=fixrook.isMoveControl();
		}
		if(firstPositionDangerousControl && secondPositionDangerousControl && emptyControl && rookMoveControl){
			setPiecePosition(targetX, targetY, king);
			setPiecePosition(rookNewPositionX, targetY, rook);
			determineTurn();
			return true;
		}
		return false;
	}

	private boolean emptyControl(int controlStartPositionX, int positionY, int controlEndPositionX) {
		for (; controlStartPositionX < controlEndPositionX ; controlStartPositionX++) {
			if(findPiece(controlStartPositionX, positionY) != null)
				return false;
		}
		return true;
	}

	private void promotion(Piece movingPiece) throws InitializeException  {
		if(movingPiece.getPieceType() == PieceType.PAWN){
			if(controlPromotion(movingPiece)){
				promotion(movingPiece.getXPosition(),movingPiece.getYPosition(),movingPiece);
			}
		}
	}

	private void determineTurn() {
		if(turn==1)			//if whites have moved any piece,the move right pass to blacks 
			this.turn++;			
		else
			this.turn--;
	}
	
	private void promotion(int targetX, int targetY, Piece movingPiece) throws InitializeException {
		switch (chessGUI.promotion()) {
		case QUEEN:		pieces.add(new Queen(targetX, targetY,movingPiece.getColor(),PieceType.QUEEN));break;
		case KNIGHT:	pieces.add(new Knight(targetX, targetY, movingPiece.getColor(),PieceType.KNIGHT));break;
		case BISHOP:	pieces.add(new Bishop(targetX, targetY, movingPiece.getColor(), PieceType.BISHOP));break;
		default:		pieces.add(new Rook(targetX, targetY, movingPiece.getColor(), PieceType.ROOK));break;
		};
		pieces.remove(movingPiece);
	}

	private boolean controlPromotion(Piece movingPiece) {
		if(movingPiece instanceof Pawn){
			Pawn pawn= (Pawn) movingPiece;
			return pawn.promotion();
		}
		return false;
	}

	private void controlCheck(Piece movingPiece, King oppositeKing) throws InitializeException {
		ArrayList<Integer> moveable=movingPiece.getMoveable();
		if(movingPiece.checkMoveable(moveable, oppositeKing.getXPosition(), oppositeKing.getYPosition())){
			chessGUI.throwWarning("ATTENCION", "CHECK");
		}
	}
	
	private void setPiecePosition(int targetX, int targetY, Piece movingPiece) {
		checkPieceMeal(targetX, targetY);
		movingPiece.setXPosition(targetX);
		movingPiece.setYPosition(targetY);
	}
	
	private int turnControl(Piece movingPiece) {
		switch (movingPiece.getColor()) {
		case WHITE:
			return 1;					//1 describe that whites have move right
		case BLACK:
			return 2;					//2 describe that blacks have move right
		}
		return -1;
	}
	
	private King determineKingPosition(Color color) {
		int pieceNumber = 0;
		for (; pieceNumber < pieces.size(); pieceNumber++) {
			if(pieces.get(pieceNumber).getPieceType() == PieceType.KING && pieces.get(pieceNumber).getColor() != color){
				break;
			}
		}
		return (King)pieces.get(pieceNumber);
	}
	
	private void checkPieceMeal(int targetX, int targetY) {
		for (int pieceNumber = 0; pieceNumber < pieces.size(); pieceNumber++) {
			if(pieces.get(pieceNumber).getXPosition() == targetX && pieces.get(pieceNumber).getYPosition() == targetY){
				pieces.remove(pieceNumber);
			}
		}
	}
	
	private ChessPiece findPiece(int sourceX, int sourceY) {
		for (ChessPiece piece : pieces) {
			if(piece.getXPosition()== sourceX && piece.getYPosition()==sourceY)
				return piece;
		}
		return null;	
	}
}
