package Chess.Game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Chess.Piece.*;

import chessGUI.ChessGUI;
import chessGUI.cguiExceptions.InitializeException;
import chessGUI.cguiInterfaces.ChessLogicSide;
import chessGUI.cguiInterfaces.ChessPiece;
import chessGUI.cguiInterfaces.ChessPiece.Color;
import chessGUI.cguiInterfaces.ChessPiece.PieceType;
/**
 * 
 * @author Furkan Arslan
 * this class determine user what to do
 */
public class ChessGame implements ChessLogicSide{

	private static ArrayList<ChessPiece> piece;
	private ChessGUI chessGUI;
	private int turn;
	/**
	 * this is ChessGame's constructor
	 */
	public ChessGame() {
		piece=new ArrayList<ChessPiece>();
		turn=1;
	}
	/**
	 * @Override
	 * @param chessGUI
	 * @return true
	 */
	public boolean init(ChessGUI chessGUI) {
		this.chessGUI=chessGUI;
		return true;
	}
	/**
	 * for start new game
	 * @Override
	 */
	public void newGame() {
		piece=new ArrayList<ChessPiece>();
		beginingPieces();
		setTurn(1);									//the chess game always start with white
		setBoard(piece);
	}
	/**
	 * @param selectedFile which the file restore from selected location
	 * @throws exception
	 */
	@Override
	public void loadGame(File selectedFile) {
		try {
			ObjectInputStream load=new ObjectInputStream(new FileInputStream(selectedFile));
			Move loadMove=(Move) load.readObject();
			setPiece(loadMove.getPieces());
			setBoard(piece);
			setTurn(loadMove.getTurn());
			load.close();
		} catch (Exception e) {
			throwWarning("Hata", "hatalý dosya secimi",selectedFile);
		}
	}
	/**
	 * @param saveFile which user want to save this file
	 * @throws exception
	 */
	@Override
	public void saveGame(File saveFile) {
		try {
			ObjectOutputStream save=new ObjectOutputStream(new FileOutputStream(saveFile));
			Move saveMove=new Move(this);
			save.writeObject(saveMove);
			save.close();
		} catch (Exception e) {
				throwWarning("Hata", "hatalý dosya secimi",saveFile);
		}
	}

	private void throwWarning(String title,String message,File file) {
		try {
			if(file!= null)
				chessGUI.throwWarning(title, message);
			} catch (InitializeException e) {
				e.printStackTrace();
			}
	}
	/**
	 * this method check wanted piece whether can move or not then set board according to piece.
	 * @param sourceX	where moving piece be
	 * @param sourceY	where moving piece be
	 * @param targetX	where user wants to move to any piece
	 * @param targetY	where user wants to move to any piece
	 * @Override
	 */
	public void movePiece(int sourceX, int sourceY, int targetX, int targetY) {
		Move move=new Move(this);
		this.turn=move.moveProcess(sourceX, sourceY, targetX, targetY);
		setBoard(piece);
	}	

	private void setBoard(ArrayList<ChessPiece> piece) {
		try {
			chessGUI.setBoard(piece);
		} catch (InitializeException e) {
			e.printStackTrace();
		}
	}

	private void beginingPieces(){
		int xPosition=1;
		
		for(;xPosition!=9;xPosition++){
		addPiece(xPosition, 1, Color.WHITE);
		addPawn(xPosition,  2, Color.WHITE);
		addPiece(xPosition, 8, Color.BLACK);
		addPawn(xPosition,  7, Color.BLACK);
		}	
		
	}
	
	private void addPiece(int xPosition, int yPosition, Color color) {
		switch (xPosition) {
		case 1:
		case 8:
			addRook(xPosition, yPosition, color);break;
		case 2:
		case 7:
			addKnight(xPosition, yPosition, color);break;
		case 3:
		case 6:
			addBishop(xPosition, yPosition, color);break;
		case 4:
			addQueen(xPosition, yPosition, color);break;
		case 5:
			addKing(xPosition, yPosition, color);break;
		default:
			break;
		}	
	}
	
	private void addPawn(int xPosition, int yPosition, Color color) {
		ChessPiece pawn=new Pawn(xPosition,yPosition,color,PieceType.PAWN);
		piece.add(pawn);
	}

	private void addRook(int xPosition, int yPosition, Color color) {
	ChessPiece rook=new Rook(xPosition,yPosition,color,PieceType.ROOK);
	piece.add(rook);
	}
	
	private void addKnight(int xPosition, int yPosition, Color color) {
	ChessPiece knight=new Knight(xPosition,yPosition,color,PieceType.KNIGHT);
	piece.add(knight);
	}	
	
	private void addBishop(int xPosition, int yPosition, Color color) {
	ChessPiece bishop=new Bishop(xPosition,yPosition,color,PieceType.BISHOP);
	piece.add(bishop);
	}

	private void addKing(int xPosition, int yPosition, Color color) {
	ChessPiece king=new King(xPosition,yPosition,color,PieceType.KING);
	piece.add(king);
	}
	
	private void addQueen(int xPosition, int yPosition, Color color) {
	ChessPiece queen=new Queen(xPosition,yPosition,color,PieceType.QUEEN);
	piece.add(queen);
	}
	/**
	 * @return piece
	 */
	public static ArrayList<ChessPiece> getPiece (){
		return piece;
	}
	/**
	 * @param newPiece
	 */
	public static void setPiece(ArrayList<ChessPiece> newPiece){
		piece=newPiece;
	}
	/**
	 * @return turn
	 */
	public int getTurn() {
		return turn;
	}
	/**
	 * @param turn
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}
	/**
	 * @return chessGUI
	 */
	public ChessGUI getChessGUI(){
		return chessGUI;
	}
}
