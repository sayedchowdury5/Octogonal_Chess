
import java.awt.Image;


//Start Piece_pic.java
public class Piece_pic 
{

	private Image img;
	private int x;
	private int y;
	private typeOf_Piece piece;

	public Piece_pic(Image img, typeOf_Piece piece) 
	{
		this.img = img;
		this.piece = piece;

		this.resetToUnderlyingPiecePosition();
	}

	public Image getImage() 
	{
		return img;
	}

	public int getX() 
	{
		return x;
	}

	public int getY() 
	{
		return y;
	}

	public void setX(int x) 
	{
		this.x = x;
	}

	public void setY(int y) 
	{
		this.y = y;
	}

	public int getWidth() 
	{
		return img.getHeight(null);
	}

	public int getHeight() 
	{
		return img.getHeight(null);
	}

	public int getColor() 
	{
		return this.piece.getColor();
	}

	@Override
	public String toString() 
	{
		return this.piece+" "+x+"/"+y;
	}

	/**
	 * move the gui piece back to the coordinates that
	 * correspond with the underlying piece's row and column
	 */
	public void resetToUnderlyingPiecePosition() 
	{
		this.x = ChessInterface.convertColumnToX(piece.getColumn());
		this.y = ChessInterface.convertRowToY(piece.getRow());
	}

	public typeOf_Piece getPiece() 
	{
		return piece;
	}

	public boolean isCaptured() 
	{
		return this.piece.isCaptured();
	}

}
//end Piece_pic.java