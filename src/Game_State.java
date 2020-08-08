
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


public class Game_State
{
	private int gameState = GAME_STATE_WHITE;
	public static final int GAME_STATE_WHITE = 0;
	public static final int GAME_STATE_BLACK = 1;
	public static final int GAME_STATE_END = 2;

	// 0 = bottom, size = top
	private List<typeOf_Piece> pieces = new ArrayList<typeOf_Piece>();

	private MoveValidator moveValidator;

	//initialize game
	public Game_State()
	{
		this.moveValidator = new MoveValidator(this);

		// create and place pieces
		// rook, knight, bishop, queen, king, bishop, knight, and rook

		createAndAddPiece(typeOf_Piece.COLOR_WHITE, typeOf_Piece.TYPE_ROOK, typeOf_Piece.ROW_1, typeOf_Piece.COLUMN_D);
		createAndAddPiece(typeOf_Piece.COLOR_WHITE, typeOf_Piece.TYPE_KNIGHT, typeOf_Piece.ROW_1, typeOf_Piece.COLUMN_E);
		createAndAddPiece(typeOf_Piece.COLOR_WHITE, typeOf_Piece.TYPE_BISHOP, typeOf_Piece.ROW_1, typeOf_Piece.COLUMN_F);
		createAndAddPiece(typeOf_Piece.COLOR_WHITE, typeOf_Piece.TYPE_QUEEN, typeOf_Piece.ROW_1, typeOf_Piece.COLUMN_G);
		createAndAddPiece(typeOf_Piece.COLOR_WHITE, typeOf_Piece.TYPE_KING, typeOf_Piece.ROW_1, typeOf_Piece.COLUMN_H);

		// pawns
		//int currentColumn = typeOf_Piece.COLUMN_A;
		//for (int i = 0; i < 8; i++) {
			createAndAddPiece(typeOf_Piece.COLOR_WHITE, typeOf_Piece.TYPE_PAWN, typeOf_Piece.ROW_2,typeOf_Piece.COLUMN_H);
			//currentColumn++;
		//}

		//createAndAddPiece(typeOf_Piece.COLOR_BLACK, typeOf_Piece.TYPE_ROOK, typeOf_Piece.ROW_8, typeOf_Piece.COLUMN_A);
		//createAndAddPiece(typeOf_Piece.COLOR_BLACK, typeOf_Piece.TYPE_KNIGHT, typeOf_Piece.ROW_8,typeOf_Piece.COLUMN_B);
		//createAndAddPiece(typeOf_Piece.COLOR_BLACK, typeOf_Piece.TYPE_BISHOP, typeOf_Piece.ROW_8,typeOf_Piece.COLUMN_C);
		createAndAddPiece(typeOf_Piece.COLOR_BLACK, typeOf_Piece.TYPE_QUEEN, typeOf_Piece.ROW_11,typeOf_Piece.COLUMN_D);
		createAndAddPiece(typeOf_Piece.COLOR_BLACK, typeOf_Piece.TYPE_KING, typeOf_Piece.ROW_11, typeOf_Piece.COLUMN_E);
		createAndAddPiece(typeOf_Piece.COLOR_BLACK, typeOf_Piece.TYPE_BISHOP, typeOf_Piece.ROW_11,typeOf_Piece.COLUMN_F);
		createAndAddPiece(typeOf_Piece.COLOR_BLACK, typeOf_Piece.TYPE_KNIGHT, typeOf_Piece.ROW_11,typeOf_Piece.COLUMN_G);
		createAndAddPiece(typeOf_Piece.COLOR_BLACK, typeOf_Piece.TYPE_ROOK, typeOf_Piece.ROW_11, typeOf_Piece.COLUMN_H);

		// pawns
		//currentColumn = typeOf_Piece.COLUMN_A;
		//for (int i = 0; i < 8; i++) {
			createAndAddPiece(typeOf_Piece.COLOR_BLACK, typeOf_Piece.TYPE_PAWN, typeOf_Piece.ROW_10,typeOf_Piece.COLUMN_D);
			//currentColumn++;
		//}
	}

	/**
	 * create piece instance and add it to the internal list of pieces
	 *
	 * @param color on of Pieces.COLOR_..
	 * @param type on of Pieces.TYPE_..
	 * @param row on of Pieces.ROW_..
	 * @param column on of Pieces.COLUMN_..
	 */
	private void createAndAddPiece(int color, int type, int row, int column)
	{
		typeOf_Piece piece = new typeOf_Piece(color, type, row, column);
		this.pieces.add(piece);
	}

	/**
	 * Move piece to the specified location. If the target location is occupied
	 * by an opponent piece, that piece is marked as 'captured'. If the move
	 * could not be executed successfully, 'false' is returned and the game
	 * state does not change.
	 *
	 * @param move to execute
	 * @return true, if piece was moved successfully
	 */
	public boolean movePiece(Move move)
	{
		if (!this.moveValidator.isMoveValid(move) )
		{
			System.out.println("move invalid");
			return false;
		}

		typeOf_Piece piece = getNonCapturedPieceAtLocation(move.sourceRow, move.sourceColumn);

		// check if the move is capturing an opponent piece
		int opponentColor = (piece.getColor() == typeOf_Piece.COLOR_BLACK ? typeOf_Piece.COLOR_WHITE
				: typeOf_Piece.COLOR_BLACK);
		if (isNonCapturedPieceAtLocation(opponentColor, move.targetRow, move.targetColumn))
		{
			typeOf_Piece opponentPiece = getNonCapturedPieceAtLocation(move.targetRow, move.targetColumn);
			opponentPiece.isCaptured(true);
		}

		piece.setRow(move.targetRow);
		piece.setColumn(move.targetColumn);

		if (isGameEndConditionReached())
		{
			this.gameState = GAME_STATE_END;
		}
		else
		{
			this.changeGameState();
		}
		return true;
	}

	/**
	 * check if the games end condition is met: One color has a captured king
	 *
	 * @return true if the game end condition is met
	 */
	private boolean isGameEndConditionReached()
	{
		for (typeOf_Piece piece : this.pieces)
		{
			if (piece.getType() == typeOf_Piece.TYPE_KING && piece.isCaptured())
			{
                                JOptionPane.showMessageDialog(null,"CKECKMATED! GAME OVER !");
				return true;
			}
			else
			{
				// continue iterating
			}
		}
		return false;
	}

	/**
	 * returns the first piece at the specified location that is not marked as
	 * 'captured'.
	 *
	 * @param row one of typeOf_Piece.ROW_..
	 * @param column one of typeOf_Piece.COLUMN_..
	 * @return the first not captured piece at the specified location
	 */
	public typeOf_Piece getNonCapturedPieceAtLocation(int row, int column)
	{
		for (typeOf_Piece piece : this.pieces)
		{
			if (piece.getRow() == row && piece.getColumn() == column&& piece.isCaptured() == false)
			{
				return piece;
			}
		}
		return null;
	}

	/**
	 * Checks whether there is a piece at the specified location that is not
	 * marked as 'captured' and has the specified color.
	 *
	 * @param color one of typeOf_Piece.COLOR_..
	 * @param row one of typeOf_Piece.ROW_..
	 * @param column on of typeOf_Piece.COLUMN_..
	 * @return true, if the location contains a not-captured piece of the
	 *         specified color
	 */
	private boolean isNonCapturedPieceAtLocation(int color, int row, int column)
	{
		for (typeOf_Piece piece : this.pieces)
		{
			if (piece.getRow() == row && piece.getColumn() == column&& piece.isCaptured() == false && piece.getColor() == color)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether there is a non-captured piece at the specified location
	 *
	 * @param row one of typeOf_Piece.ROW_..
	 * @param column on of typeOf_Piece.COLUMN_..
	 * @return true, if the location contains a piece
	 */
	public boolean isNonCapturedPieceAtLocation(int row, int column)
	{
		for (typeOf_Piece piece : this.pieces)
		{
			if (piece.getRow() == row && piece.getColumn() == column&& piece.isCaptured() == false)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @return current game state (one of Game_State.GAME_STATE_..)
	 */
	public int getGameState()
	{
		return this.gameState;
	}

	/**
	 * @return the internal list of pieces
	 */
	public List<typeOf_Piece> getPieces()
	{
		return this.pieces;
	}

	/**
	 * switches the game state depending on the current board situation.
	 */
	public void changeGameState()
	{

		// check if game end condition has been reached
		//
		if (this.isGameEndConditionReached())
		{
			if (this.gameState == Game_State.GAME_STATE_BLACK)
			{
				//System.out.println("Game over! Black won!");
                                JOptionPane.showMessageDialog(null,"CKECKMATED!Game over! Black Win!");
			}
			else
			{
				//System.out.println("Game over! White won!");
                                JOptionPane.showMessageDialog(null,"CKECKMATED!Game over! White win!");
			}
			this.gameState = Game_State.GAME_STATE_END;
			return;
		}

		switch (this.gameState)
		{
			case GAME_STATE_BLACK:
				this.gameState = GAME_STATE_WHITE;
				break;
			case GAME_STATE_WHITE:
				this.gameState = GAME_STATE_BLACK;
				break;
			case GAME_STATE_END:
				// don't change anymore
				break;
			default:
				throw new IllegalStateException("unknown game state:" + this.gameState);
		}
	}

	public MoveValidator getMoveValidator()
	{
		return this.moveValidator;
	}

}
//end Game_State.java