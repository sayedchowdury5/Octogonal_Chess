
import javax.swing.JOptionPane;


//start MoveValidator.java
public class MoveValidator 
{

	private Game_State chessGame;
	private typeOf_Piece sourcePiece;
	private typeOf_Piece targetPiece;

	public MoveValidator(Game_State chessGame)
	{
		this.chessGame = chessGame;
	}

	/**
	 * Checks if the specified move is valid
	 * @param move to validate
	 * @return true if move is valid, false if move is invalid
	 */
	public boolean isMoveValid(Move move) 
	{
		int sourceRow = move.sourceRow;
		int sourceColumn = move.sourceColumn;
		int targetRow = move.targetRow;
		int targetColumn = move.targetColumn;

		sourcePiece = chessGame.getNonCapturedPieceAtLocation(sourceRow, sourceColumn);
		targetPiece = this.chessGame.getNonCapturedPieceAtLocation(targetRow, targetColumn);

		// source piece does not exist
		if( sourcePiece == null )
		{
			 JOptionPane.showMessageDialog(null,"No source piece.");
			return false;
		}

		// source piece has right color?
		if( sourcePiece.getColor() == typeOf_Piece.COLOR_WHITE&& this.chessGame.getGameState() == Game_State.GAME_STATE_WHITE)
		{
			// ok
		}
		else if( sourcePiece.getColor() == typeOf_Piece.COLOR_BLACK&& this.chessGame.getGameState() == Game_State.GAME_STATE_BLACK)
		{
			// ok
		}
		else
		{
			System.out.println("Please wait for your turn.");
                       
			return false;
		}

		// check if target location within boundaries
		if( targetRow < typeOf_Piece.ROW_1 || targetRow > typeOf_Piece.ROW_11|| targetColumn < typeOf_Piece.COLUMN_A || targetColumn > typeOf_Piece.COLUMN_K)
		{
			System.out.println("Target row or column out of scope");
                         
			return false;
		}

		// validate piece movement rules
		boolean validPieceMove = false;
		switch (sourcePiece.getType()) 
		{
			case typeOf_Piece.TYPE_BISHOP:
				validPieceMove = isBishopMoveValid(sourceRow,sourceColumn,targetRow,targetColumn);break;
			case typeOf_Piece.TYPE_KING:
				validPieceMove = isKingMoveValid(sourceRow,sourceColumn,targetRow,targetColumn);break;
			case typeOf_Piece.TYPE_KNIGHT:
				validPieceMove = isKnightMoveValid(sourceRow,sourceColumn,targetRow,targetColumn);break;
			case typeOf_Piece.TYPE_PAWN:
				validPieceMove = isPawnMoveValid(sourceRow,sourceColumn,targetRow,targetColumn);break;
			case typeOf_Piece.TYPE_QUEEN:
				validPieceMove = isQueenMoveValid(sourceRow,sourceColumn,targetRow,targetColumn);break;
			case typeOf_Piece.TYPE_ROOK:
				validPieceMove = isRookMoveValid(sourceRow,sourceColumn,targetRow,targetColumn);break;
			default: break;
		}
		if( !validPieceMove)
		{
			return false;
		}
		else
		{
			// ok
		}


		// handle stalemate and checkmate
		// ..

		return true;
	}

	private boolean isTargetLocationCaptureable() 
	{
		if( targetPiece == null )
		{
			return false;
		}
		else if( targetPiece.getColor() != sourcePiece.getColor())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean isTargetLocationFree() 
	{
		return targetPiece == null;
	}

	private boolean isBishopMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn) 
	{
		//The bishop can move any number of squares diagonally, but may not leap
		//over other pieces.

		// target location possible?
		if( isTargetLocationFree() || isTargetLocationCaptureable())
		{
			//ok
		}
		else
		{   
                     System.out.println("Target location not free and not captureable");
                     return false;
		}

		boolean isValid = false;
		// first lets check if the path to the target is diagonally at all
		int diffRow = targetRow - sourceRow;
		int diffColumn = targetColumn - sourceColumn;

		if( diffRow==diffColumn && diffColumn > 0)
		{
			// moving diagonally up-right
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,+1,+1);

		}
		else if( diffRow==-diffColumn && diffColumn > 0)
		{
			// moving diagnoally down-right
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,-1,+1);

		}
		else if( diffRow==diffColumn && diffColumn < 0)
		{
			// moving diagnoally down-left
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,-1,-1);

		}
		else if( diffRow==-diffColumn && diffColumn < 0)
		{
			// moving diagnoally up-left
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,+1,-1);

		}
		else
		{
			// not moving diagonally
			System.out.println(diffRow);
			System.out.println(diffColumn);
			System.out.println("not moving diagonally");
			isValid = false;
		}
		return isValid;
	}

	private boolean isQueenMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn) 
	{
		// The queen combines the power of the rook and bishop and can move any number
		// of squares along rank, file, or diagonal, but it may not leap over other pieces.
		//
		boolean result = isBishopMoveValid(sourceRow, sourceColumn, targetRow, targetColumn);
		result |= isRookMoveValid(sourceRow, sourceColumn, targetRow, targetColumn);
		return result;
	}

	private boolean isPawnMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn) 
	{

		boolean isValid = false;
		// The pawn may move forward to the unoccupied square immediately in front
		// of it on the same file, or on its first move it may advance two squares
		// along the same file provided both squares are unoccupied
		if( isTargetLocationFree() )
		{
			if( sourceColumn == targetColumn)
			{
				// same column
				if(  sourcePiece.getColor() == typeOf_Piece.COLOR_WHITE )
				{
					// white
					if( sourceRow+1 == targetRow )
					{
						// move one up
						isValid = true;
					}
					else
					{
						System.out.println("not moving one up");
						isValid = false;
					}
				}
				else
				{
					// black
					if( sourceRow-1 == targetRow )
					{
						// move one down
						isValid = true;
					}
					else
					{
						System.out.println("not moving one down");
						isValid =  false;
					}
				}
			}
			else
			{
				// not the same column
				System.out.println("not staying in same column");
				isValid = false;
			}

		// or it may move
		// to a square occupied by an opponent�s piece, which is diagonally in front
		// of it on an adjacent file, capturing that piece.
		}
		else if( isTargetLocationCaptureable() )
		{
			if( sourceColumn+1 == targetColumn || sourceColumn-1 == targetColumn)
			{
				// one column to the right or left
				if(  sourcePiece.getColor() == typeOf_Piece.COLOR_WHITE )
				{
					// white
					if( sourceRow+1 == targetRow )
					{
						// move one up
						isValid = true;
					}
					else
					{
						System.out.println("not moving one up");
						isValid = false;
					}
				}
				else
				{
					// black
					if( sourceRow-1 == targetRow )
					{
						// move one down
						isValid = true;
					}
					else
					{
						System.out.println("not moving one down");
						isValid = false;
					}
				}
			}
			else
			{
				// note one column to the left or right
				System.out.println("not moving one column to left or right");
				isValid = false;
			}
		}

		// on its first move it may advance two squares
		// ..

		// The pawn has two special
		// moves, the en passant capture, and pawn promotion.

		// en passant
		// ..
		return isValid;
	}

	private boolean isKnightMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn) 
	{
		// The knight moves to any of the closest squares which are not on the same rank,
		// file or diagonal, thus the move forms an "L"-shape two squares long and one
		// square wide. The knight is the only piece which can leap over other pieces.

		// target location possible?
		if( isTargetLocationFree() || isTargetLocationCaptureable())
		{
			//ok
		}
		else
		{
			System.out.println("target location not free and not captureable");
			return false;
		}

		if( sourceRow+2 == targetRow && sourceColumn+1 == targetColumn)
		{
			// move up up right
			return true;
		}
		else if( sourceRow+1 == targetRow && sourceColumn+2 == targetColumn)
		{
			// move up right right
			return true;
		}
		else if( sourceRow-1 == targetRow && sourceColumn+2 == targetColumn)
		{
			// move down right right
			return true;
		}
		else if( sourceRow-2 == targetRow && sourceColumn+1 == targetColumn)
		{
			// move down down right
			return true;
		}
		else if( sourceRow-2 == targetRow && sourceColumn-1 == targetColumn)
		{
			// move down down left
			return true;
		}
		else if( sourceRow-1 == targetRow && sourceColumn-2 == targetColumn)
		{
			// move down left left
			return true;
		}
		else if( sourceRow+1 == targetRow && sourceColumn-2 == targetColumn)
		{
			// move up left left
			return true;
		}
		else if( sourceRow+2 == targetRow && sourceColumn-1 == targetColumn)
		{
			// move up up left
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean isKingMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn) 
	{

		// target location possible?
		if( isTargetLocationFree() || isTargetLocationCaptureable())
		{
			//ok
		}
		else
		{
			System.out.println("target location not free and not captureable");
			return false;
		}

		// The king moves one square in any direction, the king has also a special move which is
		// called castling and also involves a rook.
		boolean isValid = true;
		if( sourceRow+1 == targetRow && sourceColumn == targetColumn)
		{
			//up
			isValid = true;
		}
		else if( sourceRow+1 == targetRow && sourceColumn+1 == targetColumn)
		{
			//up right
			isValid = true;
		}
		else if( sourceRow == targetRow && sourceColumn+1 == targetColumn)
		{
			//right
			isValid = true;
		}
		else if( sourceRow-1 == targetRow && sourceColumn+1 == targetColumn)
		{
			//down right
			isValid = true;
		}
		else if( sourceRow-1 == targetRow && sourceColumn == targetColumn)
		{
			//down
			isValid = true;
		}
		else if( sourceRow-1 == targetRow && sourceColumn-1 == targetColumn)
		{
			//down left
			isValid = true;
		}
		else if( sourceRow == targetRow && sourceColumn-1 == targetColumn)
		{
			//left
			isValid = true;
		}
		else if( sourceRow+1 == targetRow && sourceColumn-1 == targetColumn)
		{
			//up left
			isValid = true;
		}
		else
		{
			System.out.println("moving too far");
			isValid = false;
		}

		// castling
		// ..

		return isValid;
	}

	private boolean isRookMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn) 
	{
		// The rook can move any number of squares along any rank or file, but
		// may not leap over other pieces. Along with the king, the rook is also
		// involved during the king's castling move.
		if( isTargetLocationFree() || isTargetLocationCaptureable())
		{
			//ok
		}
		else
		{
			System.out.println("target location not free and not captureable");
			return false;
		}

		boolean isValid = false;

		// first lets check if the path to the target is straight at all
		int diffRow = targetRow - sourceRow;
		int diffColumn = targetColumn - sourceColumn;

		if( diffRow == 0 && diffColumn > 0)
		{
			// right
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,0,+1);
		}
		else if( diffRow == 0 && diffColumn < 0)
		{
			// left
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,0,-1);
		}
		else if( diffRow > 0 && diffColumn == 0)
		{
			// up
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,+1,0);
		}
		else if( diffRow < 0 && diffColumn == 0)
		{
			// down
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow,sourceColumn,targetRow,targetColumn,-1,0);
		}
		else
		{
			// not moving diagonally
			System.out.println("not moving straight");
			isValid = false;
		}

		return isValid;
	}


	private boolean arePiecesBetweenSourceAndTarget(int sourceRow, int sourceColumn,int targetRow, int targetColumn, int rowIncrementPerStep, int columnIncrementPerStep) 
	{

		int currentRow = sourceRow + rowIncrementPerStep;
		int currentColumn = sourceColumn + columnIncrementPerStep;
		while(true)
		{
			if(currentRow == targetRow && currentColumn == targetColumn)
			{
				break;
			}
			if( currentRow < typeOf_Piece.ROW_1 || currentRow > typeOf_Piece.ROW_11|| currentColumn < typeOf_Piece.COLUMN_A || currentColumn > typeOf_Piece.COLUMN_K)
			{
				break;
			}

			if( this.chessGame.isNonCapturedPieceAtLocation(currentRow, currentColumn))
			{
				System.out.println("pieces in between source and target");
				return true;
			}

			currentRow += rowIncrementPerStep;
			currentColumn += columnIncrementPerStep;
		}
		return false;
	}

	public  void main(String[] args) 
	{
		Game_State piecesMovement = new Game_State();
		MoveValidator validMove = new MoveValidator(piecesMovement);
		Move move = null;
		boolean isValid;

		int sourceRow; int sourceColumn; int targetRow; int targetColumn;
		int testCounter = 1;

		// ok
		sourceRow = typeOf_Piece.ROW_2; sourceColumn = typeOf_Piece.COLUMN_D;
		targetRow = typeOf_Piece.ROW_3; targetColumn = typeOf_Piece.COLUMN_D;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = validMove.isMoveValid( move );
		piecesMovement.movePiece(move);
		System.out.println(testCounter+". test result: "+(true==isValid));
		testCounter++;

		// it's not white's turn
		sourceRow = typeOf_Piece.ROW_2; sourceColumn = typeOf_Piece.COLUMN_B;
		targetRow = typeOf_Piece.ROW_3; targetColumn = typeOf_Piece.COLUMN_B;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = validMove.isMoveValid( move );
		System.out.println(testCounter+". test result: "+(false==isValid));
		testCounter++;

		// ok
		sourceRow = typeOf_Piece.ROW_7; sourceColumn = typeOf_Piece.COLUMN_E;
		targetRow = typeOf_Piece.ROW_6; targetColumn = typeOf_Piece.COLUMN_E;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = validMove.isMoveValid( move );
		piecesMovement.movePiece( move );
		System.out.println(testCounter+". test result: "+(true==isValid));
		testCounter++;

		// pieces in the way
		sourceRow = typeOf_Piece.ROW_1; sourceColumn = typeOf_Piece.COLUMN_F;
		targetRow = typeOf_Piece.ROW_4; targetColumn = typeOf_Piece.COLUMN_C;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = validMove.isMoveValid(move);
		System.out.println(testCounter+". test result: "+(false==isValid));
		testCounter++;

		// ok
		sourceRow = typeOf_Piece.ROW_1; sourceColumn = typeOf_Piece.COLUMN_C;
		targetRow = typeOf_Piece.ROW_4; targetColumn = typeOf_Piece.COLUMN_F;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = validMove.isMoveValid(move);
		piecesMovement.movePiece(move);
		System.out.println(testCounter+". test result: "+(true==isValid));
		testCounter++;

		// ok
		sourceRow = typeOf_Piece.ROW_8; sourceColumn = typeOf_Piece.COLUMN_B;
		targetRow = typeOf_Piece.ROW_6; targetColumn = typeOf_Piece.COLUMN_C;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = validMove.isMoveValid( move );
		piecesMovement.movePiece(move);
		System.out.println(testCounter+". test result: "+(true==isValid));
		testCounter++;

		// invalid knight move
		sourceRow = typeOf_Piece.ROW_1; sourceColumn = typeOf_Piece.COLUMN_G;
		targetRow = typeOf_Piece.ROW_3; targetColumn = typeOf_Piece.COLUMN_G;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = validMove.isMoveValid( move);
		System.out.println(testCounter+". test result: "+(false==isValid));
		testCounter++;

		// invalid knight move
		sourceRow = typeOf_Piece.ROW_1; sourceColumn = typeOf_Piece.COLUMN_G;
		targetRow = typeOf_Piece.ROW_2; targetColumn = typeOf_Piece.COLUMN_E;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = validMove.isMoveValid(move);
		System.out.println(testCounter+". test result: "+(false==isValid));
		testCounter++;

		// ok
		sourceRow = typeOf_Piece.ROW_1; sourceColumn = typeOf_Piece.COLUMN_G;
		targetRow = typeOf_Piece.ROW_3; targetColumn = typeOf_Piece.COLUMN_H;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = validMove.isMoveValid(move);
		piecesMovement.movePiece(move);
		System.out.println(testCounter+". test result: "+(true==isValid));
		testCounter++;

		// pieces in between
		sourceRow = typeOf_Piece.ROW_8; sourceColumn = typeOf_Piece.COLUMN_A;
		targetRow = typeOf_Piece.ROW_5; targetColumn = typeOf_Piece.COLUMN_A;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = validMove.isMoveValid(move);
		piecesMovement.movePiece(move);
		System.out.println(testCounter+". test result: "+(false==isValid));
		testCounter++;

		//ConsoleGui.printCurrentGameState(piecesMovement);

	}

}
//end MoveValidator.java
