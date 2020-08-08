
import java.io.BufferedReader;
import java.io.InputStreamReader;

//start ConsoleGui.java
public class ConsoleGui 
{
	private Game_State chessGame;

	public ConsoleGui() 
	{

		this.chessGame = new Game_State();
		// create a new chess game
		//
	}

	public  void main(String[] args) 
	{
		new ConsoleGui().run();
	}

	/**
	 * Contains the main loop of the application. The application will print the
	 * current game state and ask the user for his move. If the user enters
	 * "exit", the application ends. Otherwise the user input is interpreted as
	 * a move and the application tries to execute that move.
	 */
	public void run() 
	{

		// prepare for reading input
		//
		String input = "";
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

		while (true) 
		{

			// print game state and ask for user input
			//
			this.printCurrentGameState();
			System.out.println("your move (format: e2-e3): ");
			try 
			{
				// read user input
				input = inputReader.readLine();

				// exit, if user types 'exit'
				if (input.equalsIgnoreCase("exit"))
				{
					return;
				}
				else
				{
					this.handleMove(input);
				}

				// exit if game end condition has been reached
				if( this.chessGame.getGameState() == Game_State.GAME_STATE_END )
				{
					this.printCurrentGameState();
					System.out.println("game end reached! you won!");
					return;
				}

			} 
			catch (Exception e) 
			{
				System.out.println(e.getClass() + ": " + e.getMessage());
			}
		}
	}

	/**
	 * Move piece to the specified location.
	 *
	 * @param input - a valid move-string (e.g. "e7-e6")
	 */
	private void handleMove(String input) 
	{
		String strSourceColumn = input.substring(0, 1);
		String strSourceRow = input.substring(1, 2);
		String strTargetColumn = input.substring(3, 4);
		String strTargetRow = input.substring(4, 5);

		int sourceColumn = 0;
		int sourceRow = 0;
		int targetColumn = 0;
		int targetRow = 0;

		sourceColumn = convertColumnStrToColumnInt(strSourceColumn);
		sourceRow = convertRowStrToRowInt(strSourceRow);
		targetColumn = convertColumnStrToColumnInt(strTargetColumn);
		targetRow = convertRowStrToRowInt(strTargetRow);

		chessGame.movePiece( new Move(sourceRow, sourceColumn, targetRow, targetColumn));
	}

	/**
	 * Converts a column string (e.g. 'a') into its internal representation.
	 *
	 * @param strColumn a valid column string (e.g. 'a')
	 * @return internal integer representation of the column
	 */
	private int convertColumnStrToColumnInt(String strColumn) 
	{
		if (strColumn.equalsIgnoreCase("a")) 
		{
			return typeOf_Piece.COLUMN_A;
		} 
		else if (strColumn.equalsIgnoreCase("b")) 
		{
			return typeOf_Piece.COLUMN_B;
		} 
		else if (strColumn.equalsIgnoreCase("c")) 
		{
			return typeOf_Piece.COLUMN_C;
		} 
		else if (strColumn.equalsIgnoreCase("d")) 
		{
			return typeOf_Piece.COLUMN_D;
		} 
		else if (strColumn.equalsIgnoreCase("e")) 
		{
			return typeOf_Piece.COLUMN_E;
		} 
		else if (strColumn.equalsIgnoreCase("f")) 
		{
			return typeOf_Piece.COLUMN_F;
		} 
		else if (strColumn.equalsIgnoreCase("g")) 
		{
			return typeOf_Piece.COLUMN_G;
		} 
		else if (strColumn.equalsIgnoreCase("h")) 
		{
			return typeOf_Piece.COLUMN_H;
		} 
		else if (strColumn.equalsIgnoreCase("i")) 
		{
			return typeOf_Piece.COLUMN_I;
		} 
		else if (strColumn.equalsIgnoreCase("j")) 
		{
			return typeOf_Piece.COLUMN_J;
		} 
		else if (strColumn.equalsIgnoreCase("k")) 
		{
			return typeOf_Piece.COLUMN_K;
		} 
		else
			throw new IllegalArgumentException("invalid column: " + strColumn);
	}

	/**
	 * Converts a row string (e.g. '1') into its internal representation.
	 *
	 * @param strRow a valid row string (e.g. '1')
	 * @return internal integer representation of the row
	 */
	private int convertRowStrToRowInt(String strRow) 
	{
		if (strRow.equalsIgnoreCase("1")) 
		{
			return typeOf_Piece.ROW_1;
		} 
		else if (strRow.equalsIgnoreCase("2")) 
		{
			return typeOf_Piece.ROW_2;
		} 
		else if (strRow.equalsIgnoreCase("3")) 
		{
			return typeOf_Piece.ROW_3;
		} 
		else if (strRow.equalsIgnoreCase("4")) 
		{
			return typeOf_Piece.ROW_4;
		} 
		else if (strRow.equalsIgnoreCase("5")) 
		{
			return typeOf_Piece.ROW_5;
		} 
		else if (strRow.equalsIgnoreCase("6")) 
		{
			return typeOf_Piece.ROW_6;
		} 
		else if (strRow.equalsIgnoreCase("7")) 
		{
			return typeOf_Piece.ROW_7;
		} 
		else if (strRow.equalsIgnoreCase("8")) 
		{
			return typeOf_Piece.ROW_8;
		} 
		else if (strRow.equalsIgnoreCase("9")) 
		{
			return typeOf_Piece.ROW_9;
		} 
		else if (strRow.equalsIgnoreCase("10")) 
		{
			return typeOf_Piece.ROW_10;
		} 
		else if (strRow.equalsIgnoreCase("11")) 
		{
			return typeOf_Piece.ROW_11;
		} 
		else
			throw new IllegalArgumentException("invalid column: " + strRow);
	}

	/**
	 * Print current game board and game state information.
	 */
	private void printCurrentGameState() 
	{
		System.out.println("  a  b  c  d  e  f  g  h  ");
		for (int row = typeOf_Piece.ROW_11; row >= typeOf_Piece.ROW_1; row--) 
		{
			System.out.println(" +--+--+--+--+--+--+--+--+");
			String strRow = (row + 1) + "|";
			for (int column = typeOf_Piece.COLUMN_A; column <= typeOf_Piece.COLUMN_K; column++) 
			{
				typeOf_Piece piece = this.chessGame.getNonCapturedPieceAtLocation(row, column);
				String pieceStr = getNameOfPiece(piece);
				strRow += pieceStr + "|";
			}
			System.out.println(strRow + (row + 1));
		}
		System.out.println(" +--+--+--+--+--+--+--+--+");
		System.out.println("  a  b  c  d  e  f  g  h  ");

		String gameStateStr = "unknown";
		switch (chessGame.getGameState()) 
		{
			case Game_State.GAME_STATE_BLACK: gameStateStr="black";break;
			case Game_State.GAME_STATE_END: gameStateStr="end";break;
			case Game_State.GAME_STATE_WHITE: gameStateStr="white";break;
		}
		System.out.println("state: " + gameStateStr);

	}

	/**
	 * Returns the name of the specified piece. The name is based on color and
	 * type.
	 *
	 * The first letter represents the color: B=black, W=white, ?=unknown
	 *
	 * The second letter represents the type: B=Bishop, K=King, N=Knight,
	 * P=Pawn, Q=Queen, R=Rook, ?=unknown
	 *
	 * A two letter empty string is returned in case the specified piece is null
	 *
	 * @param piece a chess piece
	 * @return string representation of the piece or a two letter empty string
	 *         if the specified piece is null
	 */
	private String getNameOfPiece(typeOf_Piece piece) 
	{
		if (piece == null)
			return "  ";

		String strColor = "";
		switch (piece.getColor()) 
		{
			case typeOf_Piece.COLOR_BLACK:
				strColor = "B";
				break;
			case typeOf_Piece.COLOR_WHITE:
				strColor = "W";
				break;
			default:
				strColor = "?";
				break;
		}

		String strType = "";
		switch (piece.getType()) 
		{
			case typeOf_Piece.TYPE_BISHOP:
				strType = "B";
				break;
			case typeOf_Piece.TYPE_KING:
				strType = "K";
				break;
			case typeOf_Piece.TYPE_KNIGHT:
				strType = "N";
				break;
			case typeOf_Piece.TYPE_PAWN:
				strType = "P";
				break;
			case typeOf_Piece.TYPE_QUEEN:
				strType = "Q";
				break;
			case typeOf_Piece.TYPE_ROOK:
				strType = "R";
				break;
			default:
				strType = "?";
				break;
		}

		return strColor + strType;
	}

}
//end ConsoleGui.java