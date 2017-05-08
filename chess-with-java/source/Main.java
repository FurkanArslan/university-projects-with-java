package Chess;

import Chess.Game.ChessGame;
import chessGUI.ChessGUI;
import chessGUI.cguiExceptions.CguiException;
import chessGUI.cguiInterfaces.ChessGUISide;

public class Main {

	/**
	 * @param args
	 * @throws CguiException
	 */
	public static void main(String[] args) {
		ChessGame logic = new ChessGame();
		try {
			ChessGUISide chess=new ChessGUI(logic, "Chess Game");
		} catch (CguiException e) {
			e.printStackTrace();
		}

	}

}
